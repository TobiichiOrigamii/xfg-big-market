package com.origamii.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import com.alibaba.fastjson.JSON;
import com.origamii.domain.credit.model.aggreate.TradeAggregate;
import com.origamii.domain.credit.model.entity.CreditAccountEntity;
import com.origamii.domain.credit.model.entity.CreditOrderEntity;
import com.origamii.domain.credit.model.entity.TaskEntity;
import com.origamii.domain.credit.repository.ICreditRepository;
import com.origamii.infrastructure.event.EventPublisher;
import com.origamii.infrastructure.persistent.dao.ITaskDao;
import com.origamii.infrastructure.persistent.dao.IUserCreditAccountDao;
import com.origamii.infrastructure.persistent.dao.IUserCreditOrderDao;
import com.origamii.infrastructure.persistent.po.Task;
import com.origamii.infrastructure.persistent.po.UserCreditAccount;
import com.origamii.infrastructure.persistent.po.UserCreditOrder;
import com.origamii.infrastructure.persistent.redis.IRedisService;
import com.origamii.types.common.Constants;
import com.origamii.types.enums.ResponseCode;
import com.origamii.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @author Origami
 * @description 积分仓储接口实现
 * @create 2024-10-27 21:32
 **/
@Slf4j
@Repository
public class CreditRepository implements ICreditRepository {

    @Autowired
    private IDBRouterStrategy dbRouter;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private IRedisService redisService;
    @Autowired
    private IUserCreditAccountDao userCreditAccountDao;
    @Autowired
    private IUserCreditOrderDao userCreditOrderDao;
    @Autowired
    private ITaskDao taskDao;
    @Autowired
    private EventPublisher eventPublisher;


    /**
     * 保存交易聚合对象
     *
     * @param tradeAggregate 交易聚合对象
     */
    @Override
    public void saveUserCreditTradeOrder(TradeAggregate tradeAggregate) {
        String userId = tradeAggregate.getUserId();
        CreditAccountEntity creditAccountEntity = tradeAggregate.getCreditAccountEntity();
        CreditOrderEntity creditOrderEntity = tradeAggregate.getCreditOrderEntity();
        TaskEntity taskEntity = tradeAggregate.getTaskEntity();

        // 积分账户
        UserCreditAccount userCreditAccountReq = UserCreditAccount.builder()
                .userId(userId)
                .totalAmount(creditAccountEntity.getAdjustAmount())
                .availableAmount(creditAccountEntity.getAdjustAmount())
                .build();
        // 积分订单
        UserCreditOrder userCreditOrderReq = UserCreditOrder.builder()
                .userId(creditOrderEntity.getUserId())
                .orderId(creditOrderEntity.getOrderId())
                .tradeName(creditOrderEntity.getTradeName().getName())
                .tradeType(creditOrderEntity.getTradeType().getCode())
                .tradeAmount(creditOrderEntity.getTradeAmount())
                .outBusinessNo(creditOrderEntity.getOutBusinessNo())
                .build();

        Task task = Task.builder()
                .userId(taskEntity.getUserId())
                .topic(taskEntity.getTopic())
                .messageId(taskEntity.getMessageId())
                .message(JSON.toJSONString(taskEntity.getMessage()))
                .state(taskEntity.getState().getCode())
                .build();

        // Redis锁
        RLock lock = redisService.getLock(Constants.RedisKey.USER_CREDIT_ACCOUNT_LOCK + userId + Constants.UNDERLINE + creditOrderEntity.getOutBusinessNo());
        try {
            lock.lock(3, TimeUnit.SECONDS);
            dbRouter.doRouter(userId);
            // 编程式事务
            transactionTemplate.execute(status -> {
                try {
                    // 1. 保存账户积分
                    UserCreditAccount userCreditAccount = userCreditAccountDao.queryUserCreditAccount(userCreditAccountReq);
                    if (null == userCreditAccount) {
                        userCreditAccountDao.insert(userCreditAccountReq);
                    } else {
                        BigDecimal availableAmount = userCreditAccountReq.getAvailableAmount();
                        if (availableAmount.compareTo(BigDecimal.ZERO) >= 0) {
                            userCreditAccountDao.updateAddAmount(userCreditAccountReq);
                        } else {
                            int subtractionCount = userCreditAccountDao.updateSubtractionAmount(userCreditAccountReq);
                            if (1 != subtractionCount) {
                                status.setRollbackOnly();
                                throw new AppException(ResponseCode.USER_CREDIT_ACCOUNT_NO_AVAILABLE_AMOUNT.getCode(), ResponseCode.USER_CREDIT_ACCOUNT_NO_AVAILABLE_AMOUNT.getInfo());
                            }
                        }
                    }
                    // 2. 保存账户订单
                    userCreditOrderDao.insert(userCreditOrderReq);
                    // 3. 写入任务
                    taskDao.insert(task);
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("调整账户积分额度异常，唯一索引冲突 userId:{} orderId:{}", userId, creditOrderEntity.getOrderId(), e);
                } catch (Exception e) {
                    status.setRollbackOnly();
                    log.error("调整账户积分额度失败 userId:{} orderId:{}", userId, creditOrderEntity.getOrderId(), e);
                }
                return 1;
            });
        } finally {
            dbRouter.clear();
            lock.unlock();
        }


        try {
            // 发送消息【在事务外执行，如果失败还有任务补偿】
            eventPublisher.publish(task.getTopic(), task.getMessage());
            // 更新数据库记录，task 任务表
            taskDao.updateTaskSendMessageCompleted(task);
            log.info("调整账户积分记录，发送MQ消息完成 userId: {} orderId:{} topic: {}", userId, creditOrderEntity.getOrderId(), task.getTopic());
        } catch (Exception e) {
            log.error("调整账户积分记录，发送MQ消息失败 userId: {} topic: {}", userId, task.getTopic());
            taskDao.updateTaskSendMessageFail(task);
        }

    }

    /**
     * 查询用户积分账户
     *
     * @param userId 用户id
     * @return 用户积分账户
     */
    @Override
    public CreditAccountEntity queryUserCreditAccount(String userId) {
        UserCreditAccount userCreditAccountReq = new UserCreditAccount();
        userCreditAccountReq.setUserId(userId);
        try {
            dbRouter.doRouter(userId);
            UserCreditAccount userCreditAccount = userCreditAccountDao.queryUserCreditAccount(userCreditAccountReq);
            BigDecimal availableAmount = userCreditAccount.getAvailableAmount();
            if (null != availableAmount)
                availableAmount = userCreditAccount.getAvailableAmount();
            return CreditAccountEntity.builder()
                    .userId(userId)
                    .adjustAmount(availableAmount)
                    .build();
        } finally {
            dbRouter.clear();
        }
    }

}

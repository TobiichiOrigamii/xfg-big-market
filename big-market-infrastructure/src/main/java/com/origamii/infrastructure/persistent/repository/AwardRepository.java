package com.origamii.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import com.alibaba.fastjson.JSON;
import com.origamii.domain.award.model.aggreate.GiveOutPrizesAggregate;
import com.origamii.domain.award.model.aggreate.UserAwardRecordAggregate;
import com.origamii.domain.award.model.entity.TaskEntity;
import com.origamii.domain.award.model.entity.UserAwardRecordEntity;
import com.origamii.domain.award.model.entity.UserCreditAwardEntity;
import com.origamii.domain.award.model.valobj.AccountStatusVO;
import com.origamii.domain.award.repository.IAwardRepository;
import com.origamii.infrastructure.event.EventPublisher;
import com.origamii.infrastructure.persistent.dao.*;
import com.origamii.infrastructure.persistent.po.Task;
import com.origamii.infrastructure.persistent.po.UserAwardRecord;
import com.origamii.infrastructure.persistent.po.UserCreditAccount;
import com.origamii.infrastructure.persistent.po.UserRaffleOrder;
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

import java.util.concurrent.TimeUnit;

/**
 * @author Origami
 * @description 奖品服务实现类
 * @create 2024-10-14 21:06
 **/
@Slf4j
@Repository
public class AwardRepository implements IAwardRepository {

    @Autowired
    private ITaskDao taskDao;
    @Autowired
    private IDBRouterStrategy dbRouter;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private EventPublisher eventPublisher;
    @Autowired
    private IRedisService redisService;
    @Autowired
    private IUserAwardRecordDao userAwardRecordDao;
    @Autowired
    private IUserRaffleOrderDao userRaffleOrderDao;
    @Autowired
    private IAwardDao awardDao;
    @Autowired
    private IUserCreditAccountDao userCreditAccountDao;


    /**
     * 保存用户奖励记录
     *
     * @param userAwardRecordAggregate 用户奖励记录聚合
     */
    @Override
    public void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate) {
        UserAwardRecordEntity userAwardRecordEntity = userAwardRecordAggregate.getUserAwardRecordEntity();
        TaskEntity taskEntity = userAwardRecordAggregate.getTaskEntity();
        String userId = userAwardRecordEntity.getUserId();
        Long activityId = userAwardRecordEntity.getActivityId();
        Integer awardId = userAwardRecordEntity.getAwardId();

        // 转换为PO对象 - 用户奖励记录
        UserAwardRecord userAwardRecord = new UserAwardRecord();
        userAwardRecord.setUserId(userAwardRecordEntity.getUserId());
        userAwardRecord.setActivityId(userAwardRecordEntity.getActivityId());
        userAwardRecord.setStrategyId(userAwardRecordEntity.getStrategyId());
        userAwardRecord.setOrderId(userAwardRecordEntity.getOrderId());
        userAwardRecord.setAwardId(userAwardRecordEntity.getAwardId());
        userAwardRecord.setAwardTitle(userAwardRecordEntity.getAwardTitle());
        userAwardRecord.setAwardTime(userAwardRecordEntity.getAwardTime());
        userAwardRecord.setAwardState(userAwardRecordEntity.getAwardState().getCode());

        // 转换为PO对象 - 任务
        Task task = new Task();
        task.setUserId(taskEntity.getUserId());
        task.setTopic(taskEntity.getTopic());
        task.setMessageId(taskEntity.getMessageId());
        task.setMessage(JSON.toJSONString(taskEntity.getMessage()));
        task.setState(taskEntity.getState().getCode());

        // 转换为PO对象 - 订单
        UserRaffleOrder userRaffleOrderReq = new UserRaffleOrder();
        userRaffleOrderReq.setUserId(userAwardRecordEntity.getUserId());
        userRaffleOrderReq.setOrderId(userAwardRecordEntity.getOrderId());

        try {
            dbRouter.doRouter(userId);
            transactionTemplate.execute(status -> {
                try {
                    // 写入记录
                    userAwardRecordDao.insert(userAwardRecord);
                    // 写入任务
                    taskDao.insert(task);
                    // 写入订单
                    int count = userRaffleOrderDao.updateUserRaffleOrderStateUsed(userRaffleOrderReq);
                    if (count != 1) {
                        status.setRollbackOnly();
                        log.error("写入中奖记录，用户抽奖单已使用过，不可重复抽奖 userId:{},activityId:{},awardId:{}", userId, activityId, awardId);
                        throw new AppException(ResponseCode.ACTIVITY_ORDER_ERROR.getCode(), ResponseCode.ACTIVITY_ORDER_ERROR.getInfo());
                    }
                    return 1;
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("写入中奖记录，唯一索引冲突 userId:{},activityId:{},awardId:{}", userId, activityId, awardId, e);
                    throw new AppException(ResponseCode.INDEX_DUP.getCode(), e);
                }
            });
        } finally {
            dbRouter.clear();
        }

        try {
            // 发送消息【在事务外执行 如果失败还有任务补偿机制】
            eventPublisher.publish(task.getTopic(), task.getMessage());
            // 更新数据库记录 task任务表
            taskDao.updateTaskSendMessageCompleted(task);
        } catch (Exception e) {
            log.error("写入中奖记录，发送MQ消息失败 userId:{} topic:{}", userId, task.getTopic());
            taskDao.updateTaskSendMessageFail(task);
        }
    }

    /**
     * 查询奖品配置
     *
     * @param awardId 奖品id
     * @return 奖品配置
     */
    @Override
    public String queryAwardConfig(Integer awardId) {
        return awardDao.queryAwardConfig(awardId);
    }


    /**
     * 保存发放奖品记录
     *
     * @param giveOutPrizesAggregate 发放奖品记录聚合
     */
    @Override
    public void saveGiveOutPrizesAggregate(GiveOutPrizesAggregate giveOutPrizesAggregate) {
        String userId = giveOutPrizesAggregate.getUserId();
        UserCreditAwardEntity userCreditAwardEntity = giveOutPrizesAggregate.getUserCreditAwardEntity();
        UserAwardRecordEntity userAwardRecordEntity = giveOutPrizesAggregate.getUserAwardRecordEntity();

        // 更新发奖记录
        UserAwardRecord userAwardRecordReq = UserAwardRecord.builder()
                .userId(userId)
                .activityId(userAwardRecordEntity.getActivityId())
                .awardState(userAwardRecordEntity.getAwardState().getCode())
                .build();

        // 更新用户积分 - 首次则插入数据
        UserCreditAccount userCreditAccountReq = UserCreditAccount.builder()
                .userId(userCreditAwardEntity.getUserId())
                .totalAmount(userCreditAwardEntity.getCreditAmount())
                .availableAmount(userCreditAwardEntity.getCreditAmount())
                .accountStatus(AccountStatusVO.open.getCode())
                .build();
        RLock lock = redisService.getLock(Constants.RedisKey.ACTIVITY_ACCOUNT_LOCK + userId);
        try {
            lock.lock(3, TimeUnit.SECONDS);
            dbRouter.doRouter(giveOutPrizesAggregate.getUserId());
            transactionTemplate.execute(status -> {
                try {
                    // 更新积分 || 创建积分账户
                    UserCreditAccount userCreditAccountRes = userCreditAccountDao.queryUserCreditAccount(userCreditAccountReq);
                    if (null == userCreditAccountRes) {
                        userCreditAccountDao.insert(userCreditAccountReq);
                    } else {
                        userCreditAccountDao.updateAddAmount(userCreditAccountReq);
                    }

                    // 更新奖品记录
                    int updateAwardCount = userAwardRecordDao.updateAwardRecordCompletedState(userAwardRecordReq);
                    if (0 == updateAwardCount) {
                        log.warn("更新中奖记录，重复更新拦截 userId:{} giveOutPrizesAggregate:{}", userId, JSON.toJSONString(giveOutPrizesAggregate));
                        status.setRollbackOnly();
                    }
                    return 1;
                } catch (DuplicateKeyException e){
                    status.setRollbackOnly();
                    log.error("更新中奖记录，唯一索引冲突 userId: {} ", userId, e);
                    throw new AppException(ResponseCode.INDEX_DUP.getCode(), e);
                }
            });
        } finally {
            dbRouter.clear();
            if (lock.isHeldByCurrentThread()) { // 确保锁是当前线程持有的
                lock.unlock();
            }
        }
    }

    /**
     * 查询奖品key
     * @param awardId 奖品id
     * @return 奖品key
     */
    @Override
    public String queryAwardKey(Integer awardId) {
        return awardDao.queryAwardKey(awardId);
    }


}

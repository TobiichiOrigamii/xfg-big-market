package com.origamii.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import com.origamii.domain.credit.model.aggreate.TradeAggregate;
import com.origamii.domain.credit.model.entity.CreditAccountEntity;
import com.origamii.domain.credit.model.entity.CreditOrderEntity;
import com.origamii.domain.credit.repository.ICreditRepository;
import com.origamii.infrastructure.persistent.dao.IUserCreditAccountDao;
import com.origamii.infrastructure.persistent.dao.IUserCreditOrderDao;
import com.origamii.infrastructure.persistent.po.UserCreditAccount;
import com.origamii.infrastructure.persistent.po.UserCreditOrder;
import com.origamii.infrastructure.persistent.redis.IRedisService;
import com.origamii.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

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


    /**
     * 保存交易聚合对象
     * @param tradeAggregate 交易聚合对象
     */
    @Override
    public void saveUserCreditTradeOrder(TradeAggregate tradeAggregate) {
        String userId = tradeAggregate.getUserId();
        CreditAccountEntity creditAccountEntity = tradeAggregate.getCreditAccountEntity();
        CreditOrderEntity creditOrderEntity = tradeAggregate.getCreditOrderEntity();

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

        // Redis锁
        RLock lock = redisService.getLock(Constants.RedisKey.USER_CREDIT_ACCOUNT_LOCK + userId + Constants.UNDERLINE + creditOrderEntity.getOutBusinessNo());
        try {
            lock.lock(3, TimeUnit.SECONDS);
            dbRouter.doRouter(userId);
            // 事务
            transactionTemplate.execute(status -> {
                try {
                    // 1.保存积分账户
                    UserCreditAccount userCreditAccount = userCreditAccountDao.queryUserCreditAccount(userCreditAccountReq);
                    if (null == userCreditAccount) {
                        userCreditAccountDao.insert(userCreditAccountReq);
                    } else {
                        userCreditAccountDao.updateAddAmount(userCreditAccountReq);
                    }
                    // 2.保存积分订单
                    userCreditOrderDao.insert(userCreditOrderReq);
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
    }
}

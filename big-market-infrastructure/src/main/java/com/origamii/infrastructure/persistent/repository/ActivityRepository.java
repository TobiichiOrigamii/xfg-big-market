package com.origamii.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import com.origamii.domain.activity.model.aggreate.CreateOrderAggregate;
import com.origamii.domain.activity.model.entity.ActivityCountEntity;
import com.origamii.domain.activity.model.entity.ActivityEntity;
import com.origamii.domain.activity.model.entity.ActivityOrderEntity;
import com.origamii.domain.activity.model.entity.ActivitySkuEntity;
import com.origamii.domain.activity.model.valobj.ActivityStateVO;
import com.origamii.domain.activity.repository.IActivityRepository;
import com.origamii.infrastructure.persistent.dao.*;
import com.origamii.infrastructure.persistent.po.*;
import com.origamii.infrastructure.persistent.redis.IRedisService;
import com.origamii.types.common.Constants;
import com.origamii.types.enums.ResponseCode;
import com.origamii.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Origami
 * @description 活动仓储服务
 * @create 2024-09-27 16:10
 **/
@Repository
@Slf4j
public class ActivityRepository implements IActivityRepository {

    // redis缓存服务
    @Resource
    private IRedisService redisService;

    // 活动Dao
    @Resource
    private IRaffleActivityDao raffleActivityDao;

    // 活动订单Dao
    @Resource
    private IRaffleActivityOrderDao raffleActivityOrderDao;

    // 活动账户Dao
    @Resource
    private IRaffleActivityAccountDao raffleActivityAccountDao;

    // 活动SKUDao
    @Resource
    private IRaffleActivitySkuDao raffleActivitySkuDao;

    // 活动次数Dao
    @Resource
    private IRaffleActivityCountDao raffleActivityCountDao;

    // 事务模板
    @Resource
    private TransactionTemplate transactionTemplate;

    // 数据库路由策略
    @Resource
    private IDBRouterStrategy dbRouter;

    /**
     * 查询活动sku
     *
     * @param sku sku
     * @return 活动sku
     */
    @Override
    public ActivitySkuEntity queryActivitySku(Long sku) {
        RaffleActivitySku raffleActivitySku = raffleActivitySkuDao.queryActivitySku(sku);
        return ActivitySkuEntity.builder()
                .sku(raffleActivitySku.getSku())
                .activityId(raffleActivitySku.getActivityId())
                .activityCountId(raffleActivitySku.getActivityCountId())
                .stockCount(raffleActivitySku.getStockCount())
                .stockCountSurplus(raffleActivitySku.getStockCountSurplus())
                .build();
    }

    /**
     * 根据活动id查询活动
     *
     * @param activityId 活动id
     * @return 活动实体
     */
    @Override
    public ActivityEntity queryRaffleActivityByActivityId(Long activityId) {
        // 优先从缓存获取
        String cacheKey = Constants.RedisKey.ACTIVITY_KEY + activityId;
        ActivityEntity activityEntity = redisService.getValue(cacheKey);
        if (null != activityEntity)
            return activityEntity;
        // 从库中获取数据
        RaffleActivity raffleActivity = raffleActivityDao.queryRaffleActivityByActivityId(activityId);
        activityEntity = ActivityEntity.builder()
                .activityId(raffleActivity.getActivityId())
                .activityName(raffleActivity.getActivityName())
                .activityDesc(raffleActivity.getActivityDesc())
                .beginDateTime(raffleActivity.getBeginDateTime())
                .endDateTime(raffleActivity.getEndDateTime())
                .strategyId(raffleActivity.getStrategyId())
                .state(ActivityStateVO.valueOf(raffleActivity.getState()))
                .build();
        redisService.setValue(cacheKey, activityEntity);
        return activityEntity;
    }

    /**
     * 根据活动计数id查询活动计数
     *
     * @param activityCountId 活动计数id
     * @return 活动计数实体
     */
    @Override
    public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId) {
        // 优先从缓存获取
        String cacheKey = Constants.RedisKey.ACTIVITY_COUNT_KEY + activityCountId;
        ActivityCountEntity activityCountEntity = redisService.getValue(cacheKey);
        if (null != activityCountEntity)
            return activityCountEntity;

        // 从库中获取数据
        RaffleActivityCount raffleActivityCount = raffleActivityCountDao.queryRaffleActivityCountByActivityCountId(activityCountId);
        activityCountEntity = ActivityCountEntity.builder()
                .activityCountId(raffleActivityCount.getActivityCountId())
                .totalCount(raffleActivityCount.getTotalCount())
                .dayCount(raffleActivityCount.getDayCount())
                .monthCount(raffleActivityCount.getMonthCount())
                .build();
        redisService.setValue(cacheKey, activityCountEntity);
        return activityCountEntity;
    }

    /**
     * 保存订单
     *
     * @param createOrderAggregate 订单聚合实体
     */
    @Override
    public void saveOrder(CreateOrderAggregate createOrderAggregate) {
        // 订单对象
        ActivityOrderEntity activityOrderEntity = createOrderAggregate.getActivityOrderEntity();
        RaffleActivityOrder raffleActivityOrder = new RaffleActivityOrder();
        raffleActivityOrder.setUserId(activityOrderEntity.getUserId());
        raffleActivityOrder.setSku(activityOrderEntity.getSku());
        raffleActivityOrder.setActivityId(activityOrderEntity.getActivityId());
        raffleActivityOrder.setActivityName(activityOrderEntity.getActivityName());
        raffleActivityOrder.setStrategyId(activityOrderEntity.getStrategyId());
        raffleActivityOrder.setOrderId(activityOrderEntity.getOrderId());
        raffleActivityOrder.setOrderTime(activityOrderEntity.getOrderTime());
        raffleActivityOrder.setTotalCount(activityOrderEntity.getTotalCount());
        raffleActivityOrder.setDayCount(activityOrderEntity.getDayCount());
        raffleActivityOrder.setMonthCount(activityOrderEntity.getMonthCount());
        raffleActivityOrder.setState(activityOrderEntity.getState().getCode());
        raffleActivityOrder.setOutBusinessNo(activityOrderEntity.getOutBusinessNo());

        // 账户对象
        RaffleActivityAccount raffleActivityAccount = new RaffleActivityAccount();
        raffleActivityAccount.setUserId(createOrderAggregate.getUserId());
        raffleActivityAccount.setActivityId(createOrderAggregate.getActivityId());
        raffleActivityAccount.setTotalCount(createOrderAggregate.getTotalCount());
        raffleActivityAccount.setTotalCountSurplus(createOrderAggregate.getTotalCount());
        raffleActivityAccount.setDayCount(createOrderAggregate.getDayCount());
        raffleActivityAccount.setDayCountSurplus(createOrderAggregate.getDayCount());
        raffleActivityAccount.setMonthCount(createOrderAggregate.getMonthCount());
        raffleActivityAccount.setMonthCountSurplus(createOrderAggregate.getMonthCount());

        // try-catch 包裹，保证事务一致性
        try {
            // 保存订单 以用户ID作为分库键 通过doRouter设定路由
            dbRouter.doRouter(createOrderAggregate.getUserId());
            // 编程式事务
            transactionTemplate.execute(status -> {

                try {
                    // 1.写入订单
                    raffleActivityOrderDao.insert(raffleActivityOrder);
                    // 2.更新账户
                    int count = raffleActivityAccountDao.updateAccountQuota(raffleActivityAccount);
                    // 3.创建账户 - 更新为0 则账户不存在 创建新账户
                    if (count == 0)
                        // 账户不存在，插入
                        raffleActivityAccountDao.insert(raffleActivityAccount);
                    return 1;
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("写入订单记录，唯一索引冲突 userId:{} activityId:{} sku:{}",
                            activityOrderEntity.getUserId(),
                            activityOrderEntity.getActivityId(),
                            activityOrderEntity.getSku());
                    throw new AppException(ResponseCode.INDEX_DUP.getCode());
                }

            });
        } finally {
            // 清除路由缓存
            dbRouter.clear();
        }
    }

    /**
     * 查询活动sku库存
     *
     * @param cacheKey   缓存key
     * @param stockCount 库存数量
     */
    @Override
    public void cacheActivitySKuStockCount(String cacheKey, Integer stockCount) {
        if (redisService.isExists(cacheKey)) return;
        redisService.setAtomicLong(cacheKey, stockCount);
    }

    /**
     * 减少活动sku库存
     *
     * @param sku         活动SKU
     * @param cacheKey    缓存key
     * @param endDateTime 结束时间
     * @return 库存是否减少成功
     */
    @Override
    public boolean subtractionActivityStock(Long sku, String cacheKey, Date endDateTime) {
        long surplus = redisService.decr(cacheKey);
        if (surplus == 0) {
            // 库存不足 发送MQ消息 更新数据库库存
            // TODO
            return false;
        } else if (surplus < 0) {
            // 库存小于0 恢复为0个
            redisService.setAtomicLong(cacheKey, 0);
            return false;
        }

        // 1.按照cacheKey decr后的值，如99、98、97和Key组成为库存锁的Key进行使用
        // 2.加锁是为了兜底 如果后续有恢复库存 手动处理等【运营是人来操作 会有这种情况发放 系统要做好防护】 也不会超卖 因为所有可用库存Key都被加锁了
        // 3.设置加锁时间为活动到期+延迟1天 防止死锁
        String lockKey = cacheKey +Constants.UNDERLINE + surplus;
        long expireMillis = endDateTime.getTime() - System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1);
        boolean lock = redisService.setNx(lockKey, expireMillis, TimeUnit.MILLISECONDS);
        if (!lock){
            log.info("活动SKU库存加锁失败", lockKey);
        }

        return false;
    }


}

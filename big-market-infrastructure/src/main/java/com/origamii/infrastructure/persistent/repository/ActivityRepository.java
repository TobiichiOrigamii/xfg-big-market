package com.origamii.infrastructure.persistent.repository;

import com.origamii.domain.activity.model.entity.ActivityCountEntity;
import com.origamii.domain.activity.model.entity.ActivityEntity;
import com.origamii.domain.activity.model.entity.ActivitySkuEntity;
import com.origamii.domain.activity.model.valobj.ActivityStateVO;
import com.origamii.domain.activity.repository.IActivityRepository;
import com.origamii.infrastructure.persistent.dao.IRaffleActivityCountDao;
import com.origamii.infrastructure.persistent.dao.IRaffleActivityDao;
import com.origamii.infrastructure.persistent.dao.IRaffleActivitySkuDao;
import com.origamii.infrastructure.persistent.po.RaffleActivity;
import com.origamii.infrastructure.persistent.po.RaffleActivityCount;
import com.origamii.infrastructure.persistent.po.RaffleActivitySku;
import com.origamii.infrastructure.persistent.redis.IRedisService;
import com.origamii.types.common.Constants;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author Origami
 * @description 活动仓储服务
 * @create 2024-09-27 16:10
 **/
@Repository
public class ActivityRepository implements IActivityRepository {

    // redis缓存服务
    @Resource
    private IRedisService redisService;

    // 活动Dao
    @Resource
    private IRaffleActivityDao raffleActivityDao;

    // 活动SKUDao
    @Resource
    private IRaffleActivitySkuDao raffleActivitySkuDao;

    // 活动次数Dao
    @Resource
    private IRaffleActivityCountDao raffleActivityCountDao;

    /**
     * 查询活动sku
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



}

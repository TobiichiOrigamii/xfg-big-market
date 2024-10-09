package com.origamii.domain.activity.service.armory;

import com.origamii.domain.activity.model.entity.ActivitySkuEntity;
import com.origamii.domain.activity.repository.IActivityRepository;
import com.origamii.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Origami
 * @description 活动SKU预热
 * @create 2024-10-09 14:53
 **/
@Slf4j
@Service
public class ActivityArmory implements IActivityArmory, IActivityDispatch {

    @Autowired
    private IActivityRepository repository;


    @Override
    public boolean assembleActivitySku(Long sku) {
        ActivitySkuEntity activitySkuEntity = repository.queryActivitySku(sku);
        cacheActivitySkuStockCount(sku, activitySkuEntity.getStockCount());

        // 预热活动【查询时预热到缓存】
        repository.queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());

        // 预热活动次数【查询时预热到缓存】
        repository.queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());

        return false;
    }

    /**
     * 根据策略ID和奖品ID 扣减奖品缓存库存
     *
     * @param sku         活动SKU
     * @param endDateTime 活动结束时间 根据结束时间设置加锁的key为结束时间
     * @return 扣减结果
     */
    @Override
    public boolean subtractionActivityStock(Long sku, Date endDateTime) {
        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_STOCK_COUNT_KEY + sku;
        return repository.subtractionActivityStock(sku, cacheKey, endDateTime);
    }


    private void cacheActivitySkuStockCount(Long sku, Integer stockCount) {
        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_STOCK_COUNT_KEY + sku;
        repository.cacheActivitySKuStockCount(cacheKey, stockCount);

    }
}

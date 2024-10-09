package com.origamii.domain.activity.repository;

import com.origamii.domain.activity.model.aggreate.CreateOrderAggregate;
import com.origamii.domain.activity.model.entity.ActivityCountEntity;
import com.origamii.domain.activity.model.entity.ActivityEntity;
import com.origamii.domain.activity.model.entity.ActivitySkuEntity;

import java.util.Date;

/**
 * @author Origami
 * @description 活动仓储接口
 * @create 2024-09-27 15:58
 **/
public interface IActivityRepository {

    /**
     * 查询活动sku
     *
     * @param sku sku
     * @return 活动sku
     */
    ActivitySkuEntity queryActivitySku(Long sku);

    /**
     * 根据活动id查询活动
     *
     * @param activityId 活动id
     * @return 活动实体
     */
    ActivityEntity queryRaffleActivityByActivityId(Long activityId);

    /**
     * 根据活动计数id查询活动计数
     *
     * @param activityCountId 活动计数id
     * @return 活动计数实体
     */
    ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);

    /**
     * 保存订单
     *
     * @param createOrderAggregate 订单聚合实体
     */
    void saveOrder(CreateOrderAggregate createOrderAggregate);

    /**
     * 查询活动sku库存
     *
     * @param cacheKey   缓存key
     * @param stockCount 库存数量
     */
    void cacheActivitySKuStockCount(String cacheKey, Integer stockCount);

    /**
     * 减少活动sku库存
     *
     * @param sku         活动SKU
     * @param cacheKey    缓存key
     * @param endDateTime 结束时间
     * @return 库存是否减少成功
     */
    boolean subtractionActivityStock(Long sku, String cacheKey, Date endDateTime);

}

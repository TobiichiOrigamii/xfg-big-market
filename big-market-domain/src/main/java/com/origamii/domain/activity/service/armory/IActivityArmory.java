package com.origamii.domain.activity.service.armory;

/**
 * @author Origami
 * @description 活动装配预热
 * @create 2024-10-09 14:52
 **/
public interface IActivityArmory {

    /**
     * 装配活动SKU
     *
     * @param activityId 活动ID
     * @return 装配结果
     */
    boolean assembleActivitySkuByActivityId(Long activityId);

    /**
     * 装配活动SKU
     *
     * @param sku 活动SKU
     * @return 装配结果
     */
    boolean assembleActivitySku(Long sku);

}

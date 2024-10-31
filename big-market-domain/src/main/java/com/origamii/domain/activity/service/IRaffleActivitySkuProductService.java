package com.origamii.domain.activity.service;

import com.origamii.domain.activity.model.entity.SkuProductEntity;

import java.util.List;

/**
 * @author Origami
 * @description sku商品服务接口
 * @create 2024-10-31 21:28
 **/
public interface IRaffleActivitySkuProductService {

    /**
     * 根据活动id查询sku商品列表
     *
     * @param activityId 活动id
     * @return sku商品列表
     */
    List<SkuProductEntity> querySkuProductEntityListByActivityId(Long activityId);

}

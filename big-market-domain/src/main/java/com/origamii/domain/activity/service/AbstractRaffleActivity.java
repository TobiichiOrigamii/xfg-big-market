package com.origamii.domain.activity.service;

import com.alibaba.fastjson.JSON;
import com.origamii.domain.activity.model.entity.*;
import com.origamii.domain.activity.repository.IActivityRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Origami
 * @description 抽奖活动抽象类，定义标准的流程
 * @create 2024-09-27 15:55
 **/
@Slf4j
public abstract class AbstractRaffleActivity implements IRaffleOrder {

    protected IActivityRepository repository;

    public AbstractRaffleActivity(IActivityRepository activityRepository) {
        this.repository = activityRepository;
    }

    /**
     * 创建抽奖活动订单
     * @param activityShopCartEntity 活动sku实体 通过sku领取活动
     * @return 抽奖活动订单
     */
    @Override
    public ActivityOrderEntity createRaffleActivityOrder(ActivityShopCartEntity activityShopCartEntity) {
        // 1. 通过sku查询活动信息
        ActivitySkuEntity activitySkuEntity = repository.queryActivitySku(activityShopCartEntity.getSku());
        // 2. 查询活动信息
        ActivityEntity activityEntity = repository.queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        // 3. 查询次数信息（用户在活动上可参与的次数）
        ActivityCountEntity activityCountEntity = repository.queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());

        log.info("查询结果：{} {} {}", JSON.toJSONString(activitySkuEntity), JSON.toJSONString(activityEntity), JSON.toJSONString(activityCountEntity));

        return ActivityOrderEntity.builder().build();
    }



}

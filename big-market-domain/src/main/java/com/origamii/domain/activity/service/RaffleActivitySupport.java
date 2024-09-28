package com.origamii.domain.activity.service;

import com.origamii.domain.activity.model.entity.ActivityCountEntity;
import com.origamii.domain.activity.model.entity.ActivityEntity;
import com.origamii.domain.activity.model.entity.ActivitySkuEntity;
import com.origamii.domain.activity.repository.IActivityRepository;

/**
 * @author Origami
 * @description 抽奖活动的支撑类
 * @create 2024-09-28 10:42
 **/
public class RaffleActivitySupport {

    protected IActivityRepository repository;

    public RaffleActivitySupport(IActivityRepository repository) {
        this.repository = repository;
    }

    public ActivitySkuEntity queryActivitySku(Long sku){
        return repository.queryActivitySku(sku);
    }

    public ActivityEntity queryRaffleActivityByActivityId(Long activityId){
        return repository.queryRaffleActivityByActivityId(activityId);
    }

    public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId){
        return repository.queryRaffleActivityCountByActivityCountId(activityCountId);
    }


}

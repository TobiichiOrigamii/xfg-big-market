package com.origamii.domain.activity.service.quota;

import com.origamii.domain.activity.model.entity.ActivityCountEntity;
import com.origamii.domain.activity.model.entity.ActivityEntity;
import com.origamii.domain.activity.model.entity.ActivitySkuEntity;
import com.origamii.domain.activity.repository.IActivityRepository;
import com.origamii.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;

/**
 * @author Origami
 * @description 抽奖活动的支撑类
 * @create 2024-09-28 10:42
 **/
public class RaffleActivityAccountQuotaSupport {

    // 注入IActivityRepository 仓储接口
    protected IActivityRepository repository;
    // 注入DefaultActivityChainFactory 活动链工厂
    protected DefaultActivityChainFactory defaultActivityChainFactory;


    public RaffleActivityAccountQuotaSupport(IActivityRepository repository, DefaultActivityChainFactory defaultActivityChainFactory) {
        this.repository = repository;
        this.defaultActivityChainFactory = defaultActivityChainFactory;
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

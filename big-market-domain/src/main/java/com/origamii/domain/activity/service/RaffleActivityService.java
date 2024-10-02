package com.origamii.domain.activity.service;

import com.origamii.domain.activity.model.aggreate.CreateOrderAggregate;
import com.origamii.domain.activity.model.entity.ActivityCountEntity;
import com.origamii.domain.activity.model.entity.ActivityEntity;
import com.origamii.domain.activity.model.entity.ActivitySkuEntity;
import com.origamii.domain.activity.model.entity.SkuRechargeEntity;
import com.origamii.domain.activity.repository.IActivityRepository;
import com.origamii.domain.activity.service.rule.factory.DefaultActivityChainFactory;
import org.springframework.stereotype.Service;

/**
 * @author Origami
 * @description 抽奖活动服务
 * @create 2024-09-27 15:59
 **/
@Service
public class RaffleActivityService extends AbstractRaffleActivity{
    /**
     * 构造函数
     * @param repository 仓储层
     * @param defaultActivityChainFactory 活动链工厂
     */
    public RaffleActivityService(IActivityRepository repository, DefaultActivityChainFactory defaultActivityChainFactory) {
        super(repository, defaultActivityChainFactory);
    }





    @Override
    protected CreateOrderAggregate buildOrderAggregate(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity, SkuRechargeEntity skuRechargeEntity) {
        return null;
    }
}

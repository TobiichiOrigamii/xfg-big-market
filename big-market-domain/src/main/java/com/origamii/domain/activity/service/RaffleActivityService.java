package com.origamii.domain.activity.service;

import com.origamii.domain.activity.model.aggreate.CreateOrderAggregate;
import com.origamii.domain.activity.model.entity.*;
import com.origamii.domain.activity.model.valobj.OrderStateVO;
import com.origamii.domain.activity.repository.IActivityRepository;
import com.origamii.domain.activity.service.rule.factory.DefaultActivityChainFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    /**
     * 构建订单聚合对象
     * @param activitySkuEntity 活动sku实体
     * @param activityEntity 活动实体
     * @param activityCountEntity 次数实体
     * @param skuRechargeEntity sku充值实体
     * @return
     */
    @Override
    protected CreateOrderAggregate buildOrderAggregate(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity, SkuRechargeEntity skuRechargeEntity) {

        // 订单实体对象
        ActivityOrderEntity activityOrderEntity = new ActivityOrderEntity();
        activityOrderEntity.setUserId(skuRechargeEntity.getUserId());
        activityOrderEntity.setSku(skuRechargeEntity.getSku());
        activityOrderEntity.setActivityId(activityEntity.getActivityId());
        activityOrderEntity.setActivityName(activityEntity.getActivityName());
        activityOrderEntity.setStrategyId(activityEntity.getStrategyId());
        //公司里一般会有专门的雪花算法UUID服务，我们这里直接生成个12位就可以了。
        activityOrderEntity.setOrderId(RandomStringUtils.randomNumeric(12));
        activityOrderEntity.setOrderTime(new Date());
        activityOrderEntity.setTotalCount(activityCountEntity.getTotalCount());
        activityOrderEntity.setDayCount(activityCountEntity.getDayCount());
        activityOrderEntity.setMonthCount(activityCountEntity.getMonthCount());
        activityOrderEntity.setState(OrderStateVO.completed);
        activityOrderEntity.setOutBusinessNo(skuRechargeEntity.getOutBusinessNo());
        return CreateOrderAggregate.builder()
                .userId(skuRechargeEntity.getUserId())
                .activityId(activitySkuEntity.getActivityId())
                .totalCount(activityCountEntity.getTotalCount())
                .dayCount(activityCountEntity.getDayCount())
                .monthCount(activityCountEntity.getMonthCount())
                .activityOrderEntity(activityOrderEntity)
                .build();
    }

    /**
     * 保存订单
     * @param createOrderAggregate 订单聚合对象
     */
    @Override
    protected void saveOrder(CreateOrderAggregate createOrderAggregate) {
        repository.saveOrder(createOrderAggregate);
    }
}

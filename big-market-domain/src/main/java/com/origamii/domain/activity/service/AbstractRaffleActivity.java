package com.origamii.domain.activity.service;

import com.alibaba.fastjson.JSON;
import com.origamii.domain.activity.model.aggreate.CreateOrderAggregate;
import com.origamii.domain.activity.model.entity.*;
import com.origamii.domain.activity.repository.IActivityRepository;
import com.origamii.domain.activity.service.rule.IActionChain;
import com.origamii.domain.activity.service.rule.factory.DefaultActivityChainFactory;
import com.origamii.types.enums.ResponseCode;
import com.origamii.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Origami
 * @description 抽奖活动抽象类，定义标准的流程
 * @create 2024-09-27 15:55
 **/
@Slf4j
public abstract class AbstractRaffleActivity extends RaffleActivitySupport implements IRaffleOrder {

    public AbstractRaffleActivity(IActivityRepository repository, DefaultActivityChainFactory defaultActivityChainFactory) {
        super(repository, defaultActivityChainFactory);
    }

    /**
     * 创建抽奖活动订单
     *
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


    @Override
    public String createSkuRechargeOrder(SkuRechargeEntity skuRechargeEntity) {

        // 1.参数校验
        String userId = skuRechargeEntity.getUserId();
        Long sku = skuRechargeEntity.getSku();
        String outBusinessNo = skuRechargeEntity.getOutBusinessNo();
        if (null == sku || StringUtils.isEmpty(userId) || StringUtils.isEmpty(outBusinessNo))
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());

        // 2.查询信息
        // 2.1 通过sku查询活动信息
        ActivitySkuEntity activitySkuEntity = queryActivitySku(sku);
        // 2.2 查询活动信息
        ActivityEntity activityEntity = queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        // 2.3 查询次数信息（用户在活动上可参与的次数）
        ActivityCountEntity activityCountEntity = queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());

        // 3.活动动作规则校验
        // TODO 后续处理规则过滤流程 暂时也不处理责任链结果
        IActionChain actionChain = defaultActivityChainFactory.openActionChain();
        actionChain.action(activitySkuEntity, activityEntity, activityCountEntity);

        // 4.构建订单聚合对象
        CreateOrderAggregate createOrderAggregate = buildOrderAggregate(activitySkuEntity, activityEntity, activityCountEntity, skuRechargeEntity);

        // 5.保存订单
        saveOrder(createOrderAggregate);

        // 6.返回单号
        return createOrderAggregate.getActivityOrderEntity().getOrderId();
    }

    /**
     * 构建订单聚合对象
     * @param activitySkuEntity 活动sku实体
     * @param activityEntity 活动实体
     * @param activityCountEntity 次数实体
     * @param skuRechargeEntity sku充值实体
     * @return 订单聚合对象
     */
    protected abstract CreateOrderAggregate buildOrderAggregate(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity, SkuRechargeEntity skuRechargeEntity);

    /**
     * 保存订单
     * @param createOrderAggregate 订单聚合对象
     */
    protected abstract void saveOrder(CreateOrderAggregate createOrderAggregate);
}

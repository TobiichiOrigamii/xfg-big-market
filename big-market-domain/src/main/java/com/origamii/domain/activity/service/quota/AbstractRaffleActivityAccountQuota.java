package com.origamii.domain.activity.service.quota;

import com.alibaba.fastjson.JSON;
import com.origamii.domain.activity.model.aggreate.CreateQuotaOrderAggregate;
import com.origamii.domain.activity.model.entity.*;
import com.origamii.domain.activity.repository.IActivityRepository;
import com.origamii.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.origamii.domain.activity.service.quota.policy.ITradePolicy;
import com.origamii.domain.activity.service.quota.rule.IActionChain;
import com.origamii.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;
import com.origamii.types.enums.ResponseCode;
import com.origamii.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author Origami
 * @description 抽奖活动抽象类，定义标准的流程
 * @create 2024-09-27 15:55
 **/
@Slf4j
public abstract class AbstractRaffleActivityAccountQuota extends RaffleActivityAccountQuotaSupport implements IRaffleActivityAccountQuotaService {

    private final Map<String, ITradePolicy> tradePolicyMap;

    public AbstractRaffleActivityAccountQuota(IActivityRepository repository, DefaultActivityChainFactory defaultActivityChainFactory, Map<String, ITradePolicy> tradePolicyMap) {
        super(repository, defaultActivityChainFactory);
        this.tradePolicyMap = tradePolicyMap;
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
    public UnpaidActivityOrderEntity  createOrder(SkuRechargeEntity skuRechargeEntity) {

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

        // 3.活动动作规则校验「过滤失败则直接抛异常」
        IActionChain actionChain = defaultActivityChainFactory.openActionChain();
        actionChain.action(activitySkuEntity, activityEntity, activityCountEntity);

        // 4.构建订单聚合对象
        CreateQuotaOrderAggregate createquotaOrderAggregate = buildOrderAggregate(activitySkuEntity, activityEntity, activityCountEntity, skuRechargeEntity);

        // 5. 交易策略 - 【积分兑换，支付类订单】【返利无支付交易订单，直接充值到账】【订单状态变更交易类型策略】
        ITradePolicy tradePolicy = tradePolicyMap.get(skuRechargeEntity.getOrderTradeType().getCode());
        tradePolicy.trade(createquotaOrderAggregate);

        ActivityOrderEntity activityOrderEntity = createquotaOrderAggregate.getActivityOrderEntity();
        return UnpaidActivityOrderEntity.builder()
                .userId(userId)
                .orderId(activityOrderEntity.getOrderId())
                .outBusinessNo(activityOrderEntity.getOutBusinessNo())
                .payAmount(activityOrderEntity.getPayAmount())
                .build();

    }

    /**
     * 构建订单聚合对象
     * @param activitySkuEntity 活动sku实体
     * @param activityEntity 活动实体
     * @param activityCountEntity 次数实体
     * @param skuRechargeEntity sku充值实体
     * @return 订单聚合对象
     */
    protected abstract CreateQuotaOrderAggregate buildOrderAggregate(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity, SkuRechargeEntity skuRechargeEntity);

//    /**
//     * 保存订单
//     * @param createOrderAggregate 订单聚合对象
//     */
//    protected abstract void saveOrder(CreateQuotaOrderAggregate createOrderAggregate);
}

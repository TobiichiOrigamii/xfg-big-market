package com.origamii.domain.activity.service.quota;

import com.alibaba.fastjson.JSON;
import com.origamii.domain.activity.model.aggreate.CreateQuotaOrderAggregate;
import com.origamii.domain.activity.model.entity.*;
import com.origamii.domain.activity.model.valobj.OrderTradeTypeVO;
import com.origamii.domain.activity.repository.IActivityRepository;
import com.origamii.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.origamii.domain.activity.service.quota.policy.ITradePolicy;
import com.origamii.domain.activity.service.quota.rule.IActionChain;
import com.origamii.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;
import com.origamii.types.enums.ResponseCode;
import com.origamii.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
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
    public UnpaidActivityOrderEntity createOrder(SkuRechargeEntity skuRechargeEntity) {
        // 1. 参数校验
        String userId = skuRechargeEntity.getUserId();
        Long sku = skuRechargeEntity.getSku();
        String outBusinessNo = skuRechargeEntity.getOutBusinessNo();
        if (null == sku || StringUtils.isBlank(userId) || StringUtils.isBlank(outBusinessNo)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2. 查询未支付订单「一个月以内的未支付订单」
        UnpaidActivityOrderEntity unpaidCreditOrder = repository.queryUnpaidActivityOrder(skuRechargeEntity);
        if (null != unpaidCreditOrder) return unpaidCreditOrder;

        // 3. 查询基础信息「sku、活动、次数」
        ActivitySkuEntity activitySkuEntity = queryActivitySku(sku);
        ActivityEntity activityEntity = queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        ActivityCountEntity activityCountEntity = queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());

        // 4. 账户额度 【交易属性的兑换，需要校验额度账户】
        if (OrderTradeTypeVO.credit_pay_trade.equals(skuRechargeEntity.getOrderTradeType())) {
            BigDecimal availableAmount = repository.queryUserCreditAccountAmount(userId);
            if (availableAmount.compareTo(activitySkuEntity.getProductAmount()) < 0) {
                throw new AppException(ResponseCode.USER_CREDIT_ACCOUNT_NO_AVAILABLE_AMOUNT.getCode(), ResponseCode.USER_CREDIT_ACCOUNT_NO_AVAILABLE_AMOUNT.getInfo());
            }
        }

        // 5. 活动动作规则校验 「过滤失败则直接抛异常」- 责任链扣减sku库存
        IActionChain actionChain = defaultActivityChainFactory.openActionChain();
        actionChain.action(activitySkuEntity, activityEntity, activityCountEntity);

        // 6. 构建订单聚合对象
        CreateQuotaOrderAggregate createOrderAggregate = buildOrderAggregate(activitySkuEntity, activityEntity, activityCountEntity, skuRechargeEntity);

        // 7. 交易策略 - 【积分兑换，支付类订单】【返利无支付交易订单，直接充值到账】【订单状态变更交易类型策略】
        ITradePolicy tradePolicy = tradePolicyMap.get(skuRechargeEntity.getOrderTradeType().getCode());
        tradePolicy.trade(createOrderAggregate);

        // 8. 返回订单信息
        ActivityOrderEntity activityOrderEntity = createOrderAggregate.getActivityOrderEntity();
        return UnpaidActivityOrderEntity.builder()
                .userId(userId)
                .orderId(activityOrderEntity.getOrderId())
                .outBusinessNo(activityOrderEntity.getOutBusinessNo())
                .payAmount(activityOrderEntity.getPayAmount())
                .build();
    }


    /**
     * 构建订单聚合对象
     *
     * @param activitySkuEntity   活动sku实体
     * @param activityEntity      活动实体
     * @param activityCountEntity 次数实体
     * @param skuRechargeEntity   sku充值实体
     * @return 订单聚合对象
     */
    protected abstract CreateQuotaOrderAggregate buildOrderAggregate(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity, SkuRechargeEntity skuRechargeEntity);

//    /**
//     * 保存订单
//     * @param createOrderAggregate 订单聚合对象
//     */
//    protected abstract void saveOrder(CreateQuotaOrderAggregate createOrderAggregate);
}

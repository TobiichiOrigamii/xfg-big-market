package com.origamii.domain.activity.service;

import com.origamii.domain.activity.model.entity.*;

/**
 * @author Origami
 * @description 抽奖活动订单接口
 * @create 2024-09-27 15:11
 **/
public interface IRaffleActivityAccountQuotaService {

    /**
     * 以sku创建抽奖活动订单 获得参与抽奖资格 可消耗的次数
     *
     * @param activityShopCartEntity 活动sku实体 通过sku领取活动
     * @return 活动参与记录实体
     */
    ActivityOrderEntity createRaffleActivityOrder(ActivityShopCartEntity activityShopCartEntity);

    /**
     * 创建 sku 账户充值订单 增加用户抽奖次数
     * 1. 在用户【打卡 前导 分享 对话 积分兑换】等行为下 创建出活动订单 给用户的活动账户【日 月】增加可用的抽奖次数
     * 2. 对于用户可获得的抽奖次数 比如首次进来就有一次 则是依赖于运营配置的动作 在前端页面上 用户点击后 可以获得一次抽奖
     *
     * @param skuRechargeEntity 活动商品充值实体对象
     * @return 活动ID
     */
    UnpaidActivityOrderEntity createOrder(SkuRechargeEntity skuRechargeEntity);

    /**
     * 订单出货 - 积分充值
     *
     * @param deliveryOrderEntity 出货订单实体
     */
    void updateOrder(DeliveryOrderEntity deliveryOrderEntity);

    /**
     * 查询用户当日抽奖次数
     *
     * @param activityId 活动ID
     * @param userId     用户ID
     * @return 当日抽奖次数
     */
    Integer queryRaffleActivityAccountDayPartakeCount(Long activityId, String userId);

    /**
     * 查询用户活动账户信息
     *
     * @param activityId 活动ID
     * @param userId     用户ID
     * @return 用户活动账户信息
     */
    ActivityAccountEntity queryActivityAccount(Long activityId, String userId);


}

package com.origamii.domain.rebate.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Origami
 * @description 行为返利订单实体对象
 * @create 2024-10-21 23:34
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BehaviorRebateOrderEntity {

    // 用户ID
    private String userId;

    // 订单ID
    private String orderId;

    // 行为类型 sign - 签到 openai_pay - 支付
    private String behaviorType;

    // 返利描述
    private String rebateDesc;

    // 返利类型 sku 活动库存充值商品、integral 用户活动积分
    private String rebateType;

    // 返利配置 sku值，积分值
    private String rebateConfig;

    // 防重ID 外部透传
    private String outBusinessNo;

    // 业务ID 拼接的唯一值
    private String bizId;

}

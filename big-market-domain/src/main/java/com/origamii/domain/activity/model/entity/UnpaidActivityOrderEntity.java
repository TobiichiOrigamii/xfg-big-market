package com.origamii.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Origami
 * @description 未完成支付的活动单
 * @create 2024-10-31 21:39
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnpaidActivityOrderEntity {

    // 用户ID
    private String userId;

    // 订单ID
    private String orderId;

    // 外部透传ID
    private String outBusinessNo;

    // 订单金额
    private BigDecimal payAmount;

}


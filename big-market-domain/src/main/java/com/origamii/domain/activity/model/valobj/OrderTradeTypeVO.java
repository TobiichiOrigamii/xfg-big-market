package com.origamii.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Origami
 * @description 订单交易类型
 * @create 2024-10-28 21:06
 **/
@Getter
@AllArgsConstructor
public enum OrderTradeTypeVO {

    credit_pay_trade("credit_pay_trade","积分兑换，需要支持类交易"),
    rebate_no_pay_trade("rebate_no_pay_trade","返利奖品，不需要支付类交易"),
    ;

    private final String code;
    private final String desc;

}

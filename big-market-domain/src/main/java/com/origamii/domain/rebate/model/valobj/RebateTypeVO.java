package com.origamii.domain.rebate.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Origami
 * @description 返利类型（sku - 活动库存充值商品 integral - 用户活动积分）
 * @create 2024-10-22 22:33
 **/
@Getter
@AllArgsConstructor
public enum RebateTypeVO {

    SKU("sku", "活动库存充值商品"),
    INTEGRAL("integral", "用户活动积分"),
    ;

    private final String code;
    private final String info;

}

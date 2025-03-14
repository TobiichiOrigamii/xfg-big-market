package com.origamii.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Origami
 * @description 订单状态枚举值对象
 *              用于描述对象属性的值 如枚举 不影响数据库操作的对象 无生命周期
 * @create 2024-09-27 15:16
 **/
@Getter
@AllArgsConstructor
public enum OrderStateVO {

    wait_pay("wait_pay", "待支付"),
    completed("completed", "已完成"),
    ;

    private final String code;
    private final String desc;
}

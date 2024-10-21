package com.origamii.domain.rebate.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Origami
 * @description 行为类型枚举值对象
 * @create 2024-10-21 23:38
 **/
@Getter
@AllArgsConstructor
public enum BehaviorTypeVO {

    SIGN("sign", "签到（日历）"),
    OPENAI_PAY("openai_pay", "openai 外部支付完成"),
    ;

    private final String code;
    private final String info;

}

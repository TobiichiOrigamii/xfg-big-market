package com.origamii.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Origami
 * @description
 * @create 2024-09-09 21:22
 **/
@Getter
@AllArgsConstructor
public enum RuleLogicCheckTypeVO {

    ALLOW("0000", "放行：执行后续的流程，不受规则引擎的影响"),
    TAKE_OVER("0001", "接管：后续的流程，受规则引擎执行结果影响"),
    ;

    private final String code;

    private final String info;

}

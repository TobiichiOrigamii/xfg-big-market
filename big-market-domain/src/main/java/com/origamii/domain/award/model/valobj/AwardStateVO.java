package com.origamii.domain.award.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Origami
 * @description 奖品状态枚举值对象
 * @create 2024-10-14 13:14
 **/
@Getter
@AllArgsConstructor
public enum AwardStateVO {

    create("create", "创建"),
    compete("compete", "发奖完成"),
    fail("fail", "发奖失败"),
    ;

    private final String code;
    private final String desc;


}

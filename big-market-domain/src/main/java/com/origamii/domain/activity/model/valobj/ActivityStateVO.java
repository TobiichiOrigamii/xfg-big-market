package com.origamii.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Origami
 * @description 活动状态值对象
 * @create 2024-09-27 15:13
 **/
@Getter
@AllArgsConstructor
public enum ActivityStateVO {

    create("created","创建"),
    ;

    private final String code;
    private final String desc;


}

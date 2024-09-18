package com.origamii.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author Origami
 * @description 规则树节点
 * @create 2024-09-18 10:11
 **/
@Data
public class RuleTreeNode {

    /** 自增ID */
    private Long id;
    /** 规则树ID */
    private String RuleId;
    /** 规则Key */
    private String ruleKey;
    /** 规则描述 */
    private String ruleDesc;
    /** 规则比值 */
    private String ruleValue;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

}


package com.origamii.domain.strategy.model.valobj;

import lombok.*;

import java.util.Map;

/**
 * @author Origami
 * @description 规则树对象
 * @create 2024-09-14 22:57
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleTreeVO {

    // 规则树ID
    private String treeId;

    // 规则树名称
    private String treeName;

    // 规则树描述
    private String treeDesc;

    // 规则根节点
    private String treeRootRuleNode;

    // 规则节点
    private Map<String, RuleTreeNodeVO> treeNodeMap;

}


package com.origamii.domain.strategy.service.rule.tree.factory;

import com.origamii.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.origamii.domain.strategy.model.valobj.RuleTreeVO;
import com.origamii.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.origamii.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import com.origamii.domain.strategy.service.rule.tree.factory.engine.impl.DecisionTreeEngine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Origami
 * @description 规则树工厂
 * @create 2024-09-14 23:09
 **/
@Service
public class DefaultTreeFactory {

    @Autowired
    private Map<String, ILogicTreeNode> logicTreeNodeGroup;

    public IDecisionTreeEngine openLogicTree(RuleTreeVO ruleTreeVO) {
        return new DecisionTreeEngine(logicTreeNodeGroup, ruleTreeVO);
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TreeActionEntity {
        private RuleLogicCheckTypeVO ruleLogicCheckType;
        private StrategyAwardVO strategyAwardVO;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        private Integer awardId;
        private String awardRuleValue;
    }


}

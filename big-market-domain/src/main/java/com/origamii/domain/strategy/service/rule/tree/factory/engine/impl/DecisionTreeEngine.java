package com.origamii.domain.strategy.service.rule.tree.factory.engine.impl;

import com.origamii.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.origamii.domain.strategy.model.valobj.RuleTreeNodeLineVO;
import com.origamii.domain.strategy.model.valobj.RuleTreeNodeVO;
import com.origamii.domain.strategy.model.valobj.RuleTreeVO;
import com.origamii.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.origamii.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.origamii.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author Origami
 * @description 决策树引擎
 * @create 2024-09-14 23:13
 **/
@Slf4j
public class DecisionTreeEngine implements IDecisionTreeEngine {

    // 逻辑树节点组
    private final Map<String, ILogicTreeNode> logicTreeNodeGroup;

    // 规则树对象
    private final RuleTreeVO ruleTreeVO;


    public DecisionTreeEngine(Map<String, ILogicTreeNode> logicTreeNodeGroup, RuleTreeVO ruleTreeVO) {
        this.logicTreeNodeGroup = logicTreeNodeGroup;
        this.ruleTreeVO = ruleTreeVO;
    }

    /**
     * 处理决策树的逻辑，返回策略奖项
     * @param userId        用户ID
     * @param strategyId    策略ID
     * @param awardId       奖项ID
     * @return 策略奖项数据
     */
    @Override
    public DefaultTreeFactory.StrategyAwardVO process(String userId, Long strategyId, Integer awardId) {
        DefaultTreeFactory.StrategyAwardVO strategyAwardData = null;

        // 获取基础信息
        String nextNode = ruleTreeVO.getTreeRootRuleNode();
        Map<String, RuleTreeNodeVO> treeNodeMap = ruleTreeVO.getTreeNodeMap();

        // 获取起始节点 根节点记录了第一个要执行的规则
        RuleTreeNodeVO ruleTreeNode = treeNodeMap.get(nextNode);
        while (nextNode != null) {
            // 获取决策节点
            ILogicTreeNode logicTreeNode = logicTreeNodeGroup.get(ruleTreeNode.getRuleKey());
            String ruleValue = ruleTreeNode.getRuleValue();

            // 执行决策节点
            DefaultTreeFactory.TreeActionEntity logicEntity = logicTreeNode.logic(userId, strategyId, awardId, ruleValue);
            RuleLogicCheckTypeVO ruleLogicCheckType = logicEntity.getRuleLogicCheckType();
            strategyAwardData = logicEntity.getStrategyAwardVO();
            log.info("决策树引擎【{}】，treeId：{}，node：{}，code：{}",
                    ruleTreeVO.getTreeName(),
                    ruleTreeVO.getTreeId(),
                    nextNode,
                    ruleLogicCheckType.getCode()
            );
            // 决策节点执行完毕，获取下一个节点
            nextNode = nextNode(ruleLogicCheckType.getCode(), ruleTreeNode.getTreeNodeLineVOList());
            ruleTreeNode = treeNodeMap.get(nextNode);
        }
        // 决策树执行完毕，返回结果
        return strategyAwardData;
    }

    /**
     * 根据当前的值和节点线列表获取下一个节点
     * @param matterValue            当前值
     * @param ruleTreeNodeLineVOList 规则树节点线列表
     * @return 下一个节点
     */
    private String nextNode(String matterValue, List<RuleTreeNodeLineVO> ruleTreeNodeLineVOList) {
        if (null == ruleTreeNodeLineVOList || ruleTreeNodeLineVOList.isEmpty()) {
            return null;
        }
        for (RuleTreeNodeLineVO nodeLine : ruleTreeNodeLineVOList) {
            if (decisionLogic(matterValue, nodeLine)) {
                return nodeLine.getRuleNodeTo();
            }
        }
        //throw new RuntimeException("决策树引擎，nextNode方法，未找到匹配的节点");
        return null;
    }

    /**
     * 决策逻辑判断
     * @param matterValue        当前值
     * @param ruleTreeNodeLineVO 规则树节点线对象
     * @return 是否符合决策逻辑
     */
    public boolean decisionLogic(String matterValue, RuleTreeNodeLineVO ruleTreeNodeLineVO) {
        switch (ruleTreeNodeLineVO.getRuleLimitType()) {
            case EQUAL:
                return matterValue.equals(ruleTreeNodeLineVO.getRuleLimitValue().getCode());
                // TODO 其他逻辑判断
            case GT:
            case LT:
            case GE:
            case LE:
            default:
                return false;
        }
    }

}

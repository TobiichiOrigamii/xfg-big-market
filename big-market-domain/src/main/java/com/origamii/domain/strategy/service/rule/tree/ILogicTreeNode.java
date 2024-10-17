package com.origamii.domain.strategy.service.rule.tree;

import com.origamii.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

import java.util.Date;

/**
 * @author Origami
 * @description 规则树接口
 * @create 2024-09-14 23:02
 **/
public interface ILogicTreeNode {

    /**
     * 根据规则值进行逻辑判断
     * @param userId     用户id
     * @param strategyId 策略id
     * @param awardId    奖品id
     * @param ruleValue  规则值
     * @return 返回树节点的动作
     */
    DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue, Date endDateTime);

}

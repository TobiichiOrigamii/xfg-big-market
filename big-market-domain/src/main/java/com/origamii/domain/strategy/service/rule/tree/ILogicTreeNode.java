package com.origamii.domain.strategy.service.rule.tree;

import com.origamii.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author Origami
 * @description 规则树接口
 * @create 2024-09-14 23:02
 **/
public interface ILogicTreeNode {

    DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId);

}

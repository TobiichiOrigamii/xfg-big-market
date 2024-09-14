package com.origamii.domain.strategy.service.rule.tree.factory.engine;

import com.origamii.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author Origami
 * @description 规则树组合接口
 * @create 2024-09-14 23:12
 **/
public interface IDecisionTreeEngine {

    DefaultTreeFactory.StrategyAwardData process(String userId, Long strategyId, Integer awardId);


}

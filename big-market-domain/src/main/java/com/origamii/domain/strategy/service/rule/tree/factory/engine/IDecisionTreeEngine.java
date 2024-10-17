package com.origamii.domain.strategy.service.rule.tree.factory.engine;

import com.origamii.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

import java.util.Date;

/**
 * @author Origami
 * @description 规则树组合接口
 * @create 2024-09-14 23:12
 **/
public interface IDecisionTreeEngine {

    /**
     * 处理决策树的逻辑，返回策略奖项
     * @param userId        用户ID
     * @param strategyId    策略ID
     * @param awardId       奖项ID
     * @return 策略奖项数据
     */
    DefaultTreeFactory.StrategyAwardVO process(String userId, Long strategyId, Integer awardId, Date endDateTime);

}

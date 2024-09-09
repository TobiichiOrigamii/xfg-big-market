package com.origamii.domain.strategy.service.armory;

/**
 * @author Origami
 * @description 策略抽奖调度
 * @create 2024-09-09 09:25
 **/
public interface IStrategyDispatch {

    /**
     * 获得随机奖品ID
     * @param strategyId 策略ID
     * @return
     */
    Integer getRandomAwardId(Long strategyId);

    /**
     * 获得随机奖品ID 根据规则权重
     * @param strategyId
     * @param ruleWeightValue
     * @return
     */
    Integer getRandomAwardId(Long strategyId, String ruleWeightValue);
}

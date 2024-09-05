package com.origamii.domain.strategy.service.armory;

/**
 * @author Origami
 * @description 策略装配库接口 负责定义装配策略的行为
 * @create 2024-09-05 09:32
 **/
public interface IStrategyArmory {

    /**
     * 策略装配库 通过策略ID装配策略
     *
     * @param strategyId 策略ID
     */
    void assembleLotteryStrategy(Long strategyId);

    /**
     * 获得随机奖品ID
     * @param strategyId 策略ID
     * @return
     */
    Integer getRandomAwardId(Long strategyId);
}

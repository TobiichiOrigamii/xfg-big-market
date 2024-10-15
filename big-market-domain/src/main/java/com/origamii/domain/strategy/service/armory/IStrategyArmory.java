package com.origamii.domain.strategy.service.armory;

/**
 * @author Origami
 * @description 策略装配库接口 负责定义装配策略的行为
 * @create 2024-09-05 09:32
 **/
public interface IStrategyArmory {

    /**
     * 装配抽奖策略配置【触发的时机可以为活动审核通过后进行调用】
     * @param activityId 活动ID
     * @return boolean 装配结果
     */
    boolean assembleLotteryStrategyByActivityId(Long activityId);

    /**
     * 策略装配库 通过策略ID装配策略
     *
     * @param strategyId 策略ID
     * @return boolean 装配结果
     */
    boolean assembleLotteryStrategy(Long strategyId);

}

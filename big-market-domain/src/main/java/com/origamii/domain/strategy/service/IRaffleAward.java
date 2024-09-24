package com.origamii.domain.strategy.service;

import com.origamii.domain.strategy.model.entity.StrategyAwardEntity;

import java.util.List;

/**
 * @author Origami
 * @description 策略奖品查询接口
 * @create 2024-09-24 11:15
 **/
public interface IRaffleAward {

    /**
     * 根据策略ID查询抽奖奖品列表配置
     *
     * @param strategyId 策略ID
     * @return 奖品列表
     */
    List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId);

}

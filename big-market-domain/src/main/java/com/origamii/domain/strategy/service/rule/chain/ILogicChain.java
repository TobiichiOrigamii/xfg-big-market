package com.origamii.domain.strategy.service.rule.chain;

import com.origamii.domain.strategy.service.rule.chain.impl.ILogicChainArmory;

/**
 * @author Origami
 * @description 责任链接口
 * @create 2024-09-12 13:57
 **/
public interface ILogicChain extends ILogicChainArmory {

    /**
     * 责任链接口
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @return 奖品ID
     */
    Integer logic(String userId, Long strategyId);




}

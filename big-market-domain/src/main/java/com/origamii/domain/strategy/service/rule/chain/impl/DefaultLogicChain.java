package com.origamii.domain.strategy.service.rule.chain.impl;

import com.origamii.domain.strategy.service.armory.IStrategyDispatch;
import com.origamii.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.origamii.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Origami
 * @description 兜底奖品
 * @create 2024-09-12 14:13
 **/
@Slf4j
@Component("rule_default")
public class DefaultLogicChain extends AbstractLogicChain {

    @Autowired
    protected IStrategyDispatch strategyDispatch;

    /**
     * 根据用户ID和策略ID获取随机奖品ID并返回相应的奖品信息
     * @param userId 用户ID
     * @param strategyId 策略ID
     * @return 策略奖品信息
     */
    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);
        log.info("抽奖责任链-默认处理 userId: {} strategyId: {} ruleModel: {} awardId: {}", userId, strategyId, ruleModel(), awardId);
        return DefaultChainFactory.StrategyAwardVO.builder()
                .awardId(awardId)
                .logicModel(ruleModel())
                .build();
    }

    /**
     * 获取当前规则模型的代码
     * @return 当前规则模型的代码
     */
    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_DEFAULT.getCode();
    }
}
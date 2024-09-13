package com.origamii.domain.strategy.service.rule.chain.impl;

import com.origamii.domain.strategy.service.armory.IStrategyDispatch;
import com.origamii.domain.strategy.service.rule.chain.AbstractLogicChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Origami
 * @description 兜底
 * @create 2024-09-12 14:13
 **/
@Slf4j
@Component("default")
public class DefaultLogicChain extends AbstractLogicChain {

    @Autowired
    protected IStrategyDispatch strategyDispatch;

    @Override
    public Integer logic(String userId, Long strategyId) {
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);
        log.info("抽奖责任链-默认处理 userId:{}, strategyId:{}, ruleModel:{},awardId:{}", userId, strategyId, ruleModel(), awardId);
        return awardId;
    }

    @Override
    protected String ruleModel() {
        return "default";
    }
}
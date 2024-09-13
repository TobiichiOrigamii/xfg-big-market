package com.origamii.domain.strategy.service.rule.chain.factory;

import com.origamii.domain.strategy.model.entity.StrategyEntity;
import com.origamii.domain.strategy.repository.IStrategyRepository;
import com.origamii.domain.strategy.service.rule.chain.ILogicChain;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Origami
 * @description 工厂
 * @create 2024-09-13 15:54
 **/
@Service
public class DefaultChainFactory {

    private final Map<String, ILogicChain> logicChainGroup;

    private final IStrategyRepository repository;


    public DefaultChainFactory(Map<String, ILogicChain> logicChainGroup, IStrategyRepository repository) {
        this.logicChainGroup = logicChainGroup;
        this.repository = repository;
    }

    public ILogicChain openLogicChain(Long strategyId) {
        StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);
        String[] ruleModels = strategy.ruleModels();

        if (null == ruleModels || ruleModels.length == 0) {
            return logicChainGroup.get("default");
        }
        ILogicChain logicChain = logicChainGroup.get(ruleModels[0]);
        ILogicChain current = logicChain;
        for (int i = 1; i < ruleModels.length; i++) {
            ILogicChain nextChain = logicChainGroup.get(ruleModels[i]);
            current = current.setNext(nextChain);
        }
        current.setNext(logicChainGroup.get("default"));
        return logicChain;
    }

}

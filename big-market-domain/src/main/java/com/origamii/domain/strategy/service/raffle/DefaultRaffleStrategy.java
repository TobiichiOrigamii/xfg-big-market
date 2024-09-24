package com.origamii.domain.strategy.service.raffle;

import com.origamii.domain.strategy.model.entity.StrategyAwardEntity;
import com.origamii.domain.strategy.model.valobj.RuleTreeVO;
import com.origamii.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import com.origamii.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import com.origamii.domain.strategy.repository.IStrategyRepository;
import com.origamii.domain.strategy.service.AbstractRaffleStrategy;
import com.origamii.domain.strategy.service.IRaffleAward;
import com.origamii.domain.strategy.service.IRaffleStock;
import com.origamii.domain.strategy.service.armory.IStrategyDispatch;
import com.origamii.domain.strategy.service.rule.chain.ILogicChain;
import com.origamii.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.origamii.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.origamii.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Origami
 * @description 默认的抽奖策略实现 继承自抽象抽奖策略，实现抽奖奖品和库存接口
 * @create 2024-09-11 08:59
 **/
@Slf4j
@Service
public class DefaultRaffleStrategy extends AbstractRaffleStrategy implements IRaffleAward, IRaffleStock {

    // 构造函数，初始化策略仓库、策略调度、逻辑链工厂和逻辑树工厂
    public DefaultRaffleStrategy(IStrategyRepository repository,
                                 IStrategyDispatch strategyDispatch,
                                 DefaultChainFactory defaultChainFactory,
                                 DefaultTreeFactory defaultTreeFactory) {
        super(repository, strategyDispatch, defaultChainFactory, defaultTreeFactory);
    }

    /**
     * 根据用户ID和策略ID执行逻辑链，返回抽奖结果
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @return 抽奖结果
     */
    @Override
    public DefaultChainFactory.StrategyAwardVO raffleLogicChain(String userId, Long strategyId) {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(strategyId);
        return logicChain.logic(userId, strategyId);
    }

    /**
     * 根据用户ID、策略ID和奖品ID执行逻辑树，返回抽奖结果
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return 抽奖结果
     */
    @Override
    public DefaultTreeFactory.StrategyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId) {
        StrategyAwardRuleModelVO strategyAwardRuleModel = repository.queryStrategyAwardRuleModel(strategyId, awardId);

        if (null == strategyAwardRuleModel) {
            return DefaultTreeFactory.StrategyAwardVO.builder().awardId(awardId).build();
        }
        RuleTreeVO ruleTreeVO = repository.queryRuleTreeVOByTreeId(strategyAwardRuleModel.getRuleModels());
        if (null == ruleTreeVO) {
            throw new RuntimeException("存在抽奖策略配置的规则模型 Key，未在库表 rule_tree、rule_tree_node、rule_tree_line 配置对应的规则树信息 " + strategyAwardRuleModel.getRuleModels());
        }
        IDecisionTreeEngine treeEngine = defaultTreeFactory.openLogicTree(ruleTreeVO);
        return treeEngine.process(userId, strategyId, awardId);
    }

    /**
     * 从仓库中获取队列值
     * @return 队列值
     */
    @Override
    public StrategyAwardStockKeyVO takeQueueValue() {
        return repository.takeQueueValue();
    }

    /**
     * 更新策略奖品库存
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     */
    @Override
    public void updateStrategyAwardStock(Long strategyId, Integer awardId) {
        repository.updateStrategyAwardStock(strategyId, awardId);
    }

    /**
     * 查询策略奖品列表
     * @param strategyId 策略ID
     * @return
     */
    @Override
    public List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId) {
        return repository.queryStrategyAwardList(strategyId);
    }
}


package com.origamii.domain.strategy.service.rule.tree.impl;

import com.origamii.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.origamii.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import com.origamii.domain.strategy.repository.IStrategyRepository;
import com.origamii.domain.strategy.service.armory.IStrategyDispatch;
import com.origamii.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.origamii.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Origami
 * @description 库存节点
 * @create 2024-09-14 23:08
 **/
@Slf4j
@Component("rule_stock")
public class RuleStockLogicTreeNode implements ILogicTreeNode {

    @Autowired
    private IStrategyDispatch strategyDispatch;

    @Autowired
    private IStrategyRepository strategyRepository;

    /**
     * 库存节点
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @param ruleValue  规则值
     * @return 规则树
     */
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {
        log.info("规则过滤-库存扣减 userId:{},strategyId:{},awardId:{}", userId, strategyId, awardId);

        // 扣减库存
        Boolean status = strategyDispatch.subtractionAwardStock(strategyId, awardId);

        // true:库存扣减成功，TAKE_OVER规则节点接管 返回奖品ID，奖品规则配置
        if (status){
            log.info("规则过滤-扣减库存-成功 userId:{},strategyId:{},awardId:{}", userId, strategyId, awardId);

            // 写入延迟队列，延迟消费更新数据库记录。【在trigger的job：UpdateAwardStockJob下消费队列，更新数据库记录】
            strategyRepository.awardStockConsumeSendQueue(StrategyAwardStockKeyVO.builder()
                    .strategyId(strategyId)
                    .awardId(awardId)
                    .build());

            // TODO ruleValue 待定
            return DefaultTreeFactory.TreeActionEntity.builder()
                    .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                    .strategyAwardVO(DefaultTreeFactory.StrategyAwardVO.builder()
                            .awardId(awardId)
                            .awardRuleValue("")
                            .build())
                    .build();


        }

        // 如果库存不足，则直接返回放行
        log.warn("规则过滤-库存扣减-告警，库存不足 userId:{},strategyId:{},awardId:{}", userId, strategyId, awardId);
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                .build();
    }

}

package com.origamii.domain.strategy.service.rule.tree.impl;

import com.origamii.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.origamii.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.origamii.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.origamii.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Origami
 * @description 兜底奖励节点
 * @create 2024-09-14 23:07
 **/
@Slf4j
@Component("rule_luck_award")
public class RuleLuckAwardLogicTreeNode implements ILogicTreeNode {

    /**
     *  兜底奖品逻辑节点
     * @param userId     用户id
     * @param strategyId 策略id
     * @param awardId    奖品id
     * @param ruleValue  规则值
     * @return  奖品信息
     */
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue, Date endDateTime) {

        log.info("规则过滤-兜底奖品 userId:{},strategyId:{},awardId:{},ruleValue:{}", userId, strategyId, awardId, ruleValue);
        String[] split = ruleValue.split(Constants.COLON);
        if(split.length == 0){
            log.error("规则过滤-兜底奖品,兜底奖品未配置告警 userId:{},strategyId:{},awardId:{}", userId, strategyId, awardId);
            throw new RuntimeException("兜底奖品未配置"+ruleValue);
        }

        // 兜底奖品配置
        Integer luckAwardId = Integer.parseInt(split[0]);
        String awardRuleValue = split.length > 1? split[1] : "";

        // 返回兜底奖品
        log.info("规则过滤-兜底奖品 userId:{},strategyId:{},awardId:{},luckAwardId:{},awardRuleValue:{}", userId, strategyId, awardId, luckAwardId, awardRuleValue);

        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .strategyAwardVO(DefaultTreeFactory.StrategyAwardVO.builder()
                        .awardId(luckAwardId)
                        .awardRuleValue(awardRuleValue)
                        .build())
                .build();
    }

}

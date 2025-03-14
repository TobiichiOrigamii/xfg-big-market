package com.origamii.domain.strategy.service.rule.filter.impl;

import com.origamii.domain.strategy.model.entity.RuleActionEntity;
import com.origamii.domain.strategy.model.entity.RuleMatterEntity;
import com.origamii.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.origamii.domain.strategy.repository.IStrategyRepository;
import com.origamii.domain.strategy.service.annotation.LogicStrategy;
import com.origamii.domain.strategy.service.rule.filter.ILogicFilter;
import com.origamii.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author Origami
 * @description 用户抽奖n次后 对应奖品课解锁抽奖 过滤器
 * @create 2024-09-11 15:46
 **/
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_LOCK)
public class RuleLockLogicFilter implements ILogicFilter<RuleActionEntity.RaffleDuringEntity> {

    @Resource
    private IStrategyRepository strategyRepository;

    // 用户的抽奖次数
    private Long userRaffleCount = 0L;

    /**
     * 过滤规则，根据用户的抽奖次数判断是否允许抽奖
     *
     * @param ruleMatterEntity 规则相关实体
     * @return 规则动作实体
     */
    @Override
    public RuleActionEntity<RuleActionEntity.RaffleDuringEntity> filter(RuleMatterEntity ruleMatterEntity) {
        log.info("规则过滤 - 次数锁 userId:{} strategyId:{} ruleModel:{}",
                ruleMatterEntity.getUserId(),
                ruleMatterEntity.getStrategyId(),
                ruleMatterEntity.getRuleModel()
        );
        String ruleValue = strategyRepository.queryStrategyRuleValue(
                ruleMatterEntity.getStrategyId(),
                ruleMatterEntity.getAwardId(),
                ruleMatterEntity.getRuleModel()
        );

        Long raffleCount = Long.parseLong(ruleValue);

        // 如果用户抽奖次数大于等于配置值，则放行 奖品课解锁抽奖
        if (userRaffleCount >= raffleCount) {
            return RuleActionEntity.<RuleActionEntity.RaffleDuringEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();
        }

        // 用户抽奖次数小于规则限定值，规则拦截
        return RuleActionEntity.<RuleActionEntity.RaffleDuringEntity>builder()
                .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                .build();

    }
}

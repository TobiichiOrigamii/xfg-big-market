package com.origamii.domain.strategy.service.rule.filter;

import com.origamii.domain.strategy.model.entity.RuleActionEntity;
import com.origamii.domain.strategy.model.entity.RuleMatterEntity;

/**
 * @author Origami
 * @description 抽奖规则过滤接口
 * @create 2024-09-09 16:37
 **/
public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity> {

    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);





}

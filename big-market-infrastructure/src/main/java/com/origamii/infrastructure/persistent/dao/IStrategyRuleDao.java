package com.origamii.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import com.origamii.infrastructure.persistent.po.StrategyRule;

import java.util.List;

/**
 * @author Origami
 * @description 策略规则 DAO
 * @create 2024-07-18 17:47
 **/
@Mapper
public interface IStrategyRuleDao {

    List<StrategyRule> queryStrategyRuleList();

    StrategyRule queryStrategyRule(StrategyRule strategyRuleReq);
}

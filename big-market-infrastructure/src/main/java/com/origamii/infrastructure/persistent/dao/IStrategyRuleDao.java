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

    /**
     * 查询策略规则列表
     * @return 策略规则列表
     */
    List<StrategyRule> queryStrategyRuleList();

    /**
     *  根据策略规则查询策略规则值
     * @param strategyRuleReq 策略规则
     * @return 策略规则值
     */
    StrategyRule queryStrategyRule(StrategyRule strategyRuleReq);

    /**
     *  根据策略规则查询策略规则值
     * @param strategyRule 策略规则
     * @return 策略规则值
     */
    String queryStrategyRuleValue(StrategyRule strategyRule);
}

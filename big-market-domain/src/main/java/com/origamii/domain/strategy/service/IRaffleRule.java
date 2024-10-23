package com.origamii.domain.strategy.service;

import com.origamii.domain.strategy.model.valobj.RuleWeightVO;

import java.util.List;
import java.util.Map;

/**
 * @author Origami
 * @description 抽奖规则接口
 * @create 2024-10-16 22:32
 **/
public interface IRaffleRule {

    /**
     * 查询奖品规则
     * @return 奖品规则
     */
    Map<String, Integer> queryAwardRuleLockCount(String[] treeIds);

    /**
     * 查询奖品权重配置
     *
     * @param strategyId 策略ID
     * @return 权重规则
     */
    List<RuleWeightVO> queryAwardRuleWeight(Long strategyId);

    /**
     * 查询奖品权重配置
     *
     * @param activityId 活动ID
     * @return 权重规则
     */
    List<RuleWeightVO> queryAwardRuleWeightByActivityId(Long activityId);
}

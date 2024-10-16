package com.origamii.domain.strategy.service;

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

}

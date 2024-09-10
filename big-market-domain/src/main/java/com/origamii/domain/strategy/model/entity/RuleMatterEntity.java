package com.origamii.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Origami
 * @description
 * @create 2024-09-09 16:38
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleMatterEntity {

    /** 用户ID */
    private String userId;

    /** 策略ID */
    private Long strategyId;

    /** 抽奖奖品ID - 规则类型为策略 则不需要奖品ID */
    private Integer awardId;

    /** 抽奖规则类型
     * ruleRandom      - 随机值计算
     * rule_lock       - 抽奖几次后解锁
     * rule_luck_award - 幸运奖
     * */
    private String ruleModel;

}

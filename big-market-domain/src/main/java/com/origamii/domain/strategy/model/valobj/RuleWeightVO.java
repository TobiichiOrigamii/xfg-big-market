package com.origamii.domain.strategy.model.valobj;

import lombok.*;

import java.util.List;

/**
 * @author Origami
 * @description 权重规则值对象
 * @create 2024-10-24 00:05
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleWeightVO {

    // 原始规则值配置
    private String ruleValue;

    // 权重值
    private Integer weight;

    // 奖品配置
    private List<Integer> awardIds;

    // 奖品列表
    private List<Award> awardList;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Award {
        // 奖品ID
        private Integer awardId;

        // 奖品标题
        private String awardTitle;
    }

}

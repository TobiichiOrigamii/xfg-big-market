package com.origamii.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Origami
 * @description 策略奖品实体
 * @create 2024-09-05 09:40
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardEntity {

    // 抽奖策略ID
    private Long strategyId;

    // 抽奖奖品ID -内部流转使用
    private Integer awardId;

    // 抽奖奖品标题
    private String awardTitle;

    // 抽奖奖品副标题
    private String awardSubTitle;

    // 奖品库存总量
    private Integer awardCount;

    // 奖品库存剩余
    private Integer awardCountSurplus;

    // 奖品中奖概率
    private BigDecimal awardRate;

    // 排序
    private Integer sort;

    // 规则模型 rule配置的模型同步到此类 便于使用
    private String ruleModels;

}

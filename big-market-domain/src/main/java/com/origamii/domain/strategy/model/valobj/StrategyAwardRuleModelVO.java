package com.origamii.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Origami
 * @description 抽奖策略规则值对象
 * 没有唯一ID 仅限于从数据库中查询对象
 * @create 2024-09-11 21:21
 **/
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardRuleModelVO {

    private String ruleModels;


}

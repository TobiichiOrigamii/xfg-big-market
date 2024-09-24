package com.origamii.domain.strategy.service.rule.filter.impl;

import com.origamii.domain.strategy.model.entity.RuleActionEntity;
import com.origamii.domain.strategy.model.entity.RuleMatterEntity;
import com.origamii.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.origamii.domain.strategy.repository.IStrategyRepository;
import com.origamii.domain.strategy.service.annotation.LogicStrategy;
import com.origamii.domain.strategy.service.rule.filter.ILogicFilter;
import com.origamii.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;
import com.origamii.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Origami
 * @description 权重规则过滤器
 * @create 2024-09-10 10:27
 **/
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_WEIGHT)
public class RuleWeightLogicFilter implements ILogicFilter<RuleActionEntity.RaffleBeforeEntity> {


    @Resource
    private IStrategyRepository repository;

    private Long userScore = 4500L;

    /**
     * 权重规则过滤
     * 1. 权重规则格式：
     * 4000:102,103,104,105
     * 5000:102,103,104,105,106,107
     * 6000:102,103,104,105,106,107,108,109
     * 2. 解析数据格式：判断哪个范围符合用户的特定抽奖范围
     *
     * @param ruleMatterEntity 规则物料实体对象
     * @return 规则过滤结果
     */
    @Override
    public RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> filter(RuleMatterEntity ruleMatterEntity) {
        log.info("规则过滤-权重范围 userId:{} strateguyId:{} ruleModel:{}",
                ruleMatterEntity.getUserId(),
                ruleMatterEntity.getStrategyId(),
                ruleMatterEntity.getRuleModel()
        );

        String userId = ruleMatterEntity.getUserId();
        Long strategyId = ruleMatterEntity.getStrategyId();
        String ruleValue = repository.queryStrategyRuleValue(
                ruleMatterEntity.getStrategyId(),
                ruleMatterEntity.getAwardId(),
                ruleMatterEntity.getRuleModel()
        );

        // 1.根据用户ID查询用户抽奖消耗的积分值，本章节将其写为固定的值，后续改为从数据库查询
        // TODO: 消耗的积分值改为从数据库查询
        Map<Long, String> analyticalValueGroup = getAnalyticalValue(ruleValue);
        if (null == analyticalValueGroup || analyticalValueGroup.isEmpty()) {
            return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();
        }

        // 2.转换Keys值并默认排序
        List<Long> analyticalSortedKeys = new ArrayList<>(analyticalValueGroup.keySet());
        //  Collections.sort(analyticalSortedKeys); 此时是升序排列
        // 降序排列
        analyticalSortedKeys.sort(Comparator.reverseOrder());

        // 3.找出最小符合的值
        Long nextValue = analyticalSortedKeys.stream()
                .filter(key -> key >= userScore)
                .findFirst()
                .orElse(null);
        if (null == nextValue) {
            return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .data(RuleActionEntity.RaffleBeforeEntity.builder()
                            .strategyId(strategyId)
                            .ruleWeightValueKey(analyticalValueGroup.get(nextValue))
                            .build())
                    .ruleModel(DefaultLogicFactory.LogicModel.RULE_WEIGHT.getCode())
                    .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                    .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                    .build();
        }
        return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                .build();
    }

    /**
     * 解析权重规则值为键值对
     *
     * @param ruleValue 规则值字符串
     * @return 解析后的键值对映射
     */
    private Map<Long, String> getAnalyticalValue(String ruleValue) {
        String[] ruleValueGroups = ruleValue.split(Constants.SPACE);
        Map<Long, String> ruleValueMap = new HashMap<>();
        for (String ruleValueKey : ruleValueGroups) {
            // 检查输入是否为空
            if (ruleValueKey == null || ruleValueKey.isEmpty())
                return ruleValueMap;
            // 分割字符串以获得键值
            String[] parts = ruleValueKey.split(Constants.COLON);
            if (parts.length != 2) {
                throw new IllegalArgumentException("rule_weight rule_rule invalid input format" + ruleValueKey);
            }
            ruleValueMap.put(Long.parseLong(parts[0]), ruleValueKey);
        }
        return ruleValueMap;
    }
}

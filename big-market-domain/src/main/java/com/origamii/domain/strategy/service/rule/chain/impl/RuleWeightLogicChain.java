package com.origamii.domain.strategy.service.rule.chain.impl;

import com.origamii.domain.strategy.repository.IStrategyRepository;
import com.origamii.domain.strategy.service.armory.IStrategyDispatch;
import com.origamii.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.origamii.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Origami
 * @description 权重方法
 * @create 2024-09-12 14:11
 **/
@Slf4j
@Component("rule_weight")
public class RuleWeightLogicChain extends AbstractLogicChain {

    @Autowired
    private IStrategyRepository repository;

    @Autowired
    private IStrategyDispatch strategyDispatch;

    public Long userScore = 0L;


    /**
     * 权重责任链过滤：
     * 1. 权重规则格式 4000： 102 103 104 105
     * 2. 解析数据格式 判断哪个范围符合用户的特定抽奖范围
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @return
     */
    @Override
    public Integer logic(String userId, Long strategyId) {
        log.info("抽奖责任链-权重开始 userId{} strategyId{}, ruleModel{}", userId, strategyId, ruleModel());

        String ruleValue = repository.queryStrategyRuleValue(strategyId, ruleModel());

        // 1.根据用户ID查询用户抽奖消耗的积分值，本章节先写死为固定值，后续需要从数据库查询
        // TODO: 从数据库查询用户积分值
        Map<Long, String> analyticalValueGroup = getAnalyticalValue(ruleValue);
        if (null == analyticalValueGroup || analyticalValueGroup.isEmpty())
            return null;

        // 2.转换Keys值 并默认排序
        List<Long> analyticalSortedKeys = new ArrayList<>(analyticalValueGroup.keySet());
        Collections.sort(analyticalSortedKeys);

        // 3.找出最小符合的值
        Long nextValue = analyticalSortedKeys.stream()
                .sorted(Comparator.reverseOrder())
                .filter(analyticalSortedKeyValue -> userScore >= analyticalSortedKeyValue)
                .findFirst()
                .orElse(null);

        if(null != nextValue){
            Integer awardId = strategyDispatch.getRandomAwardId(strategyId, analyticalValueGroup.get(nextValue));
            log.info("抽奖责任链-权重接管 userId:{}, strategyId:{}, ruleModel:{}, awardId:{}",userId,strategyId,ruleModel(),awardId);
            return awardId;
        }

        // 4.过滤其他责任链
        log.info("抽奖责任链-权重放行 userId:{}, strategyId:{}, ruleModel:{}",userId,strategyId,ruleModel());
        return next().logic(userId, strategyId);
    }


    @Override
    protected String ruleModel() {
        return "rule_weight";
    }

    private Map<Long, String> getAnalyticalValue(String ruleValue) {
        String[] ruleValueGroups = ruleValue.split(Constants.SPACE);
        Map<Long,String> ruleValueMap = new HashMap<>();
        for(String ruleValueKey : ruleValueGroups){
            // 检查输入是否为空
            if(null == ruleValueKey || ruleValueKey.isEmpty()){
                return ruleValueMap;
            }
            // 分割字符串以获取键和值
            String[] parts = ruleValueKey.split(Constants.COLON);
            if(parts.length!= 2){
                throw new IllegalArgumentException("rule_weight rule_rule invalid input format"+ ruleValueKey);
            }
            ruleValueMap.put(Long.parseLong(parts[0]),ruleValueKey);
        }
        return ruleValueMap;
    }

}

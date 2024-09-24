package com.origamii.domain.strategy.service.rule.filter.factory;

import com.origamii.domain.strategy.model.entity.RuleActionEntity;
import com.origamii.domain.strategy.service.annotation.LogicStrategy;
import com.origamii.domain.strategy.service.rule.filter.ILogicFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Origami
 * @description 默认逻辑工厂，用于管理和提供逻辑过滤器
 * @create 2024-09-09 22:13
 **/
@Service
public class DefaultLogicFactory {

    public Map<String, ILogicFilter<?>> logicFilterMap = new ConcurrentHashMap<>();

    /**
     * DefaultLogicFactory 构造函数 用于初始化逻辑过滤器映射
     * @param logicFilters 逻辑过滤器列表
     */
    public DefaultLogicFactory(List<ILogicFilter<?>> logicFilters) {
        logicFilters.forEach(logic -> {
            LogicStrategy strategy = AnnotationUtils.findAnnotation(logic.getClass(), LogicStrategy.class);
            if (null != strategy) {
                logicFilterMap.put(strategy.logicMode().getCode(), logic);
            }
        });
    }

    /**
     * 打开逻辑过滤器
     * @param <T> 规则动作实体的子类
     * @return 逻辑过滤器映射
     */
    public <T extends RuleActionEntity.RaffleEntity> Map<String, ILogicFilter<T>> openLogicFilter() {
        return (Map<String, ILogicFilter<T>>) (Map<?, ?>) logicFilterMap;
    }

    @Getter
    @AllArgsConstructor
    public enum LogicModel{

        RULE_WEIGHT("rule_weight","【抽奖前规则】根据抽奖权重返回可抽奖范围KEY","before"),
        RULE_BLACKLIST("rule_blacklist","【抽奖前规则】黑名单规则过滤，如果在黑名单中则直接返回","before"),
        RULE_LOCK("rule_lock","【抽奖中规则】抽奖n次后，对应奖品课解锁抽奖","during"),
        RULE_LUCK_AWARD("rule_luck_award","【抽奖后规则】幸运奖兜底","after")
        ;

        private final String code;
        private final String info;
        private final String type;

        /**
         * 检查给定的代码是否属于" during "类型
         * @param code 逻辑模型代码
         * @return 是否为" during "类型
         */
        public static boolean isCenter(String code){
            return "during".equals(LogicModel.valueOf(code.toUpperCase()).getType());
        }

    }


}

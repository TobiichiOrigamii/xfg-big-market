package com.origamii.domain.strategy.repository;

import com.origamii.domain.strategy.model.entity.StrategyAwardEntity;
import com.origamii.domain.strategy.model.entity.StrategyEntity;
import com.origamii.domain.strategy.model.entity.StrategyRuleEntity;
import com.origamii.domain.strategy.model.valobj.RuleTreeVO;
import com.origamii.domain.strategy.model.valobj.StrategyAwardRuleModelVO;

import java.util.HashMap;
import java.util.List;

/**
 * @author Origami
 * @description 策略仓储接口
 * @create 2024-09-05 09:36
 **/
public interface IStrategyRepository {

    /**
     * 查询策略配置
     *
     * @param strategyId 策略ID
     * @return 策略奖项实体列表
     */
    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    /**
     * 存储策略奖励比例搜索表到 Redis 中
     *
     * @param key                                  策略ID
     * @param rateRange                            概率范围
     * @param shuffleStrategyAwardRateSearchTables 乱序的概率查找表
     */
    void storeStrategyAwardRateSearchTables(String key, Integer rateRange, HashMap<Integer, Integer> shuffleStrategyAwardRateSearchTables);

    /**
     * @param strategyId
     * @return
     */
    int getRateRange(Long strategyId);

    /**
     * @param key
     * @return
     */
    int getRateRange(String key);

    /**
     * @param key
     * @param rateKey
     * @return
     */
    Integer getStrategyAwardAssemble(String key, Integer rateKey);

    /**
     * @param strategyId
     * @return
     */
    StrategyEntity queryStrategyEntityByStrategy(Long strategyId);

    /**
     * 查询策略规则
     *
     * @param strategyId
     * @param ruleModel
     * @return
     */
    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel);

    String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel);

    String queryStrategyRuleValue(Long strategyId,String ruleModel);

    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);

    StrategyAwardRuleModelVO queryStrategyAwardRuleModel(Long strategyId, Integer awardId);

    RuleTreeVO queryRuleTreeVOByTreeId(String treeId);

    Boolean subtractionAwardStock(String cacheKey);

    void cacheStrategyAwardCount(String cacheKey, Integer awardCount);
}

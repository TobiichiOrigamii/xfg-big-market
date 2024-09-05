package com.origamii.domain.strategy.repository;

import com.origamii.domain.strategy.model.entity.StrategyAwardEntity;

import java.math.BigDecimal;
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
     * @param strategyId 策略ID
     * @param rateRange 概率范围
     * @param shuffleStrategyAwardRateSearchTables 乱序的概率查找表
     */
    void storeStrategyAwardRateSearchTables(Long strategyId, Integer rateRange, HashMap<Integer, Integer> shuffleStrategyAwardRateSearchTables);

    /**
     *
     * @param strategyId
     * @return
     */
    int getRateRange(Long strategyId);

    /**
     *
     * @param strategyId
     * @param rateKey
     * @return
     */
    Integer getStrategyAwardAssemble(Long strategyId, int rateKey);
}

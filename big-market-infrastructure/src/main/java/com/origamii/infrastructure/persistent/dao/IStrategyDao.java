package com.origamii.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import com.origamii.infrastructure.persistent.po.Strategy;

import java.util.List;

/**
 * @author Origami
 * @description 抽奖策略 DAO
 * @create 2024-07-18 17:47
 **/
@Mapper
public interface IStrategyDao {

    /**
     * 查询所有策略
     *
     * @return 策略列表
     */
    List<Strategy> queryStrategyList();

    /**
     * 根据策略 ID 查询策略
     * @param strategyId 策略 ID
     * @return 策略
     */
    Strategy queryStrategyByStrategyId(Long strategyId);


}

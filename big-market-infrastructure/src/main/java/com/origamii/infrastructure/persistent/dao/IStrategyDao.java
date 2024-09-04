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

    List<Strategy> queryStrategyList();

    Strategy queryStrategyByStrategyId(Long strategyId);


}

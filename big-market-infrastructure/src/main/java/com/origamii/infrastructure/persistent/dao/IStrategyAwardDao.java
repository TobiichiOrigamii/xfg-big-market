package com.origamii.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import com.origamii.infrastructure.persistent.po.StrategyAward;

import java.util.List;

/**
 * @author Origami
 * @description 抽奖策略奖品明细配置 DAO
 * @create 2024-07-18 17:47
 **/
@Mapper
public interface IStrategyAwardDao {

    List<StrategyAward> queryStrategyAwardList();

    // 从数据库中获取Key
    List<StrategyAward> queryStrategyAwardListByStrategyId(Long strategyId);

    String queryStrategyAwardRuleModel(StrategyAward strategyAward);

    void updateStrategyAwardStock(StrategyAward strategyAward);
}

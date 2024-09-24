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

    /**
     * 查询所有奖品配置
     * @return 奖品配置列表
     */
    List<StrategyAward> queryStrategyAwardList();

    /**
     * 根据策略ID查询奖品配置
     * @param strategyId 策略ID
     * @return 奖品配置列表
     */
    List<StrategyAward> queryStrategyAwardListByStrategyId(Long strategyId);

    /**
     * 根据策略ID和奖品ID查询奖品配置
     * @param strategyAward 策略奖品配置
     * @return 奖品配置
     */
    String queryStrategyAwardRuleModel(StrategyAward strategyAward);

    /**
     * 更新奖品库存
     * @param strategyAward 策略奖品配置
     */
    void updateStrategyAwardStock(StrategyAward strategyAward);

    /**
     * 新增奖品配置
     * @param strategyAwardReq 策略奖品配置请求
     * @return 新增的策略奖品配置ID
     */
    StrategyAward queryStrategyAward(StrategyAward strategyAwardReq);
}

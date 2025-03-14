package com.origamii.domain.strategy.service;

import com.origamii.domain.strategy.model.entity.RaffleAwardEntity;
import com.origamii.domain.strategy.model.entity.RaffleFactorEntity;
import com.origamii.domain.strategy.model.entity.StrategyAwardEntity;
import com.origamii.domain.strategy.repository.IStrategyRepository;
import com.origamii.domain.strategy.service.armory.IStrategyDispatch;
import com.origamii.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.origamii.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.origamii.types.enums.ResponseCode;
import com.origamii.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Origami
 * @description 抽奖策略抽象类 定义抽奖的标准流程
 * @create 2024-09-09 16:34
 **/
@Slf4j
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {

    // 策略仓储服务 - domin层是生产线 仓储层提供原材料
    @Autowired
    protected IStrategyRepository repository;
    // 策略调度服务 - 只负责抽奖处理 通过新增接口的方式 隔离职责 不需要使用方关心或者调用抽奖的初始化
    @Autowired
    protected IStrategyDispatch strategyDispatch;
    // 责任链工厂 - 从抽奖的规则中 解耦出前置规则为责任链处理
    @Autowired
    protected DefaultChainFactory defaultChainFactory;
    // 树工厂 - 从抽奖的规则中 解耦出抽奖树的构建
    @Autowired
    protected DefaultTreeFactory defaultTreeFactory;

    public AbstractRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch, DefaultChainFactory defaultChainFactory, DefaultTreeFactory defaultTreeFactory) {
    }

    /**
     * 抽奖策略抽象方法
     *
     * @param raffleFactorEntity 抽奖因素实体
     * @return 抽奖结果实体
     */
    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        // 1. 参数校验
        String userId = raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();
        if (null == strategyId || StringUtils.isBlank(userId)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2. 责任链抽奖计算【这步拿到的是初步的抽奖ID，之后需要根据ID处理抽奖】注意；黑名单、权重等非默认抽奖的直接返回抽奖结果
        DefaultChainFactory.StrategyAwardVO chainStrategyAwardVO = raffleLogicChain(userId, strategyId);
        log.info("抽奖策略计算-责任链 {} {} {} {}", userId, strategyId, chainStrategyAwardVO.getAwardId(), chainStrategyAwardVO.getLogicModel());
        if (!DefaultChainFactory.LogicModel.RULE_DEFAULT.getCode().equals(chainStrategyAwardVO.getLogicModel())) {
            return buildRaffleAwardEntity(strategyId, chainStrategyAwardVO.getAwardId(), chainStrategyAwardVO.getAwardRuleValue());
        }

        // 3. 规则树抽奖过滤【奖品ID，会根据抽奖次数判断、库存判断、兜底兜里返回最终的可获得奖品信息】
        DefaultTreeFactory.StrategyAwardVO treeStrategyAwardVO = raffleLogicTree(userId, strategyId, chainStrategyAwardVO.getAwardId(), raffleFactorEntity.getEndDateTime());
        log.info("抽奖策略计算-规则树 {} {} {} {}", userId, strategyId, treeStrategyAwardVO.getAwardId(), treeStrategyAwardVO.getAwardRuleValue());

        // 4. 返回抽奖结果
        return buildRaffleAwardEntity(strategyId, treeStrategyAwardVO.getAwardId(), treeStrategyAwardVO.getAwardRuleValue());
    }

    /**
     * 构建抽奖结果实体
     *
     * @param strategyId  策略ID
     * @param awardId     奖品ID
     * @param awardConfig 奖品配置
     * @return 抽奖结果实体
     */
    private RaffleAwardEntity buildRaffleAwardEntity(Long strategyId, Integer awardId, String awardConfig) {
        StrategyAwardEntity strategyAward = repository.queryStrategyAwardEntity(strategyId, awardId);
        return RaffleAwardEntity.builder()
                .awardId(awardId)
                .awardTitle(strategyAward.getAwardTitle())
                .awardConfig(awardConfig)
                .sort(strategyAward.getSort())
                .build();
    }

    /**
     * 抽奖计算，责任链抽象方法
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @return 奖品ID
     */
    public abstract DefaultChainFactory.StrategyAwardVO raffleLogicChain(String userId, Long strategyId);

    /**
     * 抽奖结果过滤，决策树抽象方法
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return 过滤结果【奖品ID，会根据抽奖次数判断、库存判断、兜底兜里返回最终可获得奖品信息】
     */
    public abstract DefaultTreeFactory.StrategyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId);

    /**
     * 抽奖结果过滤，决策树抽象方法
     *
     * @param userId      用户ID
     * @param strategyId  策略ID
     * @param awardId     奖品ID
     * @param endDateTime 抽奖结束时间
     * @return 过滤结果【奖品ID，会根据抽奖次数判断、库存判断、兜底兜里返回最终可获得奖品信息】
     */
    public abstract DefaultTreeFactory.StrategyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId, Date endDateTime);

}

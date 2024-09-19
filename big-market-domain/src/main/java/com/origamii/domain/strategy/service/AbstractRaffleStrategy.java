package com.origamii.domain.strategy.service;

import com.origamii.domain.strategy.model.entity.RaffleAwardEntity;
import com.origamii.domain.strategy.model.entity.RaffleFactorEntity;
import com.origamii.domain.strategy.model.entity.RuleActionEntity;
import com.origamii.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.origamii.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import com.origamii.domain.strategy.repository.IStrategyRepository;
import com.origamii.domain.strategy.service.armory.IStrategyDispatch;
import com.origamii.domain.strategy.service.rule.chain.ILogicChain;
import com.origamii.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.origamii.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.origamii.types.enums.ResponseCode;
import com.origamii.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Origami
 * @description 抽奖策略抽象类
 * @create 2024-09-09 16:34
 **/
@Slf4j
public abstract class AbstractRaffleStrategy implements IRaffleStrategy, IRaffleStock {

    // 策略仓储服务 - domin层是生产线 仓储层提供原材料
    protected IStrategyRepository strategyRepository;

    // 策略调度服务 - 只负责抽奖处理 通过新增接口的方式 隔离职责 不需要使用方关心或者调用抽奖的初始化
    protected IStrategyDispatch strategyDispatch;

    // 责任链工厂 - 从抽奖的规则中 解耦出前置规则为责任链处理
    private final DefaultChainFactory defaultChainFactory;

    // 树工厂 - 从抽奖的规则中 解耦出抽奖树的构建
    protected final DefaultTreeFactory defaultTreeFactory;

    // 通过构造函数注入仓储服务和调度服务
    public AbstractRaffleStrategy(IStrategyRepository strategyRepository, IStrategyDispatch strategyDispatch, DefaultChainFactory defaultChainFactory, DefaultTreeFactory defaultTreeFactory) {
        this.strategyRepository = strategyRepository;
        this.strategyDispatch = strategyDispatch;
        this.defaultChainFactory = defaultChainFactory;
        this.defaultTreeFactory = defaultTreeFactory;
    }

    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        // 1.参数校验
        String userId = raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();
        if (null == strategyId || StringUtils.isEmpty(userId)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2.责任链处理抽奖
        ILogicChain logicChain = defaultChainFactory.openLogicChain(strategyId);
        Integer awardId = logicChain.logic(userId, strategyId);


        // 3.查询奖品规则
        // 抽奖中 拿到奖品ID过滤规则
        // 抽奖后 扣减完奖品库存后过滤 抽奖中拦截和无库存则走兜底
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = strategyRepository.queryStrategyAwardRuleModel(strategyId, awardId);

        // 4.抽奖中 - 规则过滤
        RuleActionEntity<RuleActionEntity.RaffleDuringEntity> ruleActionDuringEntity = this.doCheckRaffleDuringLogic(RaffleFactorEntity.builder()
                        .userId(userId)
                        .strategyId(strategyId)
                        .awardId(awardId)
                        .build(),
                strategyAwardRuleModelVO.raffleCenterRuleModelList()
        );

        // 5.判断是否被规则接管
        if(RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionDuringEntity.getCode())){
            log.info("【临时日志】抽奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励");
            return RaffleAwardEntity.builder()
                    .awardDesc("抽奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励")
                    .build();
        }
        // TODO 库存数据扣减

        return RaffleAwardEntity.builder()
                .awardId(awardId)
                .build();
    }


    protected abstract RuleActionEntity<RuleActionEntity.RaffleDuringEntity> doCheckRaffleDuringLogic(RaffleFactorEntity raffleFactorEntity, String... logics);


}

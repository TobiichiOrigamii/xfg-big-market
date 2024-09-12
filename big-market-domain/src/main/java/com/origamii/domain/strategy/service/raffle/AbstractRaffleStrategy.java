package com.origamii.domain.strategy.service.raffle;

import com.origamii.domain.strategy.model.entity.RaffleAwardEntity;
import com.origamii.domain.strategy.model.entity.RaffleFactorEntity;
import com.origamii.domain.strategy.model.entity.RuleActionEntity;
import com.origamii.domain.strategy.model.entity.StrategyEntity;
import com.origamii.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.origamii.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import com.origamii.domain.strategy.repository.IStrategyRepository;
import com.origamii.domain.strategy.service.IRaffleStrategy;
import com.origamii.domain.strategy.service.armory.IStrategyDispatch;
import com.origamii.domain.strategy.service.rule.factory.DefaultLogicFactory;
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
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {

    // 策略仓储服务 - domin层是生产线 仓储层提供原材料
    protected IStrategyRepository strategyRepository;

    // 策略调度服务 - 只负责抽奖处理 通过新增接口的方式 隔离职责 不需要使用方关心或者调用抽奖的初始化
    protected IStrategyDispatch strategyDispatch;

    // 通过构造函数注入仓储服务和调度服务
    public AbstractRaffleStrategy(IStrategyRepository strategyRepository, IStrategyDispatch strategyDispatch) {
        this.strategyRepository = strategyRepository;
        this.strategyDispatch = strategyDispatch;
    }

    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        // 1.参数校验
        String userId = raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();
        if (null == strategyId || StringUtils.isEmpty(userId)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2.策略查询
        StrategyEntity strategy = strategyRepository.queryStrategyEntityByStrategyId(strategyId);

        // 3.抽奖前 - 规则过滤
        RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> ruleActionEntity = this.doCheckRaffleBeforeLogic(RaffleFactorEntity
                        .builder()
                        .userId(userId)
                        .strategyId(strategyId)
                        .build(),
                strategy.ruleModels()
        );

        // 4.被规则引擎接管且是黑名单用户
        if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionEntity.getCode())) {
            if (DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode().equals(ruleActionEntity.getRuleModel())) {
                return RaffleAwardEntity.builder()
                        .awardId(ruleActionEntity.getData().getAwardId())
                        .build();
            } else if (DefaultLogicFactory.LogicModel.RULE_WEIGHT.getCode().equals(ruleActionEntity.getRuleModel())) {
                // 5.根据权重返回的信息
                RuleActionEntity.RaffleBeforeEntity raffleBeforeEntity = ruleActionEntity.getData();
                String ruleWeightValueKey = raffleBeforeEntity.getRuleWeightValueKey();
                Integer awardId = strategyDispatch.getRandomAwardId(strategyId, ruleWeightValueKey);
                return RaffleAwardEntity.builder()
                        .awardId(awardId)
                        .build();
            }

        }

        // 6. 默认抽奖
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);

        // 7.查询奖品规则
        // 抽奖中 拿到奖品ID过滤规则
        // 抽奖后 扣减完奖品库存后过滤 抽奖中拦截和无库存则走兜底
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = strategyRepository.queryStrategyAwardRuleModel(strategyId, awardId);

        // 8.抽奖中 - 规则过滤
        RuleActionEntity<RuleActionEntity.RaffleDuringEntity> ruleActionDuringEntity = this.doCheckRaffleDuringLogic(RaffleFactorEntity.builder()
                        .userId(userId)
                        .strategyId(strategyId)
                        .awardId(awardId)
                        .build(),
                strategyAwardRuleModelVO.raffleCenterRuleModelList()
        );

        // 9.判断是否被规则接管
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

    protected abstract RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity raffleFactorEntity, String... logics);

    protected abstract RuleActionEntity<RuleActionEntity.RaffleDuringEntity> doCheckRaffleDuringLogic(RaffleFactorEntity raffleFactorEntity, String... logics);


}

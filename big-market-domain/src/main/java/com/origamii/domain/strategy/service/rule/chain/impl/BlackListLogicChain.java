package com.origamii.domain.strategy.service.rule.chain.impl;

import com.origamii.domain.strategy.repository.IStrategyRepository;
import com.origamii.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.origamii.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.origamii.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Origami
 * @description 黑名单责任链
 * @create 2024-09-12 14:10
 **/
@Slf4j
@Component("rule_blacklist")
public class BlackListLogicChain extends AbstractLogicChain {

    @Autowired
    private IStrategyRepository repository;

    /**
     * 判断用户是否在黑名单中
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @return 责任链结果
     */
    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        log.info("抽奖责任链-黑名单开始 userId:{},strategyId:{},ruleModel:{}", userId, strategyId, ruleModel());
        String ruleValue = repository.queryStrategyRuleValue(strategyId, ruleModel());
        String[] splitRuleValue = ruleValue.split(Constants.COLON);
        Integer awardId = Integer.parseInt(splitRuleValue[0]);

        // 过滤其他规则
        String[] userBlackIds = splitRuleValue[1].split(Constants.SPLIT);
        for (String userBlackId : userBlackIds) {
            if (userId.equals(userBlackId)) {
                log.info("抽奖责任链-黑名单接管 userId:{},strategyId:{},ruleModel:{},awardId:{}", userId, strategyId, ruleModel(), awardId);
                return DefaultChainFactory.StrategyAwardVO.builder()
                        .awardId(awardId)
                        .logicModel(ruleModel())
                        .awardRuleValue("0.01,1")
                        .build();
            }
        }
        // 过滤其他责任链
        log.info("抽奖责任链-黑名单放行 userId:{},strategyId:{},ruleModel:{}", userId, strategyId, ruleModel());
        return next().logic(userId, strategyId);
    }

    /**
     * 返回黑名单规则的代码
     *
     * @return 黑名单规则的代码
     */
    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_BLACKLIST.getCode();
    }
}

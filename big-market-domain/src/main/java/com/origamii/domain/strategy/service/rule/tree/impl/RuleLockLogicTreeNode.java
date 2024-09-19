package com.origamii.domain.strategy.service.rule.tree.impl;

import com.origamii.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.origamii.domain.strategy.repository.IStrategyRepository;
import com.origamii.domain.strategy.service.armory.IStrategyDispatch;
import com.origamii.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.origamii.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Origami
 * @description 次数锁节点
 * @create 2024-09-14 23:04
 **/
@Slf4j
@Component("rule_lock")
public class RuleLockLogicTreeNode implements ILogicTreeNode {


    // 用户抽奖次数，后续完成这部分流程开发的时候，从数据库/Redis中读取
    private Long userRaffleCount = 10L;


    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {

    }


}

package com.origamii.domain.strategy.service.rule.chain.impl;

import com.origamii.domain.strategy.service.rule.chain.ILogicChain;

/**
 * @author Origami
 * @description 装配接口
 * @create 2024-09-13 16:18
 **/
public interface ILogicChainArmory {

    //设置下一个责任链
    ILogicChain setNext(ILogicChain next);

    //获取下一个责任链
    ILogicChain next();

}

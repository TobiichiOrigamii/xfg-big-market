package com.origamii.domain.activity.service.rule;

/**
 * @author Origami
 * @description 抽奖动作责任链装配接口
 * @create 2024-09-28 10:55
 **/
public interface IActionChainArmory {

    IActionChain next();

    IActionChain setNext(IActionChain next);

}

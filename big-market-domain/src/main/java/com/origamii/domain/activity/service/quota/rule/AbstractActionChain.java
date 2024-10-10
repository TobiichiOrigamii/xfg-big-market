package com.origamii.domain.activity.service.quota.rule;

/**
 * @author Origami
 * @description 下单规则责任链抽象类
 * @create 2024-10-02 14:09
 **/
public abstract class AbstractActionChain implements IActionChain {

    private IActionChain next;

    @Override
    public IActionChain next() {
        return next;
    }

    @Override
    public IActionChain setNext(IActionChain next) {
        this.next = next;
        return next;
    }

}

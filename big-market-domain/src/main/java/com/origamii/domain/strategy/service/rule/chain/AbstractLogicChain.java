package com.origamii.domain.strategy.service.rule.chain;

/**
 * @author Origami
 * @description
 * @create 2024-09-12 14:04
 **/
public abstract class AbstractLogicChain implements ILogicChain {

    private ILogicChain next;


    //设置下一个责任链
    @Override
    public ILogicChain setNext(ILogicChain next) {
        this.next = next;
        return next;
    }


    //获取下一个责任链
    @Override
    public ILogicChain next() {
        return next;
    }


    protected abstract String ruleModel();
}

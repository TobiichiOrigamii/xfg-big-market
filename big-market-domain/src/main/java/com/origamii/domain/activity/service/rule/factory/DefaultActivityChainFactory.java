package com.origamii.domain.activity.service.rule.factory;

import com.origamii.domain.activity.service.rule.IActionChain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Origami
 * @description 责任链工厂
 * @create 2024-10-02 14:23
 **/
@Service
public class DefaultActivityChainFactory {

    private final IActionChain actionChain;

    public DefaultActivityChainFactory(Map<String, IActionChain> actionChainGroup) {
        actionChain = actionChainGroup.get(ActionModel.activity_base_action.code);
        actionChain.setNext(actionChainGroup.get(ActionModel.activity_sku_stock_action.code));
    }

    public IActionChain openActionChain() {
        return this.actionChain;
    }

    @Getter
    @AllArgsConstructor
    public enum ActionModel{
        activity_base_action("activity_base_action","活动时间、状态校验"),
        activity_sku_stock_action("activity_sku_stock_action","活动sku校验"),
        ;
        private final String code;
        private final String info;
    }

}

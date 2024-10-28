package com.origamii.domain.activity.service.quota.policy.impl;

import com.origamii.domain.activity.model.aggreate.CreateQuotaOrderAggregate;
import com.origamii.domain.activity.model.valobj.OrderStateVO;
import com.origamii.domain.activity.repository.IActivityRepository;
import com.origamii.domain.activity.service.quota.policy.ITradePolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Origami
 * @description 返利无支付交易订单 直接充值到账
 * @create 2024-10-28 21:14
 **/
@Service("rebate_no_pay_trade")
public class RebateNoPayTradePolicy implements ITradePolicy {

    @Autowired
    private IActivityRepository repository;

    @Override
    public void trade(CreateQuotaOrderAggregate createQuotaOrderAggregate) {
        // 不需要支付则修改订单金额为0 状态为完成 直接给用户账户充值
        createQuotaOrderAggregate.getActivityOrderEntity().setState(OrderStateVO.completed);
        createQuotaOrderAggregate.getActivityOrderEntity().setPayAmount(BigDecimal.ZERO);
        repository.saveNoPayOrder(createQuotaOrderAggregate);
    }


}

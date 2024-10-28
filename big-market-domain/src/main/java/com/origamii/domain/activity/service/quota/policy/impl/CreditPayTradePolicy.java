package com.origamii.domain.activity.service.quota.policy.impl;

import com.origamii.domain.activity.model.aggreate.CreateQuotaOrderAggregate;
import com.origamii.domain.activity.model.valobj.OrderStateVO;
import com.origamii.domain.activity.repository.IActivityRepository;
import com.origamii.domain.activity.service.quota.policy.ITradePolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Origami
 * @description 积分对话 支付类订单
 * @create 2024-10-28 21:14
 **/
@Service("credit_pay_trade")
public class CreditPayTradePolicy implements ITradePolicy {

    @Autowired
    private IActivityRepository repository;

    @Override
    public void trade(CreateQuotaOrderAggregate createQuotaOrderAggregate) {
        createQuotaOrderAggregate.getActivityOrderEntity().setState(OrderStateVO.wait_pay);
        repository.saveCreditPayOrder(createQuotaOrderAggregate);


    }


}

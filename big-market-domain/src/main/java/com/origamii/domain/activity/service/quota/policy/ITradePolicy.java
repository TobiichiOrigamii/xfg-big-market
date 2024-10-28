package com.origamii.domain.activity.service.quota.policy;

import com.origamii.domain.activity.model.aggreate.CreateQuotaOrderAggregate;

/**
 * @author Origami
 * @description
 * @create 2024-10-28 21:13
 **/
public interface ITradePolicy {

    void trade(CreateQuotaOrderAggregate createQuotaOrderAggregate);

}

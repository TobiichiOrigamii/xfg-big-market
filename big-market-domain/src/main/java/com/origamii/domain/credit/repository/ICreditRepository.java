package com.origamii.domain.credit.repository;

import com.origamii.domain.credit.model.aggreate.TradeAggregate;

/**
 * @author Origami
 * @description 积分仓储接口
 * @create 2024-10-27 21:32
 **/
public interface ICreditRepository {

    /**
     * 保存交易聚合对象
     *
     * @param tradeAggregate 交易聚合对象
     */
    void saveUserCreditTradeOrder(TradeAggregate tradeAggregate);



}

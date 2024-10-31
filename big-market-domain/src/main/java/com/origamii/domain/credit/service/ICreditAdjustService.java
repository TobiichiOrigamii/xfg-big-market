package com.origamii.domain.credit.service;

import com.origamii.domain.credit.model.entity.CreditAccountEntity;
import com.origamii.domain.credit.model.entity.TradeEntity;

/**
 * @author Origami
 * @description 积分调额接口【正逆向，增减积分】
 * @create 2024-10-27 21:34
 **/
public interface ICreditAdjustService {

    /**
     * 创建增加积分额度订单
     *
     * @param tradeEntity 交易实体对象
     * @return 单号
     */
    String createOrder(TradeEntity tradeEntity);

    /**
     * 查询用户积分账户
     *
     * @param userId 用户id
     * @return 用户积分账户
     */
    CreditAccountEntity queryUserCreditAccount(String userId);


}

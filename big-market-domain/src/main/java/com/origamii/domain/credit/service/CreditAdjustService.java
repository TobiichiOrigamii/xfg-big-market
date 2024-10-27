package com.origamii.domain.credit.service;

import com.origamii.domain.credit.model.aggreate.TradeAggregate;
import com.origamii.domain.credit.model.entity.CreditAccountEntity;
import com.origamii.domain.credit.model.entity.CreditOrderEntity;
import com.origamii.domain.credit.model.entity.TradeEntity;
import com.origamii.domain.credit.repository.ICreditRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Origami
 * @description
 * @create 2024-10-27 21:49
 **/
@Slf4j
@Service
public class CreditAdjustService implements ICreditAdjustService {

    @Autowired
    private ICreditRepository repository;

    /**
     * 创建订单
     *
     * @return 订单号
     * @description 创建订单
     */
    @Override
    public String createOrder(TradeEntity tradeEntity) {
        log.info("增加账户积分额度开始 userId:{}, tradeName:{}, amount:{}", tradeEntity.getUserId(), tradeEntity.getTradeName(), tradeEntity.getAmount());
        // 1.创建账户积分实体
        CreditAccountEntity creditAccountEntity = TradeAggregate.createCreditAccountEntity(tradeEntity.getUserId(), tradeEntity.getAmount());

        // 2.创建账户订单实体
        CreditOrderEntity creditOrderEntity = TradeAggregate.createCreditOrderEntity(tradeEntity.getUserId(), tradeEntity.getTradeName(), tradeEntity.getTradeType(), tradeEntity.getAmount(), tradeEntity.getOutBusinessNo());

        // 3.构建交易聚合对象
        TradeAggregate tradeAggregate = TradeAggregate.builder()
                .userId(tradeEntity.getUserId())
                .creditAccountEntity(creditAccountEntity)
                .creditOrderEntity(creditOrderEntity)
                .build();

        // 4.保存交易聚合对象
        repository.saveUserCreditTradeOrder(tradeAggregate);
        log.info("增加账户积分额度结束 userId:{}, tradeName:{}, amount:{}", tradeEntity.getUserId(), tradeEntity.getTradeName(), tradeEntity.getAmount());








        return null;
    }


}

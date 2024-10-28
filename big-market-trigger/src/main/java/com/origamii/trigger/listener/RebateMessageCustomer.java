package com.origamii.trigger.listener;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.origamii.domain.activity.model.entity.SkuRechargeEntity;
import com.origamii.domain.activity.model.valobj.OrderTradeTypeVO;
import com.origamii.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.origamii.domain.credit.model.entity.TradeEntity;
import com.origamii.domain.credit.model.valobj.TradeNameVO;
import com.origamii.domain.credit.model.valobj.TradeTypeVO;
import com.origamii.domain.credit.service.ICreditAdjustService;
import com.origamii.domain.rebate.event.SendRebateMessageEvent;
import com.origamii.types.enums.ResponseCode;
import com.origamii.types.event.BaseEvent;
import com.origamii.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author Origami
 * @description 监听返利消息
 * @create 2024-10-22 22:25
 **/
@Slf4j
@Component
public class RebateMessageCustomer {

    @Value("${spring.rabbitmq.topic.send_rebate}")
    private String topic;
    @Autowired
    private IRaffleActivityAccountQuotaService raffleActivityAccountQuotaService;
    @Autowired
    private ICreditAdjustService creditAdjustService;

    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.send_rebate}"))
    public void listener(String message) {
        try {
            log.info("【监听活动】用户行为返利消息 topic:{}, message:{}", topic, message);
            // 1.转换对象
            BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage> eventMessage = JSON.parseObject(message, new TypeReference<BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage>>() {
            }.getType());

            SendRebateMessageEvent.RebateMessage rebateMessage = eventMessage.getData();


            // 2. 入账奖励
            switch (rebateMessage.getRebateType()) {
                case "sku":
                    SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
                    skuRechargeEntity.setUserId(rebateMessage.getUserId());
                    skuRechargeEntity.setSku(Long.valueOf(rebateMessage.getRebateConfig()));
                    skuRechargeEntity.setOutBusinessNo(rebateMessage.getBizId());
                    skuRechargeEntity.setOrderTradeType(OrderTradeTypeVO.rebate_no_pay_trade);
                    raffleActivityAccountQuotaService.createOrder(skuRechargeEntity);
                    break;
                case "integral":
                    TradeEntity tradeEntity = new TradeEntity();
                    tradeEntity.setUserId(rebateMessage.getUserId());
                    tradeEntity.setTradeName(TradeNameVO.REBATE);
                    tradeEntity.setTradeType(TradeTypeVO.FORWARD);
                    tradeEntity.setAmount(new BigDecimal(rebateMessage.getRebateConfig()));
                    tradeEntity.setOutBusinessNo(rebateMessage.getBizId());
                    creditAdjustService.createOrder(tradeEntity);
                    break;
            }
        } catch (AppException e) {
            if (ResponseCode.INDEX_DUP.getCode().equals(e.getCode())) {
                log.info("【监听活动】用户行为返利消息 重复消费 topic:{}, message:{}", topic, message, e);
            }
            throw e;
        } catch (Exception e) {
            log.error("【监听活动】用户行为返利消息 消费失败 topic:{}, message:{}", topic, message, e);
            throw e;
        }


    }


}

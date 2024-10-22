package com.origamii.trigger.listener;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.origamii.domain.activity.model.entity.SkuRechargeEntity;
import com.origamii.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.origamii.domain.rebate.event.SendRebateMessageEvent;
import com.origamii.domain.rebate.model.valobj.RebateTypeVO;
import com.origamii.types.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.send_rebate}"))
    public void listener(String message) {
        try {
            log.info("【监听活动】用户行为返利消息 topic:{}, message:{}", topic, message);
            // 1.转换对象
            BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage> eventMessage = JSON.parseObject(message, new TypeReference<BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage>>() {
            }.getType());

            SendRebateMessageEvent.RebateMessage rebateMessage = eventMessage.getData();
            if (!RebateTypeVO.SKU.getCode().equals(rebateMessage.getRebateType())){
                log.info("【监听活动】用户行为返利消息 非SKU返利消息 topic:{}, message:{}", topic, message);
                return;
            }

            // 2.入账奖励
            SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
            skuRechargeEntity.setUserId(rebateMessage.getUserId());
            skuRechargeEntity.setSku(Long.valueOf(rebateMessage.getRebateType()));
            skuRechargeEntity.setOutBusinessNo(rebateMessage.getBizId());
            raffleActivityAccountQuotaService.createOrder(skuRechargeEntity);

        } catch (Exception e) {
            log.error("【监听活动】用户行为返利消息 消费失败 topic:{}, message:{}", topic, message, e);
            throw e;
        }


    }


}

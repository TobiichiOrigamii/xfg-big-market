package com.origamii.trigger.listener;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.origamii.domain.award.event.SendAwardMessageEvent;
import com.origamii.domain.award.model.entity.DistributeAwardEntity;
import com.origamii.domain.award.service.IAwardService;
import com.origamii.types.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Origami
 * @description 用户奖品记录消息消费者
 * @create 2024-10-14 23:40
 **/
@Slf4j
@Component
public class SendAwardCustomer {

    @Value("${spring.rabbitmq.topic.send_award}")
    private String topic;

    @Autowired
    private IAwardService awardService;

    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.send_award}"))
    public void listener(String message) {
        try {
            log.info("监听用户奖品发送消息 topic:{} message:{}", topic, message);
            // 转换数据
            BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage> eventMessage = JSON.parseObject(message, new TypeReference<BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage>>() {
            }.getType());
            SendAwardMessageEvent.SendAwardMessage sendAwardMessage = eventMessage.getData();

            DistributeAwardEntity distributeAwardEntity = DistributeAwardEntity.builder()
                    .userId(sendAwardMessage.getUserId())
                    .orderId(sendAwardMessage.getOrderId())
                    .awardId(sendAwardMessage.getAwardId())
                    .awardConfig(sendAwardMessage.getAwardConfig())
                    .build();
            awardService.distributeAward(distributeAwardEntity);
        } catch (Exception e) {
            log.error("用户奖品发送消息 消费失败 topic:{} message:{}", topic, message);
            throw e;
        }
    }

}

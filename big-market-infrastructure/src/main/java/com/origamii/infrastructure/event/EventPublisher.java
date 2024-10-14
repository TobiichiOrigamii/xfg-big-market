package com.origamii.infrastructure.event;

import com.alibaba.fastjson.JSON;
import com.origamii.types.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Origami
 * @description 消息发送
 * @create 2024-10-09 16:05
 **/
@Slf4j
@Component
public class EventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发布消息 - 需要序列化
     * @param topic 主题
     * @param eventMessage 事件消息
     */
    public void publish(String topic, BaseEvent.EventMessage<?> eventMessage) {
        try {
            String messageJson = JSON.toJSONString(eventMessage);
            rabbitTemplate.convertAndSend(topic, messageJson);
            log.info("发送MQ消息 topic:{}, message:{}", topic, messageJson);
        } catch (Exception e) {
            log.error("发送MQ消息失败 topic:{}, message:{}", topic, JSON.toJSONString(eventMessage), e);
            throw e;
        }
    }

    /**
     * 发布消息 - 直接发送json字符串
     * @param topic 主题
     * @param messageJson 消息json字符串
     */
    public void publish(String topic, String messageJson) {
        try {
            rabbitTemplate.convertAndSend(topic, messageJson);
            log.info("发送MQ消息 topic:{}, message:{}", topic, messageJson);
        } catch (Exception e) {
            log.error("发送MQ消息失败 topic:{}, message:{}", topic, messageJson, e);
            throw e;
        }
    }


}

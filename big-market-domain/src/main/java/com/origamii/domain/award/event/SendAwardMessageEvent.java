package com.origamii.domain.award.event;

import com.origamii.types.event.BaseEvent;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

/**
 * @author Origami
 * @description
 * @create 2024-10-14 13:23
 **/
public class SendAwardMessageEvent extends BaseEvent<SendAwardMessageEvent.SendAwardMessage> {

    @Value("${spring.rabbitmq.topic.send_award")
    private String topic;

    @Override
    public EventMessage<SendAwardMessage> buildEventMessage(SendAwardMessage data) {
        return EventMessage.<SendAwardMessage>builder()
                .id(RandomStringUtils.randomNumeric(11))
                .timestamp(new Date())
                .data(data)
                .build();
    }

    @Override
    public String topic() {
        return topic;
    }

    public static class SendAwardMessage {
        // 用户ID
        private String userId;
        // 奖品ID
        private String awardId;
        // 奖品名称
        private String awardTitle;
    }


}

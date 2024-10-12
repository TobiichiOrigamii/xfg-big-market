package com.origamii.domain.activity.event;

import com.origamii.types.event.BaseEvent;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Origami
 * @description 活动SKU库存清空消息
 * @create 2024-10-09 16:12
 **/
@Component
public class ActivitySkuStockZeroMessageEvent extends BaseEvent<Long> {

    @Value("${spring.rabbitmq.topic.activity_sku_stock_zero}")
    private String topic;

    /**
     * 构建消息
     * @param sku SKU
     * @return EventMessage
     */
    @Override
    public EventMessage<Long> buildEventMessage(Long sku) {
        return EventMessage.<Long>builder()
                .id(RandomStringUtils.randomNumeric(11))
                .timestamp(new Date())
                .data(sku)
                .build();
    }

    /**
     * 获取消息主题
     * @return 主题
     */
    @Override
    public String topic() {
        return topic;
    }
}

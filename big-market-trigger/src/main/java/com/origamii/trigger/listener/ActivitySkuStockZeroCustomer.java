package com.origamii.trigger.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.origamii.domain.activity.service.ISkuStock;
import com.origamii.types.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Origami
 * @description 活动sku库存耗尽
 * @create 2024-10-09 22:12
 **/
@Slf4j
@Component
public class ActivitySkuStockZeroCustomer {

    @Value("${spring.rabbitmq.topic.activity-sku-stock-zero}")
    private String topic;

    @Autowired
    private ISkuStock skuStock;

    @RabbitListener(queuesToDeclare = @Queue(value = "activity-sku-stock-zero"))
    public void listener(String message) {
        try {
            log.info("【监听活动】sku库存耗尽 topic:{} message={}", "activity-sku-stock-zero", message);
            // 转换对象
            BaseEvent.EventMessage<Long> eventMessage = JSON.parseObject(
                    message,
                    new TypeReference<BaseEvent.EventMessage<Long>>() {
                    }.getType()
            );
            Long sku = eventMessage.getData();
            // 更新库存
            skuStock.clearActivitySkuStock(sku);
            // 清空队列 此时不需要延迟更新数据库记录
            skuStock.clearQueueValue();
        } catch (Exception e) {
            log.error("【监听活动】sku库存耗尽 消费失败 topic:{} message={}", topic, message);
            throw e;
        }
    }


}

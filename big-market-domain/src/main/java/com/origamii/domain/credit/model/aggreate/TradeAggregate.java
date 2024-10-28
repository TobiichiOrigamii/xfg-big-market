package com.origamii.domain.credit.model.aggreate;

import com.origamii.domain.award.model.valobj.TaskStateVO;
import com.origamii.domain.credit.event.CreditAdjustSuccessMessageEvent;
import com.origamii.domain.credit.model.entity.CreditAccountEntity;
import com.origamii.domain.credit.model.entity.CreditOrderEntity;
import com.origamii.domain.credit.model.entity.TaskEntity;
import com.origamii.domain.credit.model.valobj.TradeNameVO;
import com.origamii.domain.credit.model.valobj.TradeTypeVO;
import com.origamii.types.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Origami
 * @description 交易聚合对象
 * @create 2024-10-27 21:45
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeAggregate {

    // 用户ID
    private String userId;

    // 积分账户实体
    private CreditAccountEntity creditAccountEntity;

    // 积分订单实体
    private CreditOrderEntity creditOrderEntity;

    // 任务实体 - 补偿MQ消息
    private TaskEntity taskEntity;

    public static CreditAccountEntity createCreditAccountEntity(String userId, BigDecimal adjustAmount) {
        return CreditAccountEntity.builder()
                .userId(userId)
                .adjustAmount(adjustAmount)
                .build();
    }


    public static CreditOrderEntity createCreditOrderEntity(String userId, TradeNameVO tradeName, TradeTypeVO tradeType, BigDecimal tradeAmount, String outBusinessNo) {
        return CreditOrderEntity.builder()
                .userId(userId)
                .tradeName(tradeName)
                .tradeType(tradeType)
                .tradeAmount(tradeAmount)
                .outBusinessNo(outBusinessNo)
                .build();
    }

    public static TaskEntity createTaskEntity(String userId, String topic, String messageId, BaseEvent.EventMessage<CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage> message) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setUserId(userId);
        taskEntity.setTopic(topic);
        taskEntity.setMessageId(messageId);
        taskEntity.setMessage(message);
        taskEntity.setState(TaskStateVO.create);
        return taskEntity;
    }



}

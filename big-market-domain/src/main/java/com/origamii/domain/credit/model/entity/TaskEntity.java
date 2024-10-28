package com.origamii.domain.credit.model.entity;

import com.origamii.domain.award.model.valobj.TaskStateVO;
import com.origamii.domain.credit.event.CreditAdjustSuccessMessageEvent;
import com.origamii.types.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Origami
 * @description 任务实体对象
 * @create 2024-10-28 22:40
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {

    // 用户ID
    private String userId;

    // 消息主体
    private String topic;

    // 消息编号
    private String messageId;

    // 消息主体
    private BaseEvent.EventMessage<CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage> message;

    // 任务状态 create - 创建 completed - 完成 fail - 失败
    private TaskStateVO state;



}

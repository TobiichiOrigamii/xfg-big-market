package com.origamii.domain.award.model.entity;

import com.origamii.domain.award.event.SendAwardMessageEvent;
import com.origamii.domain.award.model.valobj.TaskStateVO;
import com.origamii.types.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Origami
 * @description 任务实体对象
 * @create 2024-10-14 13:21
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {

    // 用户ID
    private String userId;

    // 消息主题
    private String topic;

    // 消息编号
    private String messageId;

    // 消息主体
    private BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage> message;

    // 任务状态
    private TaskStateVO state;

}

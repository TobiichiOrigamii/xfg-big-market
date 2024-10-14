package com.origamii.domain.task.model.entity;

import lombok.Data;

/**
 * @author Origami
 * @description 任务实体对象
 * @create 2024-10-14 22:36
 **/
@Data
public class TaskEntity {

    // 用户ID
    private String userId;

    // 消息主题
    private String topic;

    // 消息编号
    private String messageId;

    // 消息主体
    private String message;
}

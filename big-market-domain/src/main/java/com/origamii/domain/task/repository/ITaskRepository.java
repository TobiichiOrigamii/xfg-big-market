package com.origamii.domain.task.repository;

import com.origamii.domain.task.model.entity.TaskEntity;

import java.util.List;

/**
 * @author Origami
 * @description 任务服务仓储接口
 * @create 2024-10-14 22:39
 **/
public interface ITaskRepository {

    List<TaskEntity> queryNoSendMessageTaskList();

    void sendMessage(TaskEntity taskEntity);

    void updateTaskSendMessageCompleted(String userId, String messageId);

    void updateTaskSendMessageFail(String userId, String messageId);



}

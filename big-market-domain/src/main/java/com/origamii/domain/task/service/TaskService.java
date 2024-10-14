package com.origamii.domain.task.service;

import com.origamii.domain.task.model.entity.TaskEntity;
import com.origamii.domain.task.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Origami
 * @description 消息任务服务实现类
 * @create 2024-10-14 22:39
 **/
@Service
public class TaskService implements ITaskService {

    @Autowired
    private ITaskRepository taskRepository;

    /**
     * 查询发送MQ失败和超时1分钟未发送的MQ
     *
     * @return 未发送的任务消息列表10条
     */
    @Override
    public List<TaskEntity> queryNoSendMessageTaskList() {
        return taskRepository.queryNoSendMessageTaskList();
    }

    @Override
    public void sendMessage(TaskEntity taskEntity) {
        taskRepository.sendMessage(taskEntity);
    }

    @Override
    public void updateTaskSendMessageCompleted(String userId, String messageId) {
        taskRepository.updateTaskSendMessageCompleted(userId, messageId);
    }

    @Override
    public void updateTaskSendMessageFail(String userId, String messageId) {
        taskRepository.updateTaskSendMessageFail(userId, messageId);
    }
}

package com.origamii.infrastructure.persistent.repository;

import com.origamii.domain.task.model.entity.TaskEntity;
import com.origamii.domain.task.repository.ITaskRepository;
import com.origamii.infrastructure.event.EventPublisher;
import com.origamii.infrastructure.persistent.dao.ITaskDao;
import com.origamii.infrastructure.persistent.po.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Origami
 * @description 任务服务仓储实现
 * @create 2024-10-14 22:42
 **/
@Repository
public class TaskRepository implements ITaskRepository {

    @Autowired
    private ITaskDao taskDao;
    @Autowired
    private EventPublisher eventPublisher;


    @Override
    public List<TaskEntity> queryNoSendMessageTaskList() {
        List<Task> tasks = taskDao.queryNoSendMessageTaskList();
        ArrayList<TaskEntity> taskEntities = new ArrayList<>(tasks.size());
        for (Task task : tasks) {
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setUserId(task.getUserId());
            taskEntity.setTopic(task.getTopic());
            taskEntity.setMessageId(task.getMessageId());
            taskEntity.setMessage(task.getMessage());
            taskEntities.add(taskEntity);
        }
        return taskEntities;
    }

    @Override
    public void sendMessage(TaskEntity taskEntity) {
        eventPublisher.publish(taskEntity.getTopic(),taskEntity.getMessage());
    }

    @Override
    public void updateTaskSendMessageCompleted(String userId, String messageId) {
        Task taskREq = new Task();
        taskREq.setUserId(userId);
        taskREq.setMessageId(messageId);
        taskDao.updateTaskSendMessageCompleted(taskREq);
    }

    @Override
    public void updateTaskSendMessageFail(String userId, String messageId) {
        Task taskREq = new Task();
        taskREq.setUserId(userId);
        taskREq.setMessageId(messageId);
        taskDao.updateTaskSendMessageFail(taskREq);
    }
}

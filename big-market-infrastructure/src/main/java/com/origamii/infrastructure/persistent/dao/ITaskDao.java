package com.origamii.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import com.origamii.infrastructure.persistent.po.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Origami
 * @description 任务表，发送MQ
 * @create 2024-10-10 12:25
 **/
@Mapper
public interface ITaskDao {

    /**
     * 写入任务
     *
     * @param task task
     */
    void insert(Task task);

    /**
     * 更新任务状态为已发送MQ
     *
     * @param task task
     */
    @DBRouter
    void updateTaskSendMessageCompleted(Task task);

    /**
     * 更新任务状态为发送MQ失败
     *
     * @param task task
     */
    @DBRouter
    void updateTaskSendMessageFail(Task task);

    /**
     * 查询未发送MQ的任务列表
     *
     * @return 任务列表
     */
    List<Task> queryNoSendMessageTaskList();


}

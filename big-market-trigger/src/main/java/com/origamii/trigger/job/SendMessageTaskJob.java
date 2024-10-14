package com.origamii.trigger.job;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import com.origamii.domain.task.model.entity.TaskEntity;
import com.origamii.domain.task.service.ITaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Origami
 * @description 发送MQ消息任务队列
 * @create 2024-10-14 22:22
 **/
@Slf4j
@Component
public class SendMessageTaskJob {

    @Autowired
    private ITaskService taskService;
    @Autowired
    private ThreadPoolExecutor executor;
    @Autowired
    private IDBRouterStrategy dbRouter;

    @Scheduled(cron = "0/5 * * * * ?")
    public void exec() {
        try {
            // 不分库分表的情况 直接查询
            // List<TaskEntity> taskEntities = taskService.queryNoSendMessageTaskList();

            // 获取分库数量
            int dbCount = dbRouter.dbCount();

            for (int dbIndex = 1; dbIndex <= dbCount; dbIndex++) {
                int finalDbIndex = dbIndex;
                executor.execute(() -> {
                    try {
                        dbRouter.setDBKey(finalDbIndex);
                        dbRouter.setTBKey(0);

                        List<TaskEntity> taskEntities = taskService.queryNoSendMessageTaskList();
                        if (taskEntities.isEmpty()) return;

                        // 发送MQ消息
                        for (TaskEntity taskEntity : taskEntities) {
                            // 开启线程发送 提高发送效率 配置的线程池策略为 CallerRunsPolicy 在 ThreadPoolConfig 配置中有4个策略
                            executor.execute(() -> {
                                try {
                                    taskService.sendMessage(taskEntity);
                                    taskService.updateTaskSendMessageCompleted(taskEntity.getUserId(), taskEntity.getMessageId());
                                } catch (Exception e) {
                                    log.error("定时任务 发送MQ消息失败 userId:{}, Topic:{}", taskEntity.getUserId(), taskEntity.getTopic());
                                    taskService.updateTaskSendMessageFail(taskEntity.getUserId(), taskEntity.getMessageId());
                                }
                            });
                        }
                    } finally {
                        dbRouter.clear();
                    }
                });
            }
        } catch (Exception e) {
            log.error("定时任务 扫描MQ任务表发送消息失败", e);
        }
    }


}

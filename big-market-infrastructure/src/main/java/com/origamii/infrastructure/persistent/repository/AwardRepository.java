package com.origamii.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import com.alibaba.fastjson.JSON;
import com.origamii.domain.award.model.aggreate.UserAwardRecordAggregate;
import com.origamii.domain.award.model.entity.TaskEntity;
import com.origamii.domain.award.model.entity.UserAwardRecordEntity;
import com.origamii.domain.award.repository.IAwardRepository;
import com.origamii.infrastructure.event.EventPublisher;
import com.origamii.infrastructure.persistent.dao.ITaskDao;
import com.origamii.infrastructure.persistent.dao.IUserAwardRecordDao;
import com.origamii.infrastructure.persistent.dao.IUserRaffleOrderDao;
import com.origamii.infrastructure.persistent.po.Task;
import com.origamii.infrastructure.persistent.po.UserAwardRecord;
import com.origamii.infrastructure.persistent.po.UserRaffleOrder;
import com.origamii.types.enums.ResponseCode;
import com.origamii.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author Origami
 * @description 奖品服务实现类
 * @create 2024-10-14 21:06
 **/
@Slf4j
@Repository
public class AwardRepository implements IAwardRepository {

    @Autowired
    private ITaskDao taskDao;
    @Autowired
    private IUserAwardRecordDao userAwardRecordDao;
    @Autowired
    private IUserRaffleOrderDao userRaffleOrderDao;
    @Autowired
    private IDBRouterStrategy dbRouter;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private EventPublisher eventPublisher;

    /**
     * 保存用户奖励记录
     *
     * @param userAwardRecordAggregate 用户奖励记录聚合
     */
    @Override
    public void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate) {
        UserAwardRecordEntity userAwardRecordEntity = userAwardRecordAggregate.getUserAwardRecordEntity();
        TaskEntity taskEntity = userAwardRecordAggregate.getTaskEntity();
        String userId = userAwardRecordEntity.getUserId();
        Long activityId = userAwardRecordEntity.getActivityId();
        Integer awardId = userAwardRecordEntity.getAwardId();

        // 转换为PO对象 - 用户奖励记录
        UserAwardRecord userAwardRecord = new UserAwardRecord();
        userAwardRecord.setUserId(userAwardRecordEntity.getUserId());
        userAwardRecord.setActivityId(userAwardRecordEntity.getActivityId());
        userAwardRecord.setStrategyId(userAwardRecordEntity.getStrategyId());
        userAwardRecord.setOrderId(userAwardRecordEntity.getOrderId());
        userAwardRecord.setAwardId(userAwardRecordEntity.getAwardId());
        userAwardRecord.setAwardTitle(userAwardRecordEntity.getAwardTitle());
        userAwardRecord.setAwardTime(userAwardRecordEntity.getAwardTime());
        userAwardRecord.setAwardState(userAwardRecordEntity.getAwardState().getCode());

        // 转换为PO对象 - 任务
        Task task = new Task();
        task.setUserId(taskEntity.getUserId());
        task.setTopic(taskEntity.getTopic());
        task.setMessageId(taskEntity.getMessageId());
        task.setMessage(JSON.toJSONString(taskEntity.getMessage()));
        task.setState(taskEntity.getState().getCode());

        // 转换为PO对象 - 订单
        UserRaffleOrder userRaffleOrderReq = new UserRaffleOrder();
        userRaffleOrderReq.setUserId(userAwardRecordEntity.getUserId());
        userRaffleOrderReq.setOrderId(userAwardRecordEntity.getOrderId());

        try {
            dbRouter.doRouter(userId);
            transactionTemplate.execute(status -> {
                try {
                    // 写入记录
                    userAwardRecordDao.insert(userAwardRecord);
                    // 写入任务
                    taskDao.insert(task);
                    // 写入订单
                    int count = userRaffleOrderDao.updateUserRaffleOrderStateUsed(userRaffleOrderReq);
                    if (count != 1) {
                        status.setRollbackOnly();
                        log.error("写入中奖记录，用户抽奖单已使用过，不可重复抽奖 userId:{},activityId:{},awardId:{}", userId, activityId, awardId);
                        throw new AppException(ResponseCode.ACTIVITY_ORDER_ERROR.getCode(), ResponseCode.ACTIVITY_ORDER_ERROR.getInfo());
                    }
                    return 1;
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("写入中奖记录，唯一索引冲突 userId:{},activityId:{},awardId:{}", userId, activityId, awardId, e);
                    throw new AppException(ResponseCode.INDEX_DUP.getCode(), e);
                }
            });
        } finally {
            dbRouter.clear();
        }

        try {
            // 发送消息【在事务外执行 如果失败还有任务补偿机制】
            eventPublisher.publish(task.getTopic(), task.getMessage());
            // 更新数据库记录 task任务表
            taskDao.updateTaskSendMessageCompleted(task);
        } catch (Exception e) {
            log.error("写入中奖记录，发送MQ消息失败 userId:{} topic:{}", userId, task.getTopic());
            taskDao.updateTaskSendMessageFail(task);
        }
    }


}

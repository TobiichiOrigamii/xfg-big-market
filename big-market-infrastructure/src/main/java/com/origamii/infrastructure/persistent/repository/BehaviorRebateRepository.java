package com.origamii.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import com.alibaba.fastjson.JSON;
import com.origamii.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import com.origamii.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import com.origamii.domain.rebate.model.entity.TaskEntity;
import com.origamii.domain.rebate.model.valobj.BehaviorTypeVO;
import com.origamii.domain.rebate.model.valobj.DailyBehaviorRebateVO;
import com.origamii.domain.rebate.repository.IBehaviorRebateRepository;
import com.origamii.infrastructure.event.EventPublisher;
import com.origamii.infrastructure.persistent.dao.IDailyBehaviorRebateDao;
import com.origamii.infrastructure.persistent.dao.ITaskDao;
import com.origamii.infrastructure.persistent.dao.IUserBehaviorRebateOrderDao;
import com.origamii.infrastructure.persistent.po.DailyBehaviorRebate;
import com.origamii.infrastructure.persistent.po.Task;
import com.origamii.infrastructure.persistent.po.UserBehaviorRebateOrder;
import com.origamii.types.enums.ResponseCode;
import com.origamii.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Origami
 * @description 行为返利服务仓储
 * @create 2024-10-21 23:43
 **/
@Slf4j
@Repository
public class BehaviorRebateRepository implements IBehaviorRebateRepository {

    @Autowired
    private IDailyBehaviorRebateDao dailyBehaviorRebateDao;
    @Autowired
    IUserBehaviorRebateOrderDao userBehaviorRebateOrderDao;
    @Autowired
    private ITaskDao taskDao;
    @Autowired
    IDBRouterStrategy dbRouter;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private EventPublisher eventPublisher;


    /**
     * 查询每日行为返利配置
     *
     * @param behaviorTypeVO 行为类型
     * @return 每日行为返利配置
     */
    @Override
    public List<DailyBehaviorRebateVO> queryDailyBehaviorRebateConfig(BehaviorTypeVO behaviorTypeVO) {
        List<DailyBehaviorRebate> dailyBehaviorRebates = dailyBehaviorRebateDao.queryDailyBehaviorRebateConfigBehaviorType(behaviorTypeVO.getCode());
        List<DailyBehaviorRebateVO> dailyBehaviorRebateVOS = new ArrayList<>(dailyBehaviorRebates.size());
        for (DailyBehaviorRebate dailyBehaviorRebate : dailyBehaviorRebates) {
            dailyBehaviorRebateVOS.add(DailyBehaviorRebateVO.builder()
                    .behaviorType(dailyBehaviorRebate.getBehaviorType())
                    .rebateDesc(dailyBehaviorRebate.getRebateDesc())
                    .rebateType(dailyBehaviorRebate.getRebateType())
                    .rebateConfig(dailyBehaviorRebate.getRebateConfig())
                    .build());
        }
        return dailyBehaviorRebateVOS;
    }

    /**
     * 保存用户返利记录
     *
     * @param userId                   用户id
     * @param behaviorRebateAggregates 返利记录
     */
    @Override
    public void saveUserRebateRecord(String userId, List<BehaviorRebateAggregate> behaviorRebateAggregates) {
        try {
            dbRouter.doRouter(userId);
            transactionTemplate.execute(status -> {
                try {
                    for (BehaviorRebateAggregate behaviorRebateAggregate : behaviorRebateAggregates) {
                        BehaviorRebateOrderEntity behaviorRebateOrderEntity = behaviorRebateAggregate.getBehaviorRebateOrderEntity();
                        // 用户行为返利订单对象
                        UserBehaviorRebateOrder userBehaviorRebateOrder = new UserBehaviorRebateOrder();
                        userBehaviorRebateOrder.setUserId(behaviorRebateOrderEntity.getUserId());
                        userBehaviorRebateOrder.setOrderId(behaviorRebateOrderEntity.getOrderId());
                        userBehaviorRebateOrder.setBehaviorType(behaviorRebateOrderEntity.getBehaviorType());
                        userBehaviorRebateOrder.setRebateDesc(behaviorRebateOrderEntity.getRebateDesc());
                        userBehaviorRebateOrder.setRebateType(behaviorRebateOrderEntity.getRebateType());
                        userBehaviorRebateOrder.setRebateConfig(behaviorRebateOrderEntity.getRebateConfig());
                        userBehaviorRebateOrder.setOutBusinessNo(behaviorRebateOrderEntity.getOutBusinessNo());
                        userBehaviorRebateOrder.setBizId(behaviorRebateOrderEntity.getBizId());
                        userBehaviorRebateOrderDao.insert(userBehaviorRebateOrder);

                        // 任务对象
                        TaskEntity taskEntity = behaviorRebateAggregate.getTaskEntity();
                        Task task = new Task();
                        task.setUserId(taskEntity.getUserId());
                        task.setTopic(taskEntity.getTopic());
                        task.setMessageId(taskEntity.getMessageId());
                        task.setMessage(JSON.toJSONString(taskEntity.getMessage()));
                        task.setState(taskEntity.getState().getCode());
                        taskDao.insert(task);
                    }
                    return 1;
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("写入返利记录，唯一索引冲突 userId: {}", userId, e);
                    throw new AppException(ResponseCode.INDEX_DUP.getCode(), ResponseCode.INDEX_DUP.getInfo());
                }
            });
        } finally {
            dbRouter.clear();
        }

        // 同步发送MQ消息
        for (BehaviorRebateAggregate behaviorRebateAggregate : behaviorRebateAggregates) {
            TaskEntity taskEntity = behaviorRebateAggregate.getTaskEntity();
            Task task = new Task();
            task.setUserId(taskEntity.getUserId());
            task.setMessageId(taskEntity.getMessageId());
            try {
                // 发送消息【在事务外执行，如果失败还有任务补偿】
                eventPublisher.publish(taskEntity.getTopic(), taskEntity.getMessage());
                // 更新数据库记录，task 任务表
                taskDao.updateTaskSendMessageCompleted(task);
            } catch (Exception e) {
                log.error("写入返利记录，发送MQ消息失败 userId: {} topic: {}", userId, task.getTopic());
                taskDao.updateTaskSendMessageFail(task);
            }
        }

    }

    /**
     * 根据外部单号查询订单
     *
     * @param userId        用户id
     * @param outBusinessNo 外部单号
     * @return 订单列表
     */
    @Override
    public List<BehaviorRebateOrderEntity> queryOrderByOutBusinessNo(String userId, String outBusinessNo) {
        // 1.请求对象
        UserBehaviorRebateOrder userBehaviorRebateOrderReq = new UserBehaviorRebateOrder();
        userBehaviorRebateOrderReq.setUserId(userId);
        userBehaviorRebateOrderReq.setOutBusinessNo(outBusinessNo);

        // 2.查询数据库
        List<UserBehaviorRebateOrder> userBehaviorRebateOrderResList = userBehaviorRebateOrderDao.queryOrderByOutBusinessNo(userBehaviorRebateOrderReq);

        // 3.转换对象
        List<BehaviorRebateOrderEntity> behaviorRebateOrderEntityList = new ArrayList<>(userBehaviorRebateOrderResList.size());
        for (UserBehaviorRebateOrder userBehaviorRebateOrderRes : userBehaviorRebateOrderResList) {
            BehaviorRebateOrderEntity behaviorRebateOrderEntity = BehaviorRebateOrderEntity.builder()
                    .userId(userBehaviorRebateOrderRes.getUserId())
                    .orderId(userBehaviorRebateOrderRes.getOrderId())
                    .behaviorType(userBehaviorRebateOrderRes.getBehaviorType())
                    .rebateDesc(userBehaviorRebateOrderRes.getRebateDesc())
                    .rebateType(userBehaviorRebateOrderRes.getRebateType())
                    .rebateConfig(userBehaviorRebateOrderRes.getRebateConfig())
                    .outBusinessNo(userBehaviorRebateOrderRes.getOutBusinessNo())
                    .bizId(userBehaviorRebateOrderRes.getBizId())
                    .build();
            behaviorRebateOrderEntityList.add(behaviorRebateOrderEntity);
        }
        return behaviorRebateOrderEntityList;
    }


}

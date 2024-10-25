package com.origamii.domain.award.service;

import com.origamii.domain.award.event.SendAwardMessageEvent;
import com.origamii.domain.award.model.aggreate.UserAwardRecordAggregate;
import com.origamii.domain.award.model.entity.DistributeAwardEntity;
import com.origamii.domain.award.model.entity.TaskEntity;
import com.origamii.domain.award.model.entity.UserAwardRecordEntity;
import com.origamii.domain.award.model.valobj.TaskStateVO;
import com.origamii.domain.award.repository.IAwardRepository;
import com.origamii.domain.award.service.distribute.IDistributeAward;
import com.origamii.types.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Origami
 * @description
 * @create 2024-10-14 20:49
 **/
@Slf4j
@Service
public class AwardService implements IAwardService {

    @Autowired
    private IAwardRepository repository;
    @Autowired
    SendAwardMessageEvent sendAwardMessageEvent;
    @Autowired
    private Map<String, IDistributeAward> distributeAwardMap;

    /**
     * 保存用户奖励记录
     *
     * @param userAwardRecordEntity 用户奖励记录实体
     */
    @Override
    public void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity) {
        // 构建消息实体
        SendAwardMessageEvent.SendAwardMessage sendAwardMessage = SendAwardMessageEvent.SendAwardMessage.builder()
                .userId(userAwardRecordEntity.getUserId())
                .orderId(userAwardRecordEntity.getOrderId())
                .awardId(userAwardRecordEntity.getAwardId())
                .awardTitle(userAwardRecordEntity.getAwardTitle())
                .awardConfig(userAwardRecordEntity.getAwardConfig())
                .build();

        BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage> sendAwardMessageEventMessage = sendAwardMessageEvent.buildEventMessage(sendAwardMessage);

        // 构建任务对象
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setUserId(userAwardRecordEntity.getUserId());
        taskEntity.setTopic(sendAwardMessageEvent.topic());
        taskEntity.setMessageId(sendAwardMessageEventMessage.getId());
        taskEntity.setMessage(sendAwardMessageEventMessage);
        taskEntity.setState(TaskStateVO.create);

        // 构建聚合对象
        UserAwardRecordAggregate userAwardRecordAggregate = UserAwardRecordAggregate.builder()
                .userAwardRecordEntity(userAwardRecordEntity)
                .taskEntity(taskEntity)
                .build();

        // 存储聚合对象 - 一个事务下用户的中奖记录
        repository.saveUserAwardRecord(userAwardRecordAggregate);


    }

    /**
     * 发放奖品
     *
     * @param distributeAwardEntity 奖品发放实体
     */
    @Override
    public void distributeAward(DistributeAwardEntity distributeAwardEntity) {
        // 查询奖品Key
        String awardKey = repository.queryAwardKey(distributeAwardEntity.getAwardId());
        if (null == awardKey) {
            log.error("分发奖品 奖品ID不存在 awardId:{}", distributeAwardEntity.getAwardId());
            return;
        }

        IDistributeAward distributeAward = distributeAwardMap.get(awardKey);
        if (null == distributeAward) {
            log.error("分发奖品 奖品类型不存在 awardKey:{}", awardKey);
            // TODO 这里应该抛出异常，但是这里暂时不做处理，因为奖品类型实现还未完成
            // throw new RuntimeException("奖品类型 " + awardKey + " 不存在");
            return;
        }

        // 发放奖品
        distributeAward.giveOutPrizes(distributeAwardEntity);


    }


}

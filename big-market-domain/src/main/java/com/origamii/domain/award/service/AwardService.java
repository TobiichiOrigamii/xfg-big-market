package com.origamii.domain.award.service;

import com.origamii.domain.award.event.SendAwardMessageEvent;
import com.origamii.domain.award.model.aggreate.UserAwardRecordAggregate;
import com.origamii.domain.award.model.entity.TaskEntity;
import com.origamii.domain.award.model.entity.UserAwardRecordEntity;
import com.origamii.domain.award.model.valobj.TaskStateVO;
import com.origamii.domain.award.repository.IAwardRepository;
import com.origamii.types.event.BaseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Origami
 * @description
 * @create 2024-10-14 20:49
 **/
@Service
public class AwardService implements IAwardService {

    @Autowired
    private IAwardRepository repository;

    @Autowired
    SendAwardMessageEvent sendAwardMessageEvent;

    /**
     * 保存用户奖励记录
     *
     * @param userAwardRecordEntity 用户奖励记录实体
     */
    @Override
    public void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity) {
        // 构建消息实体
        SendAwardMessageEvent.SendAwardMessage sendAwardMessage = new SendAwardMessageEvent.SendAwardMessage();
        sendAwardMessage.setUserId(userAwardRecordEntity.getUserId());
        sendAwardMessage.setAwardId(userAwardRecordEntity.getAwardId());
        sendAwardMessage.setAwardTitle(userAwardRecordEntity.getAwardTitle());
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


}

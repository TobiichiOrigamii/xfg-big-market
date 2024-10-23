package com.origamii.domain.rebate.service;

import com.origamii.domain.award.model.valobj.TaskStateVO;
import com.origamii.domain.rebate.event.SendRebateMessageEvent;
import com.origamii.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import com.origamii.domain.rebate.model.entity.BehaviorEntity;
import com.origamii.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import com.origamii.domain.rebate.model.entity.TaskEntity;
import com.origamii.domain.rebate.model.valobj.DailyBehaviorRebateVO;
import com.origamii.domain.rebate.repository.IBehaviorRebateRepository;
import com.origamii.types.common.Constants;
import com.origamii.types.event.BaseEvent;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Origami
 * @description 行为返利服务实现类
 * @create 2024-10-21 23:44
 **/
@Service
public class BehaviorRebateService implements IBehaviorRebateService {

    @Autowired
    private IBehaviorRebateRepository repository;
    @Autowired
    private SendRebateMessageEvent sendRebateMessageEvent;

    /**
     * 创建订单
     *
     * @param behaviorEntity 订单实体
     * @return 订单行为
     */
    @Override
    public List<String> createOrder(BehaviorEntity behaviorEntity) {
        // 1.查询返利配置
        List<DailyBehaviorRebateVO> dailyBehaviorRebateVOS = repository.queryDailyBehaviorRebateConfig(behaviorEntity.getBehaviorTypeVO());
        if (null == dailyBehaviorRebateVOS || dailyBehaviorRebateVOS.isEmpty())
            return new ArrayList<>();

        // 2.构建聚合对象
        List<String> orderIds = new ArrayList<>();
        List<BehaviorRebateAggregate> behaviorRebateAggregates = new ArrayList<>();

        for (DailyBehaviorRebateVO dailyBehaviorRebateVO : dailyBehaviorRebateVOS) {
            // 3.拼装业务ID： 用户ID_反例类型_外部透传业务ID
            String bizId = behaviorEntity.getUserId() +
                    Constants.UNDERLINE +
                    dailyBehaviorRebateVO.getRebateType() +
                    Constants.UNDERLINE +
                    behaviorEntity.getOutBusinessNo();
            // 4.构建订单实体
            BehaviorRebateOrderEntity behaviorRebateOrderEntity = BehaviorRebateOrderEntity.builder()
                    .userId(behaviorEntity.getUserId())
                    .orderId(RandomStringUtils.randomNumeric(12))
                    .behaviorType(dailyBehaviorRebateVO.getBehaviorType())
                    .rebateDesc(dailyBehaviorRebateVO.getRebateDesc())
                    .rebateType(dailyBehaviorRebateVO.getRebateType())
                    .rebateConfig(dailyBehaviorRebateVO.getRebateConfig())
                    .outBusinessNo(behaviorEntity.getOutBusinessNo())
                    .bizId(bizId)
                    .build();
            orderIds.add(behaviorRebateOrderEntity.getOrderId());

            // 5.MQ消息对象
            SendRebateMessageEvent.RebateMessage rebateMessage = SendRebateMessageEvent.RebateMessage.builder()
                    .userId(behaviorEntity.getUserId())
                    .rebateConfig(dailyBehaviorRebateVO.getRebateConfig())
                    .rebateType(dailyBehaviorRebateVO.getRebateType())
                    .bizId(bizId)
                    .build();

            // 6.组装MQ消息对象
            BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage> rebateMessageEventMessage = sendRebateMessageEvent.buildEventMessage(rebateMessage);


            // 7.组装任务对象
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setUserId(behaviorEntity.getUserId());
            taskEntity.setTopic(sendRebateMessageEvent.topic());
            taskEntity.setMessageId(rebateMessageEventMessage.getId());
            taskEntity.setMessage(rebateMessageEventMessage);
            taskEntity.setState(TaskStateVO.create);

            // 8.创建聚合对象
            BehaviorRebateAggregate behaviorRebateAggregate = BehaviorRebateAggregate.builder()
                    .userId(behaviorEntity.getUserId())
                    .behaviorRebateOrderEntity(behaviorRebateOrderEntity)
                    .taskEntity(taskEntity)
                    .build();

            behaviorRebateAggregates.add(behaviorRebateAggregate);
         }
        // 9.存储聚合对象数据
        repository.saveUserRebateRecord(behaviorEntity.getUserId(), behaviorRebateAggregates);

        // 10.返回订单ID集合
        return orderIds;
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
        return repository.queryOrderByOutBusinessNo(userId, outBusinessNo);

    }


}

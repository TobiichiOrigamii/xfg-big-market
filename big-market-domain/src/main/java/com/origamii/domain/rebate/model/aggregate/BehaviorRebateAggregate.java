package com.origamii.domain.rebate.model.aggregate;

import com.origamii.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import com.origamii.domain.rebate.model.entity.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Origami
 * @description 行为返利聚合对象
 * @create 2024-10-21 23:27
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BehaviorRebateAggregate {

    //用户ID
    private String userId;

    //行为返利订单实体对象
    private BehaviorRebateOrderEntity behaviorRebateOrderEntity;

    //任务实体对象
    private TaskEntity taskEntity;

}

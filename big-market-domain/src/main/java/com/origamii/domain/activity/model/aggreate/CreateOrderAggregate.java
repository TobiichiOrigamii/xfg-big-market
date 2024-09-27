package com.origamii.domain.activity.model.aggreate;

import com.origamii.domain.activity.model.entity.ActivityAccountEntity;
import com.origamii.domain.activity.model.entity.ActivityOrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Origami
 * @description 下单聚合对象
 * @create 2024-09-27 15:34
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderAggregate {

    //活动账户实体
    private ActivityAccountEntity activityAccountEntity;

    //活动订单实体
    private ActivityOrderEntity activityOrderEntity;

}

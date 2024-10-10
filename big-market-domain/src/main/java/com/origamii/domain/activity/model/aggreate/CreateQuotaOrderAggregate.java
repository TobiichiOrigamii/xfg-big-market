package com.origamii.domain.activity.model.aggreate;

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
public class CreateQuotaOrderAggregate {

    //用户ID
    private String userId;

    //活动ID
    private Long activityId;

    //总次数
    private Integer totalCount;

    //日次数
    private Integer dayCount;

    //月次数
    private Integer monthCount;

    //活动订单实体
    private ActivityOrderEntity activityOrderEntity;

}

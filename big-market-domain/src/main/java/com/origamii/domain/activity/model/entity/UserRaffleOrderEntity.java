package com.origamii.domain.activity.model.entity;

import com.origamii.domain.activity.model.valobj.UserRaffleOrderStateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Origami
 * @description 用户抽奖订单实体对象
 * @create 2024-10-10 22:24
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRaffleOrderEntity {

    // 用户ID
    private String userId;

    // 活动ID
    private Long activityId;

    // 活动名称
    private String activityName;

    // 抽奖策略ID
    private Long strategyId;

    // 订单ID
    private String orderId;

    // 下单时间
    private Date orderTime;

    // 订单状态 created-创建 used-已使用 cancel-已作废
    private UserRaffleOrderStateVO orderState;

    // 活动结束时间
    private Date endDateTime;

}

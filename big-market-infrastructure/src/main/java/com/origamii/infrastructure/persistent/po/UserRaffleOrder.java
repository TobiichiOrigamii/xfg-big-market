package com.origamii.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Origami
 * @description 用户抽奖订单表
 * @create 2024-10-10 12:23
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRaffleOrder {

    // 自增ID
    private String id;

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

    // 订单状态 create-创建、used-已使用、cancel-已作废
    private String orderState;

    // 创建时间
    private Date createTime;

    // 更新时间
    private Date updateTime;

}

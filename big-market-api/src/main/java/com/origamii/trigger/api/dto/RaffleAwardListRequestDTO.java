package com.origamii.trigger.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Origami
 * @description 抽奖奖品列表 请求对象
 * @create 2024-09-24 10:23
 **/
@Data
public class RaffleAwardListRequestDTO implements Serializable {

    // 抽奖策略ID
    @Deprecated
    private Long strategyId;

    // 活动ID
    private Long activityId;

    // 用户ID
    private String userId;

}

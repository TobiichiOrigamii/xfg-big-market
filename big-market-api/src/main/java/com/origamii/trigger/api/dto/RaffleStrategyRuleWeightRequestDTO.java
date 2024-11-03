package com.origamii.trigger.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Origami
 * @description 抽奖策略规则 权重配置 查询N次抽奖课解锁奖品范围 请求对象
 * @create 2024-10-23 23:50
 **/
@Data
public class RaffleStrategyRuleWeightRequestDTO implements Serializable {

    // 用户ID
    private String userId;

    // 活动ID
    private Long activityId;

}

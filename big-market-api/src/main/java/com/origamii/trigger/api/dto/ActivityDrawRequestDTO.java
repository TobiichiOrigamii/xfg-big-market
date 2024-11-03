package com.origamii.trigger.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Origami
 * @description 活动抽奖请求对象
 * @create 2024-10-15 13:20
 **/
@Data
public class ActivityDrawRequestDTO implements Serializable {

    // 用户ID
    private String userId;
    // 活动ID
    private Long activityId;

}

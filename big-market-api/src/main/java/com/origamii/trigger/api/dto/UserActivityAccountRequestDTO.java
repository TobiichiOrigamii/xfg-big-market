package com.origamii.trigger.api.dto;

import lombok.Data;

/**
 * @author Origami
 * @description 用户活动账户请求对象
 * @create 2024-10-23 23:14
 **/
@Data
public class UserActivityAccountRequestDTO {

    // 用户ID
    private String userId;

    // 活动ID
    private Long activityId;

}

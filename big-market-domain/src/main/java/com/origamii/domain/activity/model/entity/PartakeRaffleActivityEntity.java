package com.origamii.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Origami
 * @description 臭鱼抽奖活动实体对象
 * @create 2024-10-10 22:22
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartakeRaffleActivityEntity {

    // 用户ID
    private String userId;

    // 活动ID
    private Long activityId;

}

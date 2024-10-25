package com.origamii.domain.award.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Origami
 * @description 分发奖品实体对象
 * @create 2024-10-25 20:27
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistributeAwardEntity {

    // 用户ID
    private String userId;

    // 订单ID
    private String orderId;

    // 奖品ID
    private Integer awardId;

    // 奖品配置
    private String awardConfig;

}

package com.origamii.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Origami
 * @description 抽奖因子实体
 * @create 2024-09-09 16:27
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleFactorEntity {

    // 用户名ID
    private String userId;

    // 策略ID
    private Long strategyId;

    // 活动结束时间
    private Date endDateTime;

}

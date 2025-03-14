package com.origamii.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Origami
 * @description 抽奖奖品实体
 * @create 2024-09-09 16:28
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleAwardEntity {

    // 奖品ID
    private Integer awardId;

    // 奖品标题
    private String awardTitle;

    // 奖品配置信息
    private String awardConfig;

    // 奖品顺序号
    private Integer sort;

    // 活动结束时间
    private Date endDateTime;

}

package com.origamii.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Origami
 * @description 抽奖奖品列表 应答对象
 * @create 2024-09-24 10:25
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleAwardListResponseDTO {

    // 奖品ID
    private Integer awardId;

    // 奖品标题
    private String awardTitle;

    // 奖品副标题【抽奖1次后解锁】
    private String awardSubTitle;

    // 排序编号
    private Integer sort;

}

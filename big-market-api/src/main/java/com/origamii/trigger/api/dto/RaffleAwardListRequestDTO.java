package com.origamii.trigger.api.dto;

import lombok.Data;

/**
 * @author Origami
 * @description 抽奖奖品列表 请求对象
 * @create 2024-09-24 10:23
 **/
@Data
public class RaffleAwardListRequestDTO {

    // 抽奖策略ID
    private Long strategyId;
}

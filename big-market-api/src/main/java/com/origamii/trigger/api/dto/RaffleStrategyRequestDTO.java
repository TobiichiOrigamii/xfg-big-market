package com.origamii.trigger.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Origami
 * @description 抽奖请求参数
 * @create 2024-09-24 10:29
 **/
@Data
public class RaffleStrategyRequestDTO implements Serializable {

    // 抽奖策略ID
    private Long strategyId;


}

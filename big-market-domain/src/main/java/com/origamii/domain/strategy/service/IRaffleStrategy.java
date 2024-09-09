package com.origamii.domain.strategy.service;

import com.origamii.domain.strategy.model.entity.RaffleAwardEntity;
import com.origamii.domain.strategy.model.entity.RaffleFactorEntity;

/**
 * @author Origami
 * @description 抽奖
 * @create 2024-09-09 16:23
 **/
public interface IRaffleStrategy {

    RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity);

}

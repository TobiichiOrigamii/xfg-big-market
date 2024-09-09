package com.origamii.domain.strategy.service.raffle;

import com.origamii.domain.strategy.model.entity.RaffleAwardEntity;
import com.origamii.domain.strategy.model.entity.RaffleFactorEntity;
import com.origamii.domain.strategy.service.IRaffleStrategy;

/**
 * @author Origami
 * @description 抽奖策略抽象类
 * @create 2024-09-09 16:34
 **/
public class AbstractRaffleStrategy implements IRaffleStrategy {


    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        return null;
    }
}

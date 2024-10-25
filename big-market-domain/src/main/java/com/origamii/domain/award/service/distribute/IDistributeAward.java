package com.origamii.domain.award.service.distribute;

import com.origamii.domain.award.model.entity.DistributeAwardEntity;

/**
 * @author Origami
 * @description 分发奖品接口
 * @create 2024-10-25 20:26
 **/
public interface IDistributeAward {

    /**
     * 分发奖品
     * @param distributeAwardEntity 奖品信息
     */
    void giveOutPrizes(DistributeAwardEntity distributeAwardEntity);

}

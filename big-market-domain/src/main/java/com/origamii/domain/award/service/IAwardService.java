package com.origamii.domain.award.service;

import com.origamii.domain.award.model.entity.DistributeAwardEntity;
import com.origamii.domain.award.model.entity.UserAwardRecordEntity;

/**
 * @author Origami
 * @description 奖品服务接口
 * @create 2024-10-14 13:08
 **/
public interface IAwardService {

    /**
     * 保存用户奖品记录
     * @param userAwardRecordEntity 用户奖品记录实体
     */
    void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity);

    /**
     * 发放奖品
     * @param distributeAwardEntity 奖品发放实体
     */
    void distributeAward(DistributeAwardEntity distributeAwardEntity);

}

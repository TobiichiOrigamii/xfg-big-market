package com.origamii.domain.award.repository;

import com.origamii.domain.award.model.aggreate.UserAwardRecordAggregate;

/**
 * @author Origami
 * @description 奖品仓储服务接口
 * @create 2024-10-14 20:46
 **/
public interface IAwardRepository {
    /**
     * 保存用户奖励记录
     *
     * @param userAwardRecordAggregate 用户奖励记录聚合
     */
    void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate);


}

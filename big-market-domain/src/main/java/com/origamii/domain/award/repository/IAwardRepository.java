package com.origamii.domain.award.repository;

import com.origamii.domain.award.model.aggreate.GiveOutPrizesAggregate;
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

    /**
     * 查询奖品配置
     * @param awardId 奖品id
     * @return 奖品配置
     */
    String queryAwardConfig(Integer awardId);

    /**
     * 保存发放奖品记录
     * @param giveOutPrizesAggregate 发放奖品记录聚合
     */
    void saveGiveOutPrizesAggregate(GiveOutPrizesAggregate giveOutPrizesAggregate);

    /**
     * 查询奖品key
     * @param awardId 奖品id
     * @return 奖品key
     */
    String queryAwardKey(Integer awardId);
}

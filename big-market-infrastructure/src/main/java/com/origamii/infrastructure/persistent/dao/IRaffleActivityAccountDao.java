package com.origamii.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import com.origamii.infrastructure.persistent.po.RaffleActivityAccount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Origami
 * @description 抽奖活动账户表Dao
 * @create 2024-09-26 14:34
 **/
@Mapper
public interface IRaffleActivityAccountDao {

    /**
     * 更新账户额度
     *
     * @param raffleActivityAccount 账户信息
     * @return 更新结果
     */
    int updateAccountQuota(RaffleActivityAccount raffleActivityAccount);

    /**
     * 插入账户信息
     *
     * @param raffleActivityAccount 账户信息
     */
    void insert(RaffleActivityAccount raffleActivityAccount);

    /**
     * 根据用户id查询账户信息
     *
     * @param raffleActivityAccountReq 账户信息
     * @return 账户信息
     */
    @DBRouter
    RaffleActivityAccount queryActivityAccountByUserId(RaffleActivityAccount raffleActivityAccountReq);

    /**
     * 更新总账户
     *
     * @param raffleActivityAccount 账户信息
     * @return 更新结果
     */
    int updateActivityAccountSubtractionQuota(RaffleActivityAccount raffleActivityAccount);

    /**
     * 更新月镜像账户
     *
     * @param raffleActivityAccount 账户信息
     */
    void updateActivityAccountMonthSurplusImageQuota(RaffleActivityAccount raffleActivityAccount);

    /**
     * 更新日镜像账户
     *
     * @param raffleActivityAccount 账户信息
     */
    void updateActivityAccountDaySurplusImageQuota(RaffleActivityAccount raffleActivityAccount);

    /**
     * 根据用户id查询账户信息
     * @param raffleActivityAccount 账户信息
     * @return 账户信息
     */
    RaffleActivityAccount queryAccountByUserId(RaffleActivityAccount raffleActivityAccount);
}

package com.origamii.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import com.origamii.infrastructure.persistent.po.RaffleActivityAccountMonth;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Origami
 * @description 奖活动账户表-月次数
 * @create 2024-10-12 18:15
 **/
@Mapper
public interface IRaffleActivityAccountMonthDao {

    /**
     * 查询活动账户月表
     * @param raffleActivityAccountMonth 月账户表
     * @return 月账户表
     */
    @DBRouter
    RaffleActivityAccountMonth queryActivityAccountMonthByUserId(RaffleActivityAccountMonth raffleActivityAccountMonth);

    /**
     * 更新活动账户月表
     * @param raffleActivityAccountMonth 月账户表
     * @return 更新结果
     */
    int updateActivityAccountMonthSubtractionQuota(RaffleActivityAccountMonth raffleActivityAccountMonth);

    /**
     * 插入活动账户月表
     * @param raffleActivityAccountMonth 月账户表
     */
    void insertActivityAccountMonth(RaffleActivityAccountMonth raffleActivityAccountMonth);




}

package com.origamii.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import com.origamii.infrastructure.persistent.po.RaffleActivityAccountDay;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Origami
 * @description 抽奖活动账户表-日次数
 * @create 2024-10-12 18:40
 **/
@Mapper
public interface IRaffleActivityAccountDayDao {

    /**
     * 查询活动账户日表
     *
     * @param raffleActivityAccountDay 月账户表
     * @return 月账户表
     */
    @DBRouter
    RaffleActivityAccountDay queryActivityAccountDayByUserId(RaffleActivityAccountDay raffleActivityAccountDay);

    /**
     * 更新账户日数据
     *
     * @param raffleActivityAccountDay 账户日数据
     * @return 更新结果
     */
    int updateActivityAccountDaySubtractionQuota(RaffleActivityAccountDay raffleActivityAccountDay);

    /**
     * 插入账户日数据
     *
     * @param raffleActivityAccountDay 账户日数据
     */
    void insertActivityAccountDay(RaffleActivityAccountDay raffleActivityAccountDay);

    /**
     * 查询用户当日抽奖次数
     *
     * @param raffleActivityAccountDay 用户信息
     * @return 用户当日抽奖次数
     */
    @DBRouter
    Integer queryRaffleActivityAccountDayPartakeCount(RaffleActivityAccountDay raffleActivityAccountDay);

    /**
     * 更新账户日数据
     * @param raffleActivityAccountDay 账户日数据
     */
    void addAccountQuota(RaffleActivityAccountDay raffleActivityAccountDay);
}

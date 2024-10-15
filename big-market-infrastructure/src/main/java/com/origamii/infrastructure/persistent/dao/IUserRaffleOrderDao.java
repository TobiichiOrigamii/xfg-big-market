package com.origamii.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import com.origamii.infrastructure.persistent.po.UserRaffleOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Origami
 * @description 用户抽奖订单表
 * @create 2024-10-10 12:26
 **/
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserRaffleOrderDao {

    /**
     * 插入用户抽奖订单
     * @param userRaffleOrder 用户抽奖订单
     */
    void insert(UserRaffleOrder userRaffleOrder);

    /**
     * 根据用户id和抽奖id查询用户抽奖订单
     * @param userRaffleOrder 用户抽奖订单
     * @return 用户抽奖订单
     */
    @DBRouter
    UserRaffleOrder queryNoUsedRaffleOrder(UserRaffleOrder userRaffleOrder);

    /**
     * 更新用户抽奖订单状态为已使用
     * @param userRaffleOrderReq 用户抽奖订单
     * @return 更新数量
     */
    int updateUserRaffleOrderStateUsed(UserRaffleOrder userRaffleOrderReq);
}

package com.origamii.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import com.origamii.infrastructure.persistent.po.RaffleActivityOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Origami
 * @description 抽奖活动单Dao
 * @create 2024-09-26 14:34
 **/
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IRaffleActivityOrderDao {

    /**
     * 插入抽奖活动单
     *
     * @param raffleActivityOrder 抽奖活动单
     */
    void insert(RaffleActivityOrder raffleActivityOrder);

    /**
     * 根据用户id查询抽奖活动单
     *
     * @param userId 用户id
     * @return 抽奖活动单列表
     */
    @DBRouter
    List<RaffleActivityOrder> queryRaffleActivityOrderByUserId(String userId);

    /**
     * 查询抽奖活动单
     * @param raffleActivityOrderReq 抽奖活动单请求
     * @return 抽奖活动单
     */
    @DBRouter
    RaffleActivityOrder queryRaffleActivityOrder(RaffleActivityOrder raffleActivityOrderReq);

    /**
     * 更新订单状态为已完成
     * @param raffleActivityOrderReq 订单请求
     * @return 更新结果
     */
    int updateOrderCompleted(RaffleActivityOrder raffleActivityOrderReq);

    /**
     * 查询未支付的活动订单
     * @param raffleActivityOrderReq 订单请求
     * @return 未支付的活动订单
     */
    @DBRouter
    RaffleActivityOrder queryUnpaidActivityOrder(RaffleActivityOrder raffleActivityOrderReq);
}

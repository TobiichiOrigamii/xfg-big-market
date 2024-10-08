package com.origamii.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import com.origamii.infrastructure.persistent.po.RaffleActivityOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Origami
 * @description 抽奖活动单Dao
 * @create 2024-09-26 14:34
 **/
@Mapper
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


}

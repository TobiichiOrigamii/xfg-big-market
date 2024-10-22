package com.origamii.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import com.origamii.infrastructure.persistent.po.UserBehaviorRebateOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Origami
 * @description 用户行为返利流水订单表
 * @create 2024-10-21 23:04
 **/
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserBehaviorRebateOrderDao {

    /**
     * 插入用户行为返利流水订单
     *
     * @param userBehaviorRebateOrder 用户行为返利流水订单
     */
    void insert(UserBehaviorRebateOrder userBehaviorRebateOrder);
}

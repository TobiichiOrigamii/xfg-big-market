package com.origamii.infrastructure.persistent.dao;

import com.origamii.infrastructure.persistent.po.RaffleActivity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Origami
 * @description 抽奖活动表Dao
 * @create 2024-09-26 14:33
 **/
@Mapper
public interface IRaffleActivityDao {

    /**
     * 根据活动id查询抽奖活动
     *
     * @param activityId 活动id
     * @return 抽奖活动
     */
    RaffleActivity queryRaffleActivityByActivityId(Long activityId);

    /**
     * 根据活动id查询策略id
     *
     * @param activityId 活动id
     * @return 策略id
     */
    Long queryStrategyIdByActivityId(Long activityId);
}

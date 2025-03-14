package com.origamii.infrastructure.persistent.dao;

import com.origamii.infrastructure.persistent.po.RaffleActivityCount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Origami
 * @description 抽奖活动次数配置表Dao
 * @create 2024-09-26 14:34
 **/
@Mapper
public interface IRaffleActivityCountDao {

    RaffleActivityCount queryRaffleActivityCountByActivityCountId(Long activityCountId);

}



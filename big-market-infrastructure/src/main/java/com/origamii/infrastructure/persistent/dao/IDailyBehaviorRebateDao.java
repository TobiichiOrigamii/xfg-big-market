package com.origamii.infrastructure.persistent.dao;

import com.origamii.infrastructure.persistent.po.DailyBehaviorRebate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Origami
 * @description 日常行为返利活动配置
 * @create 2024-10-21 22:59
 **/
@Mapper
public interface IDailyBehaviorRebateDao {

    /**
     * 查询日常行为返利活动配置
     * @param behaviorType 活动编码
     * @return 日常行为返利活动配置列表
     */
    List<DailyBehaviorRebate> queryDailyBehaviorRebateConfigBehaviorType(String behaviorType);
}

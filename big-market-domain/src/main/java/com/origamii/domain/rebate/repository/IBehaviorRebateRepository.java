package com.origamii.domain.rebate.repository;

import com.origamii.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import com.origamii.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import com.origamii.domain.rebate.model.valobj.BehaviorTypeVO;
import com.origamii.domain.rebate.model.valobj.DailyBehaviorRebateVO;

import java.util.List;

/**
 * @author Origami
 * @description 行为返利服务仓储接口
 * @create 2024-10-21 23:42
 **/
public interface IBehaviorRebateRepository {

    /**
     * 查询每日行为返利配置
     *
     * @param behaviorTypeVO 行为类型
     * @return 每日行为返利配置
     */
    List<DailyBehaviorRebateVO> queryDailyBehaviorRebateConfig(BehaviorTypeVO behaviorTypeVO);

    /**
     * 保存用户返利记录
     *
     * @param userId                   用户id
     * @param behaviorRebateAggregates 返利记录
     */
    void saveUserRebateRecord(String userId, List<BehaviorRebateAggregate> behaviorRebateAggregates);

    /**
     * 根据外部单号查询订单
     *
     * @param userId        用户id
     * @param outBusinessNo 外部单号
     * @return 订单列表
     */
    List<BehaviorRebateOrderEntity> queryOrderByOutBusinessNo(String userId, String outBusinessNo);

}

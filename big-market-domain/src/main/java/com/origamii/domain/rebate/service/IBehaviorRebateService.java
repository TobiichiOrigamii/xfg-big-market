package com.origamii.domain.rebate.service;

import com.origamii.domain.rebate.model.entity.BehaviorEntity;
import com.origamii.domain.rebate.model.entity.BehaviorRebateOrderEntity;

import java.util.List;

/**
 * @author Origami
 * @description 行为返利服务接口
 * @create 2024-10-21 23:29
 **/
public interface IBehaviorRebateService {

    /**
     * 创建订单
     *
     * @param behaviorEntity 订单实体
     * @return 订单行为
     */
    List<String> createOrder(BehaviorEntity behaviorEntity);

    /**
     * 根据外部单号查询订单
     *
     * @param userId        用户id
     * @param outBusinessNo 外部单号
     * @return 订单列表
     */
    List<BehaviorRebateOrderEntity> queryOrderByOutBusinessNo(String userId, String outBusinessNo);


}

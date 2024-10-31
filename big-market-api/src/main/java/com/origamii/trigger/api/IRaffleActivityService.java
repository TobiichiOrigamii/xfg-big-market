package com.origamii.trigger.api;


import com.origamii.trigger.api.dto.*;
import com.origamii.types.model.Response;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Origami
 * @description 抽奖活动服务
 * @create 2024-10-15 13:17
 **/
public interface IRaffleActivityService {

    /**
     * 活动装配 数据预热缓存
     *
     * @param activityId 活动ID
     * @return 装配结果
     */
    Response<Boolean> armory(Long activityId);

    /**
     * 抽奖
     *
     * @param request 抽奖请求
     * @return 抽奖结果
     */
    Response<ActivityDrawResponseDTO> draw(ActivityDrawRequestDTO request);

    /**
     * 签到返利
     *
     * @param userId 用户ID
     * @return 签到结果
     */
    Response<Boolean> calendarSignRebate(String userId);

    /**
     * 判断是否完成日历签到返利
     *
     * @param userId 用户ID
     * @return 是否完成日历签到返利
     */
    Response<Boolean> isCalendarSignRebate(String userId);

    /**
     * 查询用户活动账户信息
     *
     * @param request 查询请求
     * @return 用户活动账户信息
     */
    Response<UserActivityAccountResponseDTO> queryUserActivityAccount(UserActivityAccountRequestDTO request);

    /**
     * 查询活动SKU商品列表
     * @param activityId 活动ID
     * @return 活动SKU商品列表
     */
    Response<List<SkuProductResponseDTO>> querySkuProductListByActivityId(Long activityId);

    /**
     * 查询用户积分
     * @param userId 用户ID
     * @return 用户积分
     */
    Response<BigDecimal> queryUserCreditAccount(String userId);

    /**
     * 积分兑换商品SKU
     * @return 积分兑换商品SKU结果
     */
    Response<Boolean> creditPayExchangeSku(@RequestBody SkuProductShopCartRequestDTO request);
}

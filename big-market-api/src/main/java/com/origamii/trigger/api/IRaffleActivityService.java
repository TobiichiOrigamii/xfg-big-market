package com.origamii.trigger.api;


import com.origamii.trigger.api.dto.ActivityDrawRequestDTO;
import com.origamii.trigger.api.dto.ActivityDrawResponseDTO;
import com.origamii.types.model.Response;

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

}

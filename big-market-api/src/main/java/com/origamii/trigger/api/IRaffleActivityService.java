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


    Response<ActivityDrawResponseDTO> draw(ActivityDrawRequestDTO request);



}

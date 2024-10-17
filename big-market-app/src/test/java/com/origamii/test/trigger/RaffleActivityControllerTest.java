package com.origamii.test.trigger;

import com.alibaba.fastjson.JSON;
import com.origamii.trigger.api.IRaffleActivityService;
import com.origamii.trigger.api.dto.ActivityDrawRequestDTO;
import com.origamii.trigger.api.dto.ActivityDrawResponseDTO;
import com.origamii.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Origami
 * @description 抽奖活动控制器测试类
 * @create 2024-10-17 23:05
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivityControllerTest {

    @Autowired
    private IRaffleActivityService raffleActivityService;

    @Test
    public void test_armory(){
        Response<Boolean> response = raffleActivityService.armory(100301L);
        log.info("测试结果：{}", JSON.toJSONString(response));
    }

    @Test
    public void test_draw(){
        ActivityDrawRequestDTO request = new ActivityDrawRequestDTO();
        request.setActivityId(100301L);
        request.setUserId("origami");
        Response<ActivityDrawResponseDTO> response = raffleActivityService.draw(request);
        log.info("请求参数：{}", JSON.toJSONString(request));
        log.info("测试结果：{}", JSON.toJSONString(response));
    }


}

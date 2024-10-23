package com.origamii.test.trigger;

import com.alibaba.fastjson.JSON;
import com.origamii.trigger.api.IRaffleStrategyService;
import com.origamii.trigger.api.dto.RaffleAwardListRequestDTO;
import com.origamii.trigger.api.dto.RaffleAwardListResponseDTO;
import com.origamii.trigger.api.dto.RaffleStrategyRuleWeightRequestDTO;
import com.origamii.trigger.api.dto.RaffleStrategyRuleWeightResponseDTO;
import com.origamii.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Origami
 * @description 营销抽奖服务测试
 * @create 2024-10-17 21:06
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleStrategyControllerTest {

    @Autowired
    private IRaffleStrategyService raffleStrategyService;

    @Test
    public void test_queryRaffleAwardList(){
        RaffleAwardListRequestDTO request = new RaffleAwardListRequestDTO();
        request.setUserId("origami");
        request.setActivityId(100301L);
        Response<List<RaffleAwardListResponseDTO>> response = raffleStrategyService.queryRaffleAwardList(request);
        log.info("请求参数：{}", JSON.toJSONString(request));
        log.info("响应结果：{}", JSON.toJSONString(response));
    }

    @Test
    public void test_queryRaffleStrategyRuleWeight() {
        RaffleStrategyRuleWeightRequestDTO request = new RaffleStrategyRuleWeightRequestDTO();
        request.setUserId("origami");
        request.setActivityId(100301L);

        Response<List<RaffleStrategyRuleWeightResponseDTO>> response = raffleStrategyService.queryRaffleStrategyRuleWeight(request);
        log.info("请求参数：{}", JSON.toJSONString(request));
        log.info("测试结果：{}", JSON.toJSONString(response));
    }

}

package com.origamii.test.domin.rebate;

import com.alibaba.fastjson2.JSON;
import com.origamii.domain.rebate.model.entity.BehaviorEntity;
import com.origamii.domain.rebate.model.valobj.BehaviorTypeVO;
import com.origamii.domain.rebate.service.IBehaviorRebateService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Origami
 * @description 用户行为返利服务单元测试类
 * @create 2024-10-22 21:47
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BehaviorRebateServiceTest {

    @Autowired
    private IBehaviorRebateService behaviorRebateService;

    @Test
    public void test_create_order() {
        BehaviorEntity behaviorEntity = new BehaviorEntity();
        behaviorEntity.setUserId("origami");
        behaviorEntity.setBehaviorTypeVO(BehaviorTypeVO.SIGN);
        // 重复的 OutBusinessNo 会保存唯一索引冲突，是保证幂等的手段，确保不会多记账
        behaviorEntity.setOutBusinessNo("20241022");

        List<String> orderIds = behaviorRebateService.createOrder(behaviorEntity);
        log.info("请求参数：{}", JSON.toJSONString(behaviorEntity));
        log.info("测试结果：{}", JSON.toJSONString(orderIds));
    }


}

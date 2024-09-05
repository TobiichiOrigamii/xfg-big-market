package com.origamii.test.domin;

import com.origamii.domain.strategy.service.armory.IStrategyArmory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Origami
 * @description
 * @create 2024-09-05 13:25
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyAmoryTest {


    @Resource
    private IStrategyArmory strategyAmory;


    @Test
    public void testStrategyAmory() {
        strategyAmory.assembleLotteryStrategy(100002L);
    }


    @Test
    public void testStrategyAmory2() {
        log.info("测试结果：{} - 奖品ID值：",strategyAmory.getRandomAwardId(100002L));
        log.info("测试结果：{} - 奖品ID值：",strategyAmory.getRandomAwardId(100002L));
        log.info("测试结果：{} - 奖品ID值：",strategyAmory.getRandomAwardId(100002L));
    }
}


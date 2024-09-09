package com.origamii.test.domin;

import com.origamii.domain.strategy.service.armory.IStrategyArmory;
import com.origamii.domain.strategy.service.armory.IStrategyDispatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Resource
    private IStrategyDispatch strategyDispatch;


    /**
     * 装配概率权重
     */
    @Before
    public void test_strategyAmory() {
        strategyAmory.assembleLotteryStrategy(100001L);
    }


    /**
     * 测试 无策略ID 随机获取奖品ID值
     */
    @Test
    public void test_getRandomAwardId() {
        log.info("测试结果：{} - 奖品ID值：", strategyDispatch.getRandomAwardId(100001L));
        log.info("测试结果：{} - 奖品ID值：", strategyDispatch.getRandomAwardId(100001L));
        log.info("测试结果：{} - 奖品ID值：", strategyDispatch.getRandomAwardId(100001L));
    }

    /**
     * 测试 装配了策略ID 随机获取奖品ID值
     */
    @Test
    public void test_getRandomAwardId_withRule() {
        log.info("测试结果：{} - 4000策略配置", strategyDispatch.getRandomAwardId(100001L, "4000:102,103,104,105"));
        log.info("测试结果：{} - 5000策略配置", strategyDispatch.getRandomAwardId(100001L, "5000:102,103,104,105,106,107"));
        log.info("测试结果：{} - 6000策略配置", strategyDispatch.getRandomAwardId(100001L, "6000:102,103,104,105,106,107,108,109"));
    }


}


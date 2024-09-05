package com.origamii.domain.strategy.service.armory;

import com.origamii.domain.strategy.model.entity.StrategyAwardEntity;
import com.origamii.domain.strategy.repository.IStrategyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author Origami
 * @description 策略装配库实现类 负责具体实现装配策略的逻辑
 * @create 2024-09-05 09:34
 **/
@Slf4j
@Service
public class StrategyArmory implements IStrategyArmory {

    // 注入策略仓储，用于查询和存储策略配置
    @Autowired
    private IStrategyRepository repository;

    /**
     * 策略装配库
     * @param strategyId 策略ID
     */
    @Override
    public void assembleLotteryStrategy(Long strategyId) {
        // 1.查询策略配置 获取与策略ID关联的奖品信息
        List<StrategyAwardEntity> strategyAwardEntities = repository.queryStrategyAwardList(strategyId);

        // 2.获取最小概率值
        BigDecimal minAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        // 3.获取概率值总和
        BigDecimal totalAwardRate = strategyAwardEntities.stream()
               .map(StrategyAwardEntity::getAwardRate)
               .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4.获取概率范围
        BigDecimal rateRange = totalAwardRate.divide(minAwardRate, 0, RoundingMode.CEILING);

        // 5. 初始化概率查找表，大小为计算出的概率范围长度
        ArrayList<Integer> strategyAwardRateSearchTables = new ArrayList<>(rateRange.intValue());
        for(StrategyAwardEntity strategyAward : strategyAwardEntities){
            Integer awardId = strategyAward.getAwardId();
            BigDecimal awardRate = strategyAward.getAwardRate();

            // 6. 计算出每个概率值需要存放到查找表的数量 循环填充
            for(int i = 0; i < rateRange.multiply(awardRate).setScale(0, RoundingMode.CEILING).intValue();i++){
                strategyAwardRateSearchTables.add(awardId);
            }
        }
        
        // 7.乱序
        Collections.shuffle(strategyAwardRateSearchTables);

        // 8.保存到策略配置中
        HashMap<Integer, Integer> shuffleStrategyAwardRateSearchTables = new HashMap<>();
        for(int i = 0; i < strategyAwardRateSearchTables.size(); i++) {
            shuffleStrategyAwardRateSearchTables.put(i, strategyAwardRateSearchTables.get(i));
        }

        // 9.存储到Redis中
        repository.storeStrategyAwardRateSearchTables(strategyId, shuffleStrategyAwardRateSearchTables.size(), shuffleStrategyAwardRateSearchTables);
    }

    /**
     * 获取随机奖品ID
     * @param strategyId 策略ID
     * @return
     */
    @Override
    public Integer getRandomAwardId(Long strategyId) {
        int rateRange = repository.getRateRange(strategyId);
        return repository.getStrategyAwardAssemble(strategyId,new SecureRandom().nextInt(rateRange));
    }




}

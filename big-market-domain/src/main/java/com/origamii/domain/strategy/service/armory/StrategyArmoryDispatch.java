package com.origamii.domain.strategy.service.armory;

import com.origamii.domain.strategy.model.entity.StrategyAwardEntity;
import com.origamii.domain.strategy.model.entity.StrategyEntity;
import com.origamii.domain.strategy.model.entity.StrategyRuleEntity;
import com.origamii.domain.strategy.repository.IStrategyRepository;
import com.origamii.types.common.Constants;
import com.origamii.types.enums.ResponseCode;
import com.origamii.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

/**
 * @author Origami
 * @description 策略装配库实现类 负责具体实现装配策略的逻辑
 * @create 2024-09-05 09:34
 **/
@Slf4j
@Service
public class StrategyArmoryDispatch implements IStrategyArmory, IStrategyDispatch {

    // 注入策略仓储，用于查询和存储策略配置
    @Autowired
    private IStrategyRepository repository;

    private final SecureRandom secureRandom = new SecureRandom();

    /**
     * 根据活动ID装配策略
     *
     * @param activityId 活动ID
     * @return 装配结果
     */
    @Override
    public boolean assembleLotteryStrategyByActivityId(Long activityId) {
        Long strategyId = repository.queryStrategyByActivityId(activityId);
        return assembleLotteryStrategy(strategyId);
    }

    /**
     * 策略装配库
     *
     * @param strategyId 策略ID
     */
    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        // 1.查询策略配置 获取与策略ID关联的奖品信息
        List<StrategyAwardEntity> strategyAwardEntities = repository.queryStrategyAwardList(strategyId);

        // 2.缓存奖品库存【用于decr扣减库存使用】
        for (StrategyAwardEntity strategyAwardEntity : strategyAwardEntities) {
            Integer awardId = strategyAwardEntity.getAwardId();
            Integer awardCount = strategyAwardEntity.getAwardCount();
            cacheStrategyAwardCount(strategyId, awardId, awardCount);
        }

        // 3.1 默认装配配置【全局抽奖概率】
        assembleLotteryStrategy(String.valueOf(strategyId), strategyAwardEntities);

        // 3.2 权重策略配置 - 适用于 rule_weight 权重规则配置
        StrategyEntity strategyEntity = repository.queryStrategyEntityByStrategy(strategyId);
        String ruleWeight = strategyEntity.getRuleWeight();

        // 3.3 如果没有配置权重规则，则直接返回
        if (null == ruleWeight) return true;

        // 3.4 判断配置了权重规则 则需要查询数据库得到具体的配置规则
        StrategyRuleEntity strategiesRuleEntity = repository.queryStrategyRule(strategyId, ruleWeight);

        // 3.5 如果没有配置权重规则，则抛出异常
        if (null == strategiesRuleEntity) {
            throw new AppException(ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getCode(), ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getInfo());
        }

        // 3.6 获取权重规则配置
        Map<String, List<Integer>> ruleWeightValueMap = strategiesRuleEntity.getRuleWeightValues();
        Set<String> keys = ruleWeightValueMap.keySet();


        // 3.7 遍历配置的权重规则，计算出每个奖品的权重
        for (String key : keys) {
            List<Integer> ruleWeightValues = ruleWeightValueMap.get(key);
            ArrayList<StrategyAwardEntity> strategyAwardEntitiesClone = new ArrayList<>(strategyAwardEntities);
            strategyAwardEntitiesClone.removeIf(entity -> ruleWeightValues.contains(entity.getAwardId()));
            assembleLotteryStrategy(String.valueOf(strategyId).concat("_").concat(key), strategyAwardEntitiesClone);
        }
        return true;
    }

    /**
     * 获取随机奖品ID
     *
     * @param strategyId 策略ID
     * @return 随机奖品ID
     */
    @Override
    public Integer getRandomAwardId(Long strategyId) {
        // 1.分布式部署下，不一为当前应用做的策略装配，也就是值不一定会保存到本应用，所以需要从Redis中获取策略奖品配置
        int rateRange = repository.getRateRange(strategyId);
        // 2.通过生成的随机值，获取概率值奖品查找表的结果
        return repository.getStrategyAwardAssemble(String.valueOf(strategyId), new SecureRandom().nextInt(rateRange));
    }

    /**
     * 获取随机奖品ID（带权重规则）
     *
     * @param strategyId      策略ID
     * @param ruleWeightValue 权重规则值
     * @return 随机奖品ID
     */
    @Override
    public Integer getRandomAwardId(Long strategyId, String ruleWeightValue) {
        // 1.装配key
        String key = String.valueOf(strategyId).concat("_").concat(ruleWeightValue);
        // 2.分布式部署下，不一为当前应用做的策略装配，也就是值不一定会保存到本应用，所以需要从Redis中获取策略奖品配置
        int rateRange = repository.getRateRange(key);
        // 3.通过生成的随机值，获取概率值奖品查找表的结果
        return repository.getStrategyAwardAssemble(key, new SecureRandom().nextInt(rateRange));
    }

    /**
     * 根据key获取随机奖品ID
     *
     * @param key 装配key
     * @return 随机奖品ID
     */
    @Override
    public Integer getRandomAwardId(String key) {
        // 分布式部署下，不一定为当前应用做的策略装配。也就是值不一定会保存到本应用，而是分布式应用，所以需要从 Redis 中获取。
        int rateRange = repository.getRateRange(key);
        // 通过生成的随机值，获取概率值奖品查找表的结果
        return repository.getStrategyAwardAssemble(key, secureRandom.nextInt(rateRange));
    }

    /**
     * 减少奖品库存
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return 是否成功
     */
    @Override
    public Boolean subtractionAwardStock(Long strategyId, Integer awardId) {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_KEY + strategyId + Constants.UNDERLINE + awardId;
        return repository.subtractionAwardStock(cacheKey);
    }

    /**
     * 装配抽奖策略
     *
     * @param key                   装配键
     * @param strategyAwardEntities 奖品实体列表
     */
    private void assembleLotteryStrategy(String key, List<StrategyAwardEntity> strategyAwardEntities) {
        // 1.获取最小概率值
        BigDecimal minAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        // 2.获取概率值总和
        BigDecimal totalAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 3.获取概率范围
        BigDecimal rateRange = totalAwardRate.divide(minAwardRate, 0, RoundingMode.CEILING);

        // 4. 初始化概率查找表，大小为计算出的概率范围长度
        ArrayList<Integer> strategyAwardRateSearchTables = new ArrayList<>(rateRange.intValue());
        for (StrategyAwardEntity strategyAward : strategyAwardEntities) {
            Integer awardId = strategyAward.getAwardId();
            BigDecimal awardRate = strategyAward.getAwardRate();

            // 5. 计算出每个概率值需要存放到查找表的数量 循环填充
            for (int i = 0; i < rateRange.multiply(awardRate).setScale(0, RoundingMode.CEILING).intValue(); i++) {
                strategyAwardRateSearchTables.add(awardId);
            }
        }

        // 6.乱序
        Collections.shuffle(strategyAwardRateSearchTables);

        // 7.保存到策略配置中
        HashMap<Integer, Integer> shuffleStrategyAwardRateSearchTables = new HashMap<>();
        for (int i = 0; i < strategyAwardRateSearchTables.size(); i++) {
            shuffleStrategyAwardRateSearchTables.put(i, strategyAwardRateSearchTables.get(i));
        }

        // 8.存储到Redis中
        repository.storeStrategyAwardRateSearchTables(key, shuffleStrategyAwardRateSearchTables.size(), shuffleStrategyAwardRateSearchTables);

    }

    /**
     * 缓存奖品库存到Redis
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @param awardCount 奖品库存
     */
    private void cacheStrategyAwardCount(Long strategyId, Integer awardId, Integer awardCount) {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_KEY + strategyId + Constants.UNDERLINE + awardId;
        repository.cacheStrategyAwardCount(cacheKey, awardCount);

    }
}

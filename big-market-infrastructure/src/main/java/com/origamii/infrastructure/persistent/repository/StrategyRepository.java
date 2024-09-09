package com.origamii.infrastructure.persistent.repository;

import com.origamii.domain.strategy.model.entity.StrategyAwardEntity;
import com.origamii.domain.strategy.model.entity.StrategyEntity;
import com.origamii.domain.strategy.model.entity.StrategyRuleEntity;
import com.origamii.domain.strategy.repository.IStrategyRepository;
import com.origamii.infrastructure.persistent.dao.IStrategyAwardDao;
import com.origamii.infrastructure.persistent.dao.IStrategyDao;
import com.origamii.infrastructure.persistent.dao.IStrategyRuleDao;
import com.origamii.infrastructure.persistent.po.Strategy;
import com.origamii.infrastructure.persistent.po.StrategyAward;
import com.origamii.infrastructure.persistent.po.StrategyRule;
import com.origamii.infrastructure.persistent.redis.IRedisService;
import com.origamii.types.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Origami
 * @description 策略仓储实现类
 * @create 2024-09-05 09:38
 **/
@Repository
public class StrategyRepository implements IStrategyRepository {

    // 注入抽奖策略的数据访问对象，用于从数据库中查询策略数据
    @Autowired
    private IStrategyDao strategyDao;

    // 注入抽奖规则的数据访问对象，用于从数据库中查询规则数据
    @Autowired
    private IStrategyRuleDao strategyRuleDao;

    // 注入策略奖项的数据访问对象，用于从数据库中查询策略奖项数据
    @Autowired
    private IStrategyAwardDao strategyAwardDao;

    // 注入 Redis 缓存服务，用于获取和存储缓存数据
    @Autowired
    private IRedisService redisService;

    /**
     * 查询策略配置
     * 该方法通过策略 ID 查询与该策略关联的奖项列表，优先从缓存中获取，
     * 如果缓存中没有数据，则从数据库中查询，并将结果存入缓存。
     *
     * @param strategyId 策略ID，用于确定需要查询的策略奖项列表
     * @return 返回与指定策略ID关联的奖项实体列表
     */
    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {
        // 1. 优先从缓存中获取 Key，Redis 的 key 由策略 ID 组成
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId;
        List<StrategyAwardEntity> strategyAwardEntities = redisService.getValue(cacheKey);

        // 2. 判断缓存中是否有 Key，如果缓存不为空且有数据，则直接返回缓存中的数据
        if (null != strategyAwardEntities && !strategyAwardEntities.isEmpty())
            return strategyAwardEntities;

        // 3. 如果缓存中没有对应数据，则从数据库中查询策略奖项列表
        List<StrategyAward> strategyAwards = strategyAwardDao.queryStrategyAwardListByStrategyId(strategyId);

        // 4. 将数据库查询结果转换为策略奖项实体类列表
        strategyAwardEntities = new ArrayList<>(strategyAwards.size());
        for (StrategyAward strategyAward : strategyAwards) {
            StrategyAwardEntity strategyAwardEntity = StrategyAwardEntity.builder()
                    .strategyId(strategyAward.getStrategyId())         // 设置策略 ID
                    .awardId(strategyAward.getAwardId())               // 设置奖项 ID
                    .awardCount(strategyAward.getAwardCount())         // 设置奖项总数量
                    .awardCountSurplus(strategyAward.getAwardCountSurplus()) // 设置剩余奖项数量
                    .awardRate(strategyAward.getAwardRate())           // 设置中奖率
                    .build();
            // 将创建的实体添加到列表中
            strategyAwardEntities.add(strategyAwardEntity);
        }

        // 5. 将查询结果存入缓存，便于后续请求直接从缓存中获取数据
        redisService.setValue(cacheKey, strategyAwardEntities);

        // 6. 返回从数据库中查询并缓存的数据
        return strategyAwardEntities;
    }

    /**
     * 存储策略奖励比例搜索表到 Redis 中
     *
     * @param key                                  策略ID
     * @param rateRange                            概率范围
     * @param shuffleStrategyAwardRateSearchTables 乱序的概率查找表
     */
    @Override
    public void storeStrategyAwardRateSearchTables(String key, Integer rateRange, HashMap<Integer, Integer> shuffleStrategyAwardRateSearchTables) {
        // 1. 存储抽奖策略范围值，如10000 用于生成10000以内的随机数
        redisService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key, rateRange.intValue());

        // 2. 存储概率查找表
        Map<Integer, Integer> cacheRateTable = redisService.getMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key);
        cacheRateTable.putAll(shuffleStrategyAwardRateSearchTables);
    }


    @Override
    public Integer getStrategyAwardAssemble(String key, Integer rateKey) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key, rateKey);
    }

    @Override
    public int getRateRange(Long strategyId) {
        return getRateRange(String.valueOf(strategyId));
    }

    @Override
    public int getRateRange(String key) {
        return redisService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key);
    }



    @Override
    public StrategyEntity queryStrategyEntityByStrategy(Long strategyId) {
        // 1.优先从Redis缓存中获取策略实体
        String cacheKey = Constants.RedisKey.STRATEGY_KEY + strategyId;
        StrategyEntity strategyEntity = redisService.getValue(cacheKey);

        // 2.判断缓存中是否有策略实体，如果缓存不为空且有数据，则直接返回缓存中的数据
        if (null != strategyEntity) {
            return strategyEntity;
        }

        // 3.如果缓存中没有对应数据，则从数据库中查询策略实体
        Strategy strategy = strategyDao.queryStrategyByStrategyId(strategyId);
        strategyEntity = StrategyEntity.builder()
                .strategyId(strategy.getStrategyId())
                .strategyDesc(strategy.getStrategyDesc())
                .ruleModels(strategy.getRuleModels())
                .build();

        // 2.将策略实体存入Redis缓存
        redisService.setValue(cacheKey, strategyEntity);

        // 3.返回策略实体
        return strategyEntity;
    }

    /**
     * @param strategyId
     * @param ruleModel
     * @return
     */
    @Override
    public StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel) {

        StrategyRule strategyRuleReq = new StrategyRule();
        strategyRuleReq.setStrategyId(strategyId);
        strategyRuleReq.setRuleModel(ruleModel);
        StrategyRule strategyRuleRes = strategyRuleDao.queryStrategyRule(strategyRuleReq);

        return StrategyRuleEntity.builder()
                .strategyId(strategyRuleRes.getStrategyId())
                .awardId(strategyRuleRes.getAwardId())
                .ruleType(strategyRuleRes.getRuleType())
                .ruleModel(strategyRuleRes.getRuleModel())
                .ruleValue(strategyRuleRes.getRuleValue())
                .ruleDesc(strategyRuleRes.getRuleDesc())
                .build();
    }


}




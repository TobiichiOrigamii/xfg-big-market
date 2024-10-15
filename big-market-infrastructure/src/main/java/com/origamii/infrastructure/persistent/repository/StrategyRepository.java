package com.origamii.infrastructure.persistent.repository;

import com.origamii.domain.strategy.model.entity.StrategyAwardEntity;
import com.origamii.domain.strategy.model.entity.StrategyEntity;
import com.origamii.domain.strategy.model.entity.StrategyRuleEntity;
import com.origamii.domain.strategy.model.valobj.*;
import com.origamii.domain.strategy.repository.IStrategyRepository;
import com.origamii.infrastructure.persistent.dao.*;
import com.origamii.infrastructure.persistent.po.*;
import com.origamii.infrastructure.persistent.redis.IRedisService;
import com.origamii.types.common.Constants;
import com.origamii.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.origamii.types.enums.ResponseCode.UN_ASSEMBLE_STRATEGY_ARMORY;

/**
 * @author Origami
 * @description 策略仓储实现类
 * @create 2024-09-05 09:38
 **/
@Slf4j
@Repository
public class StrategyRepository implements IStrategyRepository {

    @Autowired
    private IRedisService redisService;
    @Autowired
    private IStrategyDao strategyDao;
    @Autowired
    private IStrategyRuleDao strategyRuleDao;
    @Autowired
    private IStrategyAwardDao strategyAwardDao;
    @Autowired
    private IRuleTreeDao ruleTreeDao;
    @Autowired
    private IRuleTreeNodeDao ruleTreeNodeDao;
    @Autowired
    private IRuleTreeNodeLineDao ruleTreeNodeLineDao;
    @Autowired
    private IRaffleActivityDao raffleActivityDao;


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
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_LIST_KEY + strategyId;
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
                    .awardTitle(strategyAward.getAwardTitle())          // 设置奖项标题
                    .awardSubTitle(strategyAward.getAwardSubtitle())    // 设置奖项副标题
                    .awardCount(strategyAward.getAwardCount())           // 设置奖项总数量
                    .awardCountSurplus(strategyAward.getAwardCountSurplus()) // 设置剩余奖项数量
                    .awardRate(strategyAward.getAwardRate())           // 设置中奖率
                    .sort(strategyAward.getSort())                     // 设置排序
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

    /**
     * 根据键和概率键获取策略奖品组合
     *
     * @param key     键
     * @param rateKey 概率键
     * @return 策略奖励组合
     */
    @Override
    public Integer getStrategyAwardAssemble(String key, Integer rateKey) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key, rateKey);
    }

    /**
     * 获取指定策略ID的概率范围
     *
     * @param strategyId 策略ID
     * @return 概率范围
     */
    @Override
    public int getRateRange(Long strategyId) {
        return getRateRange(String.valueOf(strategyId));
    }

    /**
     * 根据键获取概率范围
     *
     * @param key 键
     * @return 概率范围
     */
    @Override
    public int getRateRange(String key) {
        String cacheKey = Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key;
        if (!redisService.isExists(cacheKey))
            throw new AppException(UN_ASSEMBLE_STRATEGY_ARMORY.getCode() + cacheKey + Constants.COLON + UN_ASSEMBLE_STRATEGY_ARMORY.getInfo());
        return redisService.getValue(cacheKey);
    }

    /**
     * 查询指定策略ID的策略实体
     *
     * @param strategyId 策略ID
     * @return 策略实体
     */
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
     * 查询策略规则
     *
     * @param strategyId 策略ID
     * @param ruleModel  规则模型
     * @return 策略规则实体
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

    /**
     * 根据策略ID和奖品ID查询规则值
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @param ruleModel  规则模型
     * @return 规则值
     */
    @Override
    public String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel) {
        StrategyRule strategyRule = new StrategyRule();
        strategyRule.setStrategyId(strategyId);
        strategyRule.setAwardId(awardId);
        strategyRule.setRuleModel(ruleModel);
        return strategyRuleDao.queryStrategyRuleValue(strategyRule);
    }

    /**
     * 根据策略ID查询规则值
     *
     * @param strategyId 策略ID
     * @param ruleModel  规则模型
     * @return 规则值
     */
    @Override
    public String queryStrategyRuleValue(Long strategyId, String ruleModel) {
        return queryStrategyRuleValue(strategyId, null, ruleModel);
    }

    /**
     * 通过策略ID查询策略实体
     *
     * @param strategyId 策略ID
     * @return 策略实体
     */
    @Override
    public StrategyEntity queryStrategyEntityByStrategyId(Long strategyId) {
        // 优先从缓存中获取
        String cacheKey = Constants.RedisKey.STRATEGY_KEY + strategyId;
        StrategyEntity strategyEntity = redisService.getValue(cacheKey);

        // 判断缓存中是否有策略实体，如果缓存不为空且有数据，则直接返回缓存中的数据
        if (null != strategyEntity) return strategyEntity;

        // 如果缓存中没有对应数据，则从数据库中查询策略实体
        Strategy strategy = strategyDao.queryStrategyByStrategyId(strategyId);
        if (null == strategy) return StrategyEntity.builder().build();

        strategyEntity = StrategyEntity.builder()
                .strategyId(strategy.getStrategyId())
                .strategyDesc(strategy.getStrategyDesc())
                .ruleModels(strategy.getRuleModels())
                .build();

        // 将策略实体存入Redis缓存
        redisService.setValue(cacheKey, strategyEntity);

        // 返回策略实体
        return strategyEntity;
    }

    /**
     * 查询策略奖项规则模型
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return 策略奖项规则模型
     */
    @Override
    public StrategyAwardRuleModelVO queryStrategyAwardRuleModel(Long strategyId, Integer awardId) {
        StrategyAward strategyAward = new StrategyAward();
        strategyAward.setStrategyId(strategyId);
        strategyAward.setAwardId(awardId);
        String ruleModel = strategyAwardDao.queryStrategyAwardRuleModel(strategyAward);
        return StrategyAwardRuleModelVO.builder()
                .ruleModels(ruleModel)
                .build();
    }

    /**
     * 根据规则树ID，查询树结构信息
     *
     * @param treeId 规则树ID
     * @return 树结构信息
     */
    @Override
    public RuleTreeVO queryRuleTreeVOByTreeId(String treeId) {
        // 优先从缓存获取
        String cacheKey = Constants.RedisKey.RULE_TREE_VO_KEY + treeId;
        RuleTreeVO ruleTreeVOCache = redisService.getValue(cacheKey);
        if (null != ruleTreeVOCache) return ruleTreeVOCache;

        // 从数据库获取
        RuleTree ruleTree = ruleTreeDao.queryRuleTreeByTreeId(treeId);
        List<RuleTreeNode> ruleTreeNodes = ruleTreeNodeDao.queryRuleTreeNodeListByTreeId(treeId);
        List<RuleTreeNodeLine> ruleTreeNodeLines = ruleTreeNodeLineDao.queryRuleTreeNodeLineListByTreeId(treeId);

        // 1. tree node line 转换Map结构
        Map<String, List<RuleTreeNodeLineVO>> ruleTreeNodeLineMap = new HashMap<>();
        for (RuleTreeNodeLine ruleTreeNodeLine : ruleTreeNodeLines) {
            RuleTreeNodeLineVO ruleTreeNodeLineVO = RuleTreeNodeLineVO.builder()
                    .treeId(ruleTreeNodeLine.getTreeId())
                    .ruleNodeFrom(ruleTreeNodeLine.getRuleNodeFrom())
                    .ruleNodeTo(ruleTreeNodeLine.getRuleNodeTo())
                    .ruleLimitType(RuleLimitTypeVO.valueOf(ruleTreeNodeLine.getRuleLimitType()))
                    .ruleLimitValue(RuleLogicCheckTypeVO.valueOf(ruleTreeNodeLine.getRuleLimitValue()))
                    .build();

            List<RuleTreeNodeLineVO> ruleTreeNodeLineVOList = ruleTreeNodeLineMap.computeIfAbsent(ruleTreeNodeLine.getRuleNodeFrom(), k -> new ArrayList<>());
            ruleTreeNodeLineVOList.add(ruleTreeNodeLineVO);
        }

        // 2. tree node 转换为Map结构
        Map<String, RuleTreeNodeVO> treeNodeMap = new HashMap<>();
        for (RuleTreeNode ruleTreeNode : ruleTreeNodes) {
            RuleTreeNodeVO ruleTreeNodeVO = RuleTreeNodeVO.builder()
                    .treeId(ruleTreeNode.getTreeId())
                    .ruleKey(ruleTreeNode.getRuleKey())
                    .ruleDesc(ruleTreeNode.getRuleDesc())
                    .ruleValue(ruleTreeNode.getRuleValue())
                    .treeNodeLineVOList(ruleTreeNodeLineMap.get(ruleTreeNode.getRuleKey()))
                    .build();
            treeNodeMap.put(ruleTreeNode.getRuleKey(), ruleTreeNodeVO);
        }

        // 3. 构建 Rule Tree
        RuleTreeVO ruleTreeVODB = RuleTreeVO.builder()
                .treeId(ruleTree.getTreeId())
                .treeName(ruleTree.getTreeName())
                .treeDesc(ruleTree.getTreeDesc())
                .treeRootRuleNode(ruleTree.getTreeRootRuleKey())
                .treeNodeMap(treeNodeMap)
                .build();

        redisService.setValue(cacheKey, ruleTreeVODB);
        return ruleTreeVODB;

    }

    /**
     * 缓存key decr 方式扣减库存
     *
     * @param cacheKey 缓存key
     * @return 扣减结果
     */
    @Override
    public Boolean subtractionAwardStock(String cacheKey) {
        long surplus = redisService.decr(cacheKey);
        if (surplus < 0) {
            // 库存小于0 恢复为0个
            redisService.setValue(cacheKey, 0);
            return false;
        }

        // 1.按照cacheKey decr后的值，如99、98、97 和 key 组成为库存所的key进行使用
        // 2.加锁为了兜底，如果后续有回复库存，手动处理等，也不会超卖。因为所有的可用库存Key，都被加锁了
        String lockKey = cacheKey + Constants.UNDERLINE + surplus;
        Boolean lock = redisService.setNx(lockKey);
        if (!lock)
            log.info("策略奖品库存加锁失败{}", lockKey);
        return lock;
    }

    /**
     * 缓存奖品库存
     *
     * @param cacheKey   key
     * @param awardCount 库存值
     */
    @Override
    public void cacheStrategyAwardCount(String cacheKey, Integer awardCount) {
        if (redisService.isExists(cacheKey)) return;
        redisService.setAtomicLong(cacheKey, awardCount);
    }

    /**
     * 写入奖品库存消费队列
     *
     * @param strategyAwardStockKeyVO 对象值对象
     */
    @Override
    public void awardStockConsumeSendQueue(StrategyAwardStockKeyVO strategyAwardStockKeyVO) {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_QUEUE_KEY;
        RBlockingQueue<StrategyAwardStockKeyVO> blockingQueue = redisService.getBlockingQueue(cacheKey);
        RDelayedQueue<StrategyAwardStockKeyVO> delayedQueue = redisService.getDelayedQueue(blockingQueue);
        delayedQueue.offer(strategyAwardStockKeyVO, 3, TimeUnit.SECONDS);

    }

    /**
     * 获取奖品库存消费队列
     */
    @Override
    public StrategyAwardStockKeyVO takeQueueValue() {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_QUEUE_KEY;
        RBlockingQueue<StrategyAwardStockKeyVO> destinationQueue = redisService.getBlockingQueue(cacheKey);
        return destinationQueue.poll();
    }

    /**
     * 更新奖品库存消耗
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     */
    @Override
    public void updateStrategyAwardStock(Long strategyId, Integer awardId) {
        StrategyAward strategyAward = new StrategyAward();
        strategyAward.setStrategyId(strategyId);
        strategyAward.setAwardId(awardId);
        strategyAwardDao.updateStrategyAwardStock(strategyAward);
    }

    /**
     * 根据策略ID+奖品ID的唯一组合 查询奖品信息
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return 奖品信息
     */
    @Override
    public StrategyAwardEntity queryStrategyAwardEntity(Long strategyId, Integer awardId) {
        // 优先从缓存中获取
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId + Constants.UNDERLINE + awardId;
        StrategyAwardEntity strategyAwardEntity = redisService.getValue(cacheKey);
        // 判断缓存中是否有奖品信息，如果缓存不为空且有数据，则直接返回缓存中的数据
        if (null != strategyAwardEntity) return strategyAwardEntity;

        // 如果缓存中没有对应数据，则从数据库中查询奖品信息
        StrategyAward strategyAwardReq = new StrategyAward();
        strategyAwardReq.setStrategyId(strategyId);
        strategyAwardReq.setAwardId(awardId);
        StrategyAward strategyAwardRes = strategyAwardDao.queryStrategyAward(strategyAwardReq);

        // 转换为实体类
        strategyAwardEntity = StrategyAwardEntity.builder()
                .strategyId(strategyAwardRes.getStrategyId())
                .awardId(strategyAwardRes.getAwardId())
                .awardTitle(strategyAwardRes.getAwardTitle())
                .awardSubTitle(strategyAwardRes.getAwardSubtitle())
                .awardCount(strategyAwardRes.getAwardCount())
                .awardCountSurplus(strategyAwardRes.getAwardCountSurplus())
                .awardRate(strategyAwardRes.getAwardRate())
                .sort(strategyAwardRes.getSort())
                .build();

        // 将奖品信息存入Redis缓存
        redisService.setValue(cacheKey, strategyAwardEntity);
        // 返回奖品信息
        return strategyAwardEntity;
    }

    /**
     * 根据策略ID查询策略
     * @param activityId 活动ID
     * @return 策略ID
     */
    @Override
    public Long queryStrategyByActivityId(Long activityId) {
        return raffleActivityDao.queryStrategyIdByActivityId(activityId);
    }

}


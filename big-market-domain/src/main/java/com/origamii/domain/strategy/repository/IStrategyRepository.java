package com.origamii.domain.strategy.repository;

import com.origamii.domain.strategy.model.entity.StrategyAwardEntity;
import com.origamii.domain.strategy.model.entity.StrategyEntity;
import com.origamii.domain.strategy.model.entity.StrategyRuleEntity;
import com.origamii.domain.strategy.model.valobj.RuleTreeVO;
import com.origamii.domain.strategy.model.valobj.RuleWeightVO;
import com.origamii.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import com.origamii.domain.strategy.model.valobj.StrategyAwardStockKeyVO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Origami
 * @description 策略仓储接口
 * @create 2024-09-05 09:36
 **/
public interface IStrategyRepository {

    /**
     * 查询策略配置
     * 该方法通过策略 ID 查询与该策略关联的奖项列表，优先从缓存中获取，
     * 如果缓存中没有数据，则从数据库中查询，并将结果存入缓存。
     *
     * @param strategyId 策略ID，用于确定需要查询的策略奖项列表
     * @return 返回与指定策略ID关联的奖项实体列表
     */
    List<StrategyAwardEntity> queryStrategyAwardListByStrategyId(Long strategyId);

    /**
     * 存储策略奖励比例搜索表到 Redis 中
     *
     * @param key                                  策略ID
     * @param rateRange                            概率范围
     * @param shuffleStrategyAwardRateSearchTables 乱序的概率查找表
     */
    void storeStrategyAwardRateSearchTables(String key, Integer rateRange, HashMap<Integer, Integer> shuffleStrategyAwardRateSearchTables);

    /**
     * 获取指定策略ID的概率范围
     *
     * @param strategyId 策略ID
     * @return 概率范围
     */
    int getRateRange(Long strategyId);

    /**
     * 根据键获取概率范围
     *
     * @param key 键
     * @return 概率范围
     */
    int getRateRange(String key);

    /**
     * 根据键和概率键获取策略奖品组合
     *
     * @param key     键
     * @param rateKey 概率键
     * @return 策略奖励组合
     */
    Integer getStrategyAwardAssemble(String key, Integer rateKey);

    /**
     * 查询指定策略ID的策略实体
     *
     * @param strategyId 策略ID
     * @return 策略实体
     */
    StrategyEntity queryStrategyEntityByStrategy(Long strategyId);

    /**
     * 查询策略规则
     *
     * @param strategyId 策略ID
     * @param ruleModel  规则模型
     * @return 策略规则实体
     */
    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel);

    /**
     * 根据策略ID和奖品ID查询规则值
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @param ruleModel  规则模型
     * @return 规则值
     */
    String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel);

    /**
     * 根据策略ID查询规则值
     *
     * @param strategyId 策略ID
     * @param ruleModel  规则模型
     * @return 规则值
     */
    String queryStrategyRuleValue(Long strategyId, String ruleModel);

    /**
     * 通过策略ID查询策略实体
     *
     * @param strategyId 策略ID
     * @return 策略实体
     */
    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);

    /**
     * 查询策略奖项规则模型
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return 策略奖项规则模型
     */
    StrategyAwardRuleModelVO queryStrategyAwardRuleModel(Long strategyId, Integer awardId);

    /**
     * 根据规则树ID，查询树结构信息
     *
     * @param treeId 规则树ID
     * @return 树结构信息
     */
    RuleTreeVO queryRuleTreeVOByTreeId(String treeId);

    /**
     * 缓存key decr 方式扣减库存
     *
     * @param cacheKey 缓存key
     * @return 扣减结果
     */
    Boolean subtractionAwardStock(String cacheKey, Date endDateTime);

    /**
     * 缓存奖品库存
     *
     * @param cacheKey   key
     * @param awardCount 库存值
     */
    void cacheStrategyAwardCount(String cacheKey, Integer awardCount);

    /**
     * 写入奖品库存消费队列
     *
     * @param strategyAwardStockKeyVO 对象值对象
     */
    void awardStockConsumeSendQueue(StrategyAwardStockKeyVO strategyAwardStockKeyVO);

    /**
     * 获取奖品库存消费队列
     */
    StrategyAwardStockKeyVO takeQueueValue();

    /**
     * 更新奖品库存消耗
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     */
    void updateStrategyAwardStock(Long strategyId, Integer awardId);

    /**
     * 根据策略ID+奖品ID的唯一组合 查询奖品信息
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return 奖品信息
     */
    StrategyAwardEntity queryStrategyAwardEntity(Long strategyId, Integer awardId);

    /**
     * 根据活动ID查询策略ID
     *
     * @param activityId 活动ID
     * @return 策略ID
     */
    Long queryStrategyByActivityId(Long activityId);

    /**
     * 根据用户ID和策略ID查询今日抽奖次数
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @return 今日抽奖次数
     */
    Integer queryTodayUserRaffleCount(String userId, Long strategyId);

    /**
     * 查询奖品规则锁定次数
     *
     * @param treeIds 奖品规则ID
     * @return 奖品规则锁定次数
     */
    Map<String, Integer> queryAwardRuleLockCount(String[] treeIds);

    /**
     * 查询活动账户总使用次数
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @return 活动账户总使用次数
     */
    Integer queryActivityAccountTotalUseCount(String userId, Long strategyId);

    /**
     * 查询奖品权重配置
     *
     * @param strategyId 策略ID
     * @return 权重规则
     */
    List<RuleWeightVO> queryAwardRuleWeight(Long strategyId);
}


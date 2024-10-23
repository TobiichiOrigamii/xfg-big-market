package com.origamii.domain.activity.repository;

import com.origamii.domain.activity.model.aggreate.CreatePartakeOrderAggregate;
import com.origamii.domain.activity.model.aggreate.CreateQuotaOrderAggregate;
import com.origamii.domain.activity.model.entity.*;
import com.origamii.domain.activity.model.valobj.ActivitySkuStockKeyVO;

import java.util.Date;
import java.util.List;

/**
 * @author Origami
 * @description 活动仓储接口
 * @create 2024-09-27 15:58
 **/
public interface IActivityRepository {

    /**
     * 查询活动sku
     *
     * @param sku sku
     * @return 活动sku
     */
    ActivitySkuEntity queryActivitySku(Long sku);

    /**
     * 根据活动id查询活动
     *
     * @param activityId 活动id
     * @return 活动实体
     */
    ActivityEntity queryRaffleActivityByActivityId(Long activityId);

    /**
     * 根据活动计数id查询活动计数
     *
     * @param activityCountId 活动计数id
     * @return 活动计数实体
     */
    ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);

    /**
     * 保存订单
     *
     * @param createQuotaOrderAggregate 订单聚合实体
     */
    void saveOrder(CreateQuotaOrderAggregate createQuotaOrderAggregate);

    /**
     * 查询活动sku库存
     *
     * @param cacheKey   缓存key
     * @param stockCount 库存数量
     */
    void cacheActivitySKuStockCount(String cacheKey, Integer stockCount);

    /**
     * 减少活动sku库存
     *
     * @param sku         活动SKU
     * @param cacheKey    缓存key
     * @param endDateTime 结束时间
     * @return 库存是否减少成功
     */
    boolean subtractionActivityStock(Long sku, String cacheKey, Date endDateTime);

    /**
     * 活动sku库存消费发送队列
     *
     * @param activitySkuStockKeyVO 活动sku库存key
     */
    void activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO activitySkuStockKeyVO);

    /**
     * 从队列中获取活动sku库存key
     *
     * @return 活动sku库存key
     */
    ActivitySkuStockKeyVO takeQueueValue();

    /**
     * 清空队列
     */
    void clearQueueValue();

    /**
     * 延迟队列+任务趋势更新活动sku库存
     *
     * @param sku 活动商品
     */
    void updateActivitySkuStock(Long sku);

    /**
     * 存库存以消耗完毕 清空数据库库存
     *
     * @param sku 活动商品
     */
    void clearActivitySkuStock(Long sku);

    /**
     * 保存聚合对象
     *
     * @param createPartakeOrderAggregate 聚合对象
     */
    void saveCreatePartakeOrderAggregate(CreatePartakeOrderAggregate createPartakeOrderAggregate);

    /**
     * 根据用户id和活动id查询活动账户
     *
     * @param userId     用户id
     * @param activityId 活动id
     * @return 活动账户实体
     */
    ActivityAccountEntity queryActivityAccountByUserId(String userId, Long activityId);

    /**
     * 根据用户id、活动id、月份查询活动账户月份
     *
     * @param userId     用户id
     * @param activityId 活动id
     * @param month      月份
     * @return 活动账户月份实体
     */
    ActivityAccountMonthEntity queryActivityAccountMonthByUserId(String userId, Long activityId, String month);

    /**
     * 根据用户id、活动id、日期查询活动账户日
     *
     * @param userId     用户id
     * @param activityId 活动id
     * @param day        日期
     * @return 活动账户日实体
     */
    ActivityAccountDayEntity queryActivityAccountDayByUserId(String userId, Long activityId, String day);

    /**
     * 根据用户id、活动id、日期查询用户参与活动订单
     * @param partakeRaffleActivityEntity 参与活动实体
     * @return 用户参与活动订单实体
     */
    UserRaffleOrderEntity queryNoUsedRaffleOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity);

    /**
     * 根据活动ID查询活动SKU列表
     * @param activityId 活动ID
     * @return 活动SKU列表
     */
    List<ActivitySkuEntity> queryActivitySkuListByActivityId(Long activityId);

    /**
     * 查询用户当日抽奖次数
     * @param activityId 活动ID
     * @param userId 用户ID
     * @return 用户当日抽奖次数
     */
    Integer queryRaffleActivityAccountDayPartakeCount(Long activityId, String userId);

    /**
     * 查询用户活动账户信息
     *
     * @param activityId 活动ID
     * @param userId     用户ID
     * @return 用户活动账户信息
     */
    ActivityAccountEntity queryActivityAccount(Long activityId, String userId);
}

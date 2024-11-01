package com.origamii.domain.activity.service.quota;

import com.origamii.domain.activity.model.aggreate.CreateQuotaOrderAggregate;
import com.origamii.domain.activity.model.entity.*;
import com.origamii.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import com.origamii.domain.activity.repository.IActivityRepository;
import com.origamii.domain.activity.service.IRaffleActivitySkuStockService;
import com.origamii.domain.activity.service.quota.policy.ITradePolicy;
import com.origamii.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author Origami
 * @description 抽奖活动服务
 * @create 2024-09-27 15:59
 **/
@Service
public class RaffleActivityAccountQuotaService extends AbstractRaffleActivityAccountQuota implements IRaffleActivitySkuStockService {

    public RaffleActivityAccountQuotaService(IActivityRepository activityRepository, DefaultActivityChainFactory defaultActivityChainFactory, Map<String, ITradePolicy> tradePolicyMap) {
        super(activityRepository, defaultActivityChainFactory, tradePolicyMap);
    }

    /**
     * 构建订单聚合对象
     * @param activitySkuEntity 活动sku实体
     * @param activityEntity 活动实体
     * @param activityCountEntity 次数实体
     * @param skuRechargeEntity sku充值实体
     * @return 订单聚合对象
     */
    @Override
    protected CreateQuotaOrderAggregate buildOrderAggregate(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity, SkuRechargeEntity skuRechargeEntity) {
        // 订单实体对象
        ActivityOrderEntity activityOrderEntity = new ActivityOrderEntity();
        activityOrderEntity.setUserId(skuRechargeEntity.getUserId());
        activityOrderEntity.setSku(skuRechargeEntity.getSku());
        activityOrderEntity.setActivityId(activityEntity.getActivityId());
        activityOrderEntity.setActivityName(activityEntity.getActivityName());
        activityOrderEntity.setStrategyId(activityEntity.getStrategyId());
        // 公司里一般会有专门的雪花算法UUID服务，我们这里直接生成个12位就可以了。
        activityOrderEntity.setOrderId(RandomStringUtils.randomNumeric(12));
        activityOrderEntity.setOrderTime(new Date());
        activityOrderEntity.setTotalCount(activityCountEntity.getTotalCount());
        activityOrderEntity.setDayCount(activityCountEntity.getDayCount());
        activityOrderEntity.setMonthCount(activityCountEntity.getMonthCount());
        activityOrderEntity.setPayAmount(activitySkuEntity.getProductAmount());
        activityOrderEntity.setOutBusinessNo(skuRechargeEntity.getOutBusinessNo());

        // 构建聚合对象
        return CreateQuotaOrderAggregate.builder()
                .userId(skuRechargeEntity.getUserId())
                .activityId(activitySkuEntity.getActivityId())
                .totalCount(activityCountEntity.getTotalCount())
                .dayCount(activityCountEntity.getDayCount())
                .monthCount(activityCountEntity.getMonthCount())
                .activityOrderEntity(activityOrderEntity)
                .build();
    }

//    /**
//     * 保存订单
//     * @param createOrderAggregate 订单聚合对象
//     */
//    @Override
//    protected void saveOrder(CreateQuotaOrderAggregate createOrderAggregate) {
//        repository.saveOrder(createOrderAggregate);
//    }

    /**
     * 获取活动sku库存消耗队列
     *
     * @return 奖品库存key信息
     * @throws InterruptedException 异常
     */
    @Override
    public ActivitySkuStockKeyVO takeQueueValue() throws InterruptedException {
        return repository.takeQueueValue();
    }

    /**
     * 清空队列
     */
    @Override
    public void clearQueueValue() {
        repository.clearQueueValue();
    }

    /**
     * 延迟队列+任务趋势更新活动sku库存
     *
     * @param sku 活动商品
     */
    @Override
    public void updateActivitySkuStock(Long sku) {
        repository.updateActivitySkuStock(sku);
    }

    /**
     * 缓存库存以消耗完毕 清空数据库库存
     *
     * @param sku 活动商品
     */
    @Override
    public void clearActivitySkuStock(Long sku) {
         repository.clearActivitySkuStock(sku);
    }

    /**
     * 更新订单状态
     * @param deliveryOrderEntity 出货订单实体
     */
    @Override
    public void updateOrder(DeliveryOrderEntity deliveryOrderEntity) {
        repository.updateOrder(deliveryOrderEntity);
    }

    /**
     * 查询用户当日参与次数
     * @param activityId 活动ID
     * @param userId 用户ID
     * @return 当日参与次数
     */
    @Override
    public Integer queryRaffleActivityAccountDayPartakeCount(Long activityId, String userId) {
        return repository.queryRaffleActivityAccountDayPartakeCount(activityId, userId);
    }

    /**
     * 查询用户活动账户信息
     *
     * @param activityId 活动ID
     * @param userId     用户ID
     * @return 用户活动账户信息
     */
    @Override
    public ActivityAccountEntity queryActivityAccount(Long activityId, String userId) {
        return repository.queryActivityAccount(activityId, userId);
    }
}

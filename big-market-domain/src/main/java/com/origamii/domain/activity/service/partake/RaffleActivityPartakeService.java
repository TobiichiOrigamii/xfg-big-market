package com.origamii.domain.activity.service.partake;

import com.origamii.domain.activity.model.aggreate.CreatePartakeOrderAggregate;
import com.origamii.domain.activity.model.entity.*;
import com.origamii.domain.activity.model.valobj.UserRaffleOrderStateVO;
import com.origamii.domain.activity.repository.IActivityRepository;
import com.origamii.types.enums.ResponseCode;
import com.origamii.types.exception.AppException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Origami
 * @description 抽奖活动参与抽奖类
 * @create 2024-10-10 23:18
 **/
@Service
public class RaffleActivityPartakeService extends AbstractRaffleActivityPartake {

    @Resource
    private IActivityRepository repository;

    private final SimpleDateFormat dateFormatMonth = new SimpleDateFormat("yyyy-MM");

    private final SimpleDateFormat dateFormatDay = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 账户过滤器，抽象方法，由子类实现具体的账户过滤逻辑。
     *
     * @param userId      用户ID
     * @param activityId  活动ID
     * @param currentDate 当前时间
     * @return 账户聚合对象
     */
    @Override
    protected CreatePartakeOrderAggregate doFilterAccount(String userId, Long activityId, Date currentDate) {

        // 转换日期格式
        String month = dateFormatMonth.format(currentDate);
        String day = dateFormatDay.format(currentDate);

        // 查询总账户额度
        ActivityAccountEntity activityAccountEntity = repository.queryActivityAccountByUserId(userId, activityId);

        // 额度判断（只判断总剩余额度）
        if (null == activityAccountEntity || activityAccountEntity.getTotalCountSurplus() <= 0)
            throw new AppException(ResponseCode.ACCOUNT_QUOTA_ERROR.getCode(), ResponseCode.ACCOUNT_QUOTA_ERROR.getInfo());

        // 查询月额度账户
        ActivityAccountMonthEntity activityAccountMonthEntity = repository.queryActivityAccountMonthByUserId(userId, activityId, month);
        if (null != activityAccountMonthEntity && activityAccountMonthEntity.getMonthCountSurplus() <= 0)
            throw new AppException(ResponseCode.ACCOUNT_MONTH_QUOTA_ERROR.getCode(), ResponseCode.ACCOUNT_MONTH_QUOTA_ERROR.getInfo());

        // 创建月账户余额 true - 存在月账户 false - 不存在月账户
        boolean isExistAccountMonth = null != activityAccountMonthEntity;
        if (null == activityAccountMonthEntity) {
            activityAccountMonthEntity = new ActivityAccountMonthEntity();
            activityAccountMonthEntity.setUserId(userId);
            activityAccountMonthEntity.setActivityId(activityId);
            activityAccountMonthEntity.setMonth(month);
            activityAccountMonthEntity.setMonthCount(activityAccountMonthEntity.getMonthCount());
            activityAccountMonthEntity.setMonthCountSurplus(activityAccountMonthEntity.getMonthCount());
        }

        // 查询日额度账户
        ActivityAccountDayEntity activityAccountDayEntity = repository.queryActivityAccountDayByUserId(userId, activityId, day);
        if (null != activityAccountDayEntity && activityAccountDayEntity.getDayCountSurplus() <= 0)
            throw new AppException(ResponseCode.ACCOUNT_DAY_QUOTA_ERROR.getCode(), ResponseCode.ACCOUNT_DAY_QUOTA_ERROR.getInfo());


        // 创建月账户余额 true - 存在月账户 false - 不存在月账户
        boolean isExistAccountDay = null != activityAccountDayEntity;
        if (null == activityAccountDayEntity) {
            activityAccountDayEntity = new ActivityAccountDayEntity();
            activityAccountDayEntity.setUserId(userId);
            activityAccountDayEntity.setActivityId(activityId);
            activityAccountDayEntity.setDay(day);
            activityAccountDayEntity.setDayCount(activityAccountDayEntity.getDayCount());
            activityAccountDayEntity.setDayCountSurplus(activityAccountDayEntity.getDayCount());
        }

        // 构建聚合对象
        CreatePartakeOrderAggregate createPartakeOrderAggregate = new CreatePartakeOrderAggregate();
        createPartakeOrderAggregate.setUserId(userId);
        createPartakeOrderAggregate.setActivityId(activityId);
        createPartakeOrderAggregate.setActivityAccountEntity(activityAccountEntity);
        createPartakeOrderAggregate.setActivityAccountMonthEntity(activityAccountMonthEntity);
        createPartakeOrderAggregate.setActivityAccountDayEntity(activityAccountDayEntity);
        createPartakeOrderAggregate.setExistAccountMonth(isExistAccountMonth);
        createPartakeOrderAggregate.setExistAccountMonth(isExistAccountMonth);
        return createPartakeOrderAggregate;
    }

    /**
     * 构建订单
     *
     * @param userId      用户ID
     * @param activityId  活动ID
     * @param currentDate 当前时间
     * @return 用户抽奖订单实体对象
     */
    @Override
    protected UserRaffleOrderEntity buildUserRaffleOrder(String userId, Long activityId, Date currentDate) {
        ActivityEntity activityEntity = repository.queryRaffleActivityByActivityId(activityId);
        // 构建订单
        UserRaffleOrderEntity userRaffleOrder = new UserRaffleOrderEntity();
        userRaffleOrder.setUserId(userId);
        userRaffleOrder.setActivityId(activityId);
        userRaffleOrder.setActivityName(activityEntity.getActivityName());
        userRaffleOrder.setStrategyId(activityEntity.getStrategyId());
        userRaffleOrder.setOrderId(RandomStringUtils.randomNumeric(12));
        userRaffleOrder.setOrderTime(currentDate);
        userRaffleOrder.setOrderState(UserRaffleOrderStateVO.create);
        return userRaffleOrder;
    }


}

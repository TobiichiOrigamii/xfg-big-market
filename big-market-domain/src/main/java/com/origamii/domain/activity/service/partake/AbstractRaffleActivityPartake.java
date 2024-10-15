package com.origamii.domain.activity.service.partake;

import com.alibaba.fastjson.JSON;
import com.origamii.domain.activity.model.aggreate.CreatePartakeOrderAggregate;
import com.origamii.domain.activity.model.entity.ActivityEntity;
import com.origamii.domain.activity.model.entity.PartakeRaffleActivityEntity;
import com.origamii.domain.activity.model.entity.UserRaffleOrderEntity;
import com.origamii.domain.activity.model.valobj.ActivityStateVO;
import com.origamii.domain.activity.repository.IActivityRepository;
import com.origamii.domain.activity.service.IRaffleActivityPartakeService;
import com.origamii.types.enums.ResponseCode;
import com.origamii.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Origami
 * @description 抽奖活动参与抽奖抽象类
 * @create 2024-10-10 22:19
 **/
@Slf4j
public abstract class AbstractRaffleActivityPartake implements IRaffleActivityPartakeService {

    @Resource
    private IActivityRepository repository;

    /**
     * 创建抽奖单：用户参与抽奖活动、扣减活动账户库存、产生抽奖单。
     * 如果存在未使用的抽奖单则直接返回已存在的抽奖单。
     *
     * @param partakeRaffleActivityEntity 参与抽奖活动实体对象
     * @return 用户抽奖订单实体对象
     */
    @Override
    public UserRaffleOrderEntity createOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity) {
        // 1.基础信息
        String userId = partakeRaffleActivityEntity.getUserId();
        Long activityId = partakeRaffleActivityEntity.getActivityId();
        Date currentDate = new Date();

        // 2.活动查询
        ActivityEntity activityEntity = repository.queryRaffleActivityByActivityId(activityId);

        // 校验：活动状态
        if (!ActivityStateVO.open.equals(activityEntity.getActivityState()))
            throw new AppException(ResponseCode.ACTIVITY_STATE_ERROR.getCode(), ResponseCode.ACTIVITY_STATE_ERROR.getInfo());
        // 校验：活动日期【开始时间 - 当前时间 - 结束时间】
        if (activityEntity.getBeginDateTime().after(currentDate) || activityEntity.getEndDateTime().before(currentDate))
            throw new AppException(ResponseCode.ACTIVITY_DATE_ERROR.getCode(), ResponseCode.ACTIVITY_DATE_ERROR.getInfo());

        // 3.查询未被使用的活动参与订单
        UserRaffleOrderEntity userRaffleOrderEntity = repository.queryNoUsedRaffleOrder(partakeRaffleActivityEntity);
        if (null != userRaffleOrderEntity) {
            log.info("创建参与活动订单【已存在 未消费】 userId:{}, activityId:{}, userRaffleOrderEntity:{}", userId, activityId, JSON.toJSONString(userRaffleOrderEntity));
            return userRaffleOrderEntity;
        }

        // 4.账户额度过滤 并返回账户构建对象
        CreatePartakeOrderAggregate createPartakeOrderAggregate = this.doFilterAccount(userId, activityId, currentDate);

        // 5.构建订单
        UserRaffleOrderEntity userRaffleOrder = this.buildUserRaffleOrder(userId, activityId, currentDate);

        // 6.填充抽奖单实体对象
        createPartakeOrderAggregate.setUserRaffleOrderEntity(userRaffleOrder);

        // 7.保存订单聚合对象
        repository.saveCreatePartakeOrderAggregate(createPartakeOrderAggregate);

        return userRaffleOrder;
    }

    /**
     * 创建抽奖单：用户参与抽奖活动 扣减活动账户库存 产生抽奖单 如存在未被使用的抽奖单则直接返回已存在的抽奖单
     *
     * @param userId     用户ID
     * @param activityId 活动ID
     * @return 用户抽奖订单实体对象
     */
    @Override
    public UserRaffleOrderEntity createOrder(String userId, Long activityId) {
        return createOrder(PartakeRaffleActivityEntity.builder()
                .userId(userId)
                .activityId(activityId)
                .build());
    }

    /**
     * 账户过滤器，抽象方法，由子类实现具体的账户过滤逻辑。
     *
     * @param userId      用户ID
     * @param activityId  活动ID
     * @param currentDate 当前时间
     * @return 账户聚合对象
     */
    protected abstract CreatePartakeOrderAggregate doFilterAccount(String userId, Long activityId, Date currentDate);

    /**
     * 构建订单
     *
     * @param userId      用户ID
     * @param activityId  活动ID
     * @param currentDate 当前时间
     * @return 用户抽奖订单实体对象
     */
    protected abstract UserRaffleOrderEntity buildUserRaffleOrder(String userId, Long activityId, Date currentDate);

}

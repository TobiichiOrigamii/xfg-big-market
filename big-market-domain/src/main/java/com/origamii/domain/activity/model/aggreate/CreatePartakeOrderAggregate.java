package com.origamii.domain.activity.model.aggreate;

import com.origamii.domain.activity.model.entity.ActivityAccountDayEntity;
import com.origamii.domain.activity.model.entity.ActivityAccountEntity;
import com.origamii.domain.activity.model.entity.ActivityAccountMonthEntity;
import com.origamii.domain.activity.model.entity.UserRaffleOrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Origami
 * @description 参与活动订单聚合对象
 * @create 2024-10-10 22:56
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePartakeOrderAggregate {

    // 用户ID
    private String userId;

    // 活动ID
    private Long activityId;

    // 订单实体
    private UserRaffleOrderEntity userRaffleOrderEntity;

    // 活动账户实体
    private ActivityAccountEntity activityAccountEntity;

    // 活动账户(日) 实体
    private ActivityAccountDayEntity activityAccountDayEntity;

    // 活动账户(月) 实体
    private ActivityAccountMonthEntity activityAccountMonthEntity;

    // 日账户是否存在标识
    private boolean isExistAccountDayEntity = true;

    // 月账户是否存在标识
    private boolean isExistAccountMonthEntity = true;

}

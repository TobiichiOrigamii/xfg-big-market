package com.origamii.domain.activity.model.entity;

import com.origamii.domain.activity.model.valobj.OrderStateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author Origami
 * @description 活动参与实体对象
 * @create 2024-09-27 15:34
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityOrderEntity {

    //用户ID
    private String userId;

    //sku
    private Long sku;

    //活动ID
    private Long activityId;

    //活动名称
    private String activityName;

    //抽奖策略ID
    private Long strategyId;

    //订单ID
    private String orderId;

    //下单时间
    private Date orderTime;

    //总次数
    private Integer totalCount;

    //日次数
    private Integer dayCount;

    //月次数
    private Integer monthCount;

    // 支付金额
    private BigDecimal payAmount;

    //订单状态
    private OrderStateVO state;

    //业务防重ID - 外部透传 确保业务幂等性
    private String outBusinessNo;

}

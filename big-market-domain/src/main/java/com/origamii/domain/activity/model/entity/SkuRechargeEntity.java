package com.origamii.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Origami
 * @description 活动商品充值实体对象
 * @create 2024-09-28 10:23
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SkuRechargeEntity {

    //用户ID
    private String userId;

    //商品SKU - activity + activity count
    private Long sku;

    //幂等业务单号 外部谁充值谁透传 防止重复充值
    private String outBusinessNo;

}

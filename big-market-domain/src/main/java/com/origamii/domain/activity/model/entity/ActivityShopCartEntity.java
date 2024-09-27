package com.origamii.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Origami
 * @description 活动购物车实体对象
 * @create 2024-09-27 15:34
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityShopCartEntity {

    //用户ID
    private String userId;

    //商品SKU - activity + activity count
    private Long sku;

}

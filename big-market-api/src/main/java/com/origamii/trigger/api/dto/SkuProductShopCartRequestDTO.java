package com.origamii.trigger.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Origami
 * @description 商品购物车请求对象
 * @create 2024-10-31 11:35
 **/
@Data
public class SkuProductShopCartRequestDTO implements Serializable {

    // 用户ID
    private String userId;

    // sku 商品
    private Long sku;


}

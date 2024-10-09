package com.origamii.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Origami
 * @description 活动SKU库存 key值对象
 * @create 2024-10-09 15:49
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivitySkuStockKeyVO {

    // 商品SKU
    private Long sku;

    // 活动ID
    private Long activityId;

}

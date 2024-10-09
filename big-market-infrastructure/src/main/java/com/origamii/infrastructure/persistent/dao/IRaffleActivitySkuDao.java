package com.origamii.infrastructure.persistent.dao;

import com.origamii.infrastructure.persistent.po.RaffleActivitySku;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Origami
 * @description 商品sku dao
 * @create 2024-09-27 14:13
 **/
@Mapper
public interface IRaffleActivitySkuDao {

    /**
     * 查询活动商品
     *
     * @param sku 活动商品
     * @return 活动商品
     */
    RaffleActivitySku queryActivitySku(Long sku);

    /**
     * 趋势更新活动商品sku库存
     *
     * @param sku 活动商品
     */
    void updateActivitySkuStock(Long sku);

    /**
     * 清空数据库库存
     *
     * @param sku 活动商品
     */
    void clearActivitySkuStock(Long sku);
}

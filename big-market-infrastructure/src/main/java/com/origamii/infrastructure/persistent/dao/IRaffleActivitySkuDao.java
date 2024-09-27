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

    RaffleActivitySku queryActivitySku(Long sku);

}

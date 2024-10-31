package com.origamii.domain.activity.service.product;

import com.origamii.domain.activity.model.entity.SkuProductEntity;
import com.origamii.domain.activity.repository.IActivityRepository;
import com.origamii.domain.activity.service.IRaffleActivitySkuProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Origami
 * @description sku商品服务接口
 * @create 2024-10-31 21:29
 **/
@Service
public class RaffleActivitySkuProductService implements IRaffleActivitySkuProductService {

    @Autowired
    private IActivityRepository repository;

    /**
     * 根据活动id查询sku商品列表
     * @param activityId 活动id
     * @return sku商品列表
     */
    @Override
    public List<SkuProductEntity> querySkuProductEntityListByActivityId(Long activityId) {
        return repository.querySkuProductEntityListByActivityId(activityId);
    }
}

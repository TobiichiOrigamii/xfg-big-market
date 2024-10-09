package com.origamii.domain.activity.service.rule.impl;

import com.origamii.domain.activity.model.entity.ActivityCountEntity;
import com.origamii.domain.activity.model.entity.ActivityEntity;
import com.origamii.domain.activity.model.entity.ActivitySkuEntity;
import com.origamii.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import com.origamii.domain.activity.repository.IActivityRepository;
import com.origamii.domain.activity.service.armory.IActivityDispatch;
import com.origamii.domain.activity.service.rule.AbstractActionChain;
import com.origamii.types.enums.ResponseCode;
import com.origamii.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Origami
 * @description 商品库存规则节点
 * @create 2024-10-02 14:14
 **/
@Slf4j
@Component("activity_sku_stock_action")
public class ActivitySkuStockActionChain extends AbstractActionChain {

    @Autowired
    private IActivityDispatch activityDispatch;

    @Autowired
    private IActivityRepository repository;


    @Override
    public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {
        log.info("活动责任链-基础信息 【有效期 状态 库存（sku）】 校验开始，sku:{} activityId:{}", activitySkuEntity.getSku(), activityEntity.getActivityId());
        // 扣减库存
        boolean status = activityDispatch.subtractionActivityStock(activitySkuEntity.getSku(), activityEntity.getEndDateTime());
        // true 库存扣减成功
        if (status) {
            log.info("活动责任链-商品库存处理【有效期 状态 库存（sku）】成功 sku:{} activityId:{}", activitySkuEntity.getSku(), activityEntity.getActivityId());
            // 写入延迟队列 延迟消费更新库存记录
            repository.activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO.builder()
                    .sku(activitySkuEntity.getSku())
                    .activityId(activityEntity.getActivityId())
                    .build());
            return true;
        }
        throw new AppException(ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getCode(), ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getInfo());
    }
}

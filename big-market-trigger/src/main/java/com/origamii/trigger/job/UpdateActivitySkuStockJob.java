package com.origamii.trigger.job;

import com.origamii.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import com.origamii.domain.activity.service.IRaffleActivitySkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Origami
 * @description 更新活动sku库存任务
 * @create 2024-10-09 22:03
 **/
@Slf4j
@Component
public class UpdateActivitySkuStockJob {

    @Autowired
    private IRaffleActivitySkuStockService skuStock;

    @Scheduled(cron = "0/5 * * * * ?")
    public void exec(){
        try{
            // log.info("定时任务 - 更新活动sku库存【延迟队列获取】");
            ActivitySkuStockKeyVO activitySkuStockKeyVO = skuStock.takeQueueValue();
            if (null == activitySkuStockKeyVO) return;
            // log.info("定时任务 - 更新活动sku库存【延迟队列获取】sku sku:{} activityId:{}",activitySkuStockKeyVO.getSku(),activitySkuStockKeyVO.getActivityId());
            skuStock.updateActivitySkuStock(activitySkuStockKeyVO.getSku());
        }catch (Exception e){
            log.error("定时任务 - 更新活动sku库存【延迟队列获取】异常",e);
        }
    }


}

package com.origamii.trigger.job;

import com.origamii.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import com.origamii.domain.strategy.service.IRaffleStock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Origami
 * @description 更新奖品库存
 * 采用了redis更新库存的方式 异步队列更新数据库 数据库和redis最终一致即可
 * @create 2024-09-19 23:23
 **/
@Slf4j
@Component()
public class UpdateAwardStockJob {

    @Autowired
    private IRaffleStock raffleStock;

    @Scheduled(cron = "0/5 * * * * ?")
    public void exec() {
        try {
            // log.info("定时任务 - 更新奖品消耗库存【延迟队列获取】");
            StrategyAwardStockKeyVO strategyAwardStockKeyVO = raffleStock.takeQueueValue();
            if (null == strategyAwardStockKeyVO) return;
            // log.info("定时任务 - 更新奖品消耗库存【延迟队列获取】 strategyId:{} awardId:{}" strategyAwardStockKeyVO.getStrategyId(),strategyAwardStockKeyVO.getAwardId());
            raffleStock.updateStrategyAwardStock(
                    strategyAwardStockKeyVO.getStrategyId(),
                    strategyAwardStockKeyVO.getAwardId()
            );
        } catch (Exception e) {
            log.error("定时任务 - 更新奖品消耗库存【延迟队列获取】异常", e);
        }
    }


}

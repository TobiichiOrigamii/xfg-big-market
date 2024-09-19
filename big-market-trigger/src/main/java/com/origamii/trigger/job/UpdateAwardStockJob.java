package com.origamii.trigger.job;

import lombok.extern.slf4j.Slf4j;
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




}

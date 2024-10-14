package com.origamii.domain.award.model.aggreate;

import com.origamii.domain.award.model.entity.TaskEntity;
import com.origamii.domain.award.model.entity.UserAwardRecordEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Origami
 * @description 用户中奖记录聚合对象
 * @create 2024-10-14 20:47
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAwardRecordAggregate {

    // 用户中奖记录实体
    private UserAwardRecordEntity userAwardRecordEntity;

    // task实体
    private TaskEntity taskEntity;

}

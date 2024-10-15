package com.origamii.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Origami
 * @description 活动抽奖请求对象
 * @create 2024-10-15 13:23
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDrawResponseDTO {

    // 奖品ID
    private Integer awardId;

    // 奖品标题
    private String awardTitle;

    // 排序编号【策略奖品配置的奖品的顺序编号】
    private Integer awardIndex;

}

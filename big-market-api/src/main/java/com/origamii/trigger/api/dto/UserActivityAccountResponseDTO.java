package com.origamii.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Origami
 * @description 用户活动账户应答对象
 * @create 2024-10-23 23:15
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserActivityAccountResponseDTO {

    // 总次数
    private Integer totalCount;

    // 总次数 - 剩余次数
    private Integer totalCountSurplus;

    // 日次数
    private Integer dayCount;

    // 日次数 - 剩余次数
    private Integer dayCountSurplus;

    // 月次数
    private Integer monthCount;

    // 月次数 - 剩余次数
    private Integer monthCountSurplus;

}

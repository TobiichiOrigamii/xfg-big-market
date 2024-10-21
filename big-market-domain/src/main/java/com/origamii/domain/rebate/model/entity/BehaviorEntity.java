package com.origamii.domain.rebate.model.entity;

import com.origamii.domain.rebate.model.valobj.BehaviorTypeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Origami
 * @description 行为实体对象
 * @create 2024-10-21 23:29
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BehaviorEntity {

    // 用户ID
    private String userId;

    // 行为类型 sign - 签到  openai_pay - 支付
    private BehaviorTypeVO behaviorTypeVO;

    // 业务ID 签到则是日期字符串 支付则是外部的业务ID
    private String outBusinessNo;

}

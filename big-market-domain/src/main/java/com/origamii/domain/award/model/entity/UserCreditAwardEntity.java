package com.origamii.domain.award.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Origami
 * @description 用户积分实体对象
 * @create 2024-10-25 20:54
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreditAwardEntity {

    // 用户ID
    private String userId;

    // 积分值
    private BigDecimal creditAmount;

}

package com.origamii.domain.credit.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Origami
 * @description 积分账户实体
 * @create 2024-10-27 21:42
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditAccountEntity {

    // 用户ID
    private String userId;

    // 可用额度 每次扣减的值
    private BigDecimal adjustAmount;

}

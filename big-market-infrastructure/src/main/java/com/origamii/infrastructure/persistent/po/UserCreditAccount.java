package com.origamii.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Origami
 * @description 用户积分账户
 * @create 2024-10-25 20:10
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreditAccount {

    // 自增ID
    private Long id;

    // 用户ID
    private String userId;

    // 总积分
    private BigDecimal totalAmount;

    // 可用积分
    private BigDecimal availableAmount;

    // 账户状态【open - 正常，close - 冻结】
    private String accountStatus;

    // 创建时间
    private Date createTime;

    // 更新时间
    private Date updateTime;

}

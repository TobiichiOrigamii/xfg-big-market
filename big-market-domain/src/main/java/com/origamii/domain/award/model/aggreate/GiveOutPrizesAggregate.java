package com.origamii.domain.award.model.aggreate;

import com.origamii.domain.award.model.entity.UserAwardRecordEntity;
import com.origamii.domain.award.model.entity.UserCreditAwardEntity;
import com.origamii.domain.award.model.valobj.AwardStateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Origami
 * @description 发放奖品聚合对象
 * @create 2024-10-25 20:56
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiveOutPrizesAggregate {

    // 用户ID
    private String userId;

    // 用户发奖记录
    private UserAwardRecordEntity userAwardRecordEntity;

    // 用户积分奖品
    private UserCreditAwardEntity userCreditAwardEntity;


    public static UserAwardRecordEntity createUserAwardRecordEntity(String userId, String orderId, Integer awardId, AwardStateVO awardState) {
        return UserAwardRecordEntity.builder()
                .userId(userId)
                .orderId(orderId)
                .awardId(awardId)
                .awardState(awardState)
                .build();
    }

    public static UserCreditAwardEntity createUserCreditAwardEntity(String userId, BigDecimal creditAmount) {
        return UserCreditAwardEntity.builder()
                .userId(userId)
                .creditAmount(creditAmount)

                .build();
    }


}

package com.origamii.domain.award.service.distribute.impl;

import com.origamii.domain.award.model.aggreate.GiveOutPrizesAggregate;
import com.origamii.domain.award.model.entity.DistributeAwardEntity;
import com.origamii.domain.award.model.entity.UserAwardRecordEntity;
import com.origamii.domain.award.model.entity.UserCreditAwardEntity;
import com.origamii.domain.award.model.valobj.AwardStateVO;
import com.origamii.domain.award.repository.IAwardRepository;
import com.origamii.domain.award.service.distribute.IDistributeAward;
import com.origamii.types.common.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * @author Origami
 * @description 用户积分奖品 支持 award_config 配置  满足黑名单积分奖励
 * @create 2024-10-25 20:29
 **/
@Component("user_credit_random")
public class UserCreditRandomAward implements IDistributeAward {

    @Autowired
    private IAwardRepository repository;

    /**
     * 发放奖品
     *
     * @param distributeAwardEntity 奖品信息
     */
    @Override
    public void giveOutPrizes(DistributeAwardEntity distributeAwardEntity) {
        Integer awardId = distributeAwardEntity.getAwardId();
        String awardConfig = distributeAwardEntity.getAwardConfig();

        // 解析奖品配置
        if (StringUtils.isBlank(awardConfig)) {
            // 0.1-1   1-100 两种随机值
            awardConfig = repository.queryAwardConfig(awardId);
        }

        // 解析奖品范围
        String[] creditRange = awardConfig.split(Constants.SPLIT);
        if (creditRange.length != 2) {
            throw new RuntimeException("award_config 【" + awardConfig + "】 配置错误，请检查");
        }

        // 生成随机积分值
        BigDecimal creditAmount = generateRandom(new BigDecimal(creditRange[0]), new BigDecimal(creditRange[1]));

        // 构建聚合对象
        UserAwardRecordEntity userAwardRecordEntity = GiveOutPrizesAggregate.createUserAwardRecordEntity(
                distributeAwardEntity.getUserId(),
                distributeAwardEntity.getOrderId(),
                distributeAwardEntity.getAwardId(),
                AwardStateVO.compete
        );
        UserCreditAwardEntity userCreditAwardEntity = GiveOutPrizesAggregate.createUserCreditAwardEntity(
                distributeAwardEntity.getUserId(),
                creditAmount
        );
        GiveOutPrizesAggregate giveOutPrizesAggregate = GiveOutPrizesAggregate.builder()
                .userId(distributeAwardEntity.getUserId())
               .userAwardRecordEntity(userAwardRecordEntity)
               .userCreditAwardEntity(userCreditAwardEntity)
               .build();

        // 存储发奖对象
        repository.saveGiveOutPrizesAggregate(giveOutPrizesAggregate);
    }


    /**
     * 生成随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 介于最小值和最大值之间的随机数
     */
    private BigDecimal generateRandom(BigDecimal min, BigDecimal max) {
        if (min.equals(max)) return min;
        /*
          假设输入 min = 1.00 和 max = 10.00，代码执行的过程如下：
          1. Math.random()
          Math.random() 生成一个 [0, 1) 之间的 double 类型的随机数。假设这里生成的随机数是 0.75。
          2. BigDecimal.valueOf(Math.random())
          Math.random() 返回的是 double，需要转换为 BigDecimal，便于后续精确的计算。
          假设 Math.random() 返回 0.75，转换成 BigDecimal 后为 BigDecimal.valueOf(0.75)。
          3. max.subtract(min)
          max.subtract(min) 计算 max 和 min 之间的范围差，以便我们知道生成的随机数要在这个差值范围内调整。
          假设 max 为 10.00 和 min 为 1.00，则 max.subtract(min) 的结果是 9.00。
          4. BigDecimal.valueOf(Math.random()).multiply(max.subtract(min))
          接下来，将生成的随机数 0.75 转换成 BigDecimal 后，乘以 max.subtract(min) 结果的范围差值。
          具体计算：0.75 * 9.00 = 6.75。
          结果 6.75 是一个在 0 和 9.00 之间的随机值。
          5. min.add(...)
          最后，将生成的范围内随机值 6.75 加到 min 上。
          计算 1.00 + 6.75 = 7.75，即生成的随机数在 [min, max] 范围内，即 [1.00, 10.00] 之间的值。
         */
        BigDecimal random = min.add(BigDecimal.valueOf(Math.random()).multiply(max.subtract(min)));
        return random.round(new MathContext(3));
    }


}

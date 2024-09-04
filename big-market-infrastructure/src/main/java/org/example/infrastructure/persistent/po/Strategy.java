package org.origami.infrastructure.persistent.po;

import lombok.Data;

/**
 * @author Origami
 * @description 抽奖策略
 * @create 2024-07-18 16:47
 **/
@Data
public class Strategy {

    /** 自增ID */
    private Long id;
    /** 抽奖策略ID */
    private Long strategyId;
    /** 抽奖策略描述 */
    private String strategyDesc;
    /** 抽奖规则模型 */
    private String ruleModels;
    /** 创建时间 */
    private String createTime;
    /** 修改时间 */
    private String updateTime;
}

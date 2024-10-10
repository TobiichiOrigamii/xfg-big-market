package com.origamii.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author Origami
 * @description 用户中奖记录表
 * @create 2024-10-10 12:21
 **/
@Data
public class UserAwardRecord {

    // 自增ID
    private String id;

    // 消息主题
    private String topic;

    // 消息主体
    private String message;

    // 任务状态
    private String state;

    // 创建时间
    private Date createTime;

    // 更新时间
    private Date updateTime;

}

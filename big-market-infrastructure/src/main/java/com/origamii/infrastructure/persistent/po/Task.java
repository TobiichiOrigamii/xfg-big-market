package com.origamii.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Origami
 * @description 任务表，发送MQ
 * @create 2024-10-10 12:19
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    //自增ID
    private String id;

    //ID
    private String userId;

    //消息主题
    private String topic;

    //消息编号
    private String messageId;

    //消息主体
    private String message;

    //任务状态；create-创建、completed-完成、fail-失败
    private String state;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

}

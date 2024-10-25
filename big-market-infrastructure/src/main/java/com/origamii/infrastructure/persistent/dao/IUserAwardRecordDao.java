package com.origamii.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import com.origamii.infrastructure.persistent.po.UserAwardRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Origami
 * @description 用户中奖记录表
 * @create 2024-10-10 12:25
 **/
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserAwardRecordDao {

    /**
     * 写入用户中奖记录
     *
     * @param userAwardRecord 用户中奖记录
     */
    void insert(UserAwardRecord userAwardRecord);

    /**
     * 更新奖品记录状态为已完成
     * @param userAwardRecordReq 用户中奖记录
     * @return 更新结果
     */
    int updateAwardRecordCompletedState(UserAwardRecord userAwardRecordReq);
}

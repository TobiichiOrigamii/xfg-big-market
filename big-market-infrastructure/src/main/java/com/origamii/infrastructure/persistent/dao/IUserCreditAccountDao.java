package com.origamii.infrastructure.persistent.dao;

import com.origamii.infrastructure.persistent.po.UserCreditAccount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Origami
 * @description 用户积分账户
 * @create 2024-10-25 20:20
 **/
@Mapper
public interface IUserCreditAccountDao {

    /**
     * 更新用户积分账户
     * @param userCreditAccountReq 用户积分账户
     * @return 更新结果
     */
     int updateAddAmount(UserCreditAccount userCreditAccountReq);

    /**
     * 插入用户积分账户
     * @param userCreditAccountReq 用户积分账户
     */
    void insert(UserCreditAccount userCreditAccountReq);
}

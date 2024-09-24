package com.origamii.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import com.origamii.infrastructure.persistent.po.Award;

import java.util.List;

/**
 * @author Origami
 * @description 奖品表 DAO
 * @create 2024-07-18 17:45
 **/
@Mapper
public interface IAwardDao {

    /**
     * 查询奖品列表
     *
     * @return 奖品列表
     */
    List<Award> queryAwardList();

}

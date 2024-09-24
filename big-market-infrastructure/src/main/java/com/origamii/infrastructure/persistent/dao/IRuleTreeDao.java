package com.origamii.infrastructure.persistent.dao;

import com.origamii.infrastructure.persistent.po.RuleTree;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author Origami
 * @description 规则树表DAO
 * @create 2024-09-18 10:27
 **/
@Mapper
public interface IRuleTreeDao {

    /**
     * 根据规则树ID查询规则树
     *
     * @param treeId 规则树ID
     * @return 规则树
     */
    RuleTree queryRuleTreeByTreeId(String treeId);

}

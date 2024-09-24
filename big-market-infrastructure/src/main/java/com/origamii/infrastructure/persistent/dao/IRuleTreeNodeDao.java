package com.origamii.infrastructure.persistent.dao;

import com.origamii.infrastructure.persistent.po.RuleTreeNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Origami
 * @description 规则树节点表DAO
 * @create 2024-09-18 10:30
 **/
@Mapper
public interface IRuleTreeNodeDao {

    /**
     * 查询规则树节点列表
     * @param treeId 规则树ID
     * @return 规则树节点列表
     */
    List<RuleTreeNode> queryRuleTreeNodeListByTreeId(String treeId);

}

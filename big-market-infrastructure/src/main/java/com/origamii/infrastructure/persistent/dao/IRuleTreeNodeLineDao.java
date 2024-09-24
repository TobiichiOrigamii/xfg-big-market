package com.origamii.infrastructure.persistent.dao;

import com.origamii.infrastructure.persistent.po.RuleTreeNodeLine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Origami
 * @description 规则树节点连线表DAO
 * @create 2024-09-18 10:31
 **/
@Mapper
public interface IRuleTreeNodeLineDao {

    /**
     * 查询规则树节点连线列表
     *
     * @param treeId 规则树ID
     * @return 规则树节点连线列表
     */
    List<RuleTreeNodeLine> queryRuleTreeNodeLineListByTreeId(String treeId);

}

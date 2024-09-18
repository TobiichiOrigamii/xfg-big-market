package com.origamii.infrastructure.persistent.dao;

import com.origamii.infrastructure.persistent.po.RuleTree;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author Origami
 * @description 规则树表ID
 * @create 2024-09-18 10:27
 **/
@Mapper
public interface IRuleTreeDao {

    RuleTree queryRuleTreeByTreeId(String treeId);


}

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

    List<RuleTreeNodeLine> queryRuleTreeNodeLineListByTreeId(String treeId);

}

package com.origamii.domain.strategy.model.valobj;

import com.origamii.domain.strategy.service.rule.factory.DefaultLogicFactory;
import com.origamii.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Origami
 * @description 抽奖策略规则值对象
 * 没有唯一ID 仅限于从数据库中查询对象
 * @create 2024-09-11 21:21
 **/
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardRuleModelVO {

    private String ruleModel;

    public String[] raffleCenterRuleModelList(){
        List<String> ruleModelList = new ArrayList<>();
        String[] ruleModelvalues = ruleModel.split(Constants.SPLIT);
        for(String ruleModelvalue : ruleModelvalues){
            if(DefaultLogicFactory.LogicModel.isCenter(ruleModelvalue)){
                ruleModelList.add(ruleModelvalue);
            }
        }
        return ruleModelList.toArray(new String[0]);
    }
}

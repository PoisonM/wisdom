package com.wisdom.common.dto.rule;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by zbm84 on 2017/8/3.
 */
public class BusinessBonusRuleDTO {

    @JSONField(name = "ruleType")
    private String ruleType;

    @JSONField(name = "ruleDetailList")
    private List<RuleDetailDTO> ruleDetailList;

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public List<RuleDetailDTO> getRuleDetailList() {
        return ruleDetailList;
    }

    public void setRuleDetailList(List<RuleDetailDTO> ruleDetailList) {
        this.ruleDetailList = ruleDetailList;
    }

}

package com.wisdom.common.dto.rule;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by zbm84 on 2017/8/3.
 */
public class RuleDetailDTO {

    @JSONField(name = "minExpense")
    private float minExpense;

    @JSONField(name = "maxExpense")
    private float maxExpense;

    @JSONField(name = "percentage")
    private float percentage;

    @JSONField(name = "exceedMinPercentage")
    private float exceedMinPercentage;

    @JSONField(name = "promote")
    private boolean promote;

    @JSONField(name = "promoteLevel")
    private String promoteLevel;

    public float getMinExpense() {
        return minExpense;
    }

    public void setMinExpense(float minExpense) {
        this.minExpense = minExpense;
    }

    public float getMaxExpense() {
        return maxExpense;
    }

    public void setMaxExpense(float maxExpense) {
        this.maxExpense = maxExpense;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public float getExceedMinPercentage() {
        return exceedMinPercentage;
    }

    public void setExceedMinPercentage(float exceedMinPercentage) {
        this.exceedMinPercentage = exceedMinPercentage;
    }

    public boolean isPromote() {
        return promote;
    }

    public void setPromote(boolean promote) {
        this.promote = promote;
    }

    public String getPromoteLevel() {
        return promoteLevel;
    }

    public void setPromoteLevel(String promoteLevel) {
        this.promoteLevel = promoteLevel;
    }
}

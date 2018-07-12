package com.wisdom.common.dto.activity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by cjk on 2017/5/24.
 */
public class ShareActivityDTO {

    @JSONField(name = "sysUserId")
    private String sysUserId;

    @JSONField(name = "parentSysUserId")
    private String parentSysUserId;

    @JSONField(name = "createTime",format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JSONField(name = "transactionId")
    private String transactionId;

    @JSONField(name = "incomeId")
    private String incomeId;


    @JSONField(name = "amount")
    private Float amount;


    @JSONField(name = "parentType")
    private String parentType;


    @JSONField(name = "userTypeBefore")
    private String userTypeBefore;

    @JSONField(name = "userTypeAfter")
    private String userTypeAfter;

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public String getUserTypeBefore() {
        return userTypeBefore;
    }

    public void setUserTypeBefore(String userTypeBefore) {
        this.userTypeBefore = userTypeBefore;
    }

    public String getUserTypeAfter() {
        return userTypeAfter;
    }

    public void setUserTypeAfter(String userTypeAfter) {
        this.userTypeAfter = userTypeAfter;
    }

    public String getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(String incomeId) {
        this.incomeId = incomeId;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getParentSysUserId() {
        return parentSysUserId;
    }

    public void setParentSysUserId(String parentSysUserId) {
        this.parentSysUserId = parentSysUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}

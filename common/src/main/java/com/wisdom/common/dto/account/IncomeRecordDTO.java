package com.wisdom.common.dto.account;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by zbm84 on 2017/8/3.
 */
public class IncomeRecordDTO {

    @JSONField(name = "id")
    private String id;

    @JSONField(name = "amount")
    private float amount;

    @JSONField(name = "transactionAmount")
    private float transactionAmount;

    @JSONField(name = "incomeType")
    private String incomeType;

    @JSONField(name = "transactionId")
    private String transactionId;

    @JSONField(name = "sysUserId")
    private String sysUserId;

    @JSONField(name = "nextUserId")
    private String nextUserId;

    @JSONField(name = "userType")
    private String userType;

    @JSONField(name = "nextUserType")
    private String nextUserType;

    @JSONField(name = "parentRelation")
    private String parentRelation;

    @JSONField(name = "nickName")
    private String nickName;

    @JSONField(name = "mobile")
    private String mobile;

    @JSONField(name = "nextUserNickName")
    private String nextUserNickName;

    @JSONField(name = "nextUserMobile")
    private String nextUserMobile;

    @JSONField(name = "identifyNumber")
    private String identifyNumber;

    @JSONField(name = "nextUserIdentifyNumber")
    private String nextUserIdentifyNumber;

    @JSONField(name = "updateDate")
    private Date updateDate;

    @JSONField(name = "createDate")
    private Date createDate;

    @JSONField(name = "status")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getNextUserId() {
        return nextUserId;
    }

    public void setNextUserId(String nextUserId) {
        this.nextUserId = nextUserId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getNextUserType() {
        return nextUserType;
    }

    public void setNextUserType(String nextUserType) {
        this.nextUserType = nextUserType;
    }

    public String getParentRelation() {
        return parentRelation;
    }

    public void setParentRelation(String parentRelation) {
        this.parentRelation = parentRelation;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdentifyNumber() {
        return identifyNumber;
    }

    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNextUserNickName() {
        return nextUserNickName;
    }

    public void setNextUserNickName(String nextUserNickName) {
        this.nextUserNickName = nextUserNickName;
    }

    public String getNextUserMobile() {
        return nextUserMobile;
    }

    public void setNextUserMobile(String nextUserMobile) {
        this.nextUserMobile = nextUserMobile;
    }

    public String getNextUserIdentifyNumber() {
        return nextUserIdentifyNumber;
    }

    public void setNextUserIdentifyNumber(String nextUserIdentifyNumber) {
        this.nextUserIdentifyNumber = nextUserIdentifyNumber;
    }
}

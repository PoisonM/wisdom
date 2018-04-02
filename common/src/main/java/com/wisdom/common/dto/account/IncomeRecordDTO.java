package com.wisdom.common.dto.account;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by zbm84 on 2017/8/3.
 */
public class IncomeRecordDTO {

    @JSONField(name = "id")
    private String id;

    @JSONField(name = "sysUserId")
    private String sysUserId;

    @JSONField(name = "amount")
    private float amount;

    @JSONField(name = "createDate")
    private Date createDate;

    @JSONField(name = "updateDate")
    private Date updateDate;

    @JSONField(name = "incomeType")
    private String incomeType;

    @JSONField(name = "transactionId")
    private String transactionId;

    @JSONField(name = "status")
    private String status;

    /**
     * 用户名
     */
    @JSONField(name = "nickName")
    private String nickName;

    /**
     * 用户类型(等级)
     */
    @JSONField(name = "userType")
    private String userType;

    /**
     * 手机号
     */
    @JSONField(name = "mobile")
    private String mobile;

    /**
     * 身份证号
     */
    @JSONField(name = "identifyNumber")
    private String identifyNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}

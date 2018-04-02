package com.wisdom.common.dto.account;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class CommissionDTO {
    /**
     * 提成信息id
     */
    @JSONField(name = "id")
    private String id;

    /**
     * 用户的系统ID
     */
    @JSONField(name = "sysUserId")
    private String sysUserId;

    /**
     * 发放提成的时间
     */
    @JSONField(name = "createDate")
    private Date createDate;

    /**
     * 每月奖励用户的提成钱数
     */
    @JSONField(name = "amount")
    private String amount;

    /**
     * 用户名
     */
    @JSONField(name = "nickName")
    private String nickName;

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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
}

package com.wisdom.common.dto.system;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 导出月度依据哪个用户提成金额DTO
 */
public class ExportMonthTransactionExcelDTO {
    /**
     * 用户的系统ID
     */
    @JSONField(name = "sysUserId")
    private String sysUserId;
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
     * 交易流水号
     */
    @JSONField(name = "transactionId")
    private String transactionId;
    /**
     * 付款时间
     */
    @JSONField(name = "payDate")
    private String payDate;
    /**
     * 提成金额
     */
    @JSONField(name = "monthAmount")
    private float monthAmount;



    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public float getMonthAmount() {
        return monthAmount;
    }

    public void setMonthAmount(float monthAmount) {
        this.monthAmount = monthAmount;
    }
}

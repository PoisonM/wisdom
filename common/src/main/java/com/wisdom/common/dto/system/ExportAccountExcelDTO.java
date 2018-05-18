package com.wisdom.common.dto.system;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 导出账户余额DTO
 */
public class ExportAccountExcelDTO {
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
     * 用户账户中的余额，分位单位
     */
    @JSONField(name = "balance")
    private Float balance;

    /**
     * 可提现金额
     */
    @JSONField(name = "balanceYes")
    private String balanceYes;

    public String getBalanceYes() {
        return balanceYes;
    }

    public void setBalanceYes(String balanceYes) {
        this.balanceYes = balanceYes;
    }

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

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }
}

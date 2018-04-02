package com.wisdom.common.dto.system;

import com.alibaba.fastjson.annotation.JSONField;

public class UserBankCardInfoDTO {

    @JSONField(name = "withDrawId")
    private String withDrawId;

    @JSONField(name = "sysUserId")
    private String sysUserId;

    @JSONField(name = "userName")
    private String userName;

    @JSONField(name = "bankCardNumber")
    private String bankCardNumber;

    @JSONField(name = "bankCardAddress")
    private String bankCardAddress;

    public String getSysUserId() {
        return sysUserId;
    }

    public String getWithDrawId() {
        return withDrawId;
    }

    public void setWithDrawId(String withDrawId) {
        this.withDrawId = withDrawId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public String getBankCardAddress() {
        return bankCardAddress;
    }

    public void setBankCardAddress(String bankCardAddress) {
        this.bankCardAddress = bankCardAddress;
    }
}
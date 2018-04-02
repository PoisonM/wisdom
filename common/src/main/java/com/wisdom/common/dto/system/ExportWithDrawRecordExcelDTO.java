package com.wisdom.common.dto.system;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 导出账户提现DTO
 */
public class ExportWithDrawRecordExcelDTO {
    /**
     * 用户系统id
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
     * 提现金额
     */
    @JSONField(name = "moneyAmount")
    private float moneyAmount;

    /**
     * 提现状态，0表示未审核，1表示审核通过，2表示审核拒绝
     */
    @JSONField(name = "status")
    private String status;
    /**
     * 交易流水号
     */
    @JSONField(name = "withdrawId")
    private String withdrawId;

    /**
     * 姓名
     */
    @JSONField(name = "userName")
    private String userName;

    /**
     * 银行卡
     */
    @JSONField(name = "bankCardNumber")
    private String bankCardNumber;
    /**
     * 开户行地址
     */
    @JSONField(name = "bankCardAddress")
    private String bankCardAddress;

    /**
     * 身份证号
     */
    @JSONField(name = "identifyNumber")
    private String identifyNumber;

    /**
     * 提现申请创建时间
     */
    @JSONField(name = "createDate")
    private String createDate;

    /**
     * 提现申请更新时间
     */
    @JSONField(name = "updateDate")
    private String updateDate;



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

    public String getWithdrawId() {
        return withdrawId;
    }

    public void setWithdrawId(String withdrawId) {
        this.withdrawId = withdrawId;
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

    public String getIdentifyNumber() {
        return identifyNumber;
    }

    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public float getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(float moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public String getStatus() {
        if("0".equals(status)){
            return "申请中";
        }else if("1".equals(status)){
            return "审核通过";
        }else if("2".equals(status)){
            return "拒绝提现";
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBankCardAddress() {
        return bankCardAddress;
    }

    public void setBankCardAddress(String bankCardAddress) {
        this.bankCardAddress = bankCardAddress;
    }
}

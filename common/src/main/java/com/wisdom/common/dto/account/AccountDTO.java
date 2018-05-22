package com.wisdom.common.dto.account;

import com.alibaba.fastjson.annotation.JSONField;
import com.wisdom.common.dto.system.UserBankCardInfoDTO;

import java.util.Date;
import java.util.List;

public class AccountDTO {

    /**
     * id
     */
    @JSONField(name = "id")
    private String id;

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
     * 身份证号
     */
    @JSONField(name = "identifyNumber")
    private String identifyNumber;


    /**
     * 用户在微信公众号中的openid
     */
    @JSONField(name = "userOpenId")
    private String userOpenId;

    /**
     * 用户账户中的余额，分位单位
     */
    @JSONField(name = "balance")
    private Float balance;

    /**
     * 不可提现金额
     */
    @JSONField(name = "balanceDeny")
    private Float balanceDeny;

    /**
     * 可提现金额
     */
    private String balanceYes;

    /**
     * 用户在系统中的积分
     */
    @JSONField(name = "score")
    private Float score;

    /**
     * 用户账户的状态，normal表示正常，inactive表示没有激活，frozen表示冻结
     */
    @JSONField(name = "status")
    private String status;

    @JSONField(name = "todayIncome")
    private Float todayIncome;

    @JSONField(name = "bankCardInfo")
    private UserBankCardInfoDTO bankCardInfo;

    /**
     * 账户更新时间
     */
    @JSONField(name = "updateDate")
    private Date updateDate;

    /**
     * 各个订单状态的统计数"0","1","4","2"
     */
    @JSONField(name = "orderStatusCountList")
    private List<Integer> orderStatusCountList;

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

    public String getIdentifyNumber() {
        return identifyNumber;
    }

    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
    }

    public String getUserOpenId() {
        return userOpenId;
    }

    public void setUserOpenId(String userOpenId) {
        this.userOpenId = userOpenId;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Float getBalanceDeny() {
        return balanceDeny;
    }

    public void setBalanceDeny(Float balanceDeny) {
        this.balanceDeny = balanceDeny;
    }

    public String getBalanceYes() {
        return balanceYes;
    }

    public void setBalanceYes(String balanceYes) {
        this.balanceYes = balanceYes;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getTodayIncome() {
        return todayIncome;
    }

    public void setTodayIncome(Float todayIncome) {
        this.todayIncome = todayIncome;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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

    public UserBankCardInfoDTO getBankCardInfo() {
        return bankCardInfo;
    }

    public void setBankCardInfo(UserBankCardInfoDTO bankCardInfo) {
        this.bankCardInfo = bankCardInfo;
    }

    public List<Integer> getOrderStatusCountList() {
        return orderStatusCountList;
    }

    public void setOrderStatusCountList(List<Integer> orderStatusCountList) {
        this.orderStatusCountList = orderStatusCountList;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
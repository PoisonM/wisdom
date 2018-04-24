package com.wisdom.common.dto.account;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by zbm84 on 2017/8/3.
 */
public class IncomeRecordDTO {

    @JSONField(name = "id")
    private String id;//id

    @JSONField(name = "amount")
    private float amount;//收入金额

    @JSONField(name = "transactionAmount")
    private float transactionAmount;//来源金额

    @JSONField(name = "incomeType")
    private String incomeType;//类型

    @JSONField(name = "transactionId")
    private String transactionId;//交易id

    @JSONField(name = "sysUserId")
    private String sysUserId;//上级用户id

    @JSONField(name = "nextUserId")
    private String nextUserId;//下级用户id

    @JSONField(name = "userType")
    private String userType;//上级用户获益时等级

    @JSONField(name = "userTypeNow")
    private String userTypeNow;//上级用户现在的等级

    @JSONField(name = "nextUserTypeNow")
    private String nextUserTypeNow;//下级用户现在的等级

    @JSONField(name = "nextUserType")
    private String nextUserType;//下级用户等级

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

    @JSONField(name = "payDate")
    private Date payDate;

    @JSONField(name = "status")
    private String status;

    @JSONField(name = "orderId")
    private String orderId;

    @JSONField(name = "orderAmount")
    private String orderAmount;

    @JSONField(name = "orderStatus")
    private String orderStatus;

    //审核人id
    @JSONField(name = "checkSysUserId")
    private String checkSysUserId;

    //审核人name
    @JSONField(name = "checkUserName")
    private String checkUserName;

    //审核人类型    财务人员 finance-1  运营人员operation-1
    @JSONField(name = "checkUserType")
    private String checkUserType;

    //审核状态  1、代表审核通过  0 、代表审核未通过
    @JSONField(name = "checkStatus")
    private String checkStatus;

    //相关人员审核记录  0、代表未审核  1、代表运营人员operation-1通过 2 、代表财务人员 finance-1通过 3代表有一方拒绝 4代表双方已审核
    @JSONField(name = "secondCheckStatus")
    private String secondCheckStatus;

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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUserTypeNow() {
        return userTypeNow;
    }

    public void setUserTypeNow(String userTypeNow) {
        this.userTypeNow = userTypeNow;
    }

    public String getNextUserTypeNow() {
        return nextUserTypeNow;
    }

    public void setNextUserTypeNow(String nextUserTypeNow) {
        this.nextUserTypeNow = nextUserTypeNow;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getCheckSysUserId() {
        return checkSysUserId;
    }

    public void setCheckSysUserId(String checkSysUserId) {
        this.checkSysUserId = checkSysUserId;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getCheckUserType() {
        return checkUserType;
    }

    public void setCheckUserType(String checkUserType) {
        this.checkUserType = checkUserType;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getSecondCheckStatus() {
        return secondCheckStatus;
    }

    public void setSecondCheckStatus(String secondCheckStatus) {
        this.secondCheckStatus = secondCheckStatus;
    }
}

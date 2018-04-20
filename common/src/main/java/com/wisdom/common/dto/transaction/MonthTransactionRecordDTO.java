package com.wisdom.common.dto.transaction;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by zbm84 on 2017/8/3.
 */
public class MonthTransactionRecordDTO {

    @JSONField(name = "id")
    private String id;

    @JSONField(name = "transactionId")
    private String transactionId;

    @JSONField(name = "userId")
    private String userId;

    @JSONField(name = "amount")
    private float amount;

    @JSONField(name = "createDate")
    private Date createDate;

    @JSONField(name = "updateDate")
    private Date updateDate;

    @JSONField(name = "nextUserId")
    private String nextUserId;

    /**
     * 用户名
     */
    @JSONField(name = "nickName")
    private String nickName;

    @JSONField(name = "nextUserNickName")
    private String nextUserNickName;

    /**
     * 手机号
     */
    @JSONField(name = "mobile")
    private String mobile;

    @JSONField(name = "nextUserMobile")
    private String nextUserMobile;

    /**
     * 身份证号
     */
    @JSONField(name = "identifyNumber")
    private String identifyNumber;

    @JSONField(name = "nextUserIdentifyNumber")
    private String nextUserIdentifyNumber;

    /**
     * 用户等级
     */
    @JSONField(name = "userType")
    private String userType;

    @JSONField(name = "nextUserType")
    private String nextUserType;

    @JSONField(name = "parentRelation")
    private String parentRelation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getNextUserId() {
        return nextUserId;
    }

    public void setNextUserId(String nextUserId) {
        this.nextUserId = nextUserId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNextUserNickName() {
        return nextUserNickName;
    }

    public void setNextUserNickName(String nextUserNickName) {
        this.nextUserNickName = nextUserNickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNextUserMobile() {
        return nextUserMobile;
    }

    public void setNextUserMobile(String nextUserMobile) {
        this.nextUserMobile = nextUserMobile;
    }

    public String getIdentifyNumber() {
        return identifyNumber;
    }

    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
    }

    public String getNextUserIdentifyNumber() {
        return nextUserIdentifyNumber;
    }

    public void setNextUserIdentifyNumber(String nextUserIdentifyNumber) {
        this.nextUserIdentifyNumber = nextUserIdentifyNumber;
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
}

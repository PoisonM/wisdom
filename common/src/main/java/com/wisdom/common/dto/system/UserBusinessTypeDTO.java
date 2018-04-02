package com.wisdom.common.dto.system;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class UserBusinessTypeDTO {

    @JSONField(name = "id")
    private String id;

    @JSONField(name = "parentUserId")
    private String parentUserId;

    @JSONField(name = "sysUserId")
    private String sysUserId;

    @JSONField(name = "userType")
    private String userType;

    @JSONField(name = "createDate")
    private Date createDate;

    @JSONField(name = "livingPeriod")
    private int livingPeriod;

    @JSONField(name = "status")
    private String status;

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

    public String getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(String parentUserId) {
        this.parentUserId = parentUserId;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIdentifyNumber() {
        return identifyNumber;
    }

    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
    }

    public int getLivingPeriod() {
        return livingPeriod;
    }

    public void setLivingPeriod(int livingPeriod) {
        this.livingPeriod = livingPeriod;
    }
}
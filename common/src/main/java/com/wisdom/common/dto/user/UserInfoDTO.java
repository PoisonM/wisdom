package com.wisdom.common.dto.user;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class UserInfoDTO {

    @JSONField(name = "id")
    private String id;

    @JSONField(name = "nickname")
    private String nickname;

    @JSONField(name = "password")
    private String password;

    @JSONField(name = "email")
    private String email;

    //身份证号
    @JSONField(name = "identifyNumber")
    private String identifyNumber;

    @JSONField(name = "mobile")
    private String mobile;

    @JSONField(name = "userType")
    private String userType;

    @JSONField(name = "userOpenid")
    private String userOpenid;

    @JSONField(name = "parentUserId")
    private String parentUserId;

    @JSONField(name = "weixinAttentionStatus")
    private String weixinAttentionStatus;

    @JSONField(name = "photo")
    private String photo;

    @JSONField(name = "loginIp")
    private String loginIp;

    @JSONField(name = "loginDate")
    private Date loginDate;

    @JSONField(name = "createDate")
    private Date createDate;

    @JSONField(name = "beginTime")
    private Date beginTime;

    @JSONField(name = "livingPeriod")
    private int livingPeriod;

    @JSONField(name = "delFlag")
    private String delFlag;

    @JSONField(name = "extendUserType")
    private String extendUserType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentifyNumber() {
        return identifyNumber;
    }

    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserOpenid() {
        return userOpenid;
    }

    public void setUserOpenid(String userOpenid) {
        this.userOpenid = userOpenid;
    }

    public String getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(String parentUserId) {
        this.parentUserId = parentUserId;
    }

    public String getWeixinAttentionStatus() {
        return weixinAttentionStatus;
    }

    public void setWeixinAttentionStatus(String weixinAttentionStatus) {
        this.weixinAttentionStatus = weixinAttentionStatus;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public int getLivingPeriod() {
        return livingPeriod;
    }

    public void setLivingPeriod(int livingPeriod) {
        this.livingPeriod = livingPeriod;
    }

    public String getExtendUserType() {
        return extendUserType;
    }

    public void setExtendUserType(String extendUserType) {
        this.extendUserType = extendUserType;
    }
}
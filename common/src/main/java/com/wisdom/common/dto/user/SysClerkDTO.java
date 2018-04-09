package com.wisdom.common.dto.user;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class SysClerkDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //
    private String sysBossId;

    //
    private String sysShopId;

    //user表主键
    private String sysUserId;

    //昵称
    private String nickname;

    //
    private String name;

    //密码
    private String password;

    //
    private String email;

    //身份证号
    private String identifyNumber;

    //手机号
    private String mobile;

    //用户类型
    private String userType;

    //微信openId
    private String userOpenid;

    //关注状态
    private String weixinAttentionStatus;

    //照片
    private String photo;

    //登陆id地址
    private String loginIp;

    //登陆时间
    private Date loginDate;

    //删除标记，1表示用户是否已经剔除系统，0表示用户是系统内的正常用户
    private String delFlag;

    //
    private String address;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSysBossId() {
        return sysBossId;
    }

    public void setSysBossId(String sysBossId) {
        this.sysBossId = sysBossId;
    }

    public String getSysShopId() {
        return sysShopId;
    }

    public void setSysShopId(String sysShopId) {
        this.sysShopId = sysShopId;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
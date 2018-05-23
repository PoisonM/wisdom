package com.wisdom.common.dto.user;

import java.io.Serializable;
import java.util.Date;

import com.wisdom.common.entity.BaseEntity;

public class SysBossDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //user表主键
    private String sysUserId;

    //
    private String parentShopId;

    //
    private String currentShopId;

    //
    private String sysBossCode;

    //
    private String currentStoreId;

    //
    private String name;

    //昵称
    private String nickname;

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

    //
    private String weChat;

    //
    private String qq;

    //照片
    private String photo;

    //登陆id地址
    private String loginIp;

    //登陆时间
    private Date loginDate;

    //删除标记，1表示用户是否已经剔除系统，0表示用户是系统内的正常用户
    private String delFlag;

    //身份证正面
    private String idFrontImage;

    //身份证反面
    private String idBackImage;

    //
    private String address;

    private static final long serialVersionUID = 1L;

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

    public String getParentShopId() {
        return parentShopId;
    }

    public void setParentShopId(String parentShopId) {
        this.parentShopId = parentShopId;
    }

    public String getCurrentShopId() {
        return currentShopId;
    }

    public void setCurrentShopId(String currentShopId) {
        this.currentShopId = currentShopId;
    }

    public String getSysBossCode() {
        return sysBossCode;
    }

    public void setSysBossCode(String sysBossCode) {
        this.sysBossCode = sysBossCode;
    }

    public String getCurrentStoreId() {
        return currentStoreId;
    }

    public void setCurrentStoreId(String currentStoreId) {
        this.currentStoreId = currentStoreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getWeixinAttentionStatus() {
        return weixinAttentionStatus;
    }

    public void setWeixinAttentionStatus(String weixinAttentionStatus) {
        this.weixinAttentionStatus = weixinAttentionStatus;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
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

    public String getIdFrontImage() {
        return idFrontImage;
    }

    public void setIdFrontImage(String idFrontImage) {
        this.idFrontImage = idFrontImage;
    }

    public String getIdBackImage() {
        return idBackImage;
    }

    public void setIdBackImage(String idBackImage) {
        this.idBackImage = idBackImage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
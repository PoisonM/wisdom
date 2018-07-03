package com.wisdom.common.dto.wexin;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by zbm84 on 2017/6/7.
 */
public class WeixinShareDTO {

    @JSONField(name = "sysUserId")
    private String sysUserId;

    @JSONField(name = "userPhone")
    private String userPhone;

    @JSONField(name = "nickName")
    private String nickName;

    /**
     * 用户类型(等级)
     */
    @JSONField(name = "userType")
    private String userType;

    /**
     * 用户总钱数(即时金额)
     */
    @JSONField(name = "instanceMoney")
    private String istanceMoney;

    /**
     * 用户推荐的人数
     */
    @JSONField(name = "peoperCount")
    private int peoperCount;

    @JSONField(name = "qrCodeURL")
    private String qrCodeURL;

    @JSONField(name = "balance")
    private String balance;

    @JSONField(name = "shareCode")
    private String shareCode;

    @JSONField(name = "userImage")
    private String userImage;

    @JSONField(name = "creatTime",format = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }


    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
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

    public String getIstanceMoney() {
        return istanceMoney;
    }

    public void setIstanceMoney(String istanceMoney) {
        this.istanceMoney = istanceMoney;
    }

    public int getPeoperCount() {
        return peoperCount;
    }

    public void setPeoperCount(int peoperCount) {
        this.peoperCount = peoperCount;
    }

    public String getQrCodeURL() {
        return qrCodeURL;
    }

    public void setQrCodeURL(String qrCodeURL) {
        this.qrCodeURL = qrCodeURL;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}

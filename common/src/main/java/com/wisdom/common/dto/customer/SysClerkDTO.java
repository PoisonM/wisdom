package com.wisdom.common.dto.customer;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;

public class SysClerkDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //
    private String sysUserId;

    //
    private String userName;

    //
    private String userLevel;

    //
    private String sysShopId;

    //
    private String sysUserQq;

    //
    private String userImage;

    //
    private String userAddress;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getSysShopId() {
        return sysShopId;
    }

    public void setSysShopId(String sysShopId) {
        this.sysShopId = sysShopId;
    }

    public String getSysUserQq() {
        return sysUserQq;
    }

    public void setSysUserQq(String sysUserQq) {
        this.sysUserQq = sysUserQq;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}
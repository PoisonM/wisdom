package com.wisdom.beauty.api.extDto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;

public class ExtUserDTO extends BaseEntity implements Serializable {

    //用户sysUserId
    private String sysUserId;

    private String sysBossId;

    private String sysShopId;

    private String sysUserName;

    private String sysClerkName;

    private String sysShopName;

    private String sysBossName;

    private String userNamePhone;

    public String getUserNamePhone() {
        return userNamePhone;
    }

    public void setUserNamePhone(String userNamePhone) {
        this.userNamePhone = userNamePhone;
    }

    public String getSysUserName() {
        return sysUserName;
    }

    public void setSysUserName(String sysUserName) {
        this.sysUserName = sysUserName;
    }

    public String getSysClerkName() {
        return sysClerkName;
    }

    public void setSysClerkName(String sysClerkName) {
        this.sysClerkName = sysClerkName;
    }

    public String getSysShopName() {
        return sysShopName;
    }

    public void setSysShopName(String sysShopName) {
        this.sysShopName = sysShopName;
    }

    public String getSysBossName() {
        return sysBossName;
    }

    public void setSysBossName(String sysBossName) {
        this.sysBossName = sysBossName;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
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
}
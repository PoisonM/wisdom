package com.wisdom.beauty.api.extDto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;

public class ShopUserLoginDTO extends BaseEntity implements Serializable {
    private String sysUserId;
    private String sysShopId;
    private String sysShopName;
    private String sysBossCode;
    private String sysShopPhoto;
    private String phone;
    private String bindingStatus;
    private String bindingDesc;

    public String getBindingDesc() {
        return bindingDesc;
    }

    public void setBindingDesc(String bindingDesc) {
        this.bindingDesc = bindingDesc;
    }

    public String getBindingStatus() {
        return bindingStatus;
    }

    public void setBindingStatus(String bindingStatus) {
        this.bindingStatus = bindingStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSysShopPhoto() {
        return sysShopPhoto;
    }

    public void setSysShopPhoto(String sysShopPhoto) {
        this.sysShopPhoto = sysShopPhoto;
    }

    public String getSysBossCode() {
        return sysBossCode;
    }

    public void setSysBossCode(String sysBossCode) {
        this.sysBossCode = sysBossCode;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getSysShopId() {
        return sysShopId;
    }

    public void setSysShopId(String sysShopId) {
        this.sysShopId = sysShopId;
    }

    public String getSysShopName() {
        return sysShopName;
    }

    public void setSysShopName(String sysShopName) {
        this.sysShopName = sysShopName;
    }
}
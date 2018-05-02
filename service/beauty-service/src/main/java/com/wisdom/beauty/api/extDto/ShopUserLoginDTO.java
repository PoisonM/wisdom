package com.wisdom.beauty.api.extDto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;

public class ShopUserLoginDTO extends BaseEntity implements Serializable {
    private String sysUserId;
    private String sysShopId;
    private String sysShopName;

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
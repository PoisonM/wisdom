package com.wisdom.beauty.api.dto;

import java.io.Serializable;

import com.wisdom.common.entity.BaseEntity;

public class ShopBossRelationDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //店id
    private String sysShopId;

    //店名称
    private String sysShopName;

    //
    private String sysBossCode;

    //
    private String sysBossName;

    //店铺地址
    private String sysShopAddress;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSysBossCode() {
        return sysBossCode;
    }

    public void setSysBossCode(String sysBossCode) {
        this.sysBossCode = sysBossCode;
    }

    public String getSysBossName() {
        return sysBossName;
    }

    public void setSysBossName(String sysBossName) {
        this.sysBossName = sysBossName;
    }

    public String getSysShopAddress() {
        return sysShopAddress;
    }

    public void setSysShopAddress(String sysShopAddress) {
        this.sysShopAddress = sysShopAddress;
    }
}
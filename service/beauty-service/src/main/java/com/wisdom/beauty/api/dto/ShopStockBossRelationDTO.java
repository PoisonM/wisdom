package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class ShopStockBossRelationDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //
    private String shopBossId;

    //
    private String shopStoreId;

    //
    private String shopStockId;

    //
    private String shopProcId;

    //
    private String shopStoreName;

    //
    private String shopBossName;

    //
    private String shopProcName;

    //
    private String createBy;

    //
    private Date createDate;

    //
    private String updateUser;

    //
    private Date updateDate;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopBossId() {
        return shopBossId;
    }

    public void setShopBossId(String shopBossId) {
        this.shopBossId = shopBossId;
    }

    public String getShopStoreId() {
        return shopStoreId;
    }

    public void setShopStoreId(String shopStoreId) {
        this.shopStoreId = shopStoreId;
    }

    public String getShopStockId() {
        return shopStockId;
    }

    public void setShopStockId(String shopStockId) {
        this.shopStockId = shopStockId;
    }

    public String getShopProcId() {
        return shopProcId;
    }

    public void setShopProcId(String shopProcId) {
        this.shopProcId = shopProcId;
    }

    public String getShopStoreName() {
        return shopStoreName;
    }

    public void setShopStoreName(String shopStoreName) {
        this.shopStoreName = shopStoreName;
    }

    public String getShopBossName() {
        return shopBossName;
    }

    public void setShopBossName(String shopBossName) {
        this.shopBossName = shopBossName;
    }

    public String getShopProcName() {
        return shopProcName;
    }

    public void setShopProcName(String shopProcName) {
        this.shopProcName = shopProcName;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
package com.wisdom.beauty.api.dto;

import java.io.Serializable;
import java.util.Date;

import com.wisdom.common.entity.BaseEntity;

public class ShopStockNumberDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //产品id
    private String shopProcId;

    //仓库id
    private String shopStoreId;

    //bossID
    private String shopBossId;

    //库存量
    private Integer stockNumber;

    //实际库存量
    private Integer actualStockNumber;

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

    public String getShopProcId() {
        return shopProcId;
    }

    public void setShopProcId(String shopProcId) {
        this.shopProcId = shopProcId;
    }

    public String getShopStoreId() {
        return shopStoreId;
    }

    public void setShopStoreId(String shopStoreId) {
        this.shopStoreId = shopStoreId;
    }

    public String getShopBossId() {
        return shopBossId;
    }

    public void setShopBossId(String shopBossId) {
        this.shopBossId = shopBossId;
    }

    public Integer getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(Integer stockNumber) {
        this.stockNumber = stockNumber;
    }

    public Integer getActualStockNumber() {
        return actualStockNumber;
    }

    public void setActualStockNumber(Integer actualStockNumber) {
        this.actualStockNumber = actualStockNumber;
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
package com.wisdom.beauty.api.dto;

import java.io.Serializable;
import java.util.Date;

import com.wisdom.common.entity.BaseEntity;

public class ShopCheckRecordDTO extends BaseEntity implements Serializable {
    //主键id
    private String id;

    //仓库id
    private String shopStoreId;

    //是否平仓：0 是 1 否
    private String state;

    //
    private String shopBossId;

    //平仓记录的id
    private String shopClosePositionId;

    //产品库存数量id
    private String shopStockNumberId;

    //产品id
    private String shopProcId;

    //操作人
    private String managerId;

    //
    private Date createDate;

    //
    private Date updateDate;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopStoreId() {
        return shopStoreId;
    }

    public void setShopStoreId(String shopStoreId) {
        this.shopStoreId = shopStoreId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getShopBossId() {
        return shopBossId;
    }

    public void setShopBossId(String shopBossId) {
        this.shopBossId = shopBossId;
    }

    public String getShopClosePositionId() {
        return shopClosePositionId;
    }

    public void setShopClosePositionId(String shopClosePositionId) {
        this.shopClosePositionId = shopClosePositionId;
    }

    public String getShopStockNumberId() {
        return shopStockNumberId;
    }

    public void setShopStockNumberId(String shopStockNumberId) {
        this.shopStockNumberId = shopStockNumberId;
    }

    public String getShopProcId() {
        return shopProcId;
    }

    public void setShopProcId(String shopProcId) {
        this.shopProcId = shopProcId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
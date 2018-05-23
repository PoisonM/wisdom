package com.wisdom.beauty.api.dto;

import java.io.Serializable;
import java.util.Date;

import com.wisdom.common.entity.BaseEntity;

public class ShopCheckRecordDTO extends BaseEntity implements Serializable {
    //主键id
    private String id;

    //流水号
    private String flowNo;

    //仓库id
    private String shopStoreId;

    //是否平仓：0 是 1 否
    private String state;

    //
    private String sysBossCode;

    //平仓记录的id
    private String shopClosePositionId;

    //实际库存数
    private Integer actualStockNumber;

    //库存数
    private Integer stockNumber;

    //产品名称
    private String shopProcName;

    //产品id
    private String shopProcId;

    //品牌名字
    private String productTypeOneName;

    //品牌id
    private String productTypeOneId;

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

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
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

    public String getSysBossCode() {
        return sysBossCode;
    }

    public void setSysBossCode(String sysBossCode) {
        this.sysBossCode = sysBossCode;
    }

    public String getShopClosePositionId() {
        return shopClosePositionId;
    }

    public void setShopClosePositionId(String shopClosePositionId) {
        this.shopClosePositionId = shopClosePositionId;
    }

    public Integer getActualStockNumber() {
        return actualStockNumber;
    }

    public void setActualStockNumber(Integer actualStockNumber) {
        this.actualStockNumber = actualStockNumber;
    }

    public Integer getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(Integer stockNumber) {
        this.stockNumber = stockNumber;
    }

    public String getShopProcName() {
        return shopProcName;
    }

    public void setShopProcName(String shopProcName) {
        this.shopProcName = shopProcName;
    }

    public String getShopProcId() {
        return shopProcId;
    }

    public void setShopProcId(String shopProcId) {
        this.shopProcId = shopProcId;
    }

    public String getProductTypeOneName() {
        return productTypeOneName;
    }

    public void setProductTypeOneName(String productTypeOneName) {
        this.productTypeOneName = productTypeOneName;
    }

    public String getProductTypeOneId() {
        return productTypeOneId;
    }

    public void setProductTypeOneId(String productTypeOneId) {
        this.productTypeOneId = productTypeOneId;
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
package com.wisdom.beauty.api.dto;

import java.io.Serializable;
import java.util.Date;

import com.wisdom.common.entity.BaseEntity;

public class ShopClosePositionRecordDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //盘点单据号
    private String flowNo;

    //原始单据号
    private String originalFlowNo;

    //操作人
    private String managerId;

    //
    private String shopProcId;

    //库存数量
    private Integer stockNumber;

    //实际库存数量
    private Integer actualStockNumber;

    //备注
    private String detail;

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

    public String getOriginalFlowNo() {
        return originalFlowNo;
    }

    public void setOriginalFlowNo(String originalFlowNo) {
        this.originalFlowNo = originalFlowNo;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getShopProcId() {
        return shopProcId;
    }

    public void setShopProcId(String shopProcId) {
        this.shopProcId = shopProcId;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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
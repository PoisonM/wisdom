package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class ShopCustomerProductRelationDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //产品名称
    private String shopProductName;

    //
    private String shopProductId;

    //
    private String sysCustomerId;

    //
    private String sysShopId;

    //产品待领取的数量
    private Integer waitReceiveNumber;

    //
    private String sysShopName;

    //初始数量
    private Integer initTimes;

    //剩余数量
    private Integer surplusTimes;

    //初始金额
    private Long initAmount;

    //剩余金额
    private Long surplusAmount;

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

    public String getShopProductName() {
        return shopProductName;
    }

    public void setShopProductName(String shopProductName) {
        this.shopProductName = shopProductName;
    }

    public String getShopProductId() {
        return shopProductId;
    }

    public void setShopProductId(String shopProductId) {
        this.shopProductId = shopProductId;
    }

    public String getSysCustomerId() {
        return sysCustomerId;
    }

    public void setSysCustomerId(String sysCustomerId) {
        this.sysCustomerId = sysCustomerId;
    }

    public String getSysShopId() {
        return sysShopId;
    }

    public void setSysShopId(String sysShopId) {
        this.sysShopId = sysShopId;
    }

    public Integer getWaitReceiveNumber() {
        return waitReceiveNumber;
    }

    public void setWaitReceiveNumber(Integer waitReceiveNumber) {
        this.waitReceiveNumber = waitReceiveNumber;
    }

    public String getSysShopName() {
        return sysShopName;
    }

    public void setSysShopName(String sysShopName) {
        this.sysShopName = sysShopName;
    }

    public Integer getInitTimes() {
        return initTimes;
    }

    public void setInitTimes(Integer initTimes) {
        this.initTimes = initTimes;
    }

    public Integer getSurplusTimes() {
        return surplusTimes;
    }

    public void setSurplusTimes(Integer surplusTimes) {
        this.surplusTimes = surplusTimes;
    }

    public Long getInitAmount() {
        return initAmount;
    }

    public void setInitAmount(Long initAmount) {
        this.initAmount = initAmount;
    }

    public Long getSurplusAmount() {
        return surplusAmount;
    }

    public void setSurplusAmount(Long surplusAmount) {
        this.surplusAmount = surplusAmount;
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
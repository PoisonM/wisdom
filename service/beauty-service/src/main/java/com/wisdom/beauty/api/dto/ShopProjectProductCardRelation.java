package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class ShopProjectProductCardRelation extends BaseEntity implements Serializable {
    //主键
    private String id;

    //充值卡主键
    private String shopRechargeCardId;

    //
    private String shopProductId;

    //项目id
    private String sysShopProjectId;

    //使用方式 0：疗程 1：单次
    private String useStyle;

    //折扣
    private Float discount;

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

    public String getShopRechargeCardId() {
        return shopRechargeCardId;
    }

    public void setShopRechargeCardId(String shopRechargeCardId) {
        this.shopRechargeCardId = shopRechargeCardId;
    }

    public String getShopProductId() {
        return shopProductId;
    }

    public void setShopProductId(String shopProductId) {
        this.shopProductId = shopProductId;
    }

    public String getSysShopProjectId() {
        return sysShopProjectId;
    }

    public void setSysShopProjectId(String sysShopProjectId) {
        this.sysShopProjectId = sysShopProjectId;
    }

    public String getUseStyle() {
        return useStyle;
    }

    public void setUseStyle(String useStyle) {
        this.useStyle = useStyle;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
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
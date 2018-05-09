package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class ShopProjectProductCardRelationDTO extends BaseEntity implements Serializable {
    //主键
    private String id;

    //充值卡主键
    private String shopRechargeCardId;

    //虚拟商品主键
    private String shopGoodsTypeId;

    //0、商品类型为次卡；1、商品类型为疗程卡 ；2、商品类型为充值卡；3、商品类型为套卡 4、商品类型为产品
    private String goodsType;

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

    public String getShopGoodsTypeId() {
        return shopGoodsTypeId;
    }

    public void setShopGoodsTypeId(String shopGoodsTypeId) {
        this.shopGoodsTypeId = shopGoodsTypeId;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
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
package com.wisdom.beauty.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.wisdom.common.entity.BaseEntity;

public class ShopClerkWorkRecordDTO extends BaseEntity implements Serializable {
    //主键id
    private String id;

    //
    private String flowId;

    //消费记录表主键
    private String consumeRecordId;

    //美容店id
    private String sysShopId;

    //店员id
    private String sysClerkId;

    //店员名字
    private String sysClerkName;

    //消费流水号
    private String flowNo;

    //总价格
    private BigDecimal price;

    //0、商品类型为次卡；1、商品类型为疗程卡 ；2、商品类型为充值卡；3、商品类型为套卡 4、商品类型为产品 
    private String goodsType;

    //0：充值  1：消费 2、还欠款 3、退款
    private String consumeType;

    //支付方式  0:银行卡支付 1：微信支付 2:支付宝支付 3: 现金支付
    private String payType;

    //
    private String createBy;

    //
    private String updateUser;

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

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getConsumeRecordId() {
        return consumeRecordId;
    }

    public void setConsumeRecordId(String consumeRecordId) {
        this.consumeRecordId = consumeRecordId;
    }

    public String getSysShopId() {
        return sysShopId;
    }

    public void setSysShopId(String sysShopId) {
        this.sysShopId = sysShopId;
    }

    public String getSysClerkId() {
        return sysClerkId;
    }

    public void setSysClerkId(String sysClerkId) {
        this.sysClerkId = sysClerkId;
    }

    public String getSysClerkName() {
        return sysClerkName;
    }

    public void setSysClerkName(String sysClerkName) {
        this.sysClerkName = sysClerkName;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(String consumeType) {
        this.consumeType = consumeType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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
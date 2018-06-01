package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShopCashFlowDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //
    private String flowNo;

    //
    private String sysShopId;

    //归属老板编码
    private String sysBossCode;

    //支付方式  0:微信 1：支付宝 2:现金
    private String payType;

    //支付宝、微信、银行支付金额
    private BigDecimal payTypeAmount;

    //余额支付
    private BigDecimal balanceAmount;

    //充值卡支付
    private BigDecimal rechargeCardAmount;

    //现金支付
    private BigDecimal cashAmount;

    //欠款金额
    private BigDecimal oweAmount;

    //
    private Date createDate;

    //
    private String createBy;

    //
    private Date updateDate;

    //
    private String updateBy;

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

    public String getSysShopId() {
        return sysShopId;
    }

    public void setSysShopId(String sysShopId) {
        this.sysShopId = sysShopId;
    }

    public String getSysBossCode() {
        return sysBossCode;
    }

    public void setSysBossCode(String sysBossCode) {
        this.sysBossCode = sysBossCode;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public BigDecimal getPayTypeAmount() {
        return payTypeAmount;
    }

    public void setPayTypeAmount(BigDecimal payTypeAmount) {
        this.payTypeAmount = payTypeAmount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public BigDecimal getRechargeCardAmount() {
        return rechargeCardAmount;
    }

    public void setRechargeCardAmount(BigDecimal rechargeCardAmount) {
        this.rechargeCardAmount = rechargeCardAmount;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getOweAmount() {
        return oweAmount;
    }

    public void setOweAmount(BigDecimal oweAmount) {
        this.oweAmount = oweAmount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
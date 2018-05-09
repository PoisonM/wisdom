package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;

public class ExtShopUserRechargeCardDTO extends ShopUserRechargeCardDTO {

    //充值金额
    private String rechargeAmount;

    //现金支付
    private String cashPay;

    //剩余支付
    private String surplusPayPrice;

    //支付方式
    private String PayType;

    //店员主键
    private String sysClerkId;

    //店员名称
    private String sysCLerkName;

    //备注
    private String detail;

    /**
     * BANK_PAY("0", "银行卡支付"),
     * WECHAT_PAY("1", "微信支付"),
     * ALI_PAY("2", "支付宝支付");
     */
    //支付方式
    private String payType;

    @Override
    public String getSysClerkId() {
        return sysClerkId;
    }

    @Override
    public void setSysClerkId(String sysClerkId) {
        this.sysClerkId = sysClerkId;
    }

    public String getSysCLerkName() {
        return sysCLerkName;
    }

    public void setSysCLerkName(String sysCLerkName) {
        this.sysCLerkName = sysCLerkName;
    }

    @Override
    public String getDetail() {
        return detail;
    }

    @Override
    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCashPay() {
        return cashPay;
    }

    public void setCashPay(String cashPay) {
        this.cashPay = cashPay;
    }

    public String getSurplusPayPrice() {
        return surplusPayPrice;
    }

    public void setSurplusPayPrice(String surplusPayPrice) {
        this.surplusPayPrice = surplusPayPrice;
    }

    public String getPayType() {
        return PayType;
    }

    public void setPayType(String payType) {
        PayType = payType;
    }

    public String getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(String rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }
}
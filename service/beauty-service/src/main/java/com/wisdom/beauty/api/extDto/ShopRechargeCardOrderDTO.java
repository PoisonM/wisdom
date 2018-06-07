package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopRechargeCardDTO;

import java.util.List;

public class ShopRechargeCardOrderDTO extends ShopRechargeCardDTO {

    //充值金额
    private String rechargeAmount;

    //现金支付
    private String cashPay;

    //剩余支付
    private String surplusPayPrice;

    //支付方式
    private String payType;

    //特殊充值卡传0 普通充值卡充值传1
    private String rechargeCardType;

    //店员名称
    private String sysCLerkName;

    //用户id
    private String sysUserId;

    //交易id
    private String transactionId;

    private String signUrl;

    private String detail;

    //订单状态 1、未支付 2、待支付  2、已支付  3、已失效
    private String orderStatus;

    /**
     * BANK_PAY("0", "银行卡支付"),
     * WECHAT_PAY("1", "微信支付"),
     * ALI_PAY("2", "支付宝支付");
     */


    private String userName;
    //图片
    private List<String> imageList;

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Override
    public String getRechargeCardType() {
        return rechargeCardType;
    }

    @Override
    public void setRechargeCardType(String rechargeCardType) {
        this.rechargeCardType = rechargeCardType;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSysCLerkName() {
        return sysCLerkName;
    }

    public void setSysCLerkName(String sysCLerkName) {
        this.sysCLerkName = sysCLerkName;
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

    public String getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(String rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
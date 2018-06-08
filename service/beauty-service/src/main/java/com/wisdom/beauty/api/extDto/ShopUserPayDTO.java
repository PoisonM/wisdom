package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;

import java.util.Date;
import java.util.List;

public class ShopUserPayDTO {

    //订单号
    private String orderId;

    //档案表主键
    private String shopUserArchivesId;

    //创建时间
    private Date createDate;

    //签字地址
    private String signUrl;
    /**
     * BANK_PAY("0", "银行卡支付"),
     * WECHAT_PAY("1", "微信支付"),
     * ALI_PAY("2", "支付宝支付");
     */
    //支付方式
    private String payType;

    //用户现金支付金额
    private String cashPayPrice;

    //余额支付
    private String balancePay;

    //剩余支付金额
    private String surplusPayPrice;
    //备注
    private String detail;
    //
    private String oweAmount;

    public String getOweAmount() {
        return oweAmount;
    }

    public void setOweAmount(String oweAmount) {
        this.oweAmount = oweAmount;
    }

    public String getBalancePay() {
        return balancePay;
    }

    public void setBalancePay(String balancePay) {
        this.balancePay = balancePay;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl;
    }

    //用户与充值卡的关系
    private List<ShopUserRechargeCardDTO> shopUserRechargeCardDTOS;

    public String getCashPayPrice() {
        return cashPayPrice;
    }

    public void setCashPayPrice(String cashPayPrice) {
        this.cashPayPrice = cashPayPrice;
    }

    public String getSurplusPayPrice() {
        return surplusPayPrice;
    }

    public void setSurplusPayPrice(String surplusPayPrice) {
        this.surplusPayPrice = surplusPayPrice;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getShopUserArchivesId() {
        return shopUserArchivesId;
    }

    public void setShopUserArchivesId(String shopUserArchivesId) {
        this.shopUserArchivesId = shopUserArchivesId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<ShopUserRechargeCardDTO> getShopUserRechargeCardDTOS() {
        return shopUserRechargeCardDTOS;
    }

    public void setShopUserRechargeCardDTOS(List<ShopUserRechargeCardDTO> shopUserRechargeCardDTOS) {
        this.shopUserRechargeCardDTOS = shopUserRechargeCardDTOS;
    }
}
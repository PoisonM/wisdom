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
    /**
     * BANK_PAY("0", "银行卡支付"),
     * WECHAT_PAY("1", "微信支付"),
     * ALI_PAY("2", "支付宝支付");
     */
    //支付方式
    private String payType;

    //用户实际支付的金额
    private String actualPayPrice;

    //用户与充值卡的关系
    private List<ShopUserRechargeCardDTO> shopUserRechargeCardDTOS;

    public String getActualPayPrice() {
        return actualPayPrice;
    }

    public void setActualPayPrice(String actualPayPrice) {
        this.actualPayPrice = actualPayPrice;
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
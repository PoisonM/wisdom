package com.wisdom.common.dto.beauty;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by cjk on 2017/5/24.
 */
public class ShopTotalChargeDTO {

    @JSONField(name = "shopId")
    private String shopId;

    @JSONField(name = "value")
    private float value;

    //type为"income"表示收入,"outcome"表示支出
    @JSONField(name = "chargeType")
    private String chargeType;

    //chargeWay"cash"表示现金，"card"表示银行卡,
    // "weixin"表示微信,"alipay"表示支付宝
    //"refund"表示退款,"poundage"表示手续费
    @JSONField(name = "chargeWay")
    private String chargeWay;

    @JSONField(name = "date")
    private Date date;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getChargeWay() {
        return chargeWay;
    }

    public void setChargeWay(String chargeWay) {
        this.chargeWay = chargeWay;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

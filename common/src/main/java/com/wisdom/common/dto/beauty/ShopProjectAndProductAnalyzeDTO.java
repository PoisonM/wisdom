package com.wisdom.common.dto.beauty;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class ShopProjectAndProductAnalyzeDTO {

    @JSONField(name = "shopId")
    private String shopId;

    @JSONField(name = "shopName")
    private String shopName;

    @JSONField(name = "type")
    private String type;

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "date")
    private Date date;

    @JSONField(name = "income")
    private float income;

    @JSONField(name = "totalChargeNum")
    private int totalChargeNum;

    @JSONField(name = "totalSendNum")
    private int totalSendNum;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    public int getTotalChargeNum() {
        return totalChargeNum;
    }

    public void setTotalChargeNum(int totalChargeNum) {
        this.totalChargeNum = totalChargeNum;
    }

    public int getTotalSendNum() {
        return totalSendNum;
    }

    public void setTotalSendNum(int totalSendNum) {
        this.totalSendNum = totalSendNum;
    }
}

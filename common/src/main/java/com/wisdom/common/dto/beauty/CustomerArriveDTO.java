package com.wisdom.common.dto.beauty;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by cjk on 2017/5/24.
 */
public class CustomerArriveDTO {

    @JSONField(name = "shopId")
    private String shopId;

    @JSONField(name = "customerId")
    private String customerId;

    @JSONField(name = "customerName")
    private String customerName;

    @JSONField(name = "lastArriveDate")
    private Date lastArriveDate;

    //新客还是老客
    @JSONField(name = "type")
    private Date type;

    @JSONField(name = "arriveTimes")
    private int arriveTimes;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getLastArriveDate() {
        return lastArriveDate;
    }

    public void setLastArriveDate(Date lastArriveDate) {
        this.lastArriveDate = lastArriveDate;
    }

    public Date getType() {
        return type;
    }

    public void setType(Date type) {
        this.type = type;
    }

    public int getArriveTimes() {
        return arriveTimes;
    }

    public void setArriveTimes(int arriveTimes) {
        this.arriveTimes = arriveTimes;
    }
}

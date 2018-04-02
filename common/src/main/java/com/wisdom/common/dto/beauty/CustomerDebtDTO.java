package com.wisdom.common.dto.beauty;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class CustomerDebtDTO {

    @JSONField(name = "shopId")
    private String shopId;

    @JSONField(name = "customerId")
    private String customerId;

    @JSONField(name = "customerName")
    private String customerName;

    @JSONField(name = "endDate")
    private Date endDate;

    @JSONField(name = "totalNoExpense")
    private float totalNoExpense;

    @JSONField(name = "totalNoExpenseCard")
    private float totalNoExpenseCard;

    @JSONField(name = "totalTimes")
    private int totalTimes;

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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getTotalNoExpense() {
        return totalNoExpense;
    }

    public void setTotalNoExpense(float totalNoExpense) {
        this.totalNoExpense = totalNoExpense;
    }

    public float getTotalNoExpenseCard() {
        return totalNoExpenseCard;
    }

    public void setTotalNoExpenseCard(float totalNoExpenseCard) {
        this.totalNoExpenseCard = totalNoExpenseCard;
    }

    public int getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(int totalTimes) {
        this.totalTimes = totalTimes;
    }
}

package com.wisdom.common.dto.beauty;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class CustomerContributionDTO {

    @JSONField(name = "shopId")
    private String shopId;

    @JSONField(name = "customerId")
    private String customerId;

    @JSONField(name = "customerName")
    private String customerName;

    @JSONField(name = "contributionValue")
    private float contributionValue;

    @JSONField(name = "cardExpense")
    private int cardExpense;

    @JSONField(name = "expenseCard")
    private int expenseCard;

    @JSONField(name = "arriveTimes")
    private int arriveTimes;

    @JSONField(name = "date")
    private Date date;

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

    public float getContributionValue() {
        return contributionValue;
    }

    public void setContributionValue(float contributionValue) {
        this.contributionValue = contributionValue;
    }

    public int getCardExpense() {
        return cardExpense;
    }

    public void setCardExpense(int cardExpense) {
        this.cardExpense = cardExpense;
    }

    public int getExpenseCard() {
        return expenseCard;
    }

    public void setExpenseCard(int expenseCard) {
        this.expenseCard = expenseCard;
    }

    public int getArriveTimes() {
        return arriveTimes;
    }

    public void setArriveTimes(int arriveTimes) {
        this.arriveTimes = arriveTimes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

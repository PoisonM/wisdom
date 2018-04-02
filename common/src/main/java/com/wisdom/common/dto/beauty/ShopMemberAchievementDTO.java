package com.wisdom.common.dto.beauty;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

/**
 * Created by cjk on 2017/5/24.
 */
public class ShopMemberAchievementDTO {

    @JSONField(name = "shopId")
    private String shopId;

    @JSONField(name = "shopName")
    private String shopName;

    @JSONField(name = "clerkId")
    private String clerkId;

    @JSONField(name = "clerkName")
    private String clerkName;

    @JSONField(name = "date")
    private Date date;

    @JSONField(name = "income")
    private float income;

    @JSONField(name = "expenseCard")
    private int expenseCard;

    @JSONField(name = "cardExpense")
    private int cardExpense;

    @JSONField(name = "customerArriveTimes")
    private int customerArriveTimes;

    @JSONField(name = "customerServiceTimes")
    private int customerServiceTimes;

    @JSONField(name = "manualFee")
    private float manualFee;

    @JSONField(name = "points")
    private int points;

    @JSONField(name = "productExpense")
    private float productExpense;


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

    public String getClerkId() {
        return clerkId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
    }

    public String getClerkName() {
        return clerkName;
    }

    public void setClerkName(String clerkName) {
        this.clerkName = clerkName;
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

    public int getExpenseCard() {
        return expenseCard;
    }

    public void setExpenseCard(int expenseCard) {
        this.expenseCard = expenseCard;
    }

    public int getCardExpense() {
        return cardExpense;
    }

    public void setCardExpense(int cardExpense) {
        this.cardExpense = cardExpense;
    }

    public int getCustomerArriveTimes() {
        return customerArriveTimes;
    }

    public void setCustomerArriveTimes(int customerArriveTimes) {
        this.customerArriveTimes = customerArriveTimes;
    }

    public int getCustomerServiceTimes() {
        return customerServiceTimes;
    }

    public void setCustomerServiceTimes(int customerServiceTimes) {
        this.customerServiceTimes = customerServiceTimes;
    }

    public float getManualFee() {
        return manualFee;
    }

    public void setManualFee(float manualFee) {
        this.manualFee = manualFee;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public float getProductExpense() {
        return productExpense;
    }

    public void setProductExpense(float productExpense) {
        this.productExpense = productExpense;
    }
}

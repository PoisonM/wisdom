package com.wisdom.common.dto.beauty;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

/**
 * Created by cjk on 2017/5/24.
 */
public class ShopAchievementDTO {

    @JSONField(name = "shopId")
    private String shopId;

    @JSONField(name = "shopName")
    private String shopName;

    @JSONField(name = "income")
    private float income;

    @JSONField(name = "cardExpense")
    private int cardExpense;

    @JSONField(name = "totalIncomes")
    private List<ShopTotalChargeDTO> totalIncomes;

    @JSONField(name = "totalOutcomes")
    private List<ShopTotalChargeDTO> totalOutcomes;

    @JSONField(name = "customerExpenseRecords")
    private List<CustomerExpenseRecordDTO> customerExpenseRecords;


    @JSONField(name = "date")
    private Date date;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    public int getCardExpense() {
        return cardExpense;
    }

    public void setCardExpense(int cardExpense) {
        this.cardExpense = cardExpense;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<ShopTotalChargeDTO> getTotalIncomes() {
        return totalIncomes;
    }

    public void setTotalIncomes(List<ShopTotalChargeDTO> totalIncomes) {
        this.totalIncomes = totalIncomes;
    }

    public List<ShopTotalChargeDTO> getTotalOutcomes() {
        return totalOutcomes;
    }

    public void setTotalOutcomes(List<ShopTotalChargeDTO> totalOutcomes) {
        this.totalOutcomes = totalOutcomes;
    }

    public List<CustomerExpenseRecordDTO> getCustomerExpenseRecords() {
        return customerExpenseRecords;
    }

    public void setCustomerExpenseRecords(List<CustomerExpenseRecordDTO> customerExpenseRecords) {
        this.customerExpenseRecords = customerExpenseRecords;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

package com.wisdom.common.dto.specialShop;

import com.alibaba.fastjson.annotation.JSONField;
import com.wisdom.common.dto.system.UserBankCardInfoDTO;

import java.util.Date;
import java.util.List;

public class SpecialShopBusinessOrderDTO {

    @JSONField(name = "shopId")
    private String shopId;

    @JSONField(name = "userId")
    private String userId;

    @JSONField(name = "userIdentifyNumber")
    private String userIdentifyNumber;

    @JSONField(name = "userName")
    private String userName;

    @JSONField(name = "userPhone")
    private String userPhone;

    @JSONField(name = "businessOrderId")
    private String businessOrderId;

    @JSONField(name = "transactionId")
    private String transactionId;

    @JSONField(name = "productName")
    private String productName;

    @JSONField(name = "productNum")
    private int productNum;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIdentifyNumber() {
        return userIdentifyNumber;
    }

    public void setUserIdentifyNumber(String userIdentifyNumber) {
        this.userIdentifyNumber = userIdentifyNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getBusinessOrderId() {
        return businessOrderId;
    }

    public void setBusinessOrderId(String businessOrderId) {
        this.businessOrderId = businessOrderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }
}
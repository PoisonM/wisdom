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

    @JSONField(name = "orderId")
    private String orderId;

    @JSONField(name = "createDate")
    private Date createDate;

    //当前订单金额
    private String account;


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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
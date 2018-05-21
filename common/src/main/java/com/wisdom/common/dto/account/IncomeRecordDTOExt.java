package com.wisdom.common.dto.account;

import com.alibaba.fastjson.annotation.JSONField;
import com.wisdom.common.dto.user.UserInfoDTO;

import java.util.Date;

/**
 * Created by zbm84 on 2017/8/3.
 */
public class IncomeRecordDTOExt {


    private UserInfoDTO userInfoDTO;

    private String orderId;

    private Date payDate;

    private String transactionId;//交易id

    private Float amount;



    public UserInfoDTO getUserInfoDTO() {
        return userInfoDTO;
    }

    public void setUserInfoDTO(UserInfoDTO userInfoDTO) {
        this.userInfoDTO = userInfoDTO;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}

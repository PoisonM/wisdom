package com.wisdom.common.dto.product;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2017/12/26.
 */
public class InvoiceDTO {

    @JSONField(name = "id")
    private String userId;

    @JSONField(name = "companyName")
    private String companyName;

    @JSONField(name = "taxpayerNumber")
    private String taxpayerNumber;

    @JSONField(name = "taxType")
    private int taxType;

    @JSONField(name = "orderId")
    private String orderId;

    @JSONField(name = "transactionId")
    private String transactionId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTaxpayerNumber() {
        return taxpayerNumber;
    }

    public void setTaxpayerNumber(String taxpayerNumber) {
        this.taxpayerNumber = taxpayerNumber;
    }

    public int getTaxType() {
        return taxType;
    }

    public void setTaxType(int taxType) {
        this.taxType = taxType;
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

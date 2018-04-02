package com.wisdom.common.dto.system;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 导出账单DTO
 */
public class ExportPayRecordExcelDTO {
    /**
     * 用户id
     */
    @JSONField(name = "sysUserId")
    private String sysUserId;
    /**
     * 用户名
     */
    @JSONField(name = "nickName")
    private String nickName;
    /**
     * 手机号
     */
    @JSONField(name = "mobile")
    private String mobile;
    /**
     * 付款金额
     */
    @JSONField(name = "amount")
    private float amount;
    /**
     * 付款时间
     */
    @JSONField(name = "payDate")
    private String payDate;
    /**
     * 完成时间
     */
    @JSONField(name = "updateDate")
    private String updateDate;
    /**
     * 账单编号
     */
    @JSONField(name = "transactionId")
    private String transactionId;
    /**
     * 订单编号
     */
    @JSONField(name = "orderId")
    private String orderId;

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
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

package com.wisdom.common.dto.transaction;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2017/12/15.
 */
public class InstanceReturnMoneySignalDTO {

    @JSONField(name ="sysUserId")
    private String sysUserId;

    @JSONField(name ="transactionId")
    private String transactionId;

    @JSONField(name ="outTradeNo")
    private String outTradeNo;

    @JSONField(name ="status")
    private String status;

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

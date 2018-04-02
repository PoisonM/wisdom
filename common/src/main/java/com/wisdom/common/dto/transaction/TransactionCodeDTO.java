package com.wisdom.common.dto.transaction;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2017/12/15.
 */
public class TransactionCodeDTO {

    @JSONField(name ="transactionCode")
    private String transactionCode;

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }
}

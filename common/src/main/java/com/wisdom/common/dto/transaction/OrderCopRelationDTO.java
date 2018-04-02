package com.wisdom.common.dto.transaction;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by zbm84 on 2017/8/3.
 */
public class OrderCopRelationDTO {

    @JSONField(name = "id")
    private String id;

    //订单编号ID
    @JSONField(name = "orderId")
    private String orderId;

    /**
     * 运单号
     */
    @JSONField(name = "waybillNumber")
    private String waybillNumber;

    /**
     * 交易流水号
     */
    @JSONField(name = "transactionId")
    private String transactionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getWaybillNumber() {
        return waybillNumber;
    }

    public void setWaybillNumber(String waybillNumber) {

        this.waybillNumber = waybillNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}

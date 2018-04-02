package com.wisdom.common.dto.transaction;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by zbm84 on 2017/8/3.
 */
public class NeedPayOrderDTO {

    @JSONField(name = "orderChecked")
    private String orderChecked;

    @JSONField(name = "orderId")
    private String orderId;

    @JSONField(name = "productFirstUrl")
    private String productFirstUrl;

    @JSONField(name = "productId")
    private String productId;

    @JSONField(name = "productName")
    private String productName;

    @JSONField(name = "productNum")
    private String productNum;

    @JSONField(name = "productPrice")
    private String productPrice;

    @JSONField(name = "productSpec")
    private String productSpec;

    public String getOrderChecked() {
        return orderChecked;
    }

    public void setOrderChecked(String orderChecked) {
        this.orderChecked = orderChecked;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductFirstUrl() {
        return productFirstUrl;
    }

    public void setProductFirstUrl(String productFirstUrl) {
        this.productFirstUrl = productFirstUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }
}

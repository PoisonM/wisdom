package com.wisdom.business.constant;

/**
 * Created by sunxiao on 2017/8/15.
 */
public enum OrderStatus {

    WAIT_PAY("0"),        //待付款
    WAIT_SEND_PRODUCT("1"), //待发货
    WAIT_RECEIVE_PRODUCT("4"),   //待收货
    ORDER_FINISH("2");   //已收货

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

package com.wisdom.common.constant;


/**
 * Created by sunxiao on 2017/6/27.
 */
public enum OperationType {
    ADD("add"),
    DELETE("del"),
    UPDATE("update");

    private final String value;

    OperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }


}

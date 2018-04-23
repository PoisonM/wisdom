package com.wisdom.beauty.api.enums;

public enum OrderStatusEnum {

    NOT_PAY("0", "未支付"),
    ALREADY_PAY("1", "已支付"),
    INVALID("2", "已失效"),;

    OrderStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

package com.wisdom.beauty.api.enums;

public enum OrderStatusEnum {

    NOT_PAY("0", "未支付"),
    WAIT_PAY("1", "待支付"),
    WAIT_SIGN("2", "待签字确认"),
    CONFIRM_PAY("3", "已签字确认"),
    INVALID("4", "已失效");

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

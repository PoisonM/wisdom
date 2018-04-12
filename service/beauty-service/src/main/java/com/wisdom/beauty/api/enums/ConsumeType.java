package com.wisdom.beauty.api.enums;

public enum ConsumeType {
    //0：充值  1：消费 2、还欠款 3、退款 4、划卡
    RECHARGE("0", "充值"),
    CONSUME("1", "消费"),
    ARREARS("2", "还欠款"),
    REFUND("3", "退款"),
    PUNCH_CARD("4", "划卡"),;

    ConsumeType(String code, String desc) {
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

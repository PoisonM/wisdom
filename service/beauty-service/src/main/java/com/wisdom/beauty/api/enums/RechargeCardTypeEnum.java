package com.wisdom.beauty.api.enums;

public enum RechargeCardTypeEnum {

    SPECIAL("0", "特殊充值卡"),
    COMMON("1", "普通充值卡");

    RechargeCardTypeEnum(String code, String desc) {
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

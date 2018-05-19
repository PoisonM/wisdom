package com.wisdom.beauty.api.enums;

public enum ProductTypeEnum {

    GUEST("0", "客装产品"),
    SALON_ONLY("1", "院装产品"),
    CONSUMABLE("2", "易耗品");

    ProductTypeEnum(String code, String desc) {
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

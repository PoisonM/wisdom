package com.wisdom.beauty.api.enums;

public enum GoodsType {
    //0、商品类型为次卡；1、商品类型为疗程卡 ；2、商品类型为充值卡；3、商品类型为套卡 4、商品类型为产品
    TIME_CARD("0", "次卡"),
    TREATMENT_CARD("1", "疗程卡"),
    RECHARGE_CARD("2", "充值卡"),
    COLLECTION_CARD("3", "套卡"),
    PRODUCT("4", "产品");

    GoodsType(String code, String desc) {
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

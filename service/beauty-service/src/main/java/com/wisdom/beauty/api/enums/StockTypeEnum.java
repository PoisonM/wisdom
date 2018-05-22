package com.wisdom.beauty.api.enums;

/**
 * Created by zhanghuan on 2018/5/10.
 */
public enum StockTypeEnum {

    PURCHASE_IN_STORAGE("0", "采购入库"),
    EMPLOYEES_OUT_STORAGE("1", "内部员工出库"),
    CUSTOMER_OUT_STORAGE("2", "顾客出库"),
    giving("3", "赠送"),
    scrap("4", "报废"),
    hospital_use("5", "院用"),
    RETURN_TO_SUPPLIER("6", "退回供货商"),
    ISSUED_TO_SHOP("7", "下发到店");

    StockTypeEnum(String code, String desc) {
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

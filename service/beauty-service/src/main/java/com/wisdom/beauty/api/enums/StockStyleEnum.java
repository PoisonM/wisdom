package com.wisdom.beauty.api.enums;

/**
 * Created by zhanghuan on 2018/5/8.
 */
public enum StockStyleEnum {

    MANUAL_IN_STORAGE("0", "手动入库"),
    SCAN_IN_STORAGE("1", "扫码入库"),
    MANUAL_OUT_STORAGE("2", "手动出库"),
    SCAN_CARD_OUT_STORAGE("4", "扫码出库"),
    IN_STORAGE("5", "入库"),
    OUT_STORAGE("6", "出库");

    StockStyleEnum(String code, String desc) {
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

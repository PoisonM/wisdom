package com.wisdom.beauty.api.enums;

public enum CardTypeEnum {

    TIME_CARD("0", "次卡"),
    MONTH_CARD("1", "月卡"),
    SEASON_CARD("2", "季卡"),
    HALF_YEAR_CARD("3", "半年卡"),
    YEAR_CARD("4", "年卡"),
    TREATMENT_CARD("0", "疗程卡"),
    ONE_TIME_CARD("1", "单次"),
    ALL("2", "所有卡");

    CardTypeEnum(String code, String desc) {
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

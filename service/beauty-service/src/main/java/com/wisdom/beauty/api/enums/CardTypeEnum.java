package com.wisdom.beauty.api.enums;

public enum CardTypeEnum {

    TIME_CARD("0", "次卡",1),
    MONTH_CARD("1", "月卡",30),
    SEASON_CARD("2", "季卡",90),
    HALF_YEAR_CARD("3", "半年卡",180),
    PERMANENT_CARD("5", "长期有效卡",100000),
    YEAR_CARD("4", "年卡",365);

    CardTypeEnum(String code, String desc,int date) {
        this.code = code;
        this.desc = desc;
        this.date=date;
    }

    public static CardTypeEnum judgeValue(String code) {
        CardTypeEnum[] resultCodes = CardTypeEnum.values();
        for (CardTypeEnum resultCode : resultCodes) {
            if (resultCode.getCode().equals(code)) {
                return resultCode;
            }
        }
        return null;
    }


    private String code;
    private String desc;
    private int date;

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

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

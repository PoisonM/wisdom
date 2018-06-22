package com.wisdom.beauty.api.enums;

/**
 * Created by zhanghuan on 2018/6/22.
 */
public enum ExtCardTypeEnum {

    TREATMENT_CARD("1", "疗程卡",0),
    ONE_TIME_CARD("0", "单次",0),
    ALL("2", "所有卡",0);

    ExtCardTypeEnum(String code, String desc,int date) {
        this.code = code;
        this.desc = desc;
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

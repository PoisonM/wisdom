package com.wisdom.common.constant;

public enum RealNameResultEnum {

    MATCHING("0", "匹配"),
    NO_RESULT("2", "未能获取结果"),
    UN_MATCHING("5", "不匹配"),
    WITHOUT_ID_CARD("14", "无此身份证号码"),
    FAILURE_TRADING("96", "交易失败，请稍后重试");

    RealNameResultEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static RealNameResultEnum judgeValue(String code) {
        RealNameResultEnum[] resultCodes = RealNameResultEnum.values();
        for (RealNameResultEnum resultCode : resultCodes) {
            if (resultCode.getCode().equals(code)) {
                return resultCode;
            }
        }
        return null;
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

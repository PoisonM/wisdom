package com.wisdom.beauty.api.enums;

public enum PayTypeEnum {

    WECHAT_PAY("0", "微信支付"),
    ALI_PAY("1", "支付宝支付"),
    BANK_PAY("2", "银行卡支付"),
    CASH_PAY("3", "现金支付"),
    ALL("4", "包括所有支付方式");


    PayTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PayTypeEnum judgeValue(String code) {
        PayTypeEnum[] resultCodes = PayTypeEnum.values();
        for (PayTypeEnum resultCode : resultCodes) {
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

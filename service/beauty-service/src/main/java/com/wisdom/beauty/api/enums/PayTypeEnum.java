package com.wisdom.beauty.api.enums;

public enum PayTypeEnum {

    BANK_PAY("0", "银行卡支付"),
    WECHAT_PAY("1", "微信支付"),
    ALI_PAY("2", "支付宝支付"),
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

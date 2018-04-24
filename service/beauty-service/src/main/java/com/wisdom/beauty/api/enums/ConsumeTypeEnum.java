package com.wisdom.beauty.api.enums;

/**
 * ClassName: ConsumeTypeEnum
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/8 19:09
 * @since JDK 1.8
 */
public enum ConsumeTypeEnum {
    CONSUME("4", "consumeRecord"),
    CASHIER("5", "cashierRecord");

    ConsumeTypeEnum(String code, String desc) {
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

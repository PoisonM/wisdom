package com.wisdom.beauty.api.enums;

/**
 * Created by zhanghuan on 2018/5/10.
 */
public enum MemberEnum {

    BINDING("0", "绑定"),
    NO_BINDING("1", "未绑定");

    MemberEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public static MemberEnum judgeValue(String code) {
        MemberEnum[] resultCodes = MemberEnum.values();
        for (MemberEnum resultCode : resultCodes) {
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

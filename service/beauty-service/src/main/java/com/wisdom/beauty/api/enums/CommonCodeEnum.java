package com.wisdom.beauty.api.enums;

public enum CommonCodeEnum {

    SUCCESS("0", "正常"),
    UNSUCCESS("1", "非正常"),
    Y("Y", "正常"),
    N("N", "非正常"),
    NOTBIND("notBind", "正常"),
    ALREADYBIND("alreadyBind", "非正常"),
    ADD("0", "添加"),
    DELETE("1", "删除"),
    TRUE("true", "true");

    CommonCodeEnum(String code, String desc) {
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

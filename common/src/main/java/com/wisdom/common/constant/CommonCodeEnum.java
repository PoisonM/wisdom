package com.wisdom.common.constant;

public enum CommonCodeEnum {

    SUCCESS("0", "正常"),
    UNSUCCESS("1", "非正常"),
    Y("Y", "正常"),
    N("N", "非正常"),
    CURRENT_DATE("0", "当天"),
    UN_CURRENT_DATE("1", "非当天"),
    NOTBIND("notBind", "正常"),
    ALREADY_BIND("alreadyBind", "非正常"),
    ADD("0", "添加"),
    DELETE("1", "删除"),
    DISABLE("1", "不启用"),
    ENABLED("0", "启用"),
    BINDING("0", "綁定"),
    NO_BINDING("1", "未綁定"),
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

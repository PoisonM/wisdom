package com.wisdom.beauty.api.enums;

public enum ChannelCodeEnum {

    BOSS("boss", "老板端"),
    CLERK("clerk", "店员端");

    ChannelCodeEnum(String code, String desc) {
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

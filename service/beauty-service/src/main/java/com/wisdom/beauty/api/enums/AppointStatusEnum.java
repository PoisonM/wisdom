package com.wisdom.beauty.api.enums;

public enum AppointStatusEnum {

    NOT_STARTED("0", "未开始"),
    CONFIRM("1", "已确认"),
    ON_SERVICE("2", "服务中"),
    OVER("3", "已完成"),
    CANCEL("4", "取消预约"),
    ONGOING("5", "进行中"),
    ENDED("6", "已结束");

    AppointStatusEnum(String code, String desc) {
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

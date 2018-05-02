package com.wisdom.beauty.api.enums;

public enum ScheduleTypeEnum {
    //0 早班，1 中班，2 晚班，3 全班，4 休
    EARLY("0", "早班"),
    MIDDLE("1", "中班"),
    NIGHT("2", "晚班"),
    ALL("3", "全班"),
    HUGH("4", "休")
    ;

    ScheduleTypeEnum(String code, String desc) {
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

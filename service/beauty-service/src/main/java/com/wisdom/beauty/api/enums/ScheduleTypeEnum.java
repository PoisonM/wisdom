package com.wisdom.beauty.api.enums;

public enum ScheduleTypeEnum {
    //0 早班，1 中班，2 晚班，3 全班，4 休
    EARLY("0", "早班", "09:00", "12:00"),
    MIDDLE("1", "中班", "11:00", "21:00"),
    NIGHT("2", "晚班", "15:30", "19:00"),
    ALL("3", "全班", "09:00", "23:30"),
    HUGH("4", "休", "00:00", "00:00")
    ;

    ScheduleTypeEnum(String code, String desc, String defaultStartTime, String defaultEndTime) {
        this.code = code;
        this.desc = desc;
        this.defaultStartTime = defaultStartTime;
        this.defaultEndTime = defaultEndTime;
    }

    public static ScheduleTypeEnum judgeValue(String code) {
        ScheduleTypeEnum[] resultCodes = ScheduleTypeEnum.values();
        for (ScheduleTypeEnum resultCode : resultCodes) {
            if (resultCode.getCode().equals(code)) {
                return resultCode;
            }
        }
        return null;
    }

    private String code;
    private String desc;
    private String defaultStartTime;
    private String defaultEndTime;

    public String getDefaultStartTime() {
        return defaultStartTime;
    }

    public void setDefaultStartTime(String defaultStartTime) {
        this.defaultStartTime = defaultStartTime;
    }

    public String getDefaultEndTime() {
        return defaultEndTime;
    }

    public void setDefaultEndTime(String defaultEndTime) {
        this.defaultEndTime = defaultEndTime;
    }

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

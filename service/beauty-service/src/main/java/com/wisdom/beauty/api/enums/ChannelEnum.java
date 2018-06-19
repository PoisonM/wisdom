package com.wisdom.beauty.api.enums;

/**
 * Created by zhanghuan on 2018/6/19.
 *
 */
public enum ChannelEnum {
    MEITUAN("0", "美团"),
    DIANPING("1", "大众点评"),
    BAIDUNUOMI("2", "百度糯米"),
    FRIEND_RECOMMEND("3", "朋友推荐"),
    SCOTTMAP("4", "高德地图"),
    COME_SELF("5", "自己过来"),
    UNDEFINED("10", "未定义");

    ChannelEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public static ChannelEnum judgeValue(String code) {
        ChannelEnum[] resultCodes = ChannelEnum.values();
        for (ChannelEnum resultCode : resultCodes) {
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

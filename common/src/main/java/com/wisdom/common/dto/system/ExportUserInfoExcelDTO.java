package com.wisdom.common.dto.system;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 导出代理
 */
public class ExportUserInfoExcelDTO {

    /*昵称	账号	等级	时间	时效*/
    /**
     * 用户的系统ID
     */
    /*@JSONField(name = "sysUserId")
    private String sysUserId;*/
    /**
     * 用户名
     */
    @JSONField(name = "nickName")
    private String nickName;
    /**
     * 手机号
     */
    @JSONField(name = "mobile")
    private String mobile;
    /**
     * 用户类型(等级)
     */
    @JSONField(name = "userType")
    private String userType;

    /**
     * 时间
     */
    @JSONField(name = "createDate")
    private String createDate;

    /**
     * 时效
     */
    @JSONField(name = "livingPeriod")
    private String livingPeriod;







    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLivingPeriod() {
        return livingPeriod;
    }

    public void setLivingPeriod(String livingPeriod) {
        this.livingPeriod = livingPeriod;
    }
}

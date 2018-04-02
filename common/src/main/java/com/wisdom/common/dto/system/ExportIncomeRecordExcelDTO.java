package com.wisdom.common.dto.system;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * 导出佣金DTO
 */
public class ExportIncomeRecordExcelDTO {
    /**
     * 用户id
     */
    @JSONField(name = "sysUserId")
    private String sysUserId;
    /**
     * 用户名
     */
    @JSONField(name = "nickName")
    private String nickName;
    /**
     * 用户类型(等级)
     */
    @JSONField(name = "userType")
    private String userType;
    /**
     * 手机号
     */
    @JSONField(name = "mobile")
    private String mobile;
    /**
     * 提成金额
     */
    @JSONField(name = "amount")
    private float amount;
    /**
     * 提成类型
     */
    @JSONField(name = "incomeType")
    private String incomeType;
    /**
     * 提成时间
     */
    @JSONField(name = "createDate")
    private String createDate;

    /**
     * 提成状态
     */
    @JSONField(name = "status")
    private String status;

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

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

    public String getIncomeType() {
        if("instance".equals(incomeType)){
            return "即时返现";
        }else if("month".equals(incomeType)){
            return "月度提成";
        }
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        if("0".equals(status)){
            return "不可提现";
        }else if("1".equals(status)){
            return "可提现";
        }else if("2".equals(status)){
            return "用户未确认收货";
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}

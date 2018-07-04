package com.wisdom.common.dto.transaction;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by zbm84 on 2017/8/3.
 */
public class MonthlyIncomeErrorDTO {

    @JSONField(name = "year")
    private String year;

    @JSONField(name = "month")
    private String month;

    @JSONField(name = "day")
    private String day;

    @JSONField(name = "businessType")
    private String businessType;

    @JSONField(name = "sysUserId")
    private String sysUserId;

    @JSONField(name = "errorMessage")
    private String errorMessage;

    //0代表未处理，1代表已处理
    @JSONField(name = "status")
    private String status;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }


    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

}

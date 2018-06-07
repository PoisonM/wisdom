package com.wisdom.common.dto.transaction;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by zbm84 on 2017/8/3.
 */
public class MonthlyIncomeSignalDTO {

    @JSONField(name = "year")
    private String year;

    @JSONField(name = "month")
    private String month;

    @JSONField(name = "day")
    private String day;

    @JSONField(name = "businessType")
    private String businessType;

    @JSONField(name = "onTimeFinish")
    private String onTimeFinish;


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

    public String getOnTimeFinish() {
        return onTimeFinish;
    }

    public void setOnTimeFinish(String onTimeFinish) {
        this.onTimeFinish = onTimeFinish;
    }
}

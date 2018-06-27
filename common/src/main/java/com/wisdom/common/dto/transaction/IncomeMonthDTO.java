package com.wisdom.common.dto.transaction;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by zbm84 on 2017/8/3.
 */
public class IncomeMonthDTO {

    @JSONField(name = "incomeId")
    private String incomeId;

    @JSONField(name = "sysUserId")
    private String sysUserId;

    @JSONField(name = "timeRangeId")
    private String timeRangeId;




    public String getTimeRangeId() {
        return timeRangeId;
    }

    public void setTimeRangeId(String timeRangeId) {
        this.timeRangeId = timeRangeId;
    }

    public String getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(String incomeId) {
        this.incomeId = incomeId;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

}

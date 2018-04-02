package com.wisdom.common.dto.transaction;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2017/12/15.
 */
public class BonusRecordDTO {

    @JSONField(name ="date")
    private String date;

    @JSONField(name ="num")
    private float num;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getNum() {
        return num;
    }

    public void setNum(float num) {
        this.num = num;
    }
}

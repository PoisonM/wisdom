package com.wisdom.common.dto.log;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by cjk on 2017/5/24.
 */
public class LogDTO<T> {

    @JSONField(name = "evenType")
    private String evenType;

    @JSONField(name = "createDate")
    private Date createDate;

    @JSONField(name = "eventData")
    private T eventData;

    public String getEvenType() {
        return evenType;
    }

    public void setEvenType(String evenType) {
        this.evenType = evenType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public T getEventData() {
        return eventData;
    }

    public void setEventData(T eventData) {
        this.eventData = eventData;
    }
}

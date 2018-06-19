package com.wisdom.common.dto.system;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by Administrator on 2017/12/18.
 */
public class SuggestionDto {

    @JSONField(name ="id")
    private String id;
    @JSONField(name="userId")
    private String userId;
    @JSONField(name = "suggestion")
    private String suggestion;
    @JSONField(name = "type")
    private String type;
    @JSONField(format="yyyy-MM-ddTHH:mm:ss")
    public Date creatTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}

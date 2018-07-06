package com.wisdom.common.dto.product;

import com.alibaba.fastjson.annotation.JSONField;

public class SecondCourseDTO {

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "url")
    private String url;

    @JSONField(name = "duration")
    private String duration;

    @JSONField(name = "password")
    private String password;

    @JSONField(name = "numberOfPlayback")
    private Integer numberOfPlayback;


    public Integer getNumberOfPlayback() {
        return numberOfPlayback;
    }

    public void setNumberOfPlayback(Integer numberOfPlayback) {
        this.numberOfPlayback = numberOfPlayback;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
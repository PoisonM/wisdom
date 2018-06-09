package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;

import java.util.Date;

public class ExtShopAppointServiceDTO extends ShopAppointServiceDTO {

    private static final long serialVersionUID = 1L;

   private Date searchStartTime;

   private Date searchEndTime;

    private Float score;

    //查询预约时间开始时间
    private String appointStartTimeS;

    //查询预约时间结束时间
    private String appointEndTimeE;

    private String clerkImage;
    //最近一次预约时间
    private String lastAppointTime;

    public String getLastAppointTime() {
        return lastAppointTime;
    }

    public void setLastAppointTime(String lastAppointTime) {
        this.lastAppointTime = lastAppointTime;
    }

    public String getClerkImage() {
        return clerkImage;
    }

    public void setClerkImage(String clerkImage) {
        this.clerkImage = clerkImage;
    }

    public String getAppointStartTimeS() {
        return appointStartTimeS;
    }

    public void setAppointStartTimeS(String appointStartTimeS) {
        this.appointStartTimeS = appointStartTimeS;
    }

    public String getAppointEndTimeE() {
        return appointEndTimeE;
    }

    public void setAppointEndTimeE(String appointEndTimeE) {
        this.appointEndTimeE = appointEndTimeE;
    }

    public Date getSearchStartTime() {
        return searchStartTime;
    }

    public void setSearchStartTime(Date searchStartTime) {
        this.searchStartTime = searchStartTime;
    }

    public Date getSearchEndTime() {
        return searchEndTime;
    }

    public void setSearchEndTime(Date searchEndTime) {
        this.searchEndTime = searchEndTime;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
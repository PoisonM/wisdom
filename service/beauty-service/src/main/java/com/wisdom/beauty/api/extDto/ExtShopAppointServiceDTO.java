package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;

import java.util.Date;

public class ExtShopAppointServiceDTO extends ShopAppointServiceDTO {

    private static final long serialVersionUID = 1L;

   private Date searchStartTime;

   private Date searchEndTime;


    //查询预约时间开始时间
    private String appointStartTimeS;

    //查询预约时间结束时间
    private String appointStartTimeE;

    public String getAppointStartTimeS() {
        return appointStartTimeS;
    }

    public void setAppointStartTimeS(String appointStartTimeS) {
        this.appointStartTimeS = appointStartTimeS;
    }

    public String getAppointStartTimeE() {
        return appointStartTimeE;
    }

    public void setAppointStartTimeE(String appointStartTimeE) {
        this.appointStartTimeE = appointStartTimeE;
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
}
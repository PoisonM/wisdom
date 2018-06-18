package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopClerkScheduleDTO;

import java.util.Date;

public class ExtShopClerkScheduleDTO<T> extends ShopClerkScheduleDTO {
    //
    private Date searchStartDate;

    private Date searchEndDate;
    private T shopClerkSchedule;

    public T getShopClerkSchedule() {
        return shopClerkSchedule;
    }

    public void setShopClerkSchedule(T shopClerkSchedule) {
        this.shopClerkSchedule = shopClerkSchedule;
    }

    public Date getSearchStartDate() {
        return searchStartDate;
    }

    public void setSearchStartDate(Date searchStartDate) {
        this.searchStartDate = searchStartDate;
    }

    public Date getSearchEndDate() {
        return searchEndDate;
    }

    public void setSearchEndDate(Date searchEndDate) {
        this.searchEndDate = searchEndDate;
    }
}
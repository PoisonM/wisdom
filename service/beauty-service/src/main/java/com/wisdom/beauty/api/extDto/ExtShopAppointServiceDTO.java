package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopAppointService;

import java.util.Date;

public class ExtShopAppointServiceDTO extends ShopAppointService {

    private static final long serialVersionUID = 1L;

   private Date searchStartTime;

   private Date searchEndTime;

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
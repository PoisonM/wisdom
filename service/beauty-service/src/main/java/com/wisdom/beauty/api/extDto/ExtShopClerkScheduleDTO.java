package com.wisdom.beauty.api.extDto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;

public class ExtShopClerkScheduleDTO<T> extends BaseEntity implements Serializable {


    private T shopClerkSchedule;

    public T getShopClerkSchedule() {
        return shopClerkSchedule;
    }

    public void setShopClerkSchedule(T shopClerkSchedule) {
        this.shopClerkSchedule = shopClerkSchedule;
    }
}
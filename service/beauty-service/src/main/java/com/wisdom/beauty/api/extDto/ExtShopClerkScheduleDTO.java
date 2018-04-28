package com.wisdom.beauty.api.extDto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;

public class ExtShopClerkScheduleDTO<T> extends BaseEntity implements Serializable {


    private T shopClerkScheduleDTO;

    public T getShopClerkScheduleDTO() {
        return shopClerkScheduleDTO;
    }

    public void setShopClerkScheduleDTO(T shopClerkScheduleDTO) {
        this.shopClerkScheduleDTO = shopClerkScheduleDTO;
    }
}
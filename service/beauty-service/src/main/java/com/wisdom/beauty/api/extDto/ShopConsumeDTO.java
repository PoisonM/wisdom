package com.wisdom.beauty.api.extDto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;

public class ShopConsumeDTO<T> extends BaseEntity implements Serializable {


    private T shopUserConsumeDTO;

    public T getShopUserConsumeDTO() {
        return shopUserConsumeDTO;
    }

    public void setShopUserConsumeDTO(T t) {
        this.shopUserConsumeDTO = t;
    }


}
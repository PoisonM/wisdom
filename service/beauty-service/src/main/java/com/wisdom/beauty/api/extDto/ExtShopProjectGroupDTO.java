package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopProjectGroupDTO;

import java.util.List;

public class ExtShopProjectGroupDTO extends ShopProjectGroupDTO {

    List<String> ShopProjectIds;

    List<String> images;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getShopProjectIds() {
        return ShopProjectIds;
    }

    public void setShopProjectIds(List<String> shopProjectIds) {
        ShopProjectIds = shopProjectIds;
    }
}
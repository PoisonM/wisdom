package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopProjectGroupDTO;
import com.wisdom.beauty.api.dto.ShopProjectInfoDTO;

import java.util.List;

public class ExtShopProjectGroupDTO extends ShopProjectGroupDTO {

    List<ShopProjectInfoDTO> ShopProjectIds;

    List<String> images;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<ShopProjectInfoDTO> getShopProjectIds() {
        return ShopProjectIds;
    }

    public void setShopProjectIds(List<ShopProjectInfoDTO> shopProjectIds) {
        ShopProjectIds = shopProjectIds;
    }
}
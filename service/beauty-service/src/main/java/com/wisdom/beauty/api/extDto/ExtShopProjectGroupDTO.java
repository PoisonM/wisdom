package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopProjectGroupDTO;
import com.wisdom.beauty.api.dto.ShopProjectInfoDTO;

import java.util.List;

public class ExtShopProjectGroupDTO extends ShopProjectGroupDTO {

    List<ShopProjectInfoDTO> shopProjectInfoDTOS;

    List<String> images;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<ShopProjectInfoDTO> getShopProjectInfoDTOS() {
        return shopProjectInfoDTOS;
    }

    public void setShopProjectInfoDTOS(List<ShopProjectInfoDTO> shopProjectInfoDTOS) {
        this.shopProjectInfoDTOS = shopProjectInfoDTOS;
    }
}
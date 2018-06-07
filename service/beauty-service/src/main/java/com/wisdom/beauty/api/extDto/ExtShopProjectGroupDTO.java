package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopProjectGroupDTO;
import com.wisdom.beauty.api.dto.ShopProjectInfoDTO;

import java.util.List;

public class ExtShopProjectGroupDTO extends ShopProjectGroupDTO {

    private List<ShopProjectInfoDTO> shopProjectInfoDTOS;

    private List<String> imageList;



    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public List<ShopProjectInfoDTO> getShopProjectInfoDTOS() {
        return shopProjectInfoDTOS;
    }

    public void setShopProjectInfoDTOS(List<ShopProjectInfoDTO> shopProjectInfoDTOS) {
        this.shopProjectInfoDTOS = shopProjectInfoDTOS;
    }
}
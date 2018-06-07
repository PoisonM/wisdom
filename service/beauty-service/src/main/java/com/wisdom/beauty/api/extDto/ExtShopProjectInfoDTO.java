package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopProjectInfoDTO;

import java.util.List;

public class ExtShopProjectInfoDTO extends ShopProjectInfoDTO {

    /**
     * 图片详情
     */
    private List<String> imageList;

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
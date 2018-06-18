package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopProjectInfoDTO;

import java.util.List;

public class ExtShopProjectInfoDTO extends ShopProjectInfoDTO {

    /**
     * 图片详情
     */
    private List<String> imageList;
    /**
     * 是否模糊查询  0 模糊查询  1  非模糊查询
     */
    private  String fuzzyQuery;

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public String getFuzzyQuery() {
        return fuzzyQuery;
    }

    public void setFuzzyQuery(String fuzzyQuery) {
        this.fuzzyQuery = fuzzyQuery;
    }
}
package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopRechargeCardDTO;

import java.util.List;

public class ExtShopRechargeCardDTO extends ShopRechargeCardDTO {

    List<String> imageUrls;
    //适用范围 项目
    List<String> projectIds;
    //适用范围 产品
    List<String> productIds;

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<String> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<String> projectIds) {
        this.projectIds = projectIds;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }
}
package com.wisdom.beauty.api.responseDto;

import com.wisdom.beauty.api.dto.ShopProductInfoDTO;

import java.util.List;

/**
 * Created by zhanghuan on 2018/5/22.
 */
public class ShopProductInfoCheckResponseDTO extends ShopProductInfoDTO {
    /** 图片地址id */
    private List<String> imageUrl;
    //库存数量
    private Integer stockNumber;
    //仓库id
    private String shopStoreId;

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(Integer stockNumber) {
        this.stockNumber = stockNumber;
    }

    public String getShopStoreId() {
        return shopStoreId;
    }

    public void setShopStoreId(String shopStoreId) {
        this.shopStoreId = shopStoreId;
    }
}

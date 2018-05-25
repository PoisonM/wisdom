package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopProjectProductCardRelationDTO;
import com.wisdom.beauty.api.dto.ShopRechargeCardDTO;

import java.util.List;

public class ExtShopRechargeCardDTO extends ShopRechargeCardDTO {

    List<String> imageUrls;

    List<ShopProjectProductCardRelationDTO> timesList;
    List<ShopProjectProductCardRelationDTO> periodList;
    List<ShopProjectProductCardRelationDTO> groupCardList;
    List<ShopProjectProductCardRelationDTO> rechargeCardList;
    List<ShopProjectProductCardRelationDTO> productList;

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<ShopProjectProductCardRelationDTO> getTimesList() {
        return timesList;
    }

    public void setTimesList(List<ShopProjectProductCardRelationDTO> timesList) {
        this.timesList = timesList;
    }

    public List<ShopProjectProductCardRelationDTO> getPeriodList() {
        return periodList;
    }

    public void setPeriodList(List<ShopProjectProductCardRelationDTO> periodList) {
        this.periodList = periodList;
    }

    public List<ShopProjectProductCardRelationDTO> getGroupCardList() {
        return groupCardList;
    }

    public void setGroupCardList(List<ShopProjectProductCardRelationDTO> groupCardList) {
        this.groupCardList = groupCardList;
    }

    public List<ShopProjectProductCardRelationDTO> getRechargeCardList() {
        return rechargeCardList;
    }

    public void setRechargeCardList(List<ShopProjectProductCardRelationDTO> rechargeCardList) {
        this.rechargeCardList = rechargeCardList;
    }

    public List<ShopProjectProductCardRelationDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<ShopProjectProductCardRelationDTO> productList) {
        this.productList = productList;
    }
}
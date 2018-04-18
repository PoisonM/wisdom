package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.*;

import java.util.List;

public class ExtShopUserConsumeRecordDTO extends ShopUserConsumeRecordDTO {

    //用户与项目关系
    private List<ShopUserProjectRelationDTO> shopUserProjectRelationDTOS;

    //用户与产品
    private List<ShopUserProductRelationDTO> shopUserProductRelationDTOS;

    //套卡
    private List<ShopProjectGroupDTO> shopProjectGroupDTOS;

    //用户与充值卡的关系
    private List<ShopUserRechargeCardDTO> shopUserRechargeCardDTOS;


    public List<ShopUserProjectRelationDTO> getShopUserProjectRelationDTOS() {
        return shopUserProjectRelationDTOS;
    }

    public void setShopUserProjectRelationDTOS(List<ShopUserProjectRelationDTO> shopUserProjectRelationDTOS) {
        this.shopUserProjectRelationDTOS = shopUserProjectRelationDTOS;
    }

    public List<ShopUserProductRelationDTO> getShopUserProductRelationDTOS() {
        return shopUserProductRelationDTOS;
    }

    public void setShopUserProductRelationDTOS(List<ShopUserProductRelationDTO> shopUserProductRelationDTOS) {
        this.shopUserProductRelationDTOS = shopUserProductRelationDTOS;
    }

    public List<ShopProjectGroupDTO> getShopProjectGroupDTOS() {
        return shopProjectGroupDTOS;
    }

    public void setShopProjectGroupDTOS(List<ShopProjectGroupDTO> shopProjectGroupDTOS) {
        this.shopProjectGroupDTOS = shopProjectGroupDTOS;
    }

    public List<ShopUserRechargeCardDTO> getShopUserRechargeCardDTOS() {
        return shopUserRechargeCardDTOS;
    }

    public void setShopUserRechargeCardDTOS(List<ShopUserRechargeCardDTO> shopUserRechargeCardDTOS) {
        this.shopUserRechargeCardDTOS = shopUserRechargeCardDTOS;
    }
}
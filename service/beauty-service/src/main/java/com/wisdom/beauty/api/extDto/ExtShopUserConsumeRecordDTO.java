package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.*;

public class ExtShopUserConsumeRecordDTO extends ShopUserConsumeRecordDTO {

    //用户与项目关系
    private ShopUserProjectRelationDTO shopUserProjectRelationDTO;

    //用户与产品
    private ShopUserProductRelationDTO shopUserProductRelationDTO;

    //套卡
    private ShopProjectGroupDTO shopProjectGroupDTO;

    //用户与充值卡的关系
    private ShopUserRechargeCardDTO shopUserRechargeCardDTO;


    public ShopUserProjectRelationDTO getShopUserProjectRelationDTO() {
        return shopUserProjectRelationDTO;
    }

    public void setShopUserProjectRelationDTO(ShopUserProjectRelationDTO shopUserProjectRelationDTO) {
        this.shopUserProjectRelationDTO = shopUserProjectRelationDTO;
    }

    public ShopUserProductRelationDTO getShopUserProductRelationDTO() {
        return shopUserProductRelationDTO;
    }

    public void setShopUserProductRelationDTO(ShopUserProductRelationDTO shopUserProductRelationDTO) {
        this.shopUserProductRelationDTO = shopUserProductRelationDTO;
    }

    public ShopProjectGroupDTO getShopProjectGroupDTO() {
        return shopProjectGroupDTO;
    }

    public void setShopProjectGroupDTO(ShopProjectGroupDTO shopProjectGroupDTO) {
        this.shopProjectGroupDTO = shopProjectGroupDTO;
    }

    public ShopUserRechargeCardDTO getShopUserRechargeCardDTO() {
        return shopUserRechargeCardDTO;
    }

    public void setShopUserRechargeCardDTO(ShopUserRechargeCardDTO shopUserRechargeCardDTO) {
        this.shopUserRechargeCardDTO = shopUserRechargeCardDTO;
    }
}
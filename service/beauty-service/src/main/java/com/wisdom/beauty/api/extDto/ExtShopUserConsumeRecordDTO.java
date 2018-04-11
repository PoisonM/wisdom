package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.*;

public class ExtShopUserConsumeRecordDTO<T> extends ShopUserConsumeRecordDTO {

    //虚拟商品类型
    private String type;

    //用户与项目关系
    private ShopUserProjectRelationDTO shopUserProjectRelationDTO;

    //用户与产品
    private ShopUserProductRelationDTO shopUserProductRelationDTO;

    //用户与套卡的关系
    private ShopUserProjectGroupRelRelationDTO shopUserProjectGroupRelRelationDTO;

    //用户与充值卡的关系
    private ShopUserRechargeCardDTO shopUserRechargeCardDTO;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public ShopUserProjectGroupRelRelationDTO getShopUserProjectGroupRelRelationDTO() {
        return shopUserProjectGroupRelRelationDTO;
    }

    public void setShopUserProjectGroupRelRelationDTO(ShopUserProjectGroupRelRelationDTO shopUserProjectGroupRelRelationDTO) {
        this.shopUserProjectGroupRelRelationDTO = shopUserProjectGroupRelRelationDTO;
    }

    public ShopUserRechargeCardDTO getShopUserRechargeCardDTO() {
        return shopUserRechargeCardDTO;
    }

    public void setShopUserRechargeCardDTO(ShopUserRechargeCardDTO shopUserRechargeCardDTO) {
        this.shopUserRechargeCardDTO = shopUserRechargeCardDTO;
    }
}
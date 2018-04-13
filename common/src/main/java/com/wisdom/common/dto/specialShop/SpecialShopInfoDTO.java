package com.wisdom.common.dto.specialShop;

import com.alibaba.fastjson.annotation.JSONField;
import com.wisdom.common.dto.system.UserBankCardInfoDTO;

import java.util.Date;
import java.util.List;

public class SpecialShopInfoDTO {

    @JSONField(name = "shopId")
    private String shopId;

    @JSONField(name = "shopName")
    private String shopName;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
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

    @JSONField(name = "shopURL")
    private String shopURL;

    @JSONField(name = "shopQRCode")
    private String shopQRCode;

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

    public String getShopURL() {
        return shopURL;
    }

    public void setShopURL(String shopURL) {
        this.shopURL = shopURL;
    }

    public String getShopQRCode() {
        return shopQRCode;
    }

    public void setShopQRCode(String shopQRCode) {
        this.shopQRCode = shopQRCode;
    }
}
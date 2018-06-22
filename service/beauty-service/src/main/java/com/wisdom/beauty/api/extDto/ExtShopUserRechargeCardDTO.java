package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;

public class ExtShopUserRechargeCardDTO extends ShopUserRechargeCardDTO {
    private String consumePrice = "0";

    public String getConsumePrice() {
        return consumePrice;
    }

    public void setConsumePrice(String consumePrice) {
        this.consumePrice = consumePrice;
    }
}
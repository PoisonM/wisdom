package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopUserArchivesDTO;

public class ExtShopUserArchivesDTO extends ShopUserArchivesDTO {
    //账户总余额
    private String totalBalance;

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }
}
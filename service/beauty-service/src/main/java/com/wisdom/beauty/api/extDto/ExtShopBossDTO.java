package com.wisdom.beauty.api.extDto;

import com.wisdom.common.dto.user.SysBossDTO;

public class ExtShopBossDTO extends SysBossDTO {
    private String currentBeautyShopName;
    private String currentShopName;

    public String getCurrentBeautyShopName() {
        return currentBeautyShopName;
    }

    public void setCurrentBeautyShopName(String currentBeautyShopName) {
        this.currentBeautyShopName = currentBeautyShopName;
    }

    public String getCurrentShopName() {
        return currentShopName;
    }

    public void setCurrentShopName(String currentShopName) {
        this.currentShopName = currentShopName;
    }
}
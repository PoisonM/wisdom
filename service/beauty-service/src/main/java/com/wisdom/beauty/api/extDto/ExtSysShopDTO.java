package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.SysShopDTO;

public class ExtSysShopDTO extends SysShopDTO {
    private String sysBossCode;

    public String getSysBossCode() {
        return sysBossCode;
    }

    public void setSysBossCode(String sysBossCode) {
        this.sysBossCode = sysBossCode;
    }
}
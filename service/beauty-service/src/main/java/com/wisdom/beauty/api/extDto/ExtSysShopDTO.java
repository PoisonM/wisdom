package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.SysShopDTO;

public class ExtSysShopDTO extends SysShopDTO {
    private String sysBossCode;

    public String getSysBossId() {
        return sysBossCode;
    }

    public void setSysBossId(String sysBossCode) {
        this.sysBossCode = sysBossCode;
    }
}
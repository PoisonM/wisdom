package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.SysShopDTO;

public class ExtSysShopDTO extends SysShopDTO {
    private String sysBossId;

    public String getSysBossId() {
        return sysBossId;
    }

    public void setSysBossId(String sysBossId) {
        this.sysBossId = sysBossId;
    }
}
package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.SysShopDTO;

public class ExtSysShopDTO extends SysShopDTO {
    private String sysBossCode;
    //0为特殊账户
    private String type;

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    public String getSysBossId() {
        return sysBossCode;
    }

    public void setSysBossId(String sysBossCode) {
        this.sysBossCode = sysBossCode;
    }
}
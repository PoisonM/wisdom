package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.SysShopDTO;

import java.util.List;

public class ExtSysShopDTO extends SysShopDTO {
    private String sysBossCode;

    private List imageList;

    public String getSysBossCode() {
        return sysBossCode;
    }

    public void setSysBossCode(String sysBossCode) {
        this.sysBossCode = sysBossCode;
    }

    public List getImageList() {
        return imageList;
    }

    public void setImageList(List imageList) {
        this.imageList = imageList;
    }
}
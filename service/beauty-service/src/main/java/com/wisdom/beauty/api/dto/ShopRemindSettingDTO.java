package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;

public class ShopRemindSettingDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //
    private String sysBossCode;

    //0 开启  1关闭
    private String status;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSysBossId() {
        return sysBossCode;
    }

    public void setSysBossId(String sysBossCode) {
        this.sysBossCode = sysBossCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
package com.wisdom.common.dto.customer;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;

public class SysBossDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //
    private String sysUserId;

    //
    private String userImage;

    //
    private String userAddress;

    //
    private String userSpeciality;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserSpeciality() {
        return userSpeciality;
    }

    public void setUserSpeciality(String userSpeciality) {
        this.userSpeciality = userSpeciality;
    }
}
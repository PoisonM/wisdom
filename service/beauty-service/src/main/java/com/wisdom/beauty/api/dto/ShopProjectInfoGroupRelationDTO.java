package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class ShopProjectInfoGroupRelationDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //
    private String sysShopId;

    //套卡名称
    private String projectGroupName;

    //项目名称
    private String shopProjectInfoName;

    //套卡表主键
    private String shopProjectGroupId;

    //项目表主键
    private String shopProjectInfoId;

    //
    private String createBy;

    //
    private Date createDate;

    //
    private String updateUser;

    //
    private Date updateDate;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSysShopId() {
        return sysShopId;
    }

    public void setSysShopId(String sysShopId) {
        this.sysShopId = sysShopId;
    }

    public String getProjectGroupName() {
        return projectGroupName;
    }

    public void setProjectGroupName(String projectGroupName) {
        this.projectGroupName = projectGroupName;
    }

    public String getShopProjectInfoName() {
        return shopProjectInfoName;
    }

    public void setShopProjectInfoName(String shopProjectInfoName) {
        this.shopProjectInfoName = shopProjectInfoName;
    }

    public String getShopProjectGroupId() {
        return shopProjectGroupId;
    }

    public void setShopProjectGroupId(String shopProjectGroupId) {
        this.shopProjectGroupId = shopProjectGroupId;
    }

    public String getShopProjectInfoId() {
        return shopProjectInfoId;
    }

    public void setShopProjectInfoId(String shopProjectInfoId) {
        this.shopProjectInfoId = shopProjectInfoId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
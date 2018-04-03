package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class ShopStoreDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //仓库名称
    private String name;

    //库管人名称
    private String sysUserName;

    //库管人角色 0：店员、1：老板、2：职工、3：店长
    private String storeRole;

    //库管人主键
    private String storeManagerId;

    //权限控制
    private String authorityStatus;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSysUserName() {
        return sysUserName;
    }

    public void setSysUserName(String sysUserName) {
        this.sysUserName = sysUserName;
    }

    public String getStoreRole() {
        return storeRole;
    }

    public void setStoreRole(String storeRole) {
        this.storeRole = storeRole;
    }

    public String getStoreManagerId() {
        return storeManagerId;
    }

    public void setStoreManagerId(String storeManagerId) {
        this.storeManagerId = storeManagerId;
    }

    public String getAuthorityStatus() {
        return authorityStatus;
    }

    public void setAuthorityStatus(String authorityStatus) {
        this.authorityStatus = authorityStatus;
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
package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class ShopCustomerProjectGroupRelRelationDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //
    private String sysCustomerId;

    //套卡关系表id
    private String shopProjectInfoGroupRelationId;

    //项目初始总个数
    private Integer projectInitTimes;

    //项目剩余总个数
    private Integer projectSurplusTimes;

    //项目初始总金额
    private Long projectInitAmount;

    //项目剩余总金额
    private Long projectSurplusAmount;

    //
    private String sysShopId;

    //
    private String sysBossId;

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

    public String getSysCustomerId() {
        return sysCustomerId;
    }

    public void setSysCustomerId(String sysCustomerId) {
        this.sysCustomerId = sysCustomerId;
    }

    public String getShopProjectInfoGroupRelationId() {
        return shopProjectInfoGroupRelationId;
    }

    public void setShopProjectInfoGroupRelationId(String shopProjectInfoGroupRelationId) {
        this.shopProjectInfoGroupRelationId = shopProjectInfoGroupRelationId;
    }

    public Integer getProjectInitTimes() {
        return projectInitTimes;
    }

    public void setProjectInitTimes(Integer projectInitTimes) {
        this.projectInitTimes = projectInitTimes;
    }

    public Integer getProjectSurplusTimes() {
        return projectSurplusTimes;
    }

    public void setProjectSurplusTimes(Integer projectSurplusTimes) {
        this.projectSurplusTimes = projectSurplusTimes;
    }

    public Long getProjectInitAmount() {
        return projectInitAmount;
    }

    public void setProjectInitAmount(Long projectInitAmount) {
        this.projectInitAmount = projectInitAmount;
    }

    public Long getProjectSurplusAmount() {
        return projectSurplusAmount;
    }

    public void setProjectSurplusAmount(Long projectSurplusAmount) {
        this.projectSurplusAmount = projectSurplusAmount;
    }

    public String getSysShopId() {
        return sysShopId;
    }

    public void setSysShopId(String sysShopId) {
        this.sysShopId = sysShopId;
    }

    public String getSysBossId() {
        return sysBossId;
    }

    public void setSysBossId(String sysBossId) {
        this.sysBossId = sysBossId;
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
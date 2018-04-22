package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class ShopCustomerProjectRelationDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //
    private String shopAppointmentId;

    //用户id
    private String sysCustomerId;

    //美容院id
    private String sysShopId;

    //
    private String sysShopName;

    //
    private String sysShopProjectId;

    //
    private String sysShopProjectName;

    //项目初始金额
    private Long sysShopProjectInitAmount;

    //项目剩余金额
    private Long sysShopProjectSurplusAmount;

    //项目剩余次数
    private Integer sysShopProjectSurplusTimes;

    //项目总次数
    private Integer sysShopProjectInitTimes;

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

    public String getShopAppointmentId() {
        return shopAppointmentId;
    }

    public void setShopAppointmentId(String shopAppointmentId) {
        this.shopAppointmentId = shopAppointmentId;
    }

    public String getSysCustomerId() {
        return sysCustomerId;
    }

    public void setSysCustomerId(String sysCustomerId) {
        this.sysCustomerId = sysCustomerId;
    }

    public String getSysShopId() {
        return sysShopId;
    }

    public void setSysShopId(String sysShopId) {
        this.sysShopId = sysShopId;
    }

    public String getSysShopName() {
        return sysShopName;
    }

    public void setSysShopName(String sysShopName) {
        this.sysShopName = sysShopName;
    }

    public String getSysShopProjectId() {
        return sysShopProjectId;
    }

    public void setSysShopProjectId(String sysShopProjectId) {
        this.sysShopProjectId = sysShopProjectId;
    }

    public String getSysShopProjectName() {
        return sysShopProjectName;
    }

    public void setSysShopProjectName(String sysShopProjectName) {
        this.sysShopProjectName = sysShopProjectName;
    }

    public Long getSysShopProjectInitAmount() {
        return sysShopProjectInitAmount;
    }

    public void setSysShopProjectInitAmount(Long sysShopProjectInitAmount) {
        this.sysShopProjectInitAmount = sysShopProjectInitAmount;
    }

    public Long getSysShopProjectSurplusAmount() {
        return sysShopProjectSurplusAmount;
    }

    public void setSysShopProjectSurplusAmount(Long sysShopProjectSurplusAmount) {
        this.sysShopProjectSurplusAmount = sysShopProjectSurplusAmount;
    }

    public Integer getSysShopProjectSurplusTimes() {
        return sysShopProjectSurplusTimes;
    }

    public void setSysShopProjectSurplusTimes(Integer sysShopProjectSurplusTimes) {
        this.sysShopProjectSurplusTimes = sysShopProjectSurplusTimes;
    }

    public Integer getSysShopProjectInitTimes() {
        return sysShopProjectInitTimes;
    }

    public void setSysShopProjectInitTimes(Integer sysShopProjectInitTimes) {
        this.sysShopProjectInitTimes = sysShopProjectInitTimes;
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
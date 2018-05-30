package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class ShopBossScheduleSettingRelationDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //
    private String sysClerkId;

    //
    private String sysShopId;

    //
    private String sysBossCode;

    //
    private String sysBossName;

    //
    private String scheduleSettingId;

    //设置日期
    private Date schedulingDate;

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

    public String getSysClerkId() {
        return sysClerkId;
    }

    public void setSysClerkId(String sysClerkId) {
        this.sysClerkId = sysClerkId;
    }

    public String getSysShopId() {
        return sysShopId;
    }

    public void setSysShopId(String sysShopId) {
        this.sysShopId = sysShopId;
    }

    public String getSysBossCode() {
        return sysBossCode;
    }

    public void setSysBossCode(String sysBossCode) {
        this.sysBossCode = sysBossCode;
    }

    public String getSysBossName() {
        return sysBossName;
    }

    public void setSysBossName(String sysBossName) {
        this.sysBossName = sysBossName;
    }

    public String getScheduleSettingId() {
        return scheduleSettingId;
    }

    public void setScheduleSettingId(String scheduleSettingId) {
        this.scheduleSettingId = scheduleSettingId;
    }

    public Date getSchedulingDate() {
        return schedulingDate;
    }

    public void setSchedulingDate(Date schedulingDate) {
        this.schedulingDate = schedulingDate;
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
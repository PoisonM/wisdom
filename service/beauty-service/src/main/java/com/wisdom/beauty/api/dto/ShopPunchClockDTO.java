package com.wisdom.beauty.api.dto;

import java.io.Serializable;
import java.util.Date;

import com.wisdom.common.entity.BaseEntity;

public class ShopPunchClockDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //
    private String sysClerkId;

    //
    private String sysShopId;

    //
    private String sysClerkName;

    //
    private String sysShopName;

    //boss编码
    private String sysBossCode;

    //
    private Date punchTime;

    //0:正常  1：异常  2：未知
    private String status;

    //打卡日期
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

    public String getSysClerkName() {
        return sysClerkName;
    }

    public void setSysClerkName(String sysClerkName) {
        this.sysClerkName = sysClerkName;
    }

    public String getSysShopName() {
        return sysShopName;
    }

    public void setSysShopName(String sysShopName) {
        this.sysShopName = sysShopName;
    }

    public String getSysBossCode() {
        return sysBossCode;
    }

    public void setSysBossCode(String sysBossCode) {
        this.sysBossCode = sysBossCode;
    }

    public Date getPunchTime() {
        return punchTime;
    }

    public void setPunchTime(Date punchTime) {
        this.punchTime = punchTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
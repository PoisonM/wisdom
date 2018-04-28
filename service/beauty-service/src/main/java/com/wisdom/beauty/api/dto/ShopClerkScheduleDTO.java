package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class ShopClerkScheduleDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //店员表主键
    private String sysClerkId;

    //老板表主键
    private String sysBossId;

    //美容店表主键
    private String sysShopId;

    //
    private String sysClerkName;

    //0 早班，1 中班，2 晚班，3 全班，4 休
    private String scheduleType;

    //存储排班的日期
    private Date scheduleDate;

    //
    private Date createDate;

    //
    private Date createBy;

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

    public String getSysBossId() {
        return sysBossId;
    }

    public void setSysBossId(String sysBossId) {
        this.sysBossId = sysBossId;
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

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Date createBy) {
        this.createBy = createBy;
    }
}
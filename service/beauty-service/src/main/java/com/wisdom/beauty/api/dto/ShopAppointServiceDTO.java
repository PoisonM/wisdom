package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class ShopAppointServiceDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //预约项目Id
    private String shopProjectId;

    //预约项目名称
    private String shopProjectName;

    //美容院id
    private String sysShopId;

    //美容院名称
    private String sysShopName;

    //美容师id
    private String sysClerkId;

    //美容师名称
    private String sysClerkName;

    //老板id
    private String sysBossId;

    //预约开始时间
    private Date appointStartTime;

    //预约结束时间
    private Date appointEndTime;

    //预约时长(分钟)
    private Integer appointPeriod;

    //用户id
    private String sysCustomerId;

    //用户名称
    private String sysCustomerName;

    //用户手机号
    private String sysCustomerPhone;

    //0:未开始 1：已确认 2：服务中 3：已完成 4、取消预约
    private String status;

    //捎句话
    private String desc;

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

    public String getShopProjectId() {
        return shopProjectId;
    }

    public void setShopProjectId(String shopProjectId) {
        this.shopProjectId = shopProjectId;
    }

    public String getShopProjectName() {
        return shopProjectName;
    }

    public void setShopProjectName(String shopProjectName) {
        this.shopProjectName = shopProjectName;
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

    public String getSysClerkId() {
        return sysClerkId;
    }

    public void setSysClerkId(String sysClerkId) {
        this.sysClerkId = sysClerkId;
    }

    public String getSysClerkName() {
        return sysClerkName;
    }

    public void setSysClerkName(String sysClerkName) {
        this.sysClerkName = sysClerkName;
    }

    public String getSysBossId() {
        return sysBossId;
    }

    public void setSysBossId(String sysBossId) {
        this.sysBossId = sysBossId;
    }

    public Date getAppointStartTime() {
        return appointStartTime;
    }

    public void setAppointStartTime(Date appointStartTime) {
        this.appointStartTime = appointStartTime;
    }

    public Date getAppointEndTime() {
        return appointEndTime;
    }

    public void setAppointEndTime(Date appointEndTime) {
        this.appointEndTime = appointEndTime;
    }

    public Integer getAppointPeriod() {
        return appointPeriod;
    }

    public void setAppointPeriod(Integer appointPeriod) {
        this.appointPeriod = appointPeriod;
    }

    public String getSysCustomerId() {
        return sysCustomerId;
    }

    public void setSysCustomerId(String sysCustomerId) {
        this.sysCustomerId = sysCustomerId;
    }

    public String getSysCustomerName() {
        return sysCustomerName;
    }

    public void setSysCustomerName(String sysCustomerName) {
        this.sysCustomerName = sysCustomerName;
    }

    public String getSysCustomerPhone() {
        return sysCustomerPhone;
    }

    public void setSysCustomerPhone(String sysCustomerPhone) {
        this.sysCustomerPhone = sysCustomerPhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
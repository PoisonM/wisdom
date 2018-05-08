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

    //预约时长英文 ; 分割
    private String appointPeriodDetail;

    //用户id
    private String sysUserId;

    //用户名称
    private String sysUserName;

    //用户手机号
    private String sysUserPhone;

    //0:未开始 1：已确认 2：服务中 3：已完成 4、取消预约
    private String status;

    //捎句话
    private String detail;

    //取消预约时间
    private String appointmentCancleDate;

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

    public String getAppointPeriodDetail() {
        return appointPeriodDetail;
    }

    public void setAppointPeriodDetail(String appointPeriodDetail) {
        this.appointPeriodDetail = appointPeriodDetail;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getSysUserName() {
        return sysUserName;
    }

    public void setSysUserName(String sysUserName) {
        this.sysUserName = sysUserName;
    }

    public String getSysUserPhone() {
        return sysUserPhone;
    }

    public void setSysUserPhone(String sysUserPhone) {
        this.sysUserPhone = sysUserPhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAppointmentCancleDate() {
        return appointmentCancleDate;
    }

    public void setAppointmentCancleDate(String appointmentCancleDate) {
        this.appointmentCancleDate = appointmentCancleDate;
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
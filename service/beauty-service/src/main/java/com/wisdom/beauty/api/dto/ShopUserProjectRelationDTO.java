package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShopUserProjectRelationDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //
    private String shopAppointmentId;

    //用户id
    private String sysUserId;

    //
    private String sysClerkId;

    //
    private String sysClerkName;

    //使用方式 0：疗程 1：单次
    private String useStyle;

    //美容院id
    private String sysShopId;

    //折扣价格
    private String discount;

    //
    private String sysShopName;

    //
    private String sysShopProjectId;

    //
    private String sysShopProjectName;

    //项目初始金额
    private BigDecimal sysShopProjectInitAmount;

    //项目剩余金额
    private BigDecimal sysShopProjectSurplusAmount;

    //项目剩余次数
    private Integer sysShopProjectSurplusTimes;

    //项目总次数
    private Integer sysShopProjectInitTimes;

    //服务次数
    private Integer serviceTime;

    //生效日期
    private Date effectiveDate;

    //有效天数
    private Integer effectiveDays;

    //失效日期
    private Date invalidDays;

    //1 赠送 0不赠送
    private String isSend;

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

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
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

    public String getUseStyle() {
        return useStyle;
    }

    public void setUseStyle(String useStyle) {
        this.useStyle = useStyle;
    }

    public String getSysShopId() {
        return sysShopId;
    }

    public void setSysShopId(String sysShopId) {
        this.sysShopId = sysShopId;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
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

    public BigDecimal getSysShopProjectInitAmount() {
        return sysShopProjectInitAmount;
    }

    public void setSysShopProjectInitAmount(BigDecimal sysShopProjectInitAmount) {
        this.sysShopProjectInitAmount = sysShopProjectInitAmount;
    }

    public BigDecimal getSysShopProjectSurplusAmount() {
        return sysShopProjectSurplusAmount;
    }

    public void setSysShopProjectSurplusAmount(BigDecimal sysShopProjectSurplusAmount) {
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

    public Integer getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Integer serviceTime) {
        this.serviceTime = serviceTime;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Integer getEffectiveDays() {
        return effectiveDays;
    }

    public void setEffectiveDays(Integer effectiveDays) {
        this.effectiveDays = effectiveDays;
    }

    public Date getInvalidDays() {
        return invalidDays;
    }

    public void setInvalidDays(Date invalidDays) {
        this.invalidDays = invalidDays;
    }

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend;
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
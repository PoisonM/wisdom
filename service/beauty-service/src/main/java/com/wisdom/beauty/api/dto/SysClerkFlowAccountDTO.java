package com.wisdom.beauty.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.wisdom.common.entity.BaseEntity;

public class SysClerkFlowAccountDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //
    private String shopUserConsumeRecordId;

    //用户id
    private String sysUserId;

    //用户名称
    private String sysUserName;

    //美容店id
    private String sysShopId;

    //美容店名称
    private String sysShopName;

    //店员id
    private String sysClerkId;

    //boss编码
    private String sysBossCode;

    //类型 0：充值  1：消费 2、还欠款 3、退款 4、划卡
    private String type;

    //流水金额
    private BigDecimal flowAmount;

    //操作时间
    private Date operDate;

    //操作信息
    private String operInfo;

    //备注
    private String detail;

    //签字url地址
    private String signUrl;

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

    public String getShopUserConsumeRecordId() {
        return shopUserConsumeRecordId;
    }

    public void setShopUserConsumeRecordId(String shopUserConsumeRecordId) {
        this.shopUserConsumeRecordId = shopUserConsumeRecordId;
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

    public String getSysBossCode() {
        return sysBossCode;
    }

    public void setSysBossCode(String sysBossCode) {
        this.sysBossCode = sysBossCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getFlowAmount() {
        return flowAmount;
    }

    public void setFlowAmount(BigDecimal flowAmount) {
        this.flowAmount = flowAmount;
    }

    public Date getOperDate() {
        return operDate;
    }

    public void setOperDate(Date operDate) {
        this.operDate = operDate;
    }

    public String getOperInfo() {
        return operInfo;
    }

    public void setOperInfo(String operInfo) {
        this.operInfo = operInfo;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl;
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
package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShopUserProjectGroupRelRelationDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //套卡表名称
    private String shopProjectGroupName;

    //套卡表主键
    private String shopProjectGroupId;

    //购买套卡的数量
    private Integer shopProjectGroupNumber;

    //
    private BigDecimal shopGroupPuchasePrice;

    //
    private String sysUserId;

    //项目表主键
    private String shopProjectInfoId;

    //
    private String shopProjectInfoName;

    //套卡关系表id
    private String shopProjectInfoGroupRelationId;

    //项目初始总个数
    private Integer projectInitTimes;

    //项目剩余总个数
    private Integer projectSurplusTimes;

    //项目初始总金额
    private BigDecimal projectInitAmount;

    //项目剩余总金额
    private BigDecimal projectSurplusAmount;

    //
    private String sysShopId;

    //
    private String sysClerkId;

    //
    private String sysClerkName;

    //boss编码
    private String sysBossCode;

    //折扣
    private Float discount;

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

    public String getShopProjectGroupName() {
        return shopProjectGroupName;
    }

    public void setShopProjectGroupName(String shopProjectGroupName) {
        this.shopProjectGroupName = shopProjectGroupName;
    }

    public String getShopProjectGroupId() {
        return shopProjectGroupId;
    }

    public void setShopProjectGroupId(String shopProjectGroupId) {
        this.shopProjectGroupId = shopProjectGroupId;
    }

    public Integer getShopProjectGroupNumber() {
        return shopProjectGroupNumber;
    }

    public void setShopProjectGroupNumber(Integer shopProjectGroupNumber) {
        this.shopProjectGroupNumber = shopProjectGroupNumber;
    }

    public BigDecimal getShopGroupPuchasePrice() {
        return shopGroupPuchasePrice;
    }

    public void setShopGroupPuchasePrice(BigDecimal shopGroupPuchasePrice) {
        this.shopGroupPuchasePrice = shopGroupPuchasePrice;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getShopProjectInfoId() {
        return shopProjectInfoId;
    }

    public void setShopProjectInfoId(String shopProjectInfoId) {
        this.shopProjectInfoId = shopProjectInfoId;
    }

    public String getShopProjectInfoName() {
        return shopProjectInfoName;
    }

    public void setShopProjectInfoName(String shopProjectInfoName) {
        this.shopProjectInfoName = shopProjectInfoName;
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

    public BigDecimal getProjectInitAmount() {
        return projectInitAmount;
    }

    public void setProjectInitAmount(BigDecimal projectInitAmount) {
        this.projectInitAmount = projectInitAmount;
    }

    public BigDecimal getProjectSurplusAmount() {
        return projectSurplusAmount;
    }

    public void setProjectSurplusAmount(BigDecimal projectSurplusAmount) {
        this.projectSurplusAmount = projectSurplusAmount;
    }

    public String getSysShopId() {
        return sysShopId;
    }

    public void setSysShopId(String sysShopId) {
        this.sysShopId = sysShopId;
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

    public String getSysBossCode() {
        return sysBossCode;
    }

    public void setSysBossCode(String sysBossCode) {
        this.sysBossCode = sysBossCode;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
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
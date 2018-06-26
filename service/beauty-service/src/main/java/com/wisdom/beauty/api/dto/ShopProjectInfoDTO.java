package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShopProjectInfoDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //
    private String sysShopId;

    //
    private String parentShopId;

    //项目名称
    private String projectName;

    //项目一级类别名称
    private String projectTypeOneName;

    //项目二级类别名称
    private String projectTypeTwoName;

    //项目一级类别id
    private String projectTypeOneId;

    //项目二级类别id
    private String projectTypeTwoId;

    //0：客装 1：院装 2：易耗品
    private String productType;

    //使用方式 1：疗程0：单次
    private String useStyle;

    //卡类别 0：次卡 1：月卡 2：季卡：3：半年卡 4：年卡 5:长期有效卡
    private String cardType;

    //子购买之日起至effective_number_month个月有效
    private Integer effectiveNumberMonth;

    //项目图片url
    private String projectUrl;

    //时长（分钟）
    private Integer projectDuration;

    //市场价格
    private BigDecimal marketPrice;

    //优惠价格
    private BigDecimal initialPrice;

    //最多包含次数
    private Integer serviceTimes;

    //回访天数
    private Integer visitDateTime;

    //单次耗卡金额
    private BigDecimal oncePrice;

    //功能介绍
    private String functionIntr;

    //是否界面展示 0显示 1不显示
    private String isDisplay;

    //是否启用 0启用 1不启用
    private String status;

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

    public String getSysShopId() {
        return sysShopId;
    }

    public void setSysShopId(String sysShopId) {
        this.sysShopId = sysShopId;
    }

    public String getParentShopId() {
        return parentShopId;
    }

    public void setParentShopId(String parentShopId) {
        this.parentShopId = parentShopId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectTypeOneName() {
        return projectTypeOneName;
    }

    public void setProjectTypeOneName(String projectTypeOneName) {
        this.projectTypeOneName = projectTypeOneName;
    }

    public String getProjectTypeTwoName() {
        return projectTypeTwoName;
    }

    public void setProjectTypeTwoName(String projectTypeTwoName) {
        this.projectTypeTwoName = projectTypeTwoName;
    }

    public String getProjectTypeOneId() {
        return projectTypeOneId;
    }

    public void setProjectTypeOneId(String projectTypeOneId) {
        this.projectTypeOneId = projectTypeOneId;
    }

    public String getProjectTypeTwoId() {
        return projectTypeTwoId;
    }

    public void setProjectTypeTwoId(String projectTypeTwoId) {
        this.projectTypeTwoId = projectTypeTwoId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getUseStyle() {
        return useStyle;
    }

    public void setUseStyle(String useStyle) {
        this.useStyle = useStyle;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Integer getEffectiveNumberMonth() {
        return effectiveNumberMonth;
    }

    public void setEffectiveNumberMonth(Integer effectiveNumberMonth) {
        this.effectiveNumberMonth = effectiveNumberMonth;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public Integer getProjectDuration() {
        return projectDuration;
    }

    public void setProjectDuration(Integer projectDuration) {
        this.projectDuration = projectDuration;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(BigDecimal initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Integer getServiceTimes() {
        return serviceTimes;
    }

    public void setServiceTimes(Integer serviceTimes) {
        this.serviceTimes = serviceTimes;
    }

    public Integer getVisitDateTime() {
        return visitDateTime;
    }

    public void setVisitDateTime(Integer visitDateTime) {
        this.visitDateTime = visitDateTime;
    }

    public BigDecimal getOncePrice() {
        return oncePrice;
    }

    public void setOncePrice(BigDecimal oncePrice) {
        this.oncePrice = oncePrice;
    }

    public String getFunctionIntr() {
        return functionIntr;
    }

    public void setFunctionIntr(String functionIntr) {
        this.functionIntr = functionIntr;
    }

    public String getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(String isDisplay) {
        this.isDisplay = isDisplay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
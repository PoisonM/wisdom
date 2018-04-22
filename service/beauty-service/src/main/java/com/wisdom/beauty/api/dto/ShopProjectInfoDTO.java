package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class ShopProjectInfoDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //
    private String sysShopId;

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

    //使用方式 0：疗程 1：单次
    private String useStyle;

    //卡类别 0：次卡 1：月卡 2：季卡：3：半年卡 4：年卡
    private String cardType;

    //项目图片url
    private String projectUrl;

    //时长（分钟）
    private Integer projectDuration;

    //市场价格
    private Long marketPrice;

    //优惠价格
    private Long discountPrice;

    //最多包含次数
    private Integer maxContainTimes;

    //回访天数
    private Integer visitDateTime;

    //单次耗卡金额
    private Long oncePrice;

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

    public Long getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Long marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getMaxContainTimes() {
        return maxContainTimes;
    }

    public void setMaxContainTimes(Integer maxContainTimes) {
        this.maxContainTimes = maxContainTimes;
    }

    public Integer getVisitDateTime() {
        return visitDateTime;
    }

    public void setVisitDateTime(Integer visitDateTime) {
        this.visitDateTime = visitDateTime;
    }

    public Long getOncePrice() {
        return oncePrice;
    }

    public void setOncePrice(Long oncePrice) {
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
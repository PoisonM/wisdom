package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShopProductInfoDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //
    private String sysShopId;

    //
    private String parentShopId;

    //产品名称
    private String productName;

    //一级类别名称
    private String productTypeOneName;

    //产品一级类别id
    private String productTypeOneId;

    //二级类别名称
    private String productTypeTwoName;

    //产品二级类别id
    private String productTypeTwoId;

    //产品图片url
    private String productUrl;

    //产品适用部位
    private String productPosition;

    //功效
    private String productFunction;

    //产品二维码url地址
    private String qrCodeUrl;

    //产品类型 0：客装 1：院装 2：易耗品
    private String productType;

    //保质期（月）
    private Integer qualityPeriod;

    //产品有效期预警（天）
    private Integer productWarningDay;

    //库存预警数量
    private Integer productWarningNum;

    //市场价格
    private BigDecimal marketPrice;

    //优惠价格
    private BigDecimal discountPrice;

    //
    private String productCode;

    //
    private BigDecimal productUnit;

    //
    private String productSpec;

    //
    private String introduce;

    //
    private String isDisplay;

    //
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductTypeOneName() {
        return productTypeOneName;
    }

    public void setProductTypeOneName(String productTypeOneName) {
        this.productTypeOneName = productTypeOneName;
    }

    public String getProductTypeOneId() {
        return productTypeOneId;
    }

    public void setProductTypeOneId(String productTypeOneId) {
        this.productTypeOneId = productTypeOneId;
    }

    public String getProductTypeTwoName() {
        return productTypeTwoName;
    }

    public void setProductTypeTwoName(String productTypeTwoName) {
        this.productTypeTwoName = productTypeTwoName;
    }

    public String getProductTypeTwoId() {
        return productTypeTwoId;
    }

    public void setProductTypeTwoId(String productTypeTwoId) {
        this.productTypeTwoId = productTypeTwoId;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getProductPosition() {
        return productPosition;
    }

    public void setProductPosition(String productPosition) {
        this.productPosition = productPosition;
    }

    public String getProductFunction() {
        return productFunction;
    }

    public void setProductFunction(String productFunction) {
        this.productFunction = productFunction;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getQualityPeriod() {
        return qualityPeriod;
    }

    public void setQualityPeriod(Integer qualityPeriod) {
        this.qualityPeriod = qualityPeriod;
    }

    public Integer getProductWarningDay() {
        return productWarningDay;
    }

    public void setProductWarningDay(Integer productWarningDay) {
        this.productWarningDay = productWarningDay;
    }

    public Integer getProductWarningNum() {
        return productWarningNum;
    }

    public void setProductWarningNum(Integer productWarningNum) {
        this.productWarningNum = productWarningNum;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public BigDecimal getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(BigDecimal productUnit) {
        this.productUnit = productUnit;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
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
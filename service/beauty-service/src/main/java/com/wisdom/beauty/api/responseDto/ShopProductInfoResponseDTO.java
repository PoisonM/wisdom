package com.wisdom.beauty.api.responseDto;

import com.wisdom.common.entity.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by zhanghuan on 2018/4/28.
 */
public class ShopProductInfoResponseDTO extends BaseEntity {
	//
	private String id;

	//
	private String sysShopId;

	// 产品名称
	private String productName;

	// 一级类别名称
	private String productTypeOneName;

	// 产品一级类别id
	private String productTypeOneId;

	// 二级类别名称
	private String productTypeTwoName;

	// 产品二级类别id
	private String productTypeTwoId;

	// 产品图片url
	private String productUrl;

	// 产品适用部位
	private String productPosition;

	// 功效
	private String productFunction;

	// 产品二维码url地址
	private String qrCodeUrl;

	// 产品类型 0：客装 1：院装 2：易耗品
	private String productType;

	// 保质期（月）
	private Integer qualityPeriod;

	// 产品有效期预警（天）
	private Integer productWarningDay;

	// 库存预警数量
	private Integer productWarningNum;

	// 市场价格
	private Long marketPrice;

	// 优惠价格
	private Long discountPrice;

	//产品编号
	private String productCode;

	//产品单位
	private Long productUnit;

	//产品规格
	private String productSpec;

	//
	private String introduce;
	/** 图片地址id */
	private String[] imageUrl;

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

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Long getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(Long productUnit) {
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


	public String[] getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String[] imageUrl) {
		this.imageUrl = imageUrl;
	}
}

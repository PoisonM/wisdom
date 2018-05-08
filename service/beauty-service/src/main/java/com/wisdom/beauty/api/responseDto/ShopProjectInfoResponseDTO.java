package com.wisdom.beauty.api.responseDto;

import com.wisdom.common.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * Created by zhanghuan on 2018/4/28.
 */
public class ShopProjectInfoResponseDTO extends BaseEntity {
	//
	private String id;

	//
	private String sysShopId;

	//
	private String sysBossId;

	// 项目名称
	private String projectName;

	// 项目一级类别名称
	private String projectTypeOneName;

	// 项目二级类别名称
	private String projectTypeTwoName;

	// 项目一级类别id
	private String projectTypeOneId;

	// 项目二级类别id
	private String projectTypeTwoId;

	// 0：客装 1：院装 2：易耗品
	private String productType;

	// 使用方式 0：疗程 1：单次
	private String useStyle;

	// 卡类别 0：次卡 1：月卡 2：季卡：3：半年卡 4：年卡
	private String cardType;

	// 项目图片url
	private String projectUrl;

	// 时长（分钟）
	private Integer projectDuration;

	// 市场价格
	private BigDecimal marketPrice;

	// 优惠价格
	private BigDecimal discountPrice;

	// 最多包含次数
	private Integer serviceTimes;

	// 回访天数
	private Integer visitDateTime;

	// 单次耗卡金额
	private BigDecimal oncePrice;

	// 功能介绍
	private String functionIntr;
    private String[] imageUrl;

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

	public String getSysBossId() {
		return sysBossId;
	}

	public void setSysBossId(String sysBossId) {
		this.sysBossId = sysBossId;
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

    public String[] getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String[] imageUrl) {
        this.imageUrl = imageUrl;
    }
}

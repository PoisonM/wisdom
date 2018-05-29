package com.wisdom.beauty.api.responseDto;

import com.wisdom.beauty.api.dto.ShopProjectInfoDTO;
import com.wisdom.common.entity.BaseEntity;

import java.util.List;

/**
 * ClassName: ProjectInfoGroupResponseDTO
 *
 * @Author： huan
 * 
 * @Description:
 * @Date:Created in 2018/4/11 17:44
 * @since JDK 1.8
 */
public class ProjectInfoGroupResponseDTO extends BaseEntity {
	private  String id;
	/** 套卡名称 */
	private String projectGroupName;

	/** 套卡图片 */
	private String projectGroupUrl;

	/** 市场价格 */
	private Long marketPrice;

	/** 折扣价格 */
	private Long discountPrice;

	/**生效日期*/
	private String effectiveDate;
	/**有效期*/
	private String expirationDate;

	/** 套卡说明 */
	private String detail;

	/** 项目List */
	private List<ShopProjectInfoDTO> shopProjectInfoDTOS;
	/** 图片地址URL */
    private List<String> imageUrl;
	/** 是否过期 */
	private  Boolean  overdue;
	/** 是否启用 */
	private  String  status;

	public String getProjectGroupName() {
		return projectGroupName;
	}

	public void setProjectGroupName(String projectGroupName) {
		this.projectGroupName = projectGroupName;
	}

	public String getProjectGroupUrl() {
		return projectGroupUrl;
	}

	public void setProjectGroupUrl(String projectGroupUrl) {
		this.projectGroupUrl = projectGroupUrl;
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



	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public List<ShopProjectInfoDTO> getShopProjectInfoDTOS() {
		return shopProjectInfoDTOS;
	}

	public void setShopProjectInfoDTOS(List<ShopProjectInfoDTO> shopProjectInfoDTOS) {
		this.shopProjectInfoDTOS = shopProjectInfoDTOS;
	}

	public List<String> getImageUrl() {
		return imageUrl;
	}

    public void setImageUrl(List<String> imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Boolean getOverdue() {
		return overdue;
	}

	public void setOverdue(Boolean overdue) {
		this.overdue = overdue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

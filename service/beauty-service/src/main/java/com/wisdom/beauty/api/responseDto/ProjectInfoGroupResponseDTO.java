package com.wisdom.beauty.api.responseDto;

import com.wisdom.beauty.api.dto.ShopProjectInfoDTO;
import com.wisdom.common.entity.BaseEntity;

import java.util.Date;
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
	/** 套卡名称 */
	private String projectGroupName;

	/** 套卡图片 */
	private String projectGroupUrl;

	/** 市场价格 */
	private Long marketPrice;

	/** 折扣价格 */
	private Long discountPrice;

	/** 有效期 */
	private Date validDate;

	/** 套卡说明 */
	private String detail;

	/** 项目List */
	private List<ShopProjectInfoDTO> list;
	/** 图片地址URL */
	private String[] imageUrl;

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

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public List<ShopProjectInfoDTO> getList() {
		return list;
	}

	public void setList(List<ShopProjectInfoDTO> list) {
		this.list = list;
	}

	public String[] getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String[] imageUrl) {
		this.imageUrl = imageUrl;
	}
}

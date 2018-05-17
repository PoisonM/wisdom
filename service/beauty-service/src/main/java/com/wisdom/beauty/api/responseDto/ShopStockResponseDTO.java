package com.wisdom.beauty.api.responseDto;

import com.wisdom.beauty.api.dto.ShopStockDTO;
import com.wisdom.common.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zhanghuan on 2018/5/2.
 */
public class ShopStockResponseDTO extends ShopStockDTO {

	/** 产品图片url */
	// 入库时间
	private Date operDate;
	private String stockType;
	private String[] imageUrl;
	private  String productId;
	private  String productCode;
	private  String productSpec;
	private  BigDecimal productUnit;
     /** 出库数量 */
	private Integer outStockNumber;
	private List<ShopStockResponseDTO> shopStockResponseDTO;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductSpec() {
		return productSpec;
	}

	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}

	public BigDecimal getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(BigDecimal productUnit) {
		this.productUnit = productUnit;
	}

	public String[] getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String[] imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<ShopStockResponseDTO> getShopStockResponseDTO() {
		return shopStockResponseDTO;
	}

	public void setShopStockResponseDTO(List<ShopStockResponseDTO> shopStockResponseDTO) {
		this.shopStockResponseDTO = shopStockResponseDTO;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Date getOperDate() {
		return operDate;
	}

	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public Integer getOutStockNumber() {
		return outStockNumber;
	}

	public void setOutStockNumber(Integer outStockNumber) {
		this.outStockNumber = outStockNumber;
	}
}

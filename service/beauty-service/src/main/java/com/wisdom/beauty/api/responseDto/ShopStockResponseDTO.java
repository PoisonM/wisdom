package com.wisdom.beauty.api.responseDto;

import com.wisdom.beauty.api.dto.ShopStockDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zhanghuan on 2018/5/2.
 */
public class ShopStockResponseDTO extends ShopStockDTO {

	/** 产品图片url */
	// 入库时间
	private Date createDate;
	private Date operDate;
	private String stockType;
	private List<String> imageUrl;
	private  String productId;
	private  String name;
	private  String detail;
	private  String applayUser;
	private  String stockStatus;
	private  String productCode;
	private  String productSpec;
	private String productUnit;
    /**产品图片*/
	private String productImage;
	/**实际库存数量*/
	private Integer actualStockNumber;
     /** 出库数量 */
	private Integer outStockNumber;
	/** 本仓库存 */
	private Integer storeNumberSelf;
	/** 库存总量 */
	private Integer allStoreNumber;
	/** 占用成本 */
	private BigDecimal useCost;
	/** 占用总成本 */
	private BigDecimal allUseCost;
	//仓库名字
	private String shopStoreName;
	//产品一级类别id
	private String productTypeOneName;

	//二级类别名称
	private String productTypeTwoName;
	private List<ShopStockResponseDTO> shopStockResponseDTO;


	public Integer getOutStockNumber() {
		return outStockNumber;
	}

	public void setOutStockNumber(Integer outStockNumber) {
		this.outStockNumber = outStockNumber;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getApplayUser() {
		return applayUser;
	}

	public void setApplayUser(String applayUser) {
		this.applayUser = applayUser;
	}

	public String getStockStatus() {
		return stockStatus;
	}

	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

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

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}

	public List<String> getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(List<String> imageUrl) {
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

	public Integer getStoreNumberSelf() {
		return storeNumberSelf;
	}

	public void setStoreNumberSelf(Integer storeNumberSelf) {
		this.storeNumberSelf = storeNumberSelf;
	}

	public Integer getAllStoreNumber() {
		return allStoreNumber;
	}

	public void setAllStoreNumber(Integer allStoreNumber) {
		this.allStoreNumber = allStoreNumber;
	}

	public BigDecimal getUseCost() {
		return useCost;
	}

	public void setUseCost(BigDecimal useCost) {
		this.useCost = useCost;
	}

	public BigDecimal getAllUseCost() {
		return allUseCost;
	}

	public void setAllUseCost(BigDecimal allUseCost) {
		this.allUseCost = allUseCost;
	}

	public String getShopStoreName() {
		return shopStoreName;
	}

	public void setShopStoreName(String shopStoreName) {
		this.shopStoreName = shopStoreName;
	}

	public Integer getActualStockNumber() {
		return actualStockNumber;
	}

	public void setActualStockNumber(Integer actualStockNumber) {
		this.actualStockNumber = actualStockNumber;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getProductTypeTwoName() {
		return productTypeTwoName;
	}

	public void setProductTypeTwoName(String productTypeTwoName) {
		this.productTypeTwoName = productTypeTwoName;
	}

	public String getProductTypeOneName() {
		return productTypeOneName;
	}

	public void setProductTypeOneName(String productTypeOneName) {
		this.productTypeOneName = productTypeOneName;
	}
}

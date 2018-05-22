package com.wisdom.beauty.api.responseDto;

import com.wisdom.common.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zhanghuan on 2018/5/2.
 */
public class ShopStockResponseDTO extends BaseEntity {
	private String id;

	/** 仓库名称 */
	private String name;

	/** 单据号 */
	private String flowNo;

	/** 申请人 */
	private String applayUser;

	/** 状态 0、已入库 */
	private String stockStatus;

	/** 备注 */
	private String detail;

	/** 库存数量 */
	private Integer stockNumber;

	/** 产品名称 */
	private String shopProcName;

	/** 生产日期 */
	private Date productDate;

	/** 库存单价（元） */
	private BigDecimal stockPrice;

	/** 产品编号 */
	private String productCode;

	/** 产品单位 */
	private Long productUnit;
	/** 产品规格 */
	private String productSpec;
	/** 产品图片url */
	// 入库时间
	private Date operDate;
	private String stockType;
	private String[] imageUrl;
	private  String productId;
     /** 出库数量 */
	private Integer outStockNumber;
	private List<ShopStockResponseDTO> shopStockResponseDTO;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getStockNumber() {
		return stockNumber;
	}

	public void setStockNumber(Integer stockNumber) {
		this.stockNumber = stockNumber;
	}

	public String getShopProcName() {
		return shopProcName;
	}

	public void setShopProcName(String shopProcName) {
		this.shopProcName = shopProcName;
	}

	public Date getProductDate() {
		return productDate;
	}

	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}

	public BigDecimal getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(BigDecimal stockPrice) {
		this.stockPrice = stockPrice;
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

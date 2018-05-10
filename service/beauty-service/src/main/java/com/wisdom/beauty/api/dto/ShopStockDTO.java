package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShopStockDTO extends BaseEntity implements Serializable {
	//
	private String id;

	//bossID
	private String shopBossId;

	//入库记录id
	private String shopStockRecordId;

	//仓库id
	private String shopStoreId;

	//单据号
	private String flowNo;

	//出库数量
	private Integer outStockNumber;

	//库存数量
	private Integer stockNumber;

	//
	private String shopProcId;

	//
	private String shopProcName;

	//生产日期
	private Date productDate;

	//库存单价（元）
	private BigDecimal stockPrice;

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

	public String getShopBossId() {
		return shopBossId;
	}

	public void setShopBossId(String shopBossId) {
		this.shopBossId = shopBossId;
	}

	public String getShopStockRecordId() {
		return shopStockRecordId;
	}

	public void setShopStockRecordId(String shopStockRecordId) {
		this.shopStockRecordId = shopStockRecordId;
	}

	public String getShopStoreId() {
		return shopStoreId;
	}

	public void setShopStoreId(String shopStoreId) {
		this.shopStoreId = shopStoreId;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public Integer getOutStockNumber() {
		return outStockNumber;
	}

	public void setOutStockNumber(Integer outStockNumber) {
		this.outStockNumber = outStockNumber;
	}

	public Integer getStockNumber() {
		return stockNumber;
	}

	public void setStockNumber(Integer stockNumber) {
		this.stockNumber = stockNumber;
	}

	public String getShopProcId() {
		return shopProcId;
	}

	public void setShopProcId(String shopProcId) {
		this.shopProcId = shopProcId;
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
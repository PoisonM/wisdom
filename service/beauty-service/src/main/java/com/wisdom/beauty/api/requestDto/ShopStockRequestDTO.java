package com.wisdom.beauty.api.requestDto;

import com.wisdom.beauty.api.dto.ShopProductInfoDTO;
import com.wisdom.common.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhanghuan on 2018/5/7.
 */
public class ShopStockRequestDTO extends ShopProductInfoDTO {
	private  String id;

	/** bossID */
	private String sysBossCode;

	/** 入库记录id */
	private String shopStockRecordId;

	/** 仓库id */
	private String shopStoreId;

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

	/***/
	private String shopProcId;

	/** 生产日期 */
	private Date productDate;

	/** 库存单价（元） */
	private BigDecimal stockPrice;

	/** 0、手动入库 1、扫码入库 2、手动出库 3、扫码出库 */
	private String stockStyle;

	/** 0、采购入库 1、内部员工出库 2、顾客出库 3、赠送 4、报废 5、院用 6、退回供货商 7、下发到店 */
	private String stockType;
	private String receiver;


	public String getShopBossId() {
		return sysBossCode;
	}

	public void setShopBossId(String sysBossCode) {
		this.sysBossCode = sysBossCode;
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

	public String getShopProcId() {
		return shopProcId;
	}

	public void setShopProcId(String shopProcId) {
		this.shopProcId = shopProcId;
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

	public String getStockStyle() {
		return stockStyle;
	}

	public void setStockStyle(String stockStyle) {
		this.stockStyle = stockStyle;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
}

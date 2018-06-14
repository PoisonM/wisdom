package com.wisdom.beauty.api.requestDto;

/**
 * Created by zhanghuan on 2018/5/7.
 */
public class ShopStockRecordRequestDTO {
	private String shopStoreId;
	private String stockStyle;
	private int pageSize;
	private String startTime;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShopStoreId() {
		return shopStoreId;
	}

	public void setShopStoreId(String shopStoreId) {
		this.shopStoreId = shopStoreId;
	}

	public String getStockStyle() {
		return stockStyle;
	}

	public void setStockStyle(String stockStyle) {
		this.stockStyle = stockStyle;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	private String endTime;

}

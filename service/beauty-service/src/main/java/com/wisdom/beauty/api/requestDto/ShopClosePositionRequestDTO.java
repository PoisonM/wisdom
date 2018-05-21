package com.wisdom.beauty.api.requestDto;

import com.wisdom.beauty.api.dto.ShopClosePositionRecordDTO;

/**
 * Created by zhanghuan on 2018/5/21.
 */
public class ShopClosePositionRequestDTO extends ShopClosePositionRecordDTO {
	// 仓库id
	private String shopStoreId;
	//
	private String shopStockNumberId;
	// 盘点记录id
	private String shopCheckRecorId;

	public String getShopStoreId() {
		return shopStoreId;
	}

	public void setShopStoreId(String shopStoreId) {
		this.shopStoreId = shopStoreId;
	}

	public String getShopStockNumberId() {
		return shopStockNumberId;
	}

	public void setShopStockNumberId(String shopStockNumberId) {
		this.shopStockNumberId = shopStockNumberId;
	}

	public String getShopCheckRecorId() {
		return shopCheckRecorId;
	}

	public void setShopCheckRecorId(String shopCheckRecorId) {
		this.shopCheckRecorId = shopCheckRecorId;
	}
}

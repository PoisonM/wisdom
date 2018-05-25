package com.wisdom.beauty.api.requestDto;

import com.wisdom.common.entity.BaseEntity;

/**
 * Created by zhanghuan on 2018/5/23.
 */
public class SetStorekeeperRequestDTO extends BaseEntity {
	/**
	 * 仓库管理员id
	 */
	private String[] storeManagerIds;
	/**
	 * 仓库管理员名字
	 */
	private String[] storeManagerNames;
	/**
	 * 仓库id
	 */

	private String shopStoreId;

	public String[] getStoreManagerIds() {
		return storeManagerIds;
	}

	public void setStoreManagerIds(String[] storeManagerIds) {
		this.storeManagerIds = storeManagerIds;
	}

	public String[] getStoreManagerNames() {
		return storeManagerNames;
	}

	public void setStoreManagerNames(String[] storeManagerNames) {
		this.storeManagerNames = storeManagerNames;
	}

	public String getShopStoreId() {
		return shopStoreId;
	}

	public void setShopStoreId(String shopStoreId) {
		this.shopStoreId = shopStoreId;
	}
}

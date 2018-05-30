package com.wisdom.beauty.api.requestDto;

import com.wisdom.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by zhanghuan on 2018/5/23.
 */
public class StoreProductIdRequestDTO extends BaseEntity {
    private  String shopStoreId;
    private  List<String> productIds;

    public String getShopStoreId() {
        return shopStoreId;
    }

    public void setShopStoreId(String shopStoreId) {
        this.shopStoreId = shopStoreId;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }
}

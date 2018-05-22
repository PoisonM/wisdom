package com.wisdom.beauty.api.responseDto;

import com.wisdom.beauty.api.dto.ShopCheckRecordDTO;

import java.util.List;

/**
 * Created by zhanghuan on 2018/5/21.
 */
public class ShopCheckRecordResponseDTO extends ShopCheckRecordDTO {
    private Integer exceptionNumber;
    private String productTypeOneName;
    private Integer productTypeNumber;
    private String productName;
    private List<ShopCheckRecordResponseDTO> shopCheckRecordResponseList;
    public Integer getExceptionNumber() {
        return exceptionNumber;
    }

    public void setExceptionNumber(Integer exceptionNumber) {
        this.exceptionNumber = exceptionNumber;
    }



    public Integer getProductTypeNumber() {
        return productTypeNumber;
    }

    public void setProductTypeNumber(Integer productTypeNumber) {
        this.productTypeNumber = productTypeNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<ShopCheckRecordResponseDTO> getShopCheckRecordResponseList() {
        return shopCheckRecordResponseList;
    }

    public void setShopCheckRecordResponseList(List<ShopCheckRecordResponseDTO> shopCheckRecordResponseList) {
        this.shopCheckRecordResponseList = shopCheckRecordResponseList;
    }

    @Override
    public String getProductTypeOneName() {
        return productTypeOneName;
    }

    @Override
    public void setProductTypeOneName(String productTypeOneName) {
        this.productTypeOneName = productTypeOneName;
    }
}

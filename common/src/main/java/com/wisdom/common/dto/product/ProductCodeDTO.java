package com.wisdom.common.dto.product;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2017/12/15.
 */
public class ProductCodeDTO {

    @JSONField(name ="productCode")
    private String productCode;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }


}

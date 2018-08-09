package com.wisdom.common.dto.transaction;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by zbm84 on 2017/8/3.
 */
public class OrderProductRelationDTO {

    @JSONField(name = "id")
    private String id;

    //订单编号ID
    @JSONField(name = "businessOrderId")
    private String businessOrderId;

    @JSONField(name = "businessProductId")
    private String businessProductId;

    @JSONField(name = "productNum")
    private int productNum;

    @JSONField(name = "productSpec")
    private String productSpec;

    @JSONField(name = "seckillId")
    private String seckillId;

    public String getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(String seckillId) {
        this.seckillId = seckillId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessOrderId() {
        return businessOrderId;
    }

    public void setBusinessOrderId(String businessOrderId) {
        this.businessOrderId = businessOrderId;
    }

    public String getBusinessProductId() {
        return businessProductId;
    }

    public void setBusinessProductId(String businessProductId) {
        this.businessProductId = businessProductId;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }
}

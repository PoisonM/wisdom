package com.wisdom.common.dto.system;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 导出产品DTO
 */
public class ExportProductExcelDTO {
    /*"订单编号", */
    /**
     * 产品的ID编号
     */
    @JSONField(name = "productId")
    private String productId;

    /**
     * 商品名称
     */
    @JSONField(name = "productName")
    private String productName;

    /**
     * 产品的品牌
     */
    @JSONField(name = "brand")
    private String brand;

    /**
     * 产品的详细分类
     */
    @JSONField(name = "secondType")
    private String secondType;

    /**
     * 产品价格
     */
    @JSONField(name = "price")
    private String price;

    /**
     * 产品状态：0表示下架，1表示上架
     */
    @JSONField(name = "status")
    private String status;

    /**
     * 产品库存
     */
    @JSONField(name = "productAmount")
    private int productAmount;

    /**
     * 产品销量
     */
    @JSONField(name = "sellNum")
    private String sellNum;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSecondType() {
        return secondType;
    }

    public void setSecondType(String secondType) {
        this.secondType = secondType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        if("0".equals(status)){
            status = "下架";
        }else if("1".equals(status)){
            status = "上架";
        }
        return status;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSellNum() {
        return sellNum;
    }

    public void setSellNum(String sellNum) {
        this.sellNum = sellNum;
    }
}

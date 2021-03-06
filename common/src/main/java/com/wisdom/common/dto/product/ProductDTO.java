package com.wisdom.common.dto.product;

import com.alibaba.fastjson.annotation.JSONField;

public class ProductDTO<T> {

    /**
     * 商品id
     */
    @JSONField(name = "id")
    private String id;

    /**
     * 商品名称
     */
    @JSONField(name = "productName")
    private String productName;

    /**
     * 产品的ID编号
     */
    @JSONField(name = "productId")
    private String productId;

    /**
     * 产品的品牌
     */
    @JSONField(name = "brand")
    private String brand;

    /**
     * type为course表示产品类型课程类，offline表示为线下产品
     */
    @JSONField(name = "type")
    private String type;

    /**
     * 产品的详细分类
     */
    @JSONField(name = "secondType")
    private String secondType;
    /**
     * 产品的详细分类Id
     */
    @JSONField(name = "productClassId")
    private String productClassId;

    /**
     * 产品的详细分类名称
     */
    @JSONField(name = "secondTypeName")
    private String secondTypeName;

    /**
     * 产品详情
     */
    @JSONField(name = "description")
    private String description;

    @JSONField(name = "productPrefecture")
    private String productPrefecture;

    /**
     * 产品宣传图
     */
    @JSONField(name = "firstUrl")
    private String firstUrl;

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

    @JSONField(name = "productDetail")
    private T productDetail;

    /**
     * 创建时间
     */
    @JSONField(name = "createDate")
    private String createDate;

    /**
     * 产品库存
     */
    @JSONField(name = "productAmount")
    private String productAmount;

    /**
     * 产品销量teacherName
     */
    @JSONField(name = "sellNum")
    private String sellNum;

    /**
     * 讲师姓名
     */
    @JSONField(name = "teacherName")
    private String teacherName;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }


    public String getSecondTypeName() {
        return secondTypeName;
    }

    public void setSecondTypeName(String secondTypeName) {
        this.secondTypeName = secondTypeName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSecondType() {
        return secondType;
    }

    public void setSecondType(String secondType) {
        this.secondType = secondType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstUrl() {
        return firstUrl;
    }

    public void setFirstUrl(String firstUrl) {
        this.firstUrl = firstUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public T getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(T productDetail) {
        this.productDetail = productDetail;
    }

    public String getStatus() {
        return status;
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

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductPrefecture() {
        return productPrefecture;
    }

    public void setProductPrefecture(String productPrefecture) {
        this.productPrefecture = productPrefecture;
    }

    public String getProductClassId() {
        return productClassId;
    }

    public void setProductClassId(String productClassId) {
        this.productClassId = productClassId;
    }
}
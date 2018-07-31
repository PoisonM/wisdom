package com.wisdom.common.dto.product;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class SeckillProductDTO<T> implements Serializable {

    /**
     * 活动id
     */
    @JSONField(name = "id")
    private int id;

    /**
     * 场次id
     */
    @JSONField(name = "fieldId")
    private int fieldId;

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
     * 产品详情
     */
    @JSONField(name = "description")
    private String description;


    /**
     * 产品宣传图
     */
    @JSONField(name = "firstUrl")
    private String firstUrl;

    /**
     * 产品价格
     */
    @JSONField(name = "price")
    private double price;

    /**
     * 产品优惠价格
     */
    @JSONField(name = "favorablePrice")
    private double favorablePrice;


    /**
     * 产品状态：0马上抢 1即将开始 2活动已结束 3已抢光
     */
    @JSONField(name = "status")
    private int status;


    /**
     * 产品库存
     */
    @JSONField(name = "productAmount")
    private int productAmount;

    /**
     * 产品销量teacherName
     */
    @JSONField(name = "sellNum")
    private int sellNum;

    /**
     * 产品总量
     */
    @JSONField(name = "activityNum")
    private int activityNum;

    /**
     * 单次最多购买数
     */
    @JSONField(name = "productNum")
    private int productNum;

    /**
     * 活动开始时间
     */
    @JSONField(name = "startTime")
    private Date startTime;

    /**
     * 活动结束时间
     */
    @JSONField(name = "endTime")
    private Date endTime;

    @JSONField(name = "productDetail")
    private T productDetail;

    /**
     * 产品类型
     */
    @JSONField(name = "productType")
    private String productType;

    /**
     * 产品类型
     */
    @JSONField(name = "countdown")
    private long countdown;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getFavorablePrice() {
        return favorablePrice;
    }

    public void setFavorablePrice(double favorablePrice) {
        this.favorablePrice = favorablePrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public int getSellNum() {
        return sellNum;
    }

    public void setSellNum(int sellNum) {
        this.sellNum = sellNum;
    }

    public int getActivityNum() {
        return activityNum;
    }

    public void setActivityNum(int activityNum) {
        this.activityNum = activityNum;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public T getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(T productDetail) {
        this.productDetail = productDetail;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public long getCountdown() {
        return countdown;
    }

    public void setCountdown(long countdown) {
        this.countdown = countdown;
    }
}
package com.wisdom.common.dto.product;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class TrainingProductDTO {

    @JSONField(name = "productId")
    private String productId;

    @JSONField(name = "trainingProductName")
    private String trainingProductName;

    @JSONField(name = "brand")
    private String brand;

    @JSONField(name = "trainingProductType")
    private String trainingProductType;

    @JSONField(name = "description")
    private String description;

    @JSONField(name = "price")
    private String price;

    @JSONField(name = "listCourse")
    private List<CourseDTO> listCourse;

    /*图片*/
    @JSONField(name = "detailList")
    private List<String> detailList;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTrainingProductName() {
        return trainingProductName;
    }

    public void setTrainingProductName(String trainingProductName) {
        this.trainingProductName = trainingProductName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTrainingProductType() {
        return trainingProductType;
    }

    public void setTrainingProductType(String trainingProductType) {
        this.trainingProductType = trainingProductType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CourseDTO> getListCourse() {
        return listCourse;
    }

    public void setListCourse(List<CourseDTO> listCourse) {
        this.listCourse = listCourse;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<String> detailList) {
        this.detailList = detailList;
    }

}
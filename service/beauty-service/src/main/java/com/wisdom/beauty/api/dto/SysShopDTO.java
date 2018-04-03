package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class SysShopDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //店面名称
    private String name;

    //店面图片
    private String shopImageUrl;

    //联系电话
    private String phone;

    //省
    private String province;

    //市
    private String city;

    //详细地址
    private String address;

    //营业执照url
    private String businessLicenseUrl;

    //身份证正面
    private String idCardFrontUrl;

    //身份证反面
    private String idCardBackUrl;

    //开户许可证
    private String openAccountLicenseUrl;

    //上门服务  0:不上门服务  1:上门服务
    private String onServiceStatus;

    //简介
    private String describe;

    //面积
    private Float area;

    //开始营业时间
    private String openDoorDate;

    //结束营业时间
    private String closeDoorDate;

    //
    private String createBy;

    //
    private Date createDate;

    //
    private String updateUser;

    //
    private Date updateDate;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopImageUrl() {
        return shopImageUrl;
    }

    public void setShopImageUrl(String shopImageUrl) {
        this.shopImageUrl = shopImageUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessLicenseUrl() {
        return businessLicenseUrl;
    }

    public void setBusinessLicenseUrl(String businessLicenseUrl) {
        this.businessLicenseUrl = businessLicenseUrl;
    }

    public String getIdCardFrontUrl() {
        return idCardFrontUrl;
    }

    public void setIdCardFrontUrl(String idCardFrontUrl) {
        this.idCardFrontUrl = idCardFrontUrl;
    }

    public String getIdCardBackUrl() {
        return idCardBackUrl;
    }

    public void setIdCardBackUrl(String idCardBackUrl) {
        this.idCardBackUrl = idCardBackUrl;
    }

    public String getOpenAccountLicenseUrl() {
        return openAccountLicenseUrl;
    }

    public void setOpenAccountLicenseUrl(String openAccountLicenseUrl) {
        this.openAccountLicenseUrl = openAccountLicenseUrl;
    }

    public String getOnServiceStatus() {
        return onServiceStatus;
    }

    public void setOnServiceStatus(String onServiceStatus) {
        this.onServiceStatus = onServiceStatus;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    public String getOpenDoorDate() {
        return openDoorDate;
    }

    public void setOpenDoorDate(String openDoorDate) {
        this.openDoorDate = openDoorDate;
    }

    public String getCloseDoorDate() {
        return closeDoorDate;
    }

    public void setCloseDoorDate(String closeDoorDate) {
        this.closeDoorDate = closeDoorDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
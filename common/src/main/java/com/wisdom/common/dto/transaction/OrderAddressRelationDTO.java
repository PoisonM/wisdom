package com.wisdom.common.dto.transaction;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by zbm84 on 2017/8/3.
 */
public class OrderAddressRelationDTO {

    @JSONField(name = "id")
    private String id;

    /**
     * 订单id
     */
    @JSONField(name = "businessOrderId")
    private String businessOrderId;
    /**
     * 地址id
     */
    @JSONField(name = "userOrderAddressId")
    private String userOrderAddressId;

    /**
     * 收货人姓名
     */
    @JSONField(name = "userNameAddress")
    private String userNameAddress;

    /**
     * 收货人手机号
     */
    @JSONField(name = "userPhoneAddress")
    private String userPhoneAddress;
    /**
     * 收货地址省份
     */
    @JSONField(name = "userProvinceAddress")
    private String userProvinceAddress;
    /**
     * 收货地址省份
     */
    @JSONField(name = "userCityAddress")
    private String userCityAddress;
    /**
     * 收货地址详情
     */
    @JSONField(name = "userDetailAddress")
    private String userDetailAddress;
    /**
     * 创建时间
     */
    @JSONField(name = "addressCreateDate")
    private Date addressCreateDate;
    /**
     * 修改时间
     */
    @JSONField(name = "addressUpdateDate")
    private Date addressUpdateDate;

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

    public String getUserNameAddress() {
        return userNameAddress;
    }

    public void setUserNameAddress(String userNameAddress) {
        this.userNameAddress = userNameAddress;
    }

    public String getUserPhoneAddress() {
        return userPhoneAddress;
    }

    public void setUserPhoneAddress(String userPhoneAddress) {
        this.userPhoneAddress = userPhoneAddress;
    }

    public String getUserProvinceAddress() {
        return userProvinceAddress;
    }

    public void setUserProvinceAddress(String userProvinceAddress) {
        this.userProvinceAddress = userProvinceAddress;
    }

    public String getUserDetailAddress() {
        return userDetailAddress;
    }

    public void setUserDetailAddress(String userDetailAddress) {
        this.userDetailAddress = userDetailAddress;
    }

    public Date getAddressCreateDate() {
        return addressCreateDate;
    }

    public void setAddressCreateDate(Date addressCreateDate) {
        this.addressCreateDate = addressCreateDate;
    }

    public String getUserOrderAddressId() {
        return userOrderAddressId;
    }

    public void setUserOrderAddressId(String userOrderAddressId) {
        this.userOrderAddressId = userOrderAddressId;
    }

    public Date getAddressUpdateDate() {
        return addressUpdateDate;
    }

    public void setAddressUpdateDate(Date addressUpdateDate) {
        this.addressUpdateDate = addressUpdateDate;
    }

    @Override
    public String toString() {
        return "OrderAddressRelationDTO{" +
                "id='" + id + '\'' +
                ", businessOrderId='" + businessOrderId + '\'' +
                ", userOrderAddressId='" + userOrderAddressId + '\'' +
                ", userNameAddress='" + userNameAddress + '\'' +
                ", userPhoneAddress='" + userPhoneAddress + '\'' +
                ", userProvinceAddress='" + userProvinceAddress + '\'' +
                ", userDetailAddress='" + userDetailAddress + '\'' +
                ", addressCreateDate=" + addressCreateDate +
                ", addressUpdateDate=" + addressUpdateDate +
                '}';
    }
}

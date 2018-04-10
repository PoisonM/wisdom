package com.wisdom.beauty.api.dto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class ShopUserConsumeRecordDTO extends BaseEntity implements Serializable {
    //
    private String id;

    //项目表主键
    private String shopProjectId;

    //项目名称
    private String shopProjectName;

    //产品表主键
    private String shopProductId;

    //产品名称
    private String shopProductName;

    //充值卡主键
    private String shopRechargeCardId;

    //套卡主键
    private String shopProjectGroupId;

    //套卡主键
    private String shopProjectGroupName;

    //优惠券主键
    private String shopCouponId;

    //
    private String shopCouponName;

    //充值卡名称
    private String shopRechargeCardName;

    //消费数量
    private Integer consumeNumber;

    //0：充值  1：消费 2、还欠款 3、退款 4、划卡
    private String type;

    //美容院表主键
    private String sysShopId;

    //美容院名称
    private String sysShopName;

    //店员表主键
    private String sysShopClerkId;

    //店员名称
    private String sysShopClerkName;

    //消费价格
    private Long price;

    //消费折扣
    private Long discount;

    //操作日期
    private Date operDate;

    //支付方式  0:微信 1：支付宝 2:现金
    private String payType;

    //签字地址
    private String signUrl;

    //用户姓名
    private String shopUserName;

    //用户表主键
    private String shopUserId;

    //消费流水号
    private String consumeFlowNo;

    //备注
    private String detail;

    //是否有效
    private String status;

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

    public String getShopProjectId() {
        return shopProjectId;
    }

    public void setShopProjectId(String shopProjectId) {
        this.shopProjectId = shopProjectId;
    }

    public String getShopProjectName() {
        return shopProjectName;
    }

    public void setShopProjectName(String shopProjectName) {
        this.shopProjectName = shopProjectName;
    }

    public String getShopProductId() {
        return shopProductId;
    }

    public void setShopProductId(String shopProductId) {
        this.shopProductId = shopProductId;
    }

    public String getShopProductName() {
        return shopProductName;
    }

    public void setShopProductName(String shopProductName) {
        this.shopProductName = shopProductName;
    }

    public String getShopRechargeCardId() {
        return shopRechargeCardId;
    }

    public void setShopRechargeCardId(String shopRechargeCardId) {
        this.shopRechargeCardId = shopRechargeCardId;
    }

    public String getShopProjectGroupId() {
        return shopProjectGroupId;
    }

    public void setShopProjectGroupId(String shopProjectGroupId) {
        this.shopProjectGroupId = shopProjectGroupId;
    }

    public String getShopProjectGroupName() {
        return shopProjectGroupName;
    }

    public void setShopProjectGroupName(String shopProjectGroupName) {
        this.shopProjectGroupName = shopProjectGroupName;
    }

    public String getShopCouponId() {
        return shopCouponId;
    }

    public void setShopCouponId(String shopCouponId) {
        this.shopCouponId = shopCouponId;
    }

    public String getShopCouponName() {
        return shopCouponName;
    }

    public void setShopCouponName(String shopCouponName) {
        this.shopCouponName = shopCouponName;
    }

    public String getShopRechargeCardName() {
        return shopRechargeCardName;
    }

    public void setShopRechargeCardName(String shopRechargeCardName) {
        this.shopRechargeCardName = shopRechargeCardName;
    }

    public Integer getConsumeNumber() {
        return consumeNumber;
    }

    public void setConsumeNumber(Integer consumeNumber) {
        this.consumeNumber = consumeNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSysShopId() {
        return sysShopId;
    }

    public void setSysShopId(String sysShopId) {
        this.sysShopId = sysShopId;
    }

    public String getSysShopName() {
        return sysShopName;
    }

    public void setSysShopName(String sysShopName) {
        this.sysShopName = sysShopName;
    }

    public String getSysShopClerkId() {
        return sysShopClerkId;
    }

    public void setSysShopClerkId(String sysShopClerkId) {
        this.sysShopClerkId = sysShopClerkId;
    }

    public String getSysShopClerkName() {
        return sysShopClerkName;
    }

    public void setSysShopClerkName(String sysShopClerkName) {
        this.sysShopClerkName = sysShopClerkName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Date getOperDate() {
        return operDate;
    }

    public void setOperDate(Date operDate) {
        this.operDate = operDate;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl;
    }

    public String getShopUserName() {
        return shopUserName;
    }

    public void setShopUserName(String shopUserName) {
        this.shopUserName = shopUserName;
    }

    public String getShopUserId() {
        return shopUserId;
    }

    public void setShopUserId(String shopUserId) {
        this.shopUserId = shopUserId;
    }

    public String getConsumeFlowNo() {
        return consumeFlowNo;
    }

    public void setConsumeFlowNo(String consumeFlowNo) {
        this.consumeFlowNo = consumeFlowNo;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
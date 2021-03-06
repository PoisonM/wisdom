package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ShopUserOrderDTO extends BaseEntity implements Serializable {

    //订单失效日期
    private Date exprDate;

    private String userName;

    //订单状态 1、未支付 2、待支付  2、已支付  3、已失效
    private String status;
    //订单号
    private String orderId;
    //美容店主键
    private String shopId;

    //用户id
    private String userId;

    //档案表主键
    private String shopUserArchivesId;

    //创建时间
    private Date createDate;

    //更新时间
    private Date updateDate;

    //备注
    private String detail;

    private String sysClerkId;

    private String sysClerkName;
    //签字图片地址
    private String signUrl;

    //0、商品类型为次卡；1、商品类型为疗程卡 ；2、商品类型为充值卡；3、商品类型为套卡 4、商品类型为产品
    private String goodsType;

    //0添加  1删除
    private String operation;

    //订单价格
    private String orderPrice;
    //状态描述
    private String statusDesc;

    //可用余额
    private BigDecimal availableBalance;

    //支付对象
    private ShopUserPayDTO shopUserPayDTO;

    //用户与项目关系
    private List<ExtShopUserProjectRelationDTO> shopUserProjectRelationDTOS;

    //用户与产品
    private List<ExtShopUserProductRelationDTO> shopUserProductRelationDTOS;

    //用户与充值卡的关系
    private ShopUserRechargeCardDTO shopUserRechargeCardDTO;

    //存储用户支付抵扣充值卡对象
    private List<ExtShopUserRechargeCardDTO> userPayRechargeCardList;

    //套卡详细
    private List<ExtShopUserProjectGroupRelRelationDTO> projectGroupRelRelationDTOS;

    //充值卡抵扣金额
    private String rechargeCardPay;


    public String getRechargeCardPay() {
        return rechargeCardPay;
    }

    public void setRechargeCardPay(String rechargeCardPay) {
        this.rechargeCardPay = rechargeCardPay;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSysClerkId() {
        return sysClerkId;
    }

    public void setSysClerkId(String sysClerkId) {
        this.sysClerkId = sysClerkId;
    }

    public String getSysClerkName() {
        return sysClerkName;
    }

    public void setSysClerkName(String sysClerkName) {
        this.sysClerkName = sysClerkName;
    }

    public ShopUserPayDTO getShopUserPayDTO() {
        return shopUserPayDTO;
    }

    public void setShopUserPayDTO(ShopUserPayDTO shopUserPayDTO) {
        this.shopUserPayDTO = shopUserPayDTO;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<ExtShopUserRechargeCardDTO> getUserPayRechargeCardList() {
        return userPayRechargeCardList;
    }

    public void setUserPayRechargeCardList(List<ExtShopUserRechargeCardDTO> userPayRechargeCardList) {
        this.userPayRechargeCardList = userPayRechargeCardList;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getShopUserArchivesId() {
        return shopUserArchivesId;
    }

    public void setShopUserArchivesId(String shopUserArchivesId) {
        this.shopUserArchivesId = shopUserArchivesId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Date getExprDate() {
        return exprDate;
    }

    public void setExprDate(Date exprDate) {
        this.exprDate = exprDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<ExtShopUserProjectRelationDTO> getShopUserProjectRelationDTOS() {
        return shopUserProjectRelationDTOS;
    }

    public void setShopUserProjectRelationDTOS(List<ExtShopUserProjectRelationDTO> shopUserProjectRelationDTOS) {
        this.shopUserProjectRelationDTOS = shopUserProjectRelationDTOS;
    }

    public List<ExtShopUserProductRelationDTO> getShopUserProductRelationDTOS() {
        return shopUserProductRelationDTOS;
    }

    public void setShopUserProductRelationDTOS(List<ExtShopUserProductRelationDTO> shopUserProductRelationDTOS) {
        this.shopUserProductRelationDTOS = shopUserProductRelationDTOS;
    }

    public List<ExtShopUserProjectGroupRelRelationDTO> getProjectGroupRelRelationDTOS() {
        return projectGroupRelRelationDTOS;
    }

    public void setProjectGroupRelRelationDTOS(List<ExtShopUserProjectGroupRelRelationDTO> projectGroupRelRelationDTOS) {
        this.projectGroupRelRelationDTOS = projectGroupRelRelationDTOS;
    }

    public ShopUserRechargeCardDTO getShopUserRechargeCardDTO() {
        return shopUserRechargeCardDTO;
    }

    public void setShopUserRechargeCardDTO(ShopUserRechargeCardDTO shopUserRechargeCardDTO) {
        this.shopUserRechargeCardDTO = shopUserRechargeCardDTO;
    }
}
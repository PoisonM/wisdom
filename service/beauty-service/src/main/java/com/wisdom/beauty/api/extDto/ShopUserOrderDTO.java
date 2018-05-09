package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectGroupRelRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ShopUserOrderDTO extends BaseEntity implements Serializable {

    //订单失效日期
    private Date exprDate;

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

    //备注
    private String detail;

    //签字图片地址
    private String signUrl;

    //0、商品类型为次卡；1、商品类型为疗程卡 ；2、商品类型为充值卡；3、商品类型为套卡 4、商品类型为产品
    private String goodsType;

    //0添加  1删除
    private String operation;

    //订单价格
    private String orderPrice;

    //用户与项目关系
    private List<ShopUserProjectRelationDTO> shopUserProjectRelationDTOS;

    //用户与产品
    private List<ShopUserProductRelationDTO> shopUserProductRelationDTOS;

    //用户与充值卡的关系
    private ShopUserRechargeCardDTO shopUserRechargeCardDTO;

    //套卡
    private List<ShopUserProjectGroupRelRelationDTO> projectGroupRelRelationDTOS;

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

    public List<ShopUserProjectRelationDTO> getShopUserProjectRelationDTOS() {
        return shopUserProjectRelationDTOS;
    }

    public void setShopUserProjectRelationDTOS(List<ShopUserProjectRelationDTO> shopUserProjectRelationDTOS) {
        this.shopUserProjectRelationDTOS = shopUserProjectRelationDTOS;
    }

    public List<ShopUserProductRelationDTO> getShopUserProductRelationDTOS() {
        return shopUserProductRelationDTOS;
    }

    public void setShopUserProductRelationDTOS(List<ShopUserProductRelationDTO> shopUserProductRelationDTOS) {
        this.shopUserProductRelationDTOS = shopUserProductRelationDTOS;
    }

    public List<ShopUserProjectGroupRelRelationDTO> getProjectGroupRelRelationDTOS() {
        return projectGroupRelRelationDTOS;
    }

    public void setProjectGroupRelRelationDTOS(List<ShopUserProjectGroupRelRelationDTO> projectGroupRelRelationDTOS) {
        this.projectGroupRelRelationDTOS = projectGroupRelRelationDTOS;
    }

    public ShopUserRechargeCardDTO getShopUserRechargeCardDTO() {
        return shopUserRechargeCardDTO;
    }

    public void setShopUserRechargeCardDTO(ShopUserRechargeCardDTO shopUserRechargeCardDTO) {
        this.shopUserRechargeCardDTO = shopUserRechargeCardDTO;
    }
}
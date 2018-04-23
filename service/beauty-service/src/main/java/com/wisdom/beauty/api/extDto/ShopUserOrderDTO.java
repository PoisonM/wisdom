package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectGroupRelRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;

import java.util.Date;
import java.util.List;

public class ShopUserOrderDTO {

    //订单失效日期
    private String exprDate;

    //订单状态 1、未支付  2、已支付  3、已失效
    private String status;
    //订单号
    private String orderId;
    //美容店主键
    private String shopId;

    //档案表主键
    private String shopUserArchivesId;

    //创建时间
    private Date createDate;

    private String detail;

    //用户与项目关系
    private List<ShopUserProjectRelationDTO> shopUserProjectRelationDTOS;

    //用户与产品
    private List<ShopUserProductRelationDTO> shopUserProductRelationDTOS;

    //套卡
    private List<ShopUserProjectGroupRelRelationDTO> projectGroupRelRelationDTOS;

    //用户与充值卡的关系
    private List<ShopUserRechargeCardDTO> shopUserRechargeCardDTOS;

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

    public String getExprDate() {
        return exprDate;
    }

    public void setExprDate(String exprDate) {
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

    public List<ShopUserRechargeCardDTO> getShopUserRechargeCardDTOS() {
        return shopUserRechargeCardDTOS;
    }

    public void setShopUserRechargeCardDTOS(List<ShopUserRechargeCardDTO> shopUserRechargeCardDTOS) {
        this.shopUserRechargeCardDTOS = shopUserRechargeCardDTOS;
    }
}
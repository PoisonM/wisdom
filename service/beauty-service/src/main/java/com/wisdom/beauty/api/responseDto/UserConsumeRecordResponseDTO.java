package com.wisdom.beauty.api.responseDto;

import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;
import com.wisdom.common.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * ClassName: ${CLASS_NAME}
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/9 19:26
 * @since JDK 1.8
 */
public class UserConsumeRecordResponseDTO extends BaseEntity {
    //流水号
    private  String flowNo;
    //总金额
    private BigDecimal sumAmount;
    //划卡和消费页面展示的名称
    private String title;
    //创建时间
    private Date createDate;
    //顾客
    private String shopUserName;
    //前台
    private String sysShopClerkName;
    //前台id
    private String sysShopClerkId;
    //操作门店
    private String sysShopName;
    //签字地址
    private String signUrl;
    //备注
    private String detail;
    private String goodType;
    private String consumeType;
    //用于返回前端判断消费还是充值字段
    private String type;
    //支付方式  0:微信 1：支付宝 2:现金
    private String payType;
    private List<ShopUserConsumeRecordDTO> userConsumeRecordList;


    public Date getCreateDate() {
        return createDate;
    }

    public String getSysShopClerkName() {
        return sysShopClerkName;
    }

    public void setSysShopClerkName(String sysShopClerkName) {
        this.sysShopClerkName = sysShopClerkName;
    }

    public String getSysShopName() {
        return sysShopName;
    }

    public void setSysShopName(String sysShopName) {
        this.sysShopName = sysShopName;
    }


    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShopUserName() {
        return shopUserName;
    }

    public void setShopUserName(String shopUserName) {
        this.shopUserName = shopUserName;
    }

    public BigDecimal getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
    }

    public List<ShopUserConsumeRecordDTO> getUserConsumeRecordList() {
        return userConsumeRecordList;
    }

    public void setUserConsumeRecordList(List<ShopUserConsumeRecordDTO> userConsumeRecordList) {
        this.userConsumeRecordList = userConsumeRecordList;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public String getSysShopClerkId() {
        return sysShopClerkId;
    }

    public void setSysShopClerkId(String sysShopClerkId) {
        this.sysShopClerkId = sysShopClerkId;
    }

    public String getGoodType() {
        return goodType;
    }

    public void setGoodType(String goodType) {
        this.goodType = goodType;
    }

    public String getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(String consumeType) {
        this.consumeType = consumeType;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}

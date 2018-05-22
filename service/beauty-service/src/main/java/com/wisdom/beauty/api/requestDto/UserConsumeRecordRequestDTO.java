package com.wisdom.beauty.api.requestDto;

import com.wisdom.common.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018/4/27.
 */
public class UserConsumeRecordRequestDTO extends BaseEntity {
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
    private int pageSize ; //每页的条目数

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public BigDecimal getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getShopUserName() {
        return shopUserName;
    }

    public void setShopUserName(String shopUserName) {
        this.shopUserName = shopUserName;
    }

    public String getSysShopClerkName() {
        return sysShopClerkName;
    }

    public void setSysShopClerkName(String sysShopClerkName) {
        this.sysShopClerkName = sysShopClerkName;
    }

    public String getSysShopClerkId() {
        return sysShopClerkId;
    }

    public void setSysShopClerkId(String sysShopClerkId) {
        this.sysShopClerkId = sysShopClerkId;
    }

    public String getSysShopName() {
        return sysShopName;
    }

    public void setSysShopName(String sysShopName) {
        this.sysShopName = sysShopName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGoodType() {
        return goodType;
    }

    public void setGoodType(String goodType) {
        this.goodType = goodType;
    }

    //操作门店
    private String sysShopName;
    //类型
    private String type;
    private String goodType;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

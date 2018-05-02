package com.wisdom.beauty.api.responseDto;


import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;

/**
 * ClassName: UserConsumeRequestDTO
 *
 * @Author： zhanghuan
 * @Description: 消费记录请求参数对象
 * @Date:Created in 2018/4/8 11:00
 * @since JDK 1.8
 */
public class UserConsumeRequestDTO extends BaseEntity  {

    /**
     * 用户表主键
     */
    private String sysUserId;

    /**
     * 0、商品类型为次卡；1、商品类型为疗程卡 ；2、商品类型为充值卡；3、商品类型为套卡 4、商品类型为产品
     */
    private String goodsType;

    /**
     * 0：充值  1：消费 2、还欠款 3、退款 4、划卡
     */
    private String consumeType;

    /**
     * BossId
     */
    private String sysBossId;

    /**
     * 美容院表主键
     */
    private String sysShopId;

    /**
     * 美容院名称
     */
    private String sysShopName;

    /**
     * 店员表主键
     */
    private String sysClerkId;
    /**
     * 请求来源，用于区分是否需要设置goodsType条件
     */
    private Boolean goodsTypeRequire;
    private int pageSize ; //每页的条目

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(String consumeType) {
        this.consumeType = consumeType;
    }

    public String getSysBossId() {
        return sysBossId;
    }

    public void setSysBossId(String sysBossId) {
        this.sysBossId = sysBossId;
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

    public String getSysClerkId() {
        return sysClerkId;
    }

    public void setSysClerkId(String sysClerkId) {
        this.sysClerkId = sysClerkId;
    }

    public Boolean getGoodsTypeRequire() {
        return goodsTypeRequire;
    }

    public void setGoodsTypeRequire(Boolean goodsTypeRequire) {
        this.goodsTypeRequire = goodsTypeRequire;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

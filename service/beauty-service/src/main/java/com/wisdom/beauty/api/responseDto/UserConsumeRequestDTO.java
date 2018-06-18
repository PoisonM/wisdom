package com.wisdom.beauty.api.responseDto;


import com.wisdom.common.entity.BaseEntity;

import java.util.List;

/**
 * ClassName: UserConsumeRequestDTO
 *
 * @Author： zhanghuan
 * @Description: 消费记录请求参数对象
 * @Date:Created in 2018/4/8 11:00
 * @since JDK 1.8
 */
public class UserConsumeRequestDTO extends BaseEntity  {

    private String id;
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
    private String sysBossCode;

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
    private Boolean goodsTypeRequire=false;
    /**
     * 是否需要去重
     */
    private Boolean disticRequire=false;
    private int pageSize ; //每页的条目
    //单次卡为当前用户与项目的关系 id ,疗程卡也为当前项目跟用户的关系id,用户与套卡跟项目的关系表的关系表,产品为当前产品跟用户的关系 id ,充值卡为用户与的当前充值卡关系的id
    private String flowId;
    private List<String> flowIds;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getSysBossCode() {
        return sysBossCode;
    }

    public void setSysBossCode(String sysBossCode) {
        this.sysBossCode = sysBossCode;
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

    public Boolean getDisticRequire() {
        return disticRequire;
    }

    public void setDisticRequire(Boolean disticRequire) {
        this.disticRequire = disticRequire;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public List<String> getFlowIds() {
        return flowIds;
    }

    public void setFlowIds(List<String> flowIds) {
        this.flowIds = flowIds;
    }
}

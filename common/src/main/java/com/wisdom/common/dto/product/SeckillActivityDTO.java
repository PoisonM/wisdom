package com.wisdom.common.dto.product;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class SeckillActivityDTO<T> {

    /**
     * 活动id
     */
    @JSONField(name = "id")
    private String id;

    /**
     * 活动编码
     */
    @JSONField(name = "activityNo")
    private String activityNo;

    /**
     * 活动名称
     */
    @JSONField(name = "activityName")
    private String activityName;

    /**
     * 产品的id
     */
    @JSONField(name = "productId")
    private String productId;

    /**
     * 活动开始时间
     */
    @JSONField(name = "startTime")
    private Date startTime;

    /**
     * 活动结束时间
     */
    @JSONField(name = "endTime")
    private Date endTime;

    /**
     * 优惠价格
     */
    @JSONField(name = "favorablePrice")
    private float favorablePrice;


    /**
     * 创建时间
     */
    @JSONField(name = "createTime")
    private Date createTime;

    /**
     * 更新时间
     */
    @JSONField(name = "updateTime")
    private Date updateTime;


    /**
     * 活动场次数量
     */
    @JSONField(name = "activityNum")
    private int activityNum;

    /**
     * 单次下单最大数量
     */
    @JSONField(name = "productNum")
    private String productNum;

    /**
     * 是否开启
     */
    @JSONField(name = "isEnable")
    private int isEnable;

    /**
     * 产品价格
     */
    @JSONField(name = "createBy")
    private String createBy;

    /**
     * 活动状态
     **/
    private String activityStatus;

    /**
     * 距离活动天数
     * */
    private long activityDays;


    private int pageNo;

    private int pageSize;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityNo() {
        return activityNo;
    }

    public void setActivityNo(String activityNo) {
        this.activityNo = activityNo;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public float getFavorablePrice() {
        return favorablePrice;
    }

    public void setFavorablePrice(float favorablePrice) {
        this.favorablePrice = favorablePrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getActivityNum() {
        return activityNum;
    }

    public void setActivityNum(int activityNum) {
        this.activityNum = activityNum;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }


    public long getActivityDays() {
        return activityDays;
    }

    public void setActivityDays(long activityDays) {
        this.activityDays = activityDays;
    }
}
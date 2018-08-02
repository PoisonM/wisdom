package com.wisdom.common.dto.product;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class SeckillActivityFieldDTO {

    /**
     * 场次id
     */
    @JSONField(name = "id")
    private Integer id;

    /**
     * 活动id
     */
    @JSONField(name = "activityId")
    private Integer activityId;

    /**
     * 销售数量
     */
    @JSONField(name = "productAmount")
    private Integer productAmount;


    /**
     * 活动开始时间
     */
    @JSONField(name = "startTime")
    private Date startTime;

    /**
     * 活动开始时间
     */
    private String  startTimeString;


    /**
     * 活动结束时间
     */
    @JSONField(name = "endTime")
    private Date endTime;

    private String  endTimeString;

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

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Integer productAmount) {
        this.productAmount = productAmount;
    }

    public String getStartTimeString() {
        return startTimeString;
    }

    public void setStartTimeString(String startTimeString) {
        this.startTimeString = startTimeString;
    }

    public String getEndTimeString() {
        return endTimeString;
    }

    public void setEndTimeString(String endTimeString) {
        this.endTimeString = endTimeString;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
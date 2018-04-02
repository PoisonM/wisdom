package com.wisdom.common.dto.beauty;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class ShopMemberAttendacneDTO {

    @JSONField(name = "shopId")
    private String shopId;

    @JSONField(name = "shopName")
    private String shopName;

    @JSONField(name = "clerkId")
    private String clerkId;

    @JSONField(name = "clerkName")
    private String clerkName;

    @JSONField(name = "clerkType")
    private String clerkType;

    @JSONField(name = "firstTime")
    private Date firstTime;

    @JSONField(name = "lastTime")
    private Date lastTime;

    @JSONField(name = "attendanceStatus")
    private String attendanceStatus;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getClerkId() {
        return clerkId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
    }

    public String getClerkName() {
        return clerkName;
    }

    public void setClerkName(String clerkName) {
        this.clerkName = clerkName;
    }

    public String getClerkType() {
        return clerkType;
    }

    public void setClerkType(String clerkType) {
        this.clerkType = clerkType;
    }

    public Date getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }
}

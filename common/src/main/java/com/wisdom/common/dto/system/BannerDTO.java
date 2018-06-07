package com.wisdom.common.dto.system;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by zbm84 on 2017/8/3.
 */
public class BannerDTO {

    //bannerId
    @JSONField(name = "bannerId")
    private String bannerId;

    //banner图片连接
    @JSONField(name = "uri")
    private String uri;

    //跳转连接
    @JSONField(name = "forward")
    private String forward;

    @JSONField(name = "place")
    private String place;

    @JSONField(name = "bannerType")
    private String bannerType;

    @JSONField(name = "status")
    private String status;

    /**
     *banner排序
     */
    @JSONField(name = "bannerRank")
    private int bannerRank;

    /**
     * 创建时间
     */
    @JSONField(name = "createDate")
    private String createDate;


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getBannerType() {
        return bannerType;
    }

    public void setBannerType(String bannerType) {
        this.bannerType = bannerType;
    }

    public int getBannerRank() {
        return bannerRank;
    }

    public void setBannerRank(int bannerRank) {
        this.bannerRank = bannerRank;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}

package com.wisdom.beauty.api.responseDto;

import com.wisdom.common.entity.BaseEntity;

import java.util.Date;

/**
 * Created by zhanghuan on 2018/5/15.
 */
public class UserInfoDTOResponseDTO extends BaseEntity {
    private String photo;
    private String nickname;
    private Date lastArriveTime;
    private Boolean newUser;
    /**
     * 到店次数，标记每个用户的到店次数
     */
    private Integer  useArriveShopTime;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getLastArriveTime() {
        return lastArriveTime;
    }

    public void setLastArriveTime(Date lastArriveTime) {
        this.lastArriveTime = lastArriveTime;
    }

    public Boolean getNewUser() {
        return newUser;
    }

    public void setNewUser(Boolean newUser) {
        this.newUser = newUser;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    private String arriveTime;

    public Integer getUseArriveShopTime() {
        return useArriveShopTime;
    }

    public void setUseArriveShopTime(Integer useArriveShopTime) {
        this.useArriveShopTime = useArriveShopTime;
    }
}

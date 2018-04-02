package com.wisdom.common.dto.wexin;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by zbm84 on 2017/6/7.
 */
public class WeixinAttentionDTO {

    @JSONField(name = "openid")
    private String openid;

    @JSONField(name = "parentUserId")
    private String parentUserId;

    @JSONField(name = "date")
    private Date date;

    @JSONField(name = "status")
    private String status;

    @JSONField(name = "nickname")
    private String nickname;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(String parentUserId) {
        this.parentUserId = parentUserId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

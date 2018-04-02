package com.wisdom.common.dto.wexin;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by zbm84 on 2017/6/7.
 */
public class WeixinTokenDTO {

    @JSONField(name = "token")
    private String token;

    @JSONField(name = "ticket")
    private String ticket;

    @JSONField(name = "updateDate")
    private Date updateDate;

    @JSONField(name = "weixinFlag")
    private String weixinFlag;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getWeixinFlag() {
        return weixinFlag;
    }

    public void setWeixinFlag(String weixinFlag) {
        this.weixinFlag = weixinFlag;
    }
}

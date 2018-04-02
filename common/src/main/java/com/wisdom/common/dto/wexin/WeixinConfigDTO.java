package com.wisdom.common.dto.wexin;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by zbm84 on 2017/6/7.
 */
public class WeixinConfigDTO {

    @JSONField(name = "url")
    private String url;

    @JSONField(name = "jsapiTicket")
    private String jsapiTicket;

    @JSONField(name = "nonceStr")
    private String nonceStr;

    @JSONField(name = "timestamp")
    private String timestamp;

    @JSONField(name = "signature")
    private String signature;

    @JSONField(name = "appid")
    private String appid;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJsapiTicket() {
        return jsapiTicket;
    }

    public void setJsapiTicket(String jsapiTicket) {
        this.jsapiTicket = jsapiTicket;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}

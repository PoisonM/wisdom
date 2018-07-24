package com.wisdom.search.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by cjk on 2017/5/24.
 */
public class PressureLoginDTO {

    @JSONField(name = "inputUserMobile")
    private String inputUserMobile;

    @JSONField(name = "inputUserOpenId")
    private String inputUserOpenId;

    @JSONField(name = "loginToken")
    private String loginToken;

    @JSONField(name = "loginUserMobile")
    private String loginUserMobile;

    @JSONField(name = "loginUserOpenId")
    private String loginUserOpenId;

    public String getInputUserMobile() {
        return inputUserMobile;
    }

    public void setInputUserMobile(String inputUserMobile) {
        this.inputUserMobile = inputUserMobile;
    }

    public String getInputUserOpenId() {
        return inputUserOpenId;
    }

    public void setInputUserOpenId(String inputUserOpenId) {
        this.inputUserOpenId = inputUserOpenId;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getLoginUserMobile() {
        return loginUserMobile;
    }

    public void setLoginUserMobile(String loginUserMobile) {
        this.loginUserMobile = loginUserMobile;
    }

    public String getLoginUserOpenId() {
        return loginUserOpenId;
    }

    public void setLoginUserOpenId(String loginUserOpenId) {
        this.loginUserOpenId = loginUserOpenId;
    }
}

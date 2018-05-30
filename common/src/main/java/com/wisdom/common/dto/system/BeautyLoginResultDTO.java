package com.wisdom.common.dto.system;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by cjk on 2017/5/24.
 */
public class BeautyLoginResultDTO {

    @JSONField(name = "beautyUserLoginToken")
    private String beautyUserLoginToken;

    @JSONField(name = "beautyBossLoginToken")
    private String beautyBossLoginToken;

    @JSONField(name = "beautyClerkLoginToken")
    private String beautyClerkLoginToken;

    @JSONField(name = "result")
    private String result;

    public String getBeautyUserLoginToken() {
        return beautyUserLoginToken;
    }

    public void setBeautyUserLoginToken(String beautyUserLoginToken) {
        this.beautyUserLoginToken = beautyUserLoginToken;
    }

    public String getBeautyBossLoginToken() {
        return beautyBossLoginToken;
    }

    public void setBeautyBossLoginToken(String beautyBossLoginToken) {
        this.beautyBossLoginToken = beautyBossLoginToken;
    }

    public String getBeautyClerkLoginToken() {
        return beautyClerkLoginToken;
    }

    public void setBeautyClerkLoginToken(String beautyClerkLoginToken) {
        this.beautyClerkLoginToken = beautyClerkLoginToken;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

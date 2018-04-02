/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.common.dto.account;


/**
 * 工作流Entity
 *
 * @author 得良
 * @version 2013-11-03
 */
public class PrePayInfoDTO {

    private String appId;//公众号ID

    private String timeStamp;//商户号

    private String nonceStr;//设备号

    private String packageData;//随即字符串

    private String signType;//商品描述

    private String paySign;//商品详情

    private int agent;//附件数据

    private String result;//结果情况

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackageData() {
        return packageData;
    }

    public void setPackageData(String packageData) {
        this.packageData = packageData;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public int getAgent() {
        return agent;
    }

    public void setAgent(int agent) {
        this.agent = agent;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}



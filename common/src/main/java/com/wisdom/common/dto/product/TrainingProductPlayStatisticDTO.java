package com.wisdom.common.dto.product;

import com.alibaba.fastjson.annotation.JSONField;

public class TrainingProductPlayStatisticDTO {

    @JSONField(name = "productId")
    private String productId;

    @JSONField(name = "url")
    private String url;

    @JSONField(name = "playNum")
    private int playNum;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPlayNum() {
        return playNum;
    }

    public void setPlayNum(int playNum) {
        this.playNum = playNum;
    }
}
package com.wisdom.common.dto.transaction;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by cjk on 2017/5/24.
 */
public class BonusFlagDTO {

    @JSONField(name = "productId")
    private String productId;

    @JSONField(name = "userId")
    private String userId;

    @JSONField(name = "messageFlag")
    private String messageFlag;

    @JSONField(name = "bonusFlag")
    private String bonusFlag;

    @JSONField(name = "bonusEndDate")
    private Date bonusEndDate;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessageFlag() {
        return messageFlag;
    }

    public void setMessageFlag(String messageFlag) {
        this.messageFlag = messageFlag;
    }

    public String getBonusFlag() {
        return bonusFlag;
    }

    public void setBonusFlag(String bonusFlag) {
        this.bonusFlag = bonusFlag;
    }

    public Date getBonusEndDate() {
        return bonusEndDate;
    }

    public void setBonusEndDate(Date bonusEndDate) {
        this.bonusEndDate = bonusEndDate;
    }
}

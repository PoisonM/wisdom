package com.wisdom.common.dto.transaction;

import com.wisdom.common.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;

public class PromotionTransactionRelation extends BaseEntity implements Serializable {
    //
    private String promotionLevelId;

    //
    private String transactionId;

    //
    private String promotionLevel;

    //
    private Date promotionLevelTime;

    //
    private String sysUserId;

    //
    private String incomeId;

    private static final long serialVersionUID = 1L;

    public String getPromotionLevelId() {
        return promotionLevelId;
    }

    public void setPromotionLevelId(String promotionLevelId) {
        this.promotionLevelId = promotionLevelId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPromotionLevel() {
        return promotionLevel;
    }

    public void setPromotionLevel(String promotionLevel) {
        this.promotionLevel = promotionLevel;
    }

    public Date getPromotionLevelTime() {
        return promotionLevelTime;
    }

    public void setPromotionLevelTime(Date promotionLevelTime) {
        this.promotionLevelTime = promotionLevelTime;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(String incomeId) {
        this.incomeId = incomeId;
    }
}
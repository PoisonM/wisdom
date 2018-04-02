package com.wisdom.common.dto.transaction;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */
public class TripleMonthBonusDTO {

    @JSONField(name ="userId")
    private String userId;

    @JSONField(name ="leftDay")
    private String leftDay;

    @JSONField(name ="productId")
    private String productId;

    @JSONField(name ="bonusRecords")
    private List<BonusRecordDTO> bonusRecords;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLeftDay() {
        return leftDay;
    }

    public void setLeftDay(String leftDay) {
        this.leftDay = leftDay;
    }

    public List<BonusRecordDTO> getBonusRecords() {
        return bonusRecords;
    }

    public void setBonusRecords(List<BonusRecordDTO> bonusRecords) {
        this.bonusRecords = bonusRecords;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}

package com.wisdom.beauty.api.extDto;

import com.wisdom.common.entity.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ShopUserConsumeDTO<T> extends BaseEntity implements Serializable {

    //关系表id
    private String consumeId;

    //关联员工
    private String clerkId;

    private String sysUserId;

    //消耗金额
    private BigDecimal consumePrice;

    //消耗数量
    private int consumeNum;

    private T shopUserConsumeDTO;

    public T getT() {
        return shopUserConsumeDTO;
    }

    public void setT(T t) {
        this.shopUserConsumeDTO = t;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getConsumeId() {
        return consumeId;
    }

    public void setConsumeId(String consumeId) {
        this.consumeId = consumeId;
    }

    public String getClerkId() {
        return clerkId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
    }


    public int getConsumeNum() {
        return consumeNum;
    }

    public void setConsumeNum(int consumeNum) {
        this.consumeNum = consumeNum;
    }

    public BigDecimal getConsumePrice() {
        return consumePrice;
    }

    public void setConsumePrice(BigDecimal consumePrice) {
        this.consumePrice = consumePrice;
    }
}
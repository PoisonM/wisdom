package com.wisdom.common.dto.transaction;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by zbm84 on 2017/8/3.
 */
public class ConfirmReceiveProductSignalDTO {

    @JSONField(name = "outTradeNo")
    private String outTradeNo;

    @JSONField(name = "sysUserId")
    private String sysUserId;

    @JSONField(name = "returnMoney")
    private float returnMoney;

    @JSONField(name = "finish")
    private boolean finish;

    @JSONField(name = "createDate")
    private Date createDate;

    @JSONField(name = "updateDate")
    private Date updateDate;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public float getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(float returnMoney) {
        this.returnMoney = returnMoney;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}

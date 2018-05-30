package com.wisdom.common.dto.product;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class OfflineProductAmountRecordDTO {

    @JSONField(name = "productId")
    private String productId;

    @JSONField(name = "orderId")
    private String orderId;

    /**
     * 新库存
     */
    @JSONField(name = "newProductAmount")
    private String newProductAmount;

    /**
     * 旧库存
     */
    @JSONField(name = "oldProductAmount")
    private String oldProductAmount;

    /**
     * 修改个数
     */
    @JSONField(name = "updateAmount")
    private String updateAmount;

    /**
     * 增加还是减少
     */
    @JSONField(name = "addAndLose")
    private String addAndLose;

    /**
     * 生成时间
     */
    @JSONField(name = "createTime")
    private Date createTime;

    @JSONField(name = "tag")
    private String tag;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getNewProductAmount() {
        return newProductAmount;
    }

    public void setNewProductAmount(String newProductAmount) {
        this.newProductAmount = newProductAmount;
    }

    public String getOldProductAmount() {
        return oldProductAmount;
    }

    public void setOldProductAmount(String oldProductAmount) {
        this.oldProductAmount = oldProductAmount;
    }

    public String getUpdateAmount() {
        return updateAmount;
    }

    public void setUpdateAmount(String updateAmount) {
        this.updateAmount = updateAmount;
    }

    public String getAddAndLose() {
        return addAndLose;
    }

    public void setAddAndLose(String addAndLose) {
        this.addAndLose = addAndLose;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
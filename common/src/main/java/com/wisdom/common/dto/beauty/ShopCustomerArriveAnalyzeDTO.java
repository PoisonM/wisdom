package com.wisdom.common.dto.beauty;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

public class ShopCustomerArriveAnalyzeDTO {

    @JSONField(name = "shopId")
    private String shopId;

    @JSONField(name = "date")
    private Date date;

    @JSONField(name = "customerArrives")
    private List<CustomerArriveDTO> customerArrives;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<CustomerArriveDTO> getCustomerArrives() {
        return customerArrives;
    }

    public void setCustomerArrives(List<CustomerArriveDTO> customerArrives) {
        this.customerArrives = customerArrives;
    }
}

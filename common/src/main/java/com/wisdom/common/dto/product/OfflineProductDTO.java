package com.wisdom.common.dto.product;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class OfflineProductDTO {

    @JSONField(name = "productId")
    private String productId;

    @JSONField(name = "tag")
    private List<String> tag;

    @JSONField(name = "listPic")
    private List<String> listPic;

    @JSONField(name = "services")
    private List<String> services;

    @JSONField(name = "spec")
    private List<String> spec;

    @JSONField(name = "detailPic")
    private List<String> detailPic;

    @JSONField(name = "senderAddress")
    private String senderAddress;
    //展示销量
    @JSONField(name = "productSalesVolume")
    private String productSalesVolume;
    //零售价
    @JSONField(name = "productMarketPrice")
    private String productMarketPrice;

    @JSONField(name = "productAmount")
    private int productAmount;

    /**
     * 下架时间
     */
    @JSONField(name = "soldOutTime")
    private String soldOutTime;

    /**
     * 当前时间
     */
    @JSONField(name = "nowTime")
    private String nowTime;


    public String getSoldOutTime() {
        return soldOutTime;
    }

    public void setSoldOutTime(String soldOutTime) {
        this.soldOutTime = soldOutTime;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public List<String> getListPic() {
        return listPic;
    }

    public void setListPic(List<String> listPic) {
        this.listPic = listPic;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public List<String> getSpec() {
        return spec;
    }

    public void setSpec(List<String> spec) {
        this.spec = spec;
    }

    public List<String> getDetailPic() {
        return detailPic;
    }

    public void setDetailPic(List<String> detailPic) {
        this.detailPic = detailPic;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {

        this.senderAddress = senderAddress;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductMarketPrice() {
        return productMarketPrice;
    }

    public void setProductMarketPrice(String productMarketPrice) {
        this.productMarketPrice = productMarketPrice;
    }

    public String getProductSalesVolume() {
        return productSalesVolume;
    }

    public void setProductSalesVolume(String productSalesVolume) {
        this.productSalesVolume = productSalesVolume;
    }
}
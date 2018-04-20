package com.wisdom.beauty.api.responseDto;

import java.util.Map;

/**
 * ClassName: ShopRechargeCardResponseDTO
 *
 * @Author： huan
 * @Description: 充值卡返回结果
 * @Date:Created in 2018/4/16 16:41
 * @since JDK 1.8
 */
public class ShopRechargeCardResponseDTO {
    //充值卡主键
    private String shopRechargeCardId;
    //充值卡名称
    private String name;
    //充值面额
    private Long amount;
    //图片
    private String imageUrl;
    //折扣
    private Map<String,Object> map;

    //折扣描述
    private String discountDesc;
    //介绍
    private String introduce;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDiscountDesc() {
        return discountDesc;
    }

    public void setDiscountDesc(String discountDesc) {
        this.discountDesc = discountDesc;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getShopRechargeCardId() {
        return shopRechargeCardId;
    }

    public void setShopRechargeCardId(String shopRechargeCardId) {
        this.shopRechargeCardId = shopRechargeCardId;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}

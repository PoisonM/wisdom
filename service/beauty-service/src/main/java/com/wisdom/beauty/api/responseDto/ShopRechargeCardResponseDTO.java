package com.wisdom.beauty.api.responseDto;

import com.wisdom.beauty.api.dto.ShopRechargeCardDTO;

import java.util.List;

/**
 * ClassName: ShopRechargeCardResponseDTO
 *
 * @Author： huan
 * @Description: 充值卡返回结果
 * @Date:Created in 2018/4/16 16:41
 * @since JDK 1.8
 */
public class ShopRechargeCardResponseDTO extends ShopRechargeCardDTO {

    //图片
    private List<String> imageUrls;

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}

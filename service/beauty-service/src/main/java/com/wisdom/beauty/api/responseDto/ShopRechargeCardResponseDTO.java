package com.wisdom.beauty.api.responseDto;

import com.wisdom.beauty.api.dto.ShopRechargeCardDTO;

import java.util.List;

/**
 * ClassName: ShopRechargeCardResponseDTO
 *
 * @Author： huan
 * 
 * @Description: 充值卡返回结果
 * @Date:Created in 2018/4/16 16:41
 * @since JDK 1.8
 */
public class ShopRechargeCardResponseDTO extends ShopRechargeCardDTO {

	// 图片
	private List<String> imageList;

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}
}

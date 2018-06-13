package com.wisdom.beauty.api.responseDto;

import com.wisdom.beauty.api.dto.ShopProductInfoDTO;

import java.util.List;

/**
 * Created by zhanghuan on 2018/4/28.
 */
public class ShopProductInfoResponseDTO extends ShopProductInfoDTO {

	/** 图片地址id */
	private List<String> imageUrl;

	public List<String> getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(List<String> imageUrl) {
		this.imageUrl = imageUrl;
	}
}

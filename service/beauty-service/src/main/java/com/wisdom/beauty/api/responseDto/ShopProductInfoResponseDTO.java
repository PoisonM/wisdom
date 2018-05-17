package com.wisdom.beauty.api.responseDto;

import com.wisdom.beauty.api.dto.ShopProductInfoDTO;
import com.wisdom.common.entity.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by zhanghuan on 2018/4/28.
 */
public class ShopProductInfoResponseDTO extends ShopProductInfoDTO {

	/** 图片地址id */
	private String[] imageUrl;

	public String[] getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String[] imageUrl) {
		this.imageUrl = imageUrl;
	}
}

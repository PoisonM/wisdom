package com.wisdom.beauty.api.responseDto;

import com.wisdom.beauty.api.dto.ShopProjectInfoDTO;

import java.util.List;

/**
 * Created by zhanghuan on 2018/4/28.
 */
public class ShopProjectInfoResponseDTO extends ShopProjectInfoDTO {

	private List<String> imageList;

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}
}

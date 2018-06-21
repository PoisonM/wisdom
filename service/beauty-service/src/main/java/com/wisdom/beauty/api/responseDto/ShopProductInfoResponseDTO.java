package com.wisdom.beauty.api.responseDto;

import com.wisdom.beauty.api.dto.ShopProductInfoDTO;

import java.util.List;

/**
 * Created by zhanghuan on 2018/4/28.
 *
 */
public class ShopProductInfoResponseDTO extends ShopProductInfoDTO {

	/** 图片地址id */
	private List<String> imageList;
	/** 库存数量 */
	private Integer stockNumber;


	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}

	public Integer getStockNumber() {
		return stockNumber;
	}

	public void setStockNumber(Integer stockNumber) {
		this.stockNumber = stockNumber;
	}
}

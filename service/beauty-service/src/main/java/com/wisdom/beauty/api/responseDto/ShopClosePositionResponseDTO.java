package com.wisdom.beauty.api.responseDto;

import com.wisdom.beauty.api.dto.ShopClosePositionRecordDTO;

/**
 * Created by Administrator on 2018/5/21.
 */
public class ShopClosePositionResponseDTO extends ShopClosePositionRecordDTO {
	/**
	 * 差异数量
	 */
	private Integer differenceNumber;
	/**
	 * 平仓状态
	 */
	private String state;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 产品品牌
	 */
	private String productTypeName;

	public Integer getDifferenceNumber() {
		return differenceNumber;
	}

	public void setDifferenceNumber(Integer differenceNumber) {
		this.differenceNumber = differenceNumber;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }
}

package com.wisdom.beauty.api.responseDto;

import java.math.BigDecimal;

/**
 * Created by zhanghuan on 2018/6/19.
 */
public class ShopChannelResponseDTO {
	private String channelName;
	private Integer channelPeopleNumber;
	private Integer channelPeopleProportionr;

	public Integer getChannelPeopleProportionr() {
		return channelPeopleProportionr;
	}

	public void setChannelPeopleProportionr(Integer channelPeopleProportionr) {
		this.channelPeopleProportionr = channelPeopleProportionr;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Integer getChannelPeopleNumber() {
		return channelPeopleNumber;
	}

	public void setChannelPeopleNumber(Integer channelPeopleNumber) {
		this.channelPeopleNumber = channelPeopleNumber;
	}

}

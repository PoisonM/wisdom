package com.wisdom.beauty.api.enums;

/**
 * Created by zhanghuan on 2018/5/21.
 */
public enum ClosePositionTypeEnum {
	CLOSE_POSITION_YES("0", "平仓"),
	CLOSE_POSITION_NO("1", "未平仓"),
	INVENTORY_PROFIT("2", "盘盈"),
	INVENTORY_LOSS("3", "盘亏");

	ClosePositionTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private String code;
	private String desc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}

package com.wisdom.beauty.api.enums;

/**
 * Created by zhanghuan on 2018/5/29.
 */
public enum IsUseUpEnum {
	// 是否被使用完，0已用完 1使用中
	USE_UP("0", "已用完"), USE_ING("1", "使用中");

	IsUseUpEnum(String code, String desc) {
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

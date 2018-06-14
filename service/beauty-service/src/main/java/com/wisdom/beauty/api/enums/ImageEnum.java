package com.wisdom.beauty.api.enums;

public enum ImageEnum {

	USER_HEAD("头像", "https://mx-beauty.oss-cn-beijing.aliyuncs.com/%E5%A4%B4%E5%83%8F.png"),
	SPECIAL_CARD("特殊充值卡", "https://mx-beauty.oss-cn-beijing.aliyuncs.com/%E7%89%B9%E6%AE%8A%E5%85%85%E5%80%BC%E5%8D%A1.png"),
	COMMON_CARD("普通充值卡","https://mx-beauty.oss-cn-beijing.aliyuncs.com/%E6%99%AE%E9%80%9A%E5%8D%A1%E5%85%85%E5%80%BC%E5%8D%A1.png"),
	GOODS_CARD("价目表","https://mx-beauty.oss-cn-beijing.aliyuncs.com/%E4%BB%B7%E7%9B%AE%E8%A1%A8.png"),
	;

	ImageEnum(String code, String desc) {
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

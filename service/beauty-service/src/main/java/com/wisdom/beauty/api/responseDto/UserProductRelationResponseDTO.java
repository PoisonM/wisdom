package com.wisdom.beauty.api.responseDto;

import com.wisdom.common.entity.BaseEntity;

/**
 * ClassName: UserProductRelationResponseDTO
 *
 * @Author： huan
 * 
 * @Description:
 * @Date:Created in 2018/4/18 11:22
 * @since JDK 1.8
 */
public class UserProductRelationResponseDTO extends BaseEntity {

	/**
	 * 未领取数量
	 */
	private Integer waitReceiveNumber;
	/**
	 * 用户id
	 */
	private String sysUserId;
	/**
	 * 姓名
	 */
	private String nickname;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 一级类别名称
	 */
	private String productTypeOneName;
	/**
	 * 二级类别名称
	 */
	private String productTypeTwoName;
	/**
	 * 规格
	 */
	private String productSpec;

	public UserProductRelationResponseDTO() {
	}

	public Integer getWaitReceiveNumber() {
		return waitReceiveNumber;
	}

	public void setWaitReceiveNumber(Integer waitReceiveNumber) {
		this.waitReceiveNumber = waitReceiveNumber;
	}

	public String getSysUserId() {
		return sysUserId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductTypeOneName() {
		return productTypeOneName;
	}

	public void setProductTypeOneName(String productTypeOneName) {
		this.productTypeOneName = productTypeOneName;
	}

	public String getProductTypeTwoName() {
		return productTypeTwoName;
	}

	public void setProductTypeTwoName(String productTypeTwoName) {
		this.productTypeTwoName = productTypeTwoName;
	}

	public String getProductSpec() {
		return productSpec;
	}

	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

}

package com.wisdom.beauty.api.responseDto;

import com.wisdom.beauty.api.dto.ShopProjectInfoDTO;
import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;
import com.wisdom.common.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * ClassName: UserConsumeRecordResponseDTO
 *
 * @Author： huan
 * 
 * @Description:
 * @Date:Created in 2018/4/9 19:26
 * @since JDK 1.8
 */
public class UserConsumeRecordResponseDTO extends ShopUserConsumeRecordDTO {
	//总金额
	private BigDecimal sumAmount;
	//划卡和消费页面展示的名称
	private String title;
	// 前台
	private String sysShopClerkName;
	// 前台id
	private String sysShopClerkId;
	// 用于返回前端判断消费还是充值字段
	private String type;
	//最多包含次数
	private Integer includeTimes;
    //包含项目
	private List<ShopProjectInfoDTO> shopProjectInfoDTOList;
	private List<UserConsumeRecordResponseDTO> userConsumeRecordResponseList;
	//店员集合
	private List<String> sysClerkNameList;

	public String getSysShopClerkName() {
		return sysShopClerkName;
	}

	public void setSysShopClerkName(String sysShopClerkName) {
		this.sysShopClerkName = sysShopClerkName;
	}


	public String getSysShopClerkId() {
		return sysShopClerkId;
	}

	public void setSysShopClerkId(String sysShopClerkId) {
		this.sysShopClerkId = sysShopClerkId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    public List<UserConsumeRecordResponseDTO> getUserConsumeRecordResponseList() {
        return userConsumeRecordResponseList;
    }

    public void setUserConsumeRecordResponseList(List<UserConsumeRecordResponseDTO> userConsumeRecordResponseList) {
        this.userConsumeRecordResponseList = userConsumeRecordResponseList;
    }

	public BigDecimal getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(BigDecimal sumAmount) {
		this.sumAmount = sumAmount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public List<ShopProjectInfoDTO> getShopProjectInfoDTOList() {
		return shopProjectInfoDTOList;
	}

	public void setShopProjectInfoDTOList(List<ShopProjectInfoDTO> shopProjectInfoDTOList) {
		this.shopProjectInfoDTOList = shopProjectInfoDTOList;
	}

	public Integer getIncludeTimes() {
		return includeTimes;
	}

	public void setIncludeTimes(Integer includeTimes) {
		this.includeTimes = includeTimes;
	}

	public List<String> getSysClerkNameList() {
		return sysClerkNameList;
	}

	public void setSysClerkNameList(List<String> sysClerkNameList) {
		this.sysClerkNameList = sysClerkNameList;
	}
}

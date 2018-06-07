package com.wisdom.beauty.api.responseDto;

import com.wisdom.beauty.api.dto.ShopProjectGroupDTO;
import com.wisdom.beauty.api.dto.ShopProjectInfoDTO;

import java.util.List;

/**
 * ClassName: ProjectInfoGroupResponseDTO
 *
 * @Author： huan
 * 
 * @Description:
 * @Date:Created in 2018/4/11 17:44
 * @since JDK 1.8
 */
public class ProjectInfoGroupResponseDTO extends ShopProjectGroupDTO {


	/** 项目List */
	private List<ShopProjectInfoDTO> shopProjectInfoDTOS;
	/** 图片地址URL */
    private List<String> imageList;
	/** 是否过期 */
	private  Boolean  overdue;
	/** 是否启用 */
	private  String  status;

	public List<ShopProjectInfoDTO> getShopProjectInfoDTOS() {
		return shopProjectInfoDTOS;
	}

	public void setShopProjectInfoDTOS(List<ShopProjectInfoDTO> shopProjectInfoDTOS) {
		this.shopProjectInfoDTOS = shopProjectInfoDTOS;
	}

	public Boolean getOverdue() {
		return overdue;
	}

	public void setOverdue(Boolean overdue) {
		this.overdue = overdue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}
}

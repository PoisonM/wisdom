package com.wisdom.system.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.BannerDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.system.service.BannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping(value = "banner")
public class BannerController {
	Logger logger = LoggerFactory.getLogger(BannerController.class);
	@Autowired
	private BannerService bannerService;

	/**
	 * 获取 Banner 图
	 *
			 */
	@RequestMapping(value = "getHomeBannerList", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<List<BannerDTO>> getHomeBannerList() {
		long startTime = System.currentTimeMillis();
		logger.info("获取 Banner 图==={}开始" , startTime);
		ResponseDTO responseDto=new ResponseDTO<>();
		List<BannerDTO> list = bannerService.getHomeBannerList();
		logger.info("获取 Banner 图List==={}" , list.size());
		if(list.size()>0){
			responseDto.setResponseData(list);
			responseDto.setResult(StatusConstant.SUCCESS);
		}else{
			responseDto.setResult(StatusConstant.FAILURE);
		}
		logger.info("获取 Banner 图,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDto;
	}

}

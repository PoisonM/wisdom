package com.wisdom.beauty.controller.analyze;

import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.beauty.core.service.ShopStatisticsAnalysisService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.beauty.ShopCustomerArriveAnalyzeDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@LoginAnnotations
@RequestMapping(value = "analyze")
public class CustomerArriveStatisticController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShopStatisticsAnalysisService shopStatisticsAnalysisService;

	//获取门店某天的业绩
	@RequestMapping(value = "customerArriveAnalyzeByDate", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopCustomerArriveAnalyzeDTO>> customerArriveAnalyzeByDate(@RequestParam String date) {

		ResponseDTO<List<ShopCustomerArriveAnalyzeDTO>> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}
	/**
	*@Author:zhanghuan
	*@Param:
	*@Return:
	*@Description:  获取所有美容院的顾客到店情况
	*@Date:2018/5/15 17:12
	*/
	@RequestMapping(value = "/getCustomerArriveList", method = RequestMethod.GET)
	@ResponseBody
	ResponseDTO<Map<String,Object>> getCustomerArriveList(@RequestParam String startTime,
																			 @RequestParam String endTime) {


		long start = System.currentTimeMillis();
		SysBossDTO bossInfo = UserUtils.getBossInfo();
		PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
		UserConsumeRequestDTO userConsumeRequestDTO = new UserConsumeRequestDTO();

		userConsumeRequestDTO.setSysBossId(bossInfo.getId());
		pageParamVoDTO.setRequestData(userConsumeRequestDTO);
		pageParamVoDTO.setStartTime(startTime);
		pageParamVoDTO.setEndTime(endTime);
		Map<String,Object> map = shopStatisticsAnalysisService.getCustomerArriveList(pageParamVoDTO);
		ResponseDTO<Map<String,Object>> responseDTO = new ResponseDTO<>();
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(map);
		logger.info("getCustomerArriveList方法耗时{}毫秒", (System.currentTimeMillis() - start));
		return responseDTO;
	}
	/**
	*@Author:zhanghuan
	*@Param:
	*@Return:
	*@Description:
	*@Date:2018/5/17 19:54
	*/
	@RequestMapping(value = "/getShopCustomerArriveList", method = RequestMethod.GET)
	@ResponseBody
	ResponseDTO<Map<String,Object>> getShopCustomerArriveList(@RequestParam String sysShopId,
			                                                  @RequestParam String startTime,
														      @RequestParam String endTime,
															  @RequestParam String condition) {

		long start = System.currentTimeMillis();
		PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
		UserConsumeRequestDTO userConsumeRequestDTO = new UserConsumeRequestDTO();
		userConsumeRequestDTO.setSysShopId(sysShopId);
		pageParamVoDTO.setRequestData(userConsumeRequestDTO);
		pageParamVoDTO.setStartTime(startTime);
		pageParamVoDTO.setEndTime(endTime);
		Map<String,Object> map = shopStatisticsAnalysisService.getShopCustomerArriveList(pageParamVoDTO, condition);
		ResponseDTO<Map<String,Object>> responseDTO = new ResponseDTO<>();
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(map);
		logger.info("getShopCustomerArriveList{}毫秒", (System.currentTimeMillis() - start));
		return responseDTO;
	}
}

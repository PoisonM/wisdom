/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.user.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.user.service.ClerkInfoService;
import com.wisdom.user.util.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * FileName: AppointmentServiceImpl
 *
 * @author: 赵得良 Date: 2018/4/3 0003 15:06 Description: 预约相关
 */
@RestController
public class ClerkServiceController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private ClerkInfoService clerkInfoService;

	/**
	 * 获取店员列表信息
	 *
	 * @param shopId
	 * @return
	 */
	@RequestMapping(value = "getClerkInfo", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	List<SysClerkDTO> getClerkInfo(@RequestParam(value = "shopId") String shopId) {

		long startTime = System.currentTimeMillis();
		ResponseDTO<List<SysClerkDTO>> listResponseDTO = new ResponseDTO<>();

		logger.info("获取店员列表信息传入参数shopId = {}", shopId);
		SysClerkDTO SysClerkDTO = new SysClerkDTO();
		SysClerkDTO.setSysShopId(shopId);
		List<SysClerkDTO> clerkInfo = clerkInfoService.getClerkInfo(SysClerkDTO);
		if (CommonUtils.objectIsEmpty(clerkInfo)) {
			logger.info("获取的店员列表信息为空！");
			listResponseDTO.setResult(StatusConstant.SUCCESS);
			return null;
		}
		listResponseDTO.setResponseData(clerkInfo);
		listResponseDTO.setResult(StatusConstant.SUCCESS);

		logger.info("获取店员列表信息耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return clerkInfo;
	}

	/**
	 * 保存店员信息
	 *
	 * @param sysClerkDTO
	 * @return
	 */
	@RequestMapping(value = "saveClerkInfo", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	ResponseDTO<Object>  saveClerkInfo(@RequestBody SysClerkDTO sysClerkDTO) {

		long startTime = System.currentTimeMillis();
		ResponseDTO<List<SysClerkDTO>> listResponseDTO = new ResponseDTO<>();
		clerkInfoService.saveSysClerk(sysClerkDTO);
		ResponseDTO<Object> responseDTO = new ResponseDTO<>();
		responseDTO.setResult(StatusConstant.SUCCESS);
		logger.info("获取店员列表信息耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * @Author:zhanghuan
	 * @Param:
	 * @Return:
	 * @Description: 根据条件获取店员信息
	 * @Date:2018/4/25 18:34
	 */
	@RequestMapping(value = "getClerkInfoList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	List<SysClerkDTO> getClerkInfoList(@RequestParam(required = false) String sysBossId,
										   @RequestParam(required = false) String sysShopId,
			                           @RequestParam(required = false) String startTime,
									   @RequestParam(required = false) String endTime,
									   int pageSize) {

		long time = System.currentTimeMillis();

		ResponseDTO<List<SysClerkDTO>> listResponseDTO = new ResponseDTO<>();
		SysClerkDTO sysClerkDTO = new SysClerkDTO();
		sysClerkDTO.setSysBossCode(sysBossId);
		sysClerkDTO.setSysShopId(sysShopId);
		PageParamVoDTO<SysClerkDTO> pageParamVoDTO = new PageParamVoDTO<>();
		pageParamVoDTO.setRequestData(sysClerkDTO);
		pageParamVoDTO.setPageSize(pageSize);
		pageParamVoDTO.setPageNo(0);
		pageParamVoDTO.setStartTime(startTime);
		pageParamVoDTO.setEndTime(endTime);
		List<SysClerkDTO> clerkInfo = clerkInfoService.getClerkInfoList(pageParamVoDTO);

		listResponseDTO.setResponseData(clerkInfo);
		listResponseDTO.setResult(StatusConstant.SUCCESS);

		logger.info("获取店员列表信息耗时{}毫秒", (System.currentTimeMillis() - time));
		return clerkInfo;
	}

	/**
	 * @Author:zhanghuan
	 * @Param:
	 * @Return:
	 * @Description: 根据clerkId查询店员信息
	 * @Date:2018/4/28 9:40
	 */
	@RequestMapping(value = "/clerkInfo/{clerkId}", method = RequestMethod.GET)
	@ResponseBody
	List<SysClerkDTO> getClerkInfoByClerkId(@PathVariable String clerkId) {

		long startTime = System.currentTimeMillis();
		ResponseDTO<List<SysClerkDTO>> listResponseDTO = new ResponseDTO<>();

		logger.info("获取店员列表信息传入参数shopId = {}", clerkId);
		SysClerkDTO sysClerkDTO = new SysClerkDTO();
		sysClerkDTO.setId(clerkId);
		List<SysClerkDTO> clerkInfo = clerkInfoService.getClerkInfo(sysClerkDTO);

		listResponseDTO.setResponseData(clerkInfo);
		listResponseDTO.setResult(StatusConstant.SUCCESS);

		logger.info("获取店员列表信息耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return clerkInfo;
	}

	@RequestMapping(value = "/upateClerkInfo", method = RequestMethod.POST)
	@ResponseBody
    ResponseDTO<String>  updateClerkInfo(@RequestBody SysClerkDTO sysClerkDTO) {
		long timeMillis = System.currentTimeMillis();
		int info = clerkInfoService.updateSysClerk(sysClerkDTO);

		ResponseDTO<String> responseDTO = new ResponseDTO<>();
		responseDTO.setResult(info > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);

		logger.info("获取某次预约详情传入参数耗时{}毫秒", (System.currentTimeMillis() - timeMillis));
        return  responseDTO;
	}
	/**
	*@Author:zhanghuan
	*@Param:
	*@Return:
	*@Description: 获取老板名下所有店员信息，不分页，不根据时间查询
	*@Date:2018/5/23 15:11
	*/
	@RequestMapping(value = "/getBossClerkInfoList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	List<SysClerkDTO> getClerkInfoList() {

		long time = System.currentTimeMillis();
		SysBossDTO sysBossDTO=UserUtils.getBossInfo();;
		ResponseDTO<List<SysClerkDTO>> listResponseDTO = new ResponseDTO<>();
		SysClerkDTO sysClerkDTO = new SysClerkDTO();
		sysClerkDTO.setSysBossCode(sysBossDTO.getSysBossCode());
		List<SysClerkDTO> clerkInfo = clerkInfoService.getClerkInfo(sysClerkDTO);

		listResponseDTO.setResponseData(clerkInfo);
		listResponseDTO.setResult(StatusConstant.SUCCESS);
		logger.info("getBossClerkInfoList获取店员列表信息耗时{}毫秒", (System.currentTimeMillis() - time));
		return clerkInfo;
	}
}

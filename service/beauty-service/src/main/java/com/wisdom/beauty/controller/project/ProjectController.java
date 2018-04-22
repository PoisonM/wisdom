package com.wisdom.beauty.controller.project;

import com.wisdom.beauty.api.dto.ShopCustomerProductRelationDTO;
import com.wisdom.beauty.api.dto.ShopCustomerProjectGroupRelRelationDTO;
import com.wisdom.beauty.api.dto.ShopCustomerProjectRelationDTO;
import com.wisdom.beauty.api.errorcode.BusinessErrorCode;
import com.wisdom.beauty.core.service.ProjectService;
import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FileName: ProjectController
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
@Controller
@RequestMapping(value = "projectInfo")
public class ProjectController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ProjectService projectService;

	/**
	 * 查询某个用户预约项目列表信息
	 *
	 * @param appointment
	 * @return
	 */
	@RequestMapping(value = "getCustomerCardProjectList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopCustomerProjectRelationDTO>> getCustomerCardList(@RequestParam String appointment) {

		long startTime = System.currentTimeMillis();

		logger.info("查询某个用户的卡片列表信息传入参数={}", "appointment = [" + appointment + "]");
		ResponseDTO<List<ShopCustomerProjectRelationDTO>> responseDTO = new ResponseDTO<>();

		ShopCustomerProjectRelationDTO shopCustomerProjectRelationDTO = new ShopCustomerProjectRelationDTO();
		shopCustomerProjectRelationDTO.setShopAppointmentId(appointment);

		List<ShopCustomerProjectRelationDTO> projectList = projectService.getCustomerAppointmentProjectList(shopCustomerProjectRelationDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(projectList);

		logger.info("查询某个用户的卡片列表信息耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 批量更改卡片信息
	 *
	 * @param shopCustomerProjectRelationDTOS
	 * @return
	 */
	@RequestMapping(value = "updateCustomerCardProjectList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopCustomerProjectRelationDTO>> updateCustomerCardProjectList(@RequestBody List<ShopCustomerProjectRelationDTO> shopCustomerProjectRelationDTOS) {

		long startTime = System.currentTimeMillis();

		logger.info("批量更改卡片信息传入参数={}", "appointment = [" + shopCustomerProjectRelationDTOS + "]");
		ResponseDTO<List<ShopCustomerProjectRelationDTO>> responseDTO = new ResponseDTO<>();

		if (shopCustomerProjectRelationDTOS == null) {
			responseDTO.setResult(StatusConstant.FAILURE);
			responseDTO.setErrorInfo(BusinessErrorCode.ERROR_DATA_TRANS.getCode());
			return responseDTO;
		}

		for (ShopCustomerProjectRelationDTO shopCustomerProjectRelationDTO : shopCustomerProjectRelationDTOS) {
			int project = projectService.updateCustomerCardProject(shopCustomerProjectRelationDTO);
			logger.info("批量更改卡片信息项目主键={}更新结果为{}", shopCustomerProjectRelationDTO.getId(), project > 0 ? "成功" : "失败");
		}

		responseDTO.setResult(StatusConstant.FAILURE);

		logger.info("批量更改卡片信息信息耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}


	/**
     * 查询某个用户的疗程卡列表信息
	 * @param sysCustomerId
     * @param sysShopId
     * @param startDate
     * @param endDate
     * @return
     */
	@RequestMapping(value = "getCustomerCourseProjectList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopCustomerProductRelationDTO>> getCustomerCourseProjectList(@RequestParam String sysCustomerId,
																			   @RequestParam String sysShopId,
																			   @RequestParam String startDate,
																			   @RequestParam String endDate) {
		ResponseDTO<List<ShopCustomerProductRelationDTO>> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}

	/**
     * 查询某个用户的单次卡列表信息
	 * @param sysCustomerId
     * @param sysShopId
     * @param startDate
     * @param endDate
     * @return
     */
	@RequestMapping(value = "getCustomerSingleProjectList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopCustomerProductRelationDTO>> getCustomerSingleProjectList(@RequestParam String sysCustomerId,
																			   @RequestParam String sysShopId,
																			   @RequestParam String startDate,
																			   @RequestParam String endDate) {
		ResponseDTO<List<ShopCustomerProductRelationDTO>> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}

	/**
	 * 查询某个用户的套卡列表信息
	 * @param sysCustomerId
	 * @param sysShopId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "getCustomerProjectGroupList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopCustomerProjectGroupRelRelationDTO>> getCustomerProjectGroupList(@RequestParam String sysCustomerId,
																						  @RequestParam String sysShopId,
																						  @RequestParam String startDate,
																						  @RequestParam String endDate) {
		ResponseDTO<List<ShopCustomerProjectGroupRelRelationDTO>> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}




}

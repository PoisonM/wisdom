package com.wisdom.beauty.controller.project;

import com.wisdom.beauty.api.dto.ShopProjectInfoDTO;
import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectGroupRelRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectRelationDTO;
import com.wisdom.beauty.api.errorcode.BusinessErrorCode;
import com.wisdom.beauty.core.service.ProjectService;
import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.CommonUtils;
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
	@RequestMapping(value = "getUserCardProjectList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopUserProjectRelationDTO>> getUserCardList(@RequestParam String appointment) {

		long startTime = System.currentTimeMillis();

		logger.info("查询某个用户的卡片列表信息传入参数={}", "appointment = [" + appointment + "]");
		ResponseDTO<List<ShopUserProjectRelationDTO>> responseDTO = new ResponseDTO<>();

		ShopUserProjectRelationDTO ShopUserProjectRelationDTO = new ShopUserProjectRelationDTO();
		ShopUserProjectRelationDTO.setShopAppointmentId(appointment);

		List<ShopUserProjectRelationDTO> projectList = projectService.getUserAppointmentProjectList(ShopUserProjectRelationDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(projectList);

		logger.info("查询某个用户的卡片列表信息耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 批量更改卡片信息
	 *
	 * @param ShopUserProjectRelationDTOS
	 * @return
	 */
	@RequestMapping(value = "updateUserCardProjectList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopUserProjectRelationDTO>> updateUserCardProjectList(@RequestBody List<ShopUserProjectRelationDTO> ShopUserProjectRelationDTOS) {

		long startTime = System.currentTimeMillis();

		logger.info("批量更改卡片信息传入参数={}", "appointment = [" + ShopUserProjectRelationDTOS + "]");
		ResponseDTO<List<ShopUserProjectRelationDTO>> responseDTO = new ResponseDTO<>();

		if (ShopUserProjectRelationDTOS == null) {
			responseDTO.setResult(StatusConstant.FAILURE);
			responseDTO.setErrorInfo(BusinessErrorCode.ERROR_DATA_TRANS.getCode());
			return responseDTO;
		}

		for (ShopUserProjectRelationDTO ShopUserProjectRelationDTO : ShopUserProjectRelationDTOS) {
			int project = projectService.updateUserCardProject(ShopUserProjectRelationDTO);
			logger.info("批量更改卡片信息项目主键={}更新结果为{}", ShopUserProjectRelationDTO.getId(), project > 0 ? "成功" : "失败");
		}

		responseDTO.setResult(StatusConstant.FAILURE);

		logger.info("批量更改卡片信息信息耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 查询某个店的疗程卡列表信息
	 *
	 * @return
	 */
	@RequestMapping(value = "getShopCourseProjectList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopProjectInfoDTO>> getShopCourseProjectList(@RequestParam String sysShopId) {

		long currentTimeMillis = System.currentTimeMillis();

		logger.info("查询某个店的疗程卡列表信息传入参数={}", "sysShopId = [" + sysShopId + "]");
		ResponseDTO<List<ShopProjectInfoDTO>> responseDTO = new ResponseDTO<>();

		ShopProjectInfoDTO shopProjectInfoDTO = new ShopProjectInfoDTO();
		shopProjectInfoDTO.setSysShopId(sysShopId);
		List<ShopProjectInfoDTO> projectList = projectService.getShopCourseProjectList(shopProjectInfoDTO);

		if (CommonUtils.objectIsNotEmpty(projectList)) {
			logger.debug("查询某个店的疗程卡列表信息查询结果为空，{}", "sysShopId = [" + sysShopId + "]");
			return null;
		}

		responseDTO.setResponseData(projectList);
		responseDTO.setResult(StatusConstant.SUCCESS);

		logger.info("查询某个店的疗程卡列表信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
		return responseDTO;
	}


	/**
     * 查询某个用户的疗程卡列表信息
	 * @param sysUserId
     * @param sysShopId
     * @param startDate
     * @param endDate
     * @return
     */
	@RequestMapping(value = "getUserCourseProjectList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopUserProductRelationDTO>> getUserCourseProjectList(@RequestParam String sysUserId,
																		   @RequestParam String sysShopId,
																		   @RequestParam String startDate,
																		   @RequestParam String endDate) {
		ResponseDTO<List<ShopUserProductRelationDTO>> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}

	/**
     * 查询某个用户的单次卡列表信息
	 * @param sysUserId
     * @param sysShopId
     * @param startDate
     * @param endDate
     * @return
     */
	@RequestMapping(value = "getUserSingleProjectList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopUserProductRelationDTO>> getUserSingleProjectList(@RequestParam String sysUserId,
																		   @RequestParam String sysShopId,
																		   @RequestParam String startDate,
																		   @RequestParam String endDate) {
		ResponseDTO<List<ShopUserProductRelationDTO>> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}

	/**
	 * 查询某个用户的套卡列表信息
	 * @param sysUserId
	 * @param sysShopId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "getUserProjectGroupList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopUserProjectGroupRelRelationDTO>> getUserProjectGroupList(@RequestParam String sysUserId,
																				  @RequestParam String sysShopId,
																				  @RequestParam String startDate,
																				  @RequestParam String endDate) {
		ResponseDTO<List<ShopUserProjectGroupRelRelationDTO>> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}




}

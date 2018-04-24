package com.wisdom.beauty.controller.project;

import com.wisdom.beauty.api.dto.ShopProjectInfoDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectGroupRelRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectRelationDTO;
import com.wisdom.beauty.api.errorcode.BusinessErrorCode;
import com.wisdom.beauty.core.service.ShopProjectService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Resource
	private ShopProjectService projectService;

	/**
	 * 查询某个用户预约项目列表信息
	 *
	 * @param appointmentId
	 * @return
	 */
	@RequestMapping(value = "getUserCardProjectList", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopUserProjectRelationDTO>> getUserCardProjectList(@RequestParam String appointmentId) {

		long startTime = System.currentTimeMillis();

		logger.info("查询某个用户的卡片列表信息传入参数={}", "appointment = [" + appointmentId + "]");
		ResponseDTO<List<ShopUserProjectRelationDTO>> responseDTO = new ResponseDTO<>();

		ShopUserProjectRelationDTO ShopUserProjectRelationDTO = new ShopUserProjectRelationDTO();
		ShopUserProjectRelationDTO.setShopAppointmentId(appointmentId);

        List<ShopUserProjectRelationDTO> projectList = projectService.getUserProjectList(ShopUserProjectRelationDTO);
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
//	@LoginRequired
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
	 * 查询某个店的疗程卡、单次卡列表信息 "0", "疗程卡"  "1", "单次")
	 *
	 * @return
	 */
	@RequestMapping(value = "getShopCourseProjectList", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopProjectInfoDTO>> getShopCourseProjectList(@RequestParam String sysShopId, @RequestParam String useStyle) {

		long currentTimeMillis = System.currentTimeMillis();

		logger.info("查询某个店的疗程卡列表信息传入参数={}", "sysShopId = [" + sysShopId + "]");
		ResponseDTO<List<ShopProjectInfoDTO>> responseDTO = new ResponseDTO<>();

		ShopProjectInfoDTO shopProjectInfoDTO = new ShopProjectInfoDTO();
		shopProjectInfoDTO.setSysShopId(sysShopId);
		shopProjectInfoDTO.setUseStyle(useStyle);
		List<ShopProjectInfoDTO> projectList = projectService.getShopCourseProjectList(shopProjectInfoDTO);

		if (CommonUtils.objectIsEmpty(projectList)) {
			logger.debug("查询某个店的疗程卡列表信息查询结果为空，{}", "sysShopId = [" + sysShopId + "]");
			return null;
		}

		responseDTO.setResponseData(projectList);
		responseDTO.setResult(StatusConstant.SUCCESS);

		logger.info("查询某个店的疗程卡列表信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
		return responseDTO;
	}


	/**
     * 查询某个用户的卡片列表信息
	 * @param sysUserId
     * @param sysShopId
     * @param cardStyle 0 查询疗程卡  1 查询单次卡
     * @return
     */
	@RequestMapping(value = "getUserCourseProjectList", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
    ResponseDTO<List<ShopUserProjectRelationDTO>> getUserCourseProjectList(@RequestParam String sysUserId,
                                                                           @RequestParam String sysShopId, @RequestParam String cardStyle) {
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("传入参数={}", "sysUserId = [" + sysUserId + "], sysShopId = [" + sysShopId + "], cardStyle = [" + cardStyle + "]");
        ResponseDTO<List<ShopUserProjectRelationDTO>> responseDTO = new ResponseDTO<>();

        ShopUserProjectRelationDTO relationDTO = new ShopUserProjectRelationDTO();
        relationDTO.setSysUserId(sysUserId);
        relationDTO.setSysShopId(sysShopId);
        relationDTO.setUseStyle(cardStyle);
        List<ShopUserProjectRelationDTO> userProjectList = projectService.getUserProjectList(relationDTO);
		if (CommonUtils.objectIsEmpty(userProjectList)) {
            logger.debug("查询某个用户的卡片列表信息为空, {}", "sysUserId = [" + sysUserId + "], sysShopId = [" + sysShopId + "], cardStyle = [" + cardStyle + "]");
            return null;
        }

        responseDTO.setResponseData(userProjectList);
        responseDTO.setResult(StatusConstant.SUCCESS);

        logger.info("耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
		return  responseDTO;
	}

	/**
	 * 查询某个用户的套卡列表信息
	 * @param sysUserId
	 * @param sysShopId
	 * @return
	 */
	@RequestMapping(value = "getUserProjectGroupList", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<HashMap<String, Object>>> getUserProjectGroupList(@RequestParam String sysUserId,
																	   @RequestParam String sysShopId) {
		ResponseDTO<List<HashMap<String, Object>>> responseDTO = new ResponseDTO<>();

		if (StringUtils.isBlank(sysShopId) || StringUtils.isBlank(sysUserId)) {
			logger.debug("查询某个用户的套卡列表信息传入参数为空， {}", "sysUserId = [" + sysUserId + "], sysShopId = [" + sysShopId + "]");
			return null;
		}

		//查询用户的套卡信息
		ShopUserProjectGroupRelRelationDTO shopUserProjectGroupRelRelationDTO = new ShopUserProjectGroupRelRelationDTO();
		shopUserProjectGroupRelRelationDTO.setSysUserId(sysUserId);
		shopUserProjectGroupRelRelationDTO.setSysShopId(sysShopId);
		List<ShopUserProjectGroupRelRelationDTO> userCollectionCardProjectList = projectService.getUserCollectionCardProjectList(shopUserProjectGroupRelRelationDTO);

		Map<String, String> helperMap = new HashMap<>(16);

		//套卡主键保存到helperMap中
		if (CommonUtils.objectIsNotEmpty(userCollectionCardProjectList)) {
			for (ShopUserProjectGroupRelRelationDTO dto : userCollectionCardProjectList) {
				helperMap.put(dto.getShopProjectGroupId(), dto.getShopProjectGroupId());
			}
		}

		List<HashMap<String, Object>> returnList = new ArrayList<>();
		if (CommonUtils.objectIsNotEmpty(helperMap)) {
			//遍历每个项目组
			for (Map.Entry entry : helperMap.entrySet()) {
				//套卡map
				HashMap<String, Object> map = new HashMap<>(6);
				//套卡总金额
				BigDecimal bigDecimal = new BigDecimal(0);
				//套卡名称
				String projectGroupName = null;
				ArrayList<Object> arrayList = new ArrayList<>();
				for (ShopUserProjectGroupRelRelationDTO dto : userCollectionCardProjectList) {
					if (entry.getKey().equals(dto.getShopProjectGroupId())) {
						arrayList.add(dto);
						bigDecimal = bigDecimal.add(new BigDecimal(dto.getProjectInitAmount()));
						projectGroupName = dto.getShopProjectGroupName();
					}
				}
				map.put("projectList", arrayList);
				map.put("totalAmount", bigDecimal);
				map.put("projectGroupName", projectGroupName);
				returnList.add(map);
			}
		}
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(returnList);
		return  responseDTO;
	}




}

package com.wisdom.beauty.controller.project;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.CardTypeEnum;
import com.wisdom.beauty.api.enums.IsUseUpEnum;
import com.wisdom.beauty.api.extDto.ExtShopProjectInfoDTO;
import com.wisdom.beauty.api.extDto.RelationIds;
import com.wisdom.beauty.api.responseDto.ShopProjectInfoResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopUserProjectRelationResponseDTO;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.ShopProjectGroupService;
import com.wisdom.beauty.core.service.ShopProjectService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * FileName: ProjectController
 *
 * @author: 赵得良 Date: 2018/4/3 0003 15:06 Description: 预约相关
 */
@Controller
@LoginAnnotations
@RequestMapping(value = "projectInfo")
public class ProjectController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private ShopProjectService projectService;

	@Resource
	private RedisUtils redisUtils;

	@Resource
	private ShopProjectGroupService shopProjectGroupService;

	/**
	 * 查询某个用户预约项目列表信息
	 *
	 * @param appointmentId
	 * @return
	 */
	@RequestMapping(value = "getUserCardProjectList", method = { RequestMethod.POST, RequestMethod.GET })
	// @LoginRequired
	public @ResponseBody ResponseDTO<HashMap<Object, Object>> getUserCardProjectList(
			@RequestParam String appointmentId) {

		ResponseDTO<HashMap<Object, Object>> responseDTO = new ResponseDTO<>();

		ShopUserProjectRelationDTO ShopUserProjectRelationDTO = new ShopUserProjectRelationDTO();
		ShopUserProjectRelationDTO.setShopAppointmentId(appointmentId);

		List<ShopUserProjectRelationDTO> projectList = projectService.getUserProjectList(ShopUserProjectRelationDTO);
		if (CommonUtils.objectIsEmpty(projectList)) {
			responseDTO.setResult(StatusConstant.FAILURE);
			return responseDTO;
		}
		// 分组，需要购买的一组（预约的时候建立关系，可使用次数为0），直接划卡(可使用次数不为0)的一组
		HashMap<Object, Object> returnMap = new HashMap<>();
		ArrayList<Object> payList = new ArrayList<>();
		ArrayList<Object> consumeList = new ArrayList<>();
		for (ShopUserProjectRelationDTO dto : projectList) {
			Integer surplusTimes = dto.getSysShopProjectSurplusTimes();
			if (null != surplusTimes && surplusTimes > 0) {
				payList.add(dto);
			} else {
				consumeList.add(dto);
			}
		}
		returnMap.put("consume", payList);
		returnMap.put("punchCard", consumeList);
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(returnMap);
		return responseDTO;
	}

	/**
	 * 查询某个店的疗程卡、单次卡列表信息 :"0", "疗程卡" "1", "单次" "2","所有")
	 *
	 * @return
	 */
	@RequestMapping(value = "searchShopProjectList", method = { RequestMethod.POST, RequestMethod.GET })
	// @LoginRequired
	public @ResponseBody ResponseDTO<HashMap<String, Object>> searchShopProjectList(@RequestParam String useStyle,
			@RequestParam String filterStr) {
        String sysShopId = redisUtils.getShopId();

		logger.info("查询某个店的疗程卡列表信息传入参数={}", "sysShopId = [" + sysShopId + "]");
		ResponseDTO<HashMap<String, Object>> responseDTO = new ResponseDTO<>();

		ShopProjectInfoDTO shopProjectInfoDTO = new ShopProjectInfoDTO();
		shopProjectInfoDTO.setSysShopId(sysShopId);
		shopProjectInfoDTO.setProjectName(filterStr);
		if (StringUtils.isNotBlank(useStyle) && (!CardTypeEnum.ALL.getCode().equals(useStyle))) {
			shopProjectInfoDTO.setUseStyle(useStyle);
		}

		HashMap<String, Object> returnMap = new HashMap<>(16);
		List<ShopProjectInfoDTO> projectList = projectService.getShopCourseProjectList(shopProjectInfoDTO);

		if (CommonUtils.objectIsEmpty(projectList)) {
			logger.debug("查询某个店的疗程卡列表信息查询结果为空，{}", "sysShopId = [" + sysShopId + "]");
			responseDTO.setResult(StatusConstant.FAILURE);
		}

		// 缓存一级
		List<ShopProjectTypeDTO> shopProjectTypeDTOList = projectService.getOneLevelProjectList(sysShopId);
		if (CommonUtils.objectIsEmpty(shopProjectTypeDTOList)) {
			responseDTO.setResult(StatusConstant.SUCCESS);
			return responseDTO;
		}

		ArrayList<Object> levelList = new ArrayList<>();
		// 遍历缓存的一级项目
		for (ShopProjectTypeDTO shopProjectTypeDTO : shopProjectTypeDTOList) {
			HashMap<Object, Object> helperMap = new HashMap<>(16);
			// 承接二级项目
			HashMap<Object, Object> twoLevelMap = new HashMap<>(16);
			for (ShopProjectInfoDTO dto : projectList) {
				if (shopProjectTypeDTO.getId().equals(dto.getProjectTypeOneId())) {
					twoLevelMap.put(dto.getProjectTypeTwoName(), dto);
				}
			}
			helperMap.put("levelTwoDetail", twoLevelMap);
			helperMap.put("levelOneDetail", shopProjectTypeDTO);
			levelList.add(helperMap);
		}
		// detailLevel集合中包含了一级二级的关联信息，detailProject集合是所有项目的列表
		returnMap.put("detailLevel", levelList);
		returnMap.put("detailProject", projectList);
		responseDTO.setResponseData(returnMap);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 查询某个用户的卡片列表信息
	 *
	 * @param sysUserId
	 * @param cardStyle
	 *            01 查询疗程卡 0 查询单次卡
	 * @return
	 */
	@RequestMapping(value = "getUserCourseProjectList", method = { RequestMethod.POST, RequestMethod.GET })
	// @LoginRequired
	public @ResponseBody ResponseDTO<List<ShopUserProjectRelationResponseDTO>> getUserCourseProjectList(
			@RequestParam(required = false) String sysUserId, @RequestParam(required = false) String cardStyle,
			@RequestParam(required = false) String id) {

		String sysBossCode = redisUtils.getBossCode();
        String sysShopId = redisUtils.getShopId();

		logger.info("传入参数={}",
				"sysUserId = [" + sysUserId + "], sysShopId = [" + sysShopId + "], cardStyle = [" + cardStyle + "]");
		ResponseDTO<List<ShopUserProjectRelationResponseDTO>> responseDTO = new ResponseDTO<>();

		ShopUserProjectRelationDTO relationDTO = new ShopUserProjectRelationDTO();
		relationDTO.setSysUserId(sysUserId);
		relationDTO.setSysShopId(sysShopId);
		relationDTO.setUseStyle(cardStyle);
		relationDTO.setSysBossCode(sysBossCode);
		relationDTO.setId(id);
		List<ShopUserProjectRelationDTO> userProjectList = projectService.getUserProjectList(relationDTO);
		if (CommonUtils.objectIsEmpty(userProjectList)) {
			logger.debug("查询某个用户的卡片列表信息为空, {}", "sysUserId = [" + sysUserId + "], sysShopId = [" + sysShopId
					+ "], cardStyle = [" + cardStyle + "]");
			responseDTO.setResponseData(null);
			responseDTO.setErrorInfo("查无数据");
			responseDTO.setResult(StatusConstant.SUCCESS);
			return responseDTO;
		}
		List<ShopUserProjectRelationResponseDTO> shopUserProjectRelationResponseDTO = new ArrayList<>();
		ShopUserProjectRelationResponseDTO shopUserProjectRelationResponse = null;
		for (ShopUserProjectRelationDTO shopUserProjectRelationDTO : userProjectList) {
			shopUserProjectRelationResponse = new ShopUserProjectRelationResponseDTO();
			BeanUtils.copyProperties(shopUserProjectRelationDTO, shopUserProjectRelationResponse);
			if (shopUserProjectRelationDTO.getInvalidDays() == null) {
                shopUserProjectRelationResponseDTO.add(shopUserProjectRelationResponse);
				continue;
			} else {
				// 产品有效期
				if (shopUserProjectRelationDTO.getInvalidDays().getTime() > System.currentTimeMillis()) {
                    //** 是否过期  0未过期 1过期
					shopUserProjectRelationResponse.setOverdue("0");

				} else {
					shopUserProjectRelationResponse.setOverdue("1");
				}
			}
			shopUserProjectRelationResponseDTO.add(shopUserProjectRelationResponse);
		}
		responseDTO.setResponseData(shopUserProjectRelationResponseDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);

		return responseDTO;
	}

	/**
	 * 查询某个用户的套卡列表信息
	 *
	 * @param sysUserId
	 * @return
	 */
	@RequestMapping(value = "/getUserProjectGroupList", method = { RequestMethod.POST, RequestMethod.GET })
	// @LoginRequired
	public @ResponseBody ResponseDTO<List<HashMap<String, Object>>> getUserProjectGroupList(
			@RequestParam String sysUserId) {

		String sysShopId = redisUtils.getShopId();

		ResponseDTO<List<HashMap<String, Object>>> responseDTO = new ResponseDTO<>();

		if (StringUtils.isBlank(sysShopId) || StringUtils.isBlank(sysUserId)) {
			logger.debug("查询某个用户的套卡列表信息传入参数为空， {}", "sysUserId = [" + sysUserId + "], sysShopId = [" + sysShopId + "]");
			return null;
		}

		// 查询用户的套卡信息
		ShopUserProjectGroupRelRelationDTO shopUserProjectGroupRelRelationDTO = new ShopUserProjectGroupRelRelationDTO();
		shopUserProjectGroupRelRelationDTO.setSysUserId(sysUserId);
		shopUserProjectGroupRelRelationDTO.setSysShopId(sysShopId);
		List<ShopUserProjectGroupRelRelationDTO> userCollectionCardProjectList = projectService
				.getUserCollectionCardProjectList(shopUserProjectGroupRelRelationDTO);

		Map<String, String> helperMap = new HashMap<>(16);

		// 套卡主键保存到helperMap中
		if (CommonUtils.objectIsNotEmpty(userCollectionCardProjectList)) {
			for (ShopUserProjectGroupRelRelationDTO dto : userCollectionCardProjectList) {
				helperMap.put(dto.getShopProjectGroupId(), dto.getShopProjectGroupId());
			}
		}

		List<HashMap<String, Object>> returnList = new ArrayList<>();
		if (CommonUtils.objectIsNotEmpty(helperMap)) {
			// 遍历每个项目组
			for (Map.Entry entry : helperMap.entrySet()) {
				// 根据主键查询套卡详细信息
				ShopProjectGroupDTO shopProjectGroupDTO = shopProjectGroupService
						.getShopProjectGroupDTO((String) entry.getKey());
				if (CommonUtils.objectIsEmpty(shopProjectGroupDTO)) {
					logger.error("根据主键查询套卡详细信息为空");
					throw new RuntimeException();
				}
				// 套卡map
				HashMap<String, Object> map = new HashMap<>(6);
				// 套卡总金额
				BigDecimal bigDecimal = new BigDecimal(0);
				// 套卡名称
				String projectGroupName = null;
				//套卡id
				String consumeRecordId=null;
				ArrayList<Object> arrayList = new ArrayList<>();
				//是否被使用完，0已用完 1使用中
				Integer surplusTimes=null;
				for (ShopUserProjectGroupRelRelationDTO dto : userCollectionCardProjectList) {
					if (entry.getKey().equals(dto.getShopProjectGroupId())) {
						arrayList.add(dto);
						if (surplusTimes==null){
							surplusTimes=dto.getProjectSurplusTimes();
						}else {
							surplusTimes=surplusTimes+dto.getProjectSurplusTimes();
						}

						bigDecimal = bigDecimal.add(dto.getProjectInitAmount());
						projectGroupName = dto.getShopProjectGroupName();
						consumeRecordId=dto.getConsumeRecordId();
					}
				}
				map.put("marketPrice", shopProjectGroupDTO.getMarketPrice());
				map.put("projectList", arrayList);
				map.put("totalAmount", bigDecimal);
				map.put("projectGroupName", projectGroupName);
				map.put("consumeRecordId", consumeRecordId);
				map.put("isUseUp", surplusTimes>0? IsUseUpEnum.USE_ING.getCode():IsUseUpEnum.USE_UP.getCode());
				returnList.add(map);
			}
		}
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(returnList);
		return responseDTO;
	}

	/**
	 * @Author:huan
	 * @Param:
	 * @Return:
	 * @Description: 获取一级列表
	 * @Date:2018/4/10 17:29
	 */
	@RequestMapping(value = "/oneLevelProject", method = RequestMethod.GET)
	@ResponseBody
	ResponseDTO<List<ShopProjectTypeDTO>> findOneLevelProject() {
		String sysShopId = null;
		if (StringUtils.isBlank(sysShopId)) {
			SysClerkDTO sysClerkDTO = UserUtils.getClerkInfo();
			sysShopId = sysClerkDTO.getSysShopId();
		}
		if (StringUtils.isBlank(sysShopId)) {
			SysBossDTO bossInfo = UserUtils.getBossInfo();
			sysShopId = bossInfo.getCurrentShopId();
		}
		ResponseDTO<List<ShopProjectTypeDTO>> responseDTO = new ResponseDTO<>();
		List<ShopProjectTypeDTO> list = projectService.getOneLevelProjectList(sysShopId);
		responseDTO.setResponseData(list);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * @Author:huan
	 * @Param: id是一级项目的id
	 * @Return:
	 * @Description: 获取二级列表
	 * @Date:2018/4/10 17:36
	 */
	@RequestMapping(value = "/twoLevelProject", method = RequestMethod.GET)
	@ResponseBody
	ResponseDTO<List<ShopProjectTypeDTO>> findTwoLevelProject(@RequestParam String id) {
		ShopProjectTypeDTO shopProjectTypeDTO = new ShopProjectTypeDTO();
		shopProjectTypeDTO.setId(id);
		// 查询数据
		List<ShopProjectTypeDTO> list = projectService.getTwoLevelProjectList(shopProjectTypeDTO);

		ResponseDTO<List<ShopProjectTypeDTO>> responseDTO = new ResponseDTO<>();
		responseDTO.setResponseData(list);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * @Author:huan
	 * @Param: id是一级项目的id
	 * @Return:
	 * @Description: 获取三级列表
	 * @Date:2018/4/10 17:36
	 */
	@RequestMapping(value = "/threeLevelProject", method = RequestMethod.GET)
	@ResponseBody
	ResponseDTO<List<ShopProjectInfoResponseDTO>> findThreeLevelProject(@RequestParam String projectTypeOneId,
			@RequestParam String ProjectTypeTwoId, @RequestParam(required = false) String projectName,
			@RequestParam int pageSize) {

		SysClerkDTO sysClerkDTO = UserUtils.getClerkInfo();
		PageParamVoDTO<ShopProjectInfoDTO> pageParamVoDTO = new PageParamVoDTO<>();
		ShopProjectInfoDTO shopProjectInfoDTO = new ShopProjectInfoDTO();

		shopProjectInfoDTO.setSysShopId(sysClerkDTO.getSysShopId());
		shopProjectInfoDTO.setProjectTypeOneId(projectTypeOneId);
		shopProjectInfoDTO.setProjectTypeTwoId(ProjectTypeTwoId);
		shopProjectInfoDTO.setProjectName(projectName);

		pageParamVoDTO.setRequestData(shopProjectInfoDTO);
		pageParamVoDTO.setPageNo(0);
		pageParamVoDTO.setPageSize(pageSize);
		// 查询数据
		List<ShopProjectInfoResponseDTO> list = projectService.getThreeLevelProjectList(pageParamVoDTO);

		ResponseDTO<List<ShopProjectInfoResponseDTO>> responseDTO = new ResponseDTO<>();
		responseDTO.setResponseData(list);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * @Author:huan
	 * @Param:
	 * @Return:
	 * @Description: 获取项目的详细信息
	 * @Date:2018/4/10 18:06
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	ResponseDTO<ShopProjectInfoResponseDTO> findDetailProject(@PathVariable String id) {
		// 查询数据
		ShopProjectInfoResponseDTO shopProjectInfoResponseDTO = projectService.getProjectDetail(id);

		ResponseDTO<ShopProjectInfoResponseDTO> responseDTO = new ResponseDTO<>();
		responseDTO.setResponseData(shopProjectInfoResponseDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 查询某店的项目列表 返回一级和三级
	 *
	 * @return
	 */
	@RequestMapping(value = "getShopProjectList", method = { RequestMethod.POST, RequestMethod.GET })
	// @LoginRequired
	public @ResponseBody
	ResponseDTO<List<Object>> getShopProjectList(@RequestParam(required = false) String pageNo,
												 @RequestParam(required = false) String pageSize, @RequestParam(required = false) String filterStr) {

		SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
		String sysShopId = clerkInfo.getSysShopId();

		ResponseDTO<List<Object>> responseDTO = new ResponseDTO<>();

		ShopProjectInfoDTO shopProjectInfoDTO = new ShopProjectInfoDTO();
		shopProjectInfoDTO.setSysShopId(sysShopId);
		if (StringUtils.isNotBlank(filterStr)) {
			shopProjectInfoDTO.setProjectName(filterStr);
		}
		List<ShopProjectInfoDTO> projectList = projectService.getShopCourseProjectList(shopProjectInfoDTO);
		if (CommonUtils.objectIsEmpty(projectList)) {
			logger.debug("查询某店的项目列表，个数为空");
			return null;
		}

		// 缓存一级项目map
		HashMap<Object, Object> oneTypeMap = new HashMap<>(16);
		for (ShopProjectInfoDTO infoDTO : projectList) {
			oneTypeMap.put(infoDTO.getProjectTypeOneId(), infoDTO.getProjectTypeOneName());
		}

		List<Object> arrayList = new ArrayList<Object>();
		// 查询一级项目下的三级项目
		for (Map.Entry entry : oneTypeMap.entrySet()) {
			HashMap<Object, Object> oneMap = new HashMap<>(16);
			ArrayList<Object> threeProjectList = new ArrayList<>();
			for (ShopProjectInfoDTO infoDTO : projectList) {
				if (infoDTO.getProjectTypeOneId().equals(entry.getKey())) {
					threeProjectList.add(infoDTO);
				}
			}
			oneMap.put(entry.getValue(), threeProjectList);
			arrayList.add(oneMap);
		}

		if (CommonUtils.objectIsEmpty(projectList)) {
			logger.debug("查询某店的项目列表查询结果为空，{}", "sysShopId = [" + sysShopId + "]");
			responseDTO.setResult(StatusConstant.FAILURE);
		}

		responseDTO.setResponseData(arrayList);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 查询返回二级和三级某店的项目列表
	 *
	 * @return
	 */
	@RequestMapping(value = "getShopTwoLevelProjectList", method = { RequestMethod.POST, RequestMethod.GET })
	// @LoginRequired
	public @ResponseBody ResponseDTO<Object> getShopTwoLevelProjectList(@RequestParam(required = false) String pageNo,
			@RequestParam(required = false) String pageSize, @RequestParam(required = false) String filterStr) {

        String sysShopId = redisUtils.getShopId();
		ResponseDTO<Object> responseDTO = new ResponseDTO<>();

		ShopProjectInfoDTO shopProjectInfoDTO = new ShopProjectInfoDTO();
		shopProjectInfoDTO.setSysShopId(sysShopId);
		if (StringUtils.isNotBlank(filterStr)) {
			shopProjectInfoDTO.setProjectName(filterStr);
		}
		List<ShopProjectInfoDTO> projectList = projectService.getShopCourseProjectList(shopProjectInfoDTO);
		if (CommonUtils.objectIsEmpty(projectList)) {
			logger.debug("查询返回二级和三级某店的项目列表的项目列表，个数为空");
			responseDTO.setErrorInfo("查询结果为空！");
			responseDTO.setResult(StatusConstant.SUCCESS);
			return responseDTO;
		}

		// 缓存二级项目map
		HashMap<String, ShopProjectInfoDTO> twoTypeMap = new HashMap<>(16);
		for (ShopProjectInfoDTO infoDTO : projectList) {
			twoTypeMap.put(infoDTO.getProjectTypeTwoId(), infoDTO);
		}

		List<Map> arrayList = new ArrayList<>();
		// 查询一级项目下的三级项目
		for (Map.Entry entry : twoTypeMap.entrySet()) {
			Map<Object, Object> levelMap = new HashMap<>();
			List<ShopProjectInfoDTO> threeProjectList = new ArrayList();
			for (ShopProjectInfoDTO infoDTO : projectList) {
				if (infoDTO.getProjectTypeTwoId().equals(entry.getKey())) {
					threeProjectList.add(infoDTO);
				}
			}
			levelMap.put("twoLevel", entry.getValue());
			levelMap.put("threeProjectList", threeProjectList);
			arrayList.add(levelMap);
		}

		if (CommonUtils.objectIsEmpty(projectList)) {
			logger.debug("查询返回二级和三级某店的项目列表查询结果为空，{}", "sysShopId = [" + sysShopId + "]");
			responseDTO.setResult(StatusConstant.FAILURE);
		}

		responseDTO.setResponseData(arrayList);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 用户端-查询某店的项目列表
	 *
	 * @return
	 */
	@RequestMapping(value = "getUserClientShopProjectList", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseDTO<Object> getUserClientShopProjectList(@RequestParam String pageNo,
			@RequestParam String pageSize) {

        String sysShopId = redisUtils.getShopId();
		ResponseDTO<Object> responseDTO = new ResponseDTO<>();
		ShopProjectInfoDTO shopProjectInfoDTO = new ShopProjectInfoDTO();
		shopProjectInfoDTO.setSysShopId(sysShopId);
		List<ShopProjectInfoDTO> projectList = projectService.getShopCourseProjectList(shopProjectInfoDTO);
		if (CommonUtils.objectIsEmpty(projectList)) {
			logger.debug("用户端-查询某店的项目列表，个数为空");
			return null;
		}

		responseDTO.setResponseData(projectList);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 根据用户与项目的关系主键列表查询用户与项目的关系
	 */
	@RequestMapping(value = "getUserShopProjectList", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseDTO<List<ShopUserProjectRelationDTO>> getUserShopProjectList(
			@RequestBody RelationIds<String> relationIds) {

		ResponseDTO<List<ShopUserProjectRelationDTO>> responseDTO = new ResponseDTO<>();

		List<ShopUserProjectRelationDTO> userShopProjectList = projectService
				.getUserShopProjectList(relationIds.getRelationIds());

		responseDTO.setResponseData(userShopProjectList);
		responseDTO.setResult(StatusConstant.SUCCESS);

		return responseDTO;
	}

	/**
	 * 查询用户套卡下的子卡的详细信息
	 */
	@RequestMapping(value = "getShopUserProjectGroupRelRelationInfo", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody ResponseDTO<ShopUserProjectGroupRelRelationDTO> getShopUserProjectGroupRelRelationInfo(
			@RequestParam String shopUserProjectGroupRelRelationId) {

		ShopUserProjectGroupRelRelationDTO shopUserProjectGroupRelRelationDTO = new ShopUserProjectGroupRelRelationDTO();
		shopUserProjectGroupRelRelationDTO.setId(shopUserProjectGroupRelRelationId);

		ResponseDTO<ShopUserProjectGroupRelRelationDTO> responseDTO = new ResponseDTO<>();

		List<ShopUserProjectGroupRelRelationDTO> relRelation = shopProjectGroupService
				.getShopUserProjectGroupRelRelation(shopUserProjectGroupRelRelationDTO);

		responseDTO
				.setResponseData(null == relRelation ? new ShopUserProjectGroupRelRelationDTO() : relRelation.get(0));
		responseDTO.setResult(StatusConstant.SUCCESS);

		return responseDTO;
	}

	/**
	 * 保存用户的项目信息
	 */
	@RequestMapping(value = "saveProjectInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseDTO<Object> saveProjectInfo(@RequestBody ExtShopProjectInfoDTO extShopProjectInfoDTO) {
		ResponseDTO responseDTO = new ResponseDTO();

		SysBossDTO bossInfo = UserUtils.getBossInfo();
		if (judgeBossCurrentShop(responseDTO, bossInfo)) {
			return responseDTO;
		}
		if (null == extShopProjectInfoDTO.getOncePrice()) {
			responseDTO.setResponseData("折扣价格不能为空");
			responseDTO.setResult(StatusConstant.FAILURE);
			return responseDTO;
		}
		if (null == extShopProjectInfoDTO.getServiceTimes()) {
			responseDTO.setResponseData("服务次数不能为空");
			responseDTO.setResult(StatusConstant.FAILURE);
			return responseDTO;
		}
		extShopProjectInfoDTO.setSysShopId(bossInfo.getCurrentShopId());
		extShopProjectInfoDTO.setCreateDate(new Date());
		if (!CardTypeEnum.ONE_TIME_CARD.getCode().equals(extShopProjectInfoDTO.getCardType())) {
			extShopProjectInfoDTO.setUseStyle(CardTypeEnum.TREATMENT_CARD.getCode());
		}
		extShopProjectInfoDTO.setMarketPrice(
				extShopProjectInfoDTO.getOncePrice().multiply(new BigDecimal(extShopProjectInfoDTO.getServiceTimes())));
		int info = projectService.saveProjectInfo(extShopProjectInfoDTO);
		responseDTO.setResult(info > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
		return responseDTO;
	}

	/**
	 * 保存用户的项目信息
	 */
	@RequestMapping(value = "updateProjectInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseDTO<Object> updateProjectInfo(
			@RequestBody ExtShopProjectInfoDTO extShopProjectInfoDTO) {
		ResponseDTO responseDTO = new ResponseDTO();

		SysBossDTO bossInfo = UserUtils.getBossInfo();
		if (judgeBossCurrentShop(responseDTO, bossInfo)) {
			return responseDTO;
		}
		if (null == extShopProjectInfoDTO.getId()) {
			responseDTO.setResponseData("更新主键不可为空不能为空");
			responseDTO.setResult(StatusConstant.FAILURE);
			return responseDTO;
		}
		extShopProjectInfoDTO.setUpdateDate(new Date());
		int info = projectService.updateProjectInfo(extShopProjectInfoDTO);
		responseDTO.setResult(info > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
		return responseDTO;
	}

	/**
	 * 判断老板有当前店铺信息
	 *
	 * @param responseDTO
	 * @param bossInfo
	 * @return
	 */
	private boolean judgeBossCurrentShop(ResponseDTO<Object> responseDTO, SysBossDTO bossInfo) {
		if (null == bossInfo || com.wisdom.common.util.StringUtils.isBlank(bossInfo.getCurrentShopId())) {
			logger.error("获取老板信息异常，{}", "bossInfo = [" + bossInfo + "]");
			responseDTO.setResult(StatusConstant.FAILURE);
			responseDTO.setResponseData("获取老板信息异常");
			return true;
		}
		return false;
	}

}

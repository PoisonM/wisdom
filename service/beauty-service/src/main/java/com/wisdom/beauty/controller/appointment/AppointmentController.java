package com.wisdom.beauty.controller.appointment;

import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.beauty.api.dto.ShopBossRelationDTO;
import com.wisdom.beauty.api.dto.ShopScheduleSettingDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectRelationDTO;
import com.wisdom.beauty.api.enums.CommonCodeEnum;
import com.wisdom.beauty.api.errorcode.BusinessErrorCode;
import com.wisdom.beauty.api.extDto.ExtShopAppointServiceDTO;
import com.wisdom.beauty.api.extDto.ShopUserLoginDTO;
import com.wisdom.beauty.api.responseDto.ShopProjectInfoResponseDTO;
import com.wisdom.beauty.client.UserServiceClient;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.ShopAppointmentService;
import com.wisdom.beauty.core.service.ShopBossService;
import com.wisdom.beauty.core.service.ShopProjectService;
import com.wisdom.beauty.core.service.ShopWorkService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.IdGen;
import com.wisdom.common.util.LunarUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * FileName: AppointmentServiceImpl
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
@Controller
@RequestMapping(value = "appointmentInfo")
public class AppointmentController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private ShopAppointmentService appointmentService;

	@Resource
	private UserServiceClient userServiceClient;

	@Resource
	private ShopWorkService workService;

	@Resource
	private ShopProjectService shopProjectService;

	@Resource
	private RedisUtils redisUtils;

	@Resource
	private ShopBossService shopBossService;

	@Value("${test.msg}")
	private String msg;


	/**
	 * 根据时间查询某个美容店预约列表
	 *
	 * @param sysShopId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "shopDayAppointmentInfoByDate", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<Map<String, Object>> shopDayAppointmentInfoByDate(@RequestParam String sysShopId,
																  @RequestParam String startDate, @RequestParam String endDate) {

		String preLog = "根据时间查询某个美容店预约列表,";
		long startTime = System.currentTimeMillis();
		logger.info(preLog + "美容店主键为={}", sysShopId);
		SysClerkDTO info = UserUtils.getClerkInfo();
		sysShopId = info.getSysShopId();

		ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
		ExtShopAppointServiceDTO extShopAppointServiceDTO = new ExtShopAppointServiceDTO();
		extShopAppointServiceDTO.setSearchStartTime(DateUtils.StrToDate(startDate, "datetime"));
		extShopAppointServiceDTO.setSearchEndTime(DateUtils.StrToDate(endDate, "datetime"));
		extShopAppointServiceDTO.setSysShopId(sysShopId);

		//根据时间查询当前店下所有美容师
		List<SysClerkDTO> clerkInfo = userServiceClient.getClerkInfo(sysShopId);

		if (judgeNull(responseDTO, preLog, clerkInfo)) {
			return responseDTO;
		}
		logger.info(preLog + "根据时间查询当前店下所有美容师个数={}", clerkInfo.size());

		HashMap<String, Object> responseMap = new HashMap<>(32);

		//查询某个店的排班信息
		ShopScheduleSettingDTO shopScheduleSettingDTO = new ShopScheduleSettingDTO();
		shopScheduleSettingDTO.setSysShopId(sysShopId);
		List<ShopScheduleSettingDTO> shopScheduleSettingInfo = workService.getShopScheduleSettingInfo(shopScheduleSettingDTO);
		if (shopScheduleSettingInfo == null) {
			logger.error("查询某个店的排班信息为空,美容店主键为{}", "sysShopId = [" + sysShopId + "]");
			//早晚班默认值
			responseMap.put("startTime", "09:00");
			responseMap.put("endTime", "23:00");
		} else {
			for (ShopScheduleSettingDTO settingDTO : shopScheduleSettingInfo) {
				if ("3".equals(settingDTO.getTypeName())) {
					responseMap.put("startTime", settingDTO.getStartTime());
					responseMap.put("endTime", settingDTO.getEndTime());
				} else {
					responseMap.put("startTime", "09:00");
					responseMap.put("endTime", "23:00");
				}
			}
		}

		//遍历美容师获取预约详情
		for (SysClerkDTO SysClerkDTO : clerkInfo) {

			HashMap<String, Object> shopAppointMap = new HashMap<>(16);

			//查询某个美容师的预约列表
			extShopAppointServiceDTO.setSysClerkId(SysClerkDTO.getId());
			//上线打开
			extShopAppointServiceDTO.setSysShopId("");
			List<ShopAppointServiceDTO> shopAppointServiceDTOS = appointmentService.getShopClerkAppointListByCriteria(extShopAppointServiceDTO);
			extShopAppointServiceDTO.setSysShopId(sysShopId);
			if (CommonUtils.objectIsEmpty(shopAppointServiceDTOS)) {
				logger.info(preLog + "美容师预约列表为空");
				shopAppointMap.put("appointmentInfo", "");
				shopAppointMap.put("point", 0);
			} else {
				ArrayList<Object> appointInfoList = new ArrayList<>();
				for (ShopAppointServiceDTO serviceDTO : shopAppointServiceDTOS) {
					try {
						HashMap<String, Object> hashMap = CommonUtils.beanToMap(serviceDTO);
						String str = CommonUtils.getArrayNo(DateUtils.DateToStr(serviceDTO.getAppointStartTime(), "time"),
								DateUtils.DateToStr(serviceDTO.getAppointEndTime(), "time"));
						hashMap.put("scheduling", str);
						appointInfoList.add(hashMap);
					} catch (Exception e) {
						logger.error(preLog + "异常，异常信息为{}" + e.getMessage(), e);
					}
				}
				shopAppointMap.put("appointmentInfo", appointInfoList);
				shopAppointMap.put("point", shopAppointServiceDTOS.size());
			}
			shopAppointMap.put("sysClerkDTO", SysClerkDTO);
			responseMap.put(SysClerkDTO.getName(), shopAppointMap);
		}

		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(responseMap);
		logger.info(preLog + "耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 获取美容店周预约列表
	 *
	 * @param sysShopId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "shopWeekAppointmentInfoByDate", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<Map<String, Object>> shopWeekAppointmentInfoByDate(@RequestParam String sysShopId,
																   @RequestParam String startDate, @RequestParam String endDate) {
		ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
		Date startTime = DateUtils.StrToDate(startDate, "datetime");
		Date endTime = DateUtils.StrToDate(endDate, "datetime");


		String preLog = "根据时间查询某个美容店周预约列表";
		long start = System.currentTimeMillis();
		logger.info(preLog + "美容店主键为={}", sysShopId);

		//根据时间查询当前店下所有美容师
		List<SysClerkDTO> clerkInfo = userServiceClient.getClerkInfo(sysShopId);

		if (judgeNull(responseDTO, preLog, clerkInfo)) {
			return responseDTO;
		}
		logger.debug(preLog + "根据时间查询当前店下所有美容师个数为 {}", clerkInfo.size());

		HashMap<String, Object> returnMap = new HashMap<>(16);

		//遍历每个美容师
		for (SysClerkDTO clerkDTO : clerkInfo) {

			Date loopDate = startTime;
			ArrayList<Object> arrayList = new ArrayList<>();

			while (loopDate.getTime() < endTime.getTime()) {

				HashMap<Object, Object> map = new HashMap<>(16);
				//获取美容师在某一时间段内的预约主键列表
				Set<String> stringSet = redisUtils.getAppointmentIdByShopClerk(redisUtils.getShopIdClerkIdKey(sysShopId, clerkDTO.getId()),
						DateUtils.getDateStartTime(loopDate), DateUtils.getDateEndTime(loopDate));

				logger.info("{}，在，{}，{}时间段的预约列表为{}", clerkDTO.getName(), DateUtils.getDateStartTime(loopDate), DateUtils.getDateEndTime(loopDate), stringSet);

				if (CommonUtils.objectIsEmpty(stringSet)) {
					loopDate = DateUtils.dateInc(loopDate);
					map.put("info", "");
					map.put("week", DateUtils.getWeek(loopDate));
					map.put("day", DateUtils.getDay(loopDate));
					map.put("Lunar", LunarUtils.getChinaDayString(new LunarUtils(loopDate).day));
					arrayList.add(map);
					continue;
				}
				//遍历预约主键获取预约详细信息
				Iterator<String> it = stringSet.iterator();
				List<ShopAppointServiceDTO> projectList = new ArrayList<>();
				while (it.hasNext()) {
					String appointmentId = it.next();
					ShopAppointServiceDTO shopAppointServiceDTO = redisUtils.getShopAppointInfoFromRedis(appointmentId);
					projectList.add(shopAppointServiceDTO);
				}
				map.put("info", projectList);
				map.put("week", DateUtils.getWeek(loopDate));
				map.put("day", DateUtils.getDay(loopDate));
				map.put("Lunar", LunarUtils.getChinaDayString(new LunarUtils(loopDate).day));

				arrayList.add(map);
				loopDate = DateUtils.dateInc(loopDate);
			}

			returnMap.put(clerkDTO.getName(), arrayList);
		}

		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(returnMap);
		logger.info(preLog + "耗时{}毫秒", (System.currentTimeMillis() - start));

		return responseDTO;
	}

	private boolean judgeNull(ResponseDTO<Map<String, Object>> responseDTO, String preLog, List<SysClerkDTO> clerkInfo) {
		if (CommonUtils.objectIsEmpty(clerkInfo)) {
			responseDTO.setResult(StatusConstant.FAILURE);
			responseDTO.setErrorInfo(BusinessErrorCode.ERROR_NULL_RECORD.getCode());
			logger.info(preLog + "根据时间查询当前店下所有美容师个数为0");
			return true;
		}
		return false;
	}

	/**
	 * 获取某次预约详情
	 *
	 * @param shopAppointServiceId
	 * @return
	 */
	@RequestMapping(value = "getAppointmentInfoById", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<ExtShopAppointServiceDTO> getAppointmentInfoById(@RequestParam String shopAppointServiceId) {

		long startTime = System.currentTimeMillis();
		logger.info("获取某次预约详情传入参数={}", "shopAppointServiceId = [" + shopAppointServiceId + "]");

		ResponseDTO<ExtShopAppointServiceDTO> responseDTO = new ResponseDTO<>();
		ShopAppointServiceDTO shopAppointInfoFromRedis = redisUtils.getShopAppointInfoFromRedis(shopAppointServiceId);
		ExtShopAppointServiceDTO extShopAppointServiceDTO = new ExtShopAppointServiceDTO();
		if (null != shopAppointInfoFromRedis) {
			BeanUtils.copyProperties(shopAppointInfoFromRedis, extShopAppointServiceDTO);
			SysClerkDTO sysClerkDTO = redisUtils.getSysClerkDTO(extShopAppointServiceDTO.getSysClerkId());
			extShopAppointServiceDTO.setSysClerkName(sysClerkDTO.getName());
			extShopAppointServiceDTO.setScore(sysClerkDTO.getScore());
			extShopAppointServiceDTO.setAppointEndTimeE(DateUtils.DateToStr(extShopAppointServiceDTO.getAppointEndTime(), "datetime"));
			extShopAppointServiceDTO.setClerkImage(sysClerkDTO.getPhoto());
		}

		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(extShopAppointServiceDTO);
		logger.info("获取某次预约详情传入参数耗时{}毫秒", (System.currentTimeMillis() - startTime));

		return responseDTO;
	}

	/**
	 * 根据预约主键修改此次预约信息（修改预约状态等）
	 *
	 * @param shopAppointServiceId
	 * @return
	 */
	@RequestMapping(value = "updateAppointmentInfoById", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<ShopAppointServiceDTO> updateAppointmentInfoById(@RequestParam String shopAppointServiceId, @RequestParam String status) {
		logger.info("根据预约主键修改此次预约信息传入参数={}", "shopAppointServiceId = [" + shopAppointServiceId + "], status = [" + status + "]");
		long timeMillis = System.currentTimeMillis();
		ResponseDTO<ShopAppointServiceDTO> responseDTO = new ResponseDTO<>();

		ShopAppointServiceDTO shopAppointServiceDTO = new ShopAppointServiceDTO();
		shopAppointServiceDTO.setId(shopAppointServiceId);
		shopAppointServiceDTO.setStatus(status);
		int info = appointmentService.updateAppointmentInfo(shopAppointServiceDTO);
		logger.debug("根据预约主键修改此次预约信息，执行结果为{}", info);

		//更新redis
		redisUtils.updateShopAppointInfoToRedis(shopAppointServiceDTO);

		responseDTO.setResult(info > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);

		logger.info("获取某次预约详情传入参数耗时{}毫秒", (System.currentTimeMillis() - timeMillis));
		return responseDTO;
	}

	/**
	 * 获取我的预约列表（修改预约状态等）
	 *
	 * @return
	 */
	@RequestMapping(value = "getMyAppointInfoList", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<Object> getMyAppointInfoList(@RequestParam String status) {
		long timeMillis = System.currentTimeMillis();
		ResponseDTO<Object> responseDTO = new ResponseDTO<>();
		UserInfoDTO userInfo = UserUtils.getUserInfo();
		if (null == userInfo && CommonCodeEnum.TRUE.getCode().equals(msg)) {
			userInfo = UserUtils.getTestUserInfoDTO();
		}
		ExtShopAppointServiceDTO shopAppointServiceDTO = new ExtShopAppointServiceDTO();
		shopAppointServiceDTO.setSysUserId(userInfo.getId());
		shopAppointServiceDTO.setStatus(status);
		String sysShopId = redisUtils.getUserLoginShop(userInfo.getId()).getSysShopId();
		if (CommonCodeEnum.TRUE.getCode().equals(msg)) {
			sysShopId = "11";
		}

		shopAppointServiceDTO.setSysShopId(sysShopId);
		List<ShopAppointServiceDTO> shopAppointServiceDTOS = appointmentService.getShopClerkAppointListByCriteria(shopAppointServiceDTO);

		logger.debug("获取我的预约列表预约信息，执行结果为{}", shopAppointServiceDTOS);

		responseDTO.setResponseData(shopAppointServiceDTOS);
		responseDTO.setResult(StatusConstant.SUCCESS);

		logger.info("获取某次预约详情传入参数耗时{}毫秒", (System.currentTimeMillis() - timeMillis));
		return responseDTO;
	}

	/**
	 * 获取某个店的店员列表
	 *
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "getShopClerkList", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<SysClerkDTO>> getShopClerkList(@RequestParam String pageNo, @RequestParam String pageSize) {

		long currentTimeMillis = System.currentTimeMillis();
		logger.info("获取某个店的店员列表传入参数={}", "pageNo = [" + pageNo + "], pageSize = [" + pageSize + "]");

		ResponseDTO<List<SysClerkDTO>> responseDTO = new ResponseDTO<>();

		UserInfoDTO userInfo = UserUtils.getUserInfo();

		String sysShopId = null;
		//pad端
		if (StringUtils.isBlank(sysShopId)) {
			logger.info("pad端登陆");
			SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
			sysShopId = clerkInfo.getSysShopId();
		}
		//用户端
		if (StringUtils.isBlank(sysShopId)) {
			logger.info("用户端登陆");
			sysShopId = redisUtils.getUserLoginShop(userInfo.getId()).getSysShopId();
		}

		List<SysClerkDTO> clerkDTOS = userServiceClient.getClerkInfo(sysShopId);
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(clerkDTOS);

		logger.info("获取某个店的店员列表耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
		return responseDTO;
	}

	/**
	 * 保存用户的预约信息
	 */
	@RequestMapping(value = "saveUserAppointInfo", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<Map> saveUserAppointInfo(@RequestBody ExtShopAppointServiceDTO shopAppointServiceDTO) {
		long currentTimeMillis = System.currentTimeMillis();

		logger.info("保存用户的预约信息传入参数={}", "shopAppointServiceDTO = [" + shopAppointServiceDTO + "]");
		ResponseDTO<Map> responseDTO = new ResponseDTO<>();
		shopAppointServiceDTO.setId(IdGen.uuid());
		UserInfoDTO userInfo = UserUtils.getUserInfo();
		SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
		//如果userInfo为空，说明是pad端用户
		if (null == userInfo) {
			shopAppointServiceDTO.setCreateBy(clerkInfo.getSysUserId());
			shopAppointServiceDTO.setSysBossCode(clerkInfo.getSysBossId());
			if (StringUtils.isBlank(shopAppointServiceDTO.getSysClerkId())) {
				shopAppointServiceDTO.setSysClerkId(clerkInfo.getId());
			}
			shopAppointServiceDTO.setSysClerkName(clerkInfo.getName());
			shopAppointServiceDTO.setSysShopId(clerkInfo.getSysShopId());
			shopAppointServiceDTO.setSysShopName(clerkInfo.getSysShopName());
		}
		//测试挡板
		if (CommonCodeEnum.TRUE.getCode().equals(msg)) {
			shopAppointServiceDTO.setSysUserId("1");
			shopAppointServiceDTO.setSysUserName("陈佳科");
			shopAppointServiceDTO.setSysShopId("11");
		}
		//如果clerkInfo为空说明是用户端用户
		else if (null == clerkInfo) {
			shopAppointServiceDTO.setSysUserId(userInfo.getId());
			shopAppointServiceDTO.setSysUserName(userInfo.getNickname());
			shopAppointServiceDTO.setCreateBy(userInfo.getId());
			shopAppointServiceDTO.setSysUserPhone(userInfo.getMobile());
			ShopUserLoginDTO userLoginShop = redisUtils.getUserLoginShop(UserUtils.getUserInfo().getId());
			shopAppointServiceDTO.setSysShopId(userLoginShop.getSysShopId());
			shopAppointServiceDTO.setSysShopName(userLoginShop.getSysShopName());
			shopAppointServiceDTO.setSysUserPhone(userInfo.getMobile());
			shopAppointServiceDTO.setSysUserName(userInfo.getNickname());
			shopAppointServiceDTO.setSysBossCode(userLoginShop.getSysBossId());
		}
		if (StringUtils.isNotBlank(shopAppointServiceDTO.getAppointStartTimeS())) {
			shopAppointServiceDTO.setAppointStartTime(DateUtils.StrToDate(shopAppointServiceDTO.getAppointStartTimeS(), "hour"));
			Date afterDate = new Date(shopAppointServiceDTO.getAppointStartTime().getTime() + shopAppointServiceDTO.getAppointPeriod() * 60 * 1000);
			shopAppointServiceDTO.setAppointEndTime(afterDate);
		}
		//根据预约时间查询当前美容师有没有被占用
		shopAppointServiceDTO.setSearchStartTime(shopAppointServiceDTO.getAppointStartTime());
		shopAppointServiceDTO.setSearchEndTime(shopAppointServiceDTO.getAppointEndTime());
		String status = shopAppointServiceDTO.getStatus();
		shopAppointServiceDTO.setStatus("");
		List<ShopAppointServiceDTO> appointListByCriteria = appointmentService.getShopClerkAppointListByCriteria(shopAppointServiceDTO);
		shopAppointServiceDTO.setStatus(status);
		if (CommonUtils.objectIsNotEmpty(appointListByCriteria)) {
			logger.info("根据预约时间查询当前美容师有没有被占用查询结果大小为={}", appointListByCriteria.size());
			responseDTO.setResult(StatusConstant.FAILURE);
			responseDTO.setErrorInfo("当前时间段已被预约，请您重新选择(-_-)");
			return responseDTO;
		}

		logger.info("保存用户的预约信息={}", "shopAppointServiceDTO = [" + shopAppointServiceDTO + "]");
		shopAppointServiceDTO.setCreateDate(new Date());
		int info = appointmentService.saveUserShopAppointInfo(shopAppointServiceDTO);
		logger.debug("保存用户的预约信息执行结果， {}", info > 0 ? "成功" : "失败");

		redisUtils.saveShopAppointInfoToRedis(shopAppointServiceDTO);

		//生成用户与项目的关系
		buildUserProjectRelation(shopAppointServiceDTO);

		HashMap<Object, Object> hashMap = new HashMap<>(1);
		hashMap.put("appointmentId", shopAppointServiceDTO.getId());
		responseDTO.setResponseData(hashMap);
		responseDTO.setResult(StatusConstant.SUCCESS);

		logger.info("保存用户的预约信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
		return responseDTO;
	}

	/**
	 * 修改用户的预约信息
	 */
	@RequestMapping(value = "updateUserAppointInfo", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<Map> updateUserAppointInfo(@RequestBody ExtShopAppointServiceDTO shopAppointServiceDTO) {
		long currentTimeMillis = System.currentTimeMillis();

		logger.info("修改用户的预约信息传入参数={}", "shopAppointServiceDTO = [" + shopAppointServiceDTO + "]");
		ResponseDTO<Map> responseDTO = new ResponseDTO<>();

		if (StringUtils.isNotBlank(shopAppointServiceDTO.getAppointStartTimeS())) {
			shopAppointServiceDTO.setAppointStartTime(DateUtils.StrToDate(shopAppointServiceDTO.getAppointStartTimeS(), "hour"));
			Date afterDate = new Date(shopAppointServiceDTO.getAppointStartTime().getTime() + shopAppointServiceDTO.getAppointPeriod() * 60 * 1000);
			shopAppointServiceDTO.setAppointEndTime(afterDate);
		}
		//根据预约时间查询当前美容师有没有被占用
		shopAppointServiceDTO.setSearchStartTime(shopAppointServiceDTO.getAppointStartTime());
		shopAppointServiceDTO.setSearchEndTime(shopAppointServiceDTO.getAppointEndTime());
		String status = shopAppointServiceDTO.getStatus();
		shopAppointServiceDTO.setStatus("");
		List<ShopAppointServiceDTO> appointListByCriteria = appointmentService.getShopClerkAppointListByCriteria(shopAppointServiceDTO);
		shopAppointServiceDTO.setStatus(status);
		if (CommonUtils.objectIsNotEmpty(appointListByCriteria)) {
			responseDTO.setResult(StatusConstant.FAILURE);
			responseDTO.setErrorInfo("当前时间段已被预约，请您重新选择(-_-)");
			return responseDTO;
		}

		logger.info("修改用户的预约信息={}", "shopAppointServiceDTO = [" + shopAppointServiceDTO + "]");
		shopAppointServiceDTO.setUpdateDate(new Date());
		int info = appointmentService.updateAppointmentInfo(shopAppointServiceDTO);
		logger.debug("修改用户的预约信息执行结果， {}", info > 0 ? "成功" : "失败");

		redisUtils.saveShopAppointInfoToRedis(shopAppointServiceDTO);

		ShopUserProjectRelationDTO deleteRelationDTO = new ShopUserProjectRelationDTO();
		deleteRelationDTO.setShopAppointmentId(shopAppointServiceDTO.getId());
		//删除用户与项目的关系
		shopProjectService.deleteUserAndProjectRelation(deleteRelationDTO);
		//生成用户与项目的关系
		buildUserProjectRelation(shopAppointServiceDTO);

		HashMap<Object, Object> hashMap = new HashMap<>(1);
		hashMap.put("appointmentId", shopAppointServiceDTO.getId());
		responseDTO.setResponseData(hashMap);
		responseDTO.setResult(StatusConstant.SUCCESS);

		logger.info("保存用户的预约信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
		return responseDTO;
	}

	/**
	 * 构建用户与项目的关系
	 *
	 * @param shopAppointServiceDTO
	 */
	private void buildUserProjectRelation(@RequestBody ExtShopAppointServiceDTO shopAppointServiceDTO) {
		if (StringUtils.isNotBlank(shopAppointServiceDTO.getShopProjectId())) {
			String[] projectStr = shopAppointServiceDTO.getShopProjectId().split(";");
			for (String project : projectStr) {
				//先查询，如果用户与项目已经建立关系，并且没有使用完，不需要重新创建用户与项目的关系
				ShopUserProjectRelationDTO relationDTO = new ShopUserProjectRelationDTO();
				relationDTO.setSysUserId(shopAppointServiceDTO.getSysUserId());
				relationDTO.setSysShopProjectId(project);
				relationDTO.setSysShopProjectSurplusTimes(0);
				List<ShopUserProjectRelationDTO> userProjectList = shopProjectService.getUserProjectList(relationDTO);
				//需要重新创建用户与项目的关系
				if (CommonUtils.objectIsEmpty(userProjectList)) {
					relationDTO.setSysShopId(shopAppointServiceDTO.getSysShopId());
					relationDTO.setShopAppointmentId(shopAppointServiceDTO.getId());
					//根据项目主键查询项目详细信息
					ShopProjectInfoResponseDTO projectDetail = shopProjectService.getProjectDetail(project);
					if (null != projectDetail) {
						relationDTO.setUseStyle(projectDetail.getUseStyle());
						relationDTO.setCreateBy(shopAppointServiceDTO.getCreateBy());
						relationDTO.setSysShopProjectName(projectDetail.getProjectName());
						relationDTO.setId(IdGen.uuid());
						relationDTO.setSysShopProjectInitTimes(0);
						relationDTO.setSysShopProjectInitAmount(new BigDecimal(0));
						relationDTO.setSysClerkId(shopAppointServiceDTO.getSysClerkId());
						relationDTO.setSysShopName(shopAppointServiceDTO.getSysShopName());
						relationDTO.setSysShopProjectSurplusAmount(new BigDecimal(0));
						relationDTO.setSysUserId(shopAppointServiceDTO.getSysUserId());
						relationDTO.setSysShopProjectId(project);
						relationDTO.setSysClerkName(shopAppointServiceDTO.getSysClerkName());
						relationDTO.setSysShopProjectSurplusTimes(0);
						relationDTO.setShopAppointmentId(shopAppointServiceDTO.getId());
						relationDTO.setSysShopId(projectDetail.getSysShopId());
						relationDTO.setSysShopProjectInitTimes(1);
						int num = shopProjectService.saveUserProjectRelation(relationDTO);
						logger.debug("建立项目与用户的关系， {}", num > 0 ? "成功" : "失败");
					}
				}
			}
		}
	}


	/**
	 * 查询某个美容院某个时间预约个数或某个店员的预约数量
	 *
	 * @param sysShopId         店铺id
	 * @param sysClerkId        店员id
	 * @param appointStartTimeS 预约开始时间（范围起始值）
	 * @param appointStartTimeE appointStartTimeE（范围结束值）
	 * @return 某一个时间段的数量数量
	 * @autur zhangchao
	 */
	@RequestMapping(value = "findNumForShopByTimeControl", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<HashMap<String, String>> findNumForShopByTimeControl(@RequestParam String sysShopId, String sysClerkId, String appointStartTimeS, String appointStartTimeE) {

		logger.info("根据预约主键修改此次预约信息传入参数={}", "sysShopId = [" + sysShopId + "], sysClerkId = [" + sysClerkId + "] ,appointStartTimeS = [" + appointStartTimeS + "]", "appointStartTimeE = [" + appointStartTimeE + "]");
		long timeMillis = System.currentTimeMillis();

		HashMap<String, String> shopAppointmentNum = appointmentService.findNumForShopByTimeService(sysShopId, sysClerkId, appointStartTimeS, appointStartTimeE);
		logger.info("获取某次预约详情传入参数耗时{}毫秒", (System.currentTimeMillis() - timeMillis));
		ResponseDTO<HashMap<String, String>> responseDTO = new ResponseDTO<>();
		//判断查询是否成功
		if (shopAppointmentNum.get("resultCode").equals("success")) {
			responseDTO.setResult(StatusConstant.SUCCESS);
		} else {
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		responseDTO.setResponseData(shopAppointmentNum);
		return responseDTO;
	}


	/**
	 * 获取某个老板下面的店的预约个数
	 *
	 * @param searchDate 2018-09-09
	 * @return
	 */
	@RequestMapping(value = "/getShopAppointmentNumberInfo", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<Object> getShopAppointmentNumberInfo(@RequestParam String searchDate) {
		long currentTimeMillis = System.currentTimeMillis();
		logger.info("获取某个老板下面的店的预约个数传入参数={}", "searchDate = [" + searchDate + "]");
		SysBossDTO bossInfo = UserUtils.getBossInfo();

		//查询老板下的店铺信息
		ResponseDTO<Object> responseDTO = new ResponseDTO<>();
		ShopBossRelationDTO shopBossRelationDTO = new ShopBossRelationDTO();
		shopBossRelationDTO.setSysBossCode(bossInfo.getId());
		List<ShopBossRelationDTO> shopBossRelationDTOS = shopBossService.ShopBossRelationList(shopBossRelationDTO);

		if (CommonUtils.objectIsEmpty(shopBossRelationDTO)) {
			responseDTO.setResult(StatusConstant.SUCCESS);
			return responseDTO;
		}
		//缓存全部店的预约信息
		ArrayList<Object> arrayList = new ArrayList<>();
		//查询店铺下的预约信息
		for (ShopBossRelationDTO bossRelationDTO : shopBossRelationDTOS) {
			//hash缓存单个店的预约信息
			HashMap<Object, Object> hashMap = new HashMap<>(16);
			ExtShopAppointServiceDTO extShopAppointServiceDTO = new ExtShopAppointServiceDTO();
			extShopAppointServiceDTO.setSysBossCode(bossRelationDTO.getSysBossCode());
			extShopAppointServiceDTO.setSysShopId(bossRelationDTO.getSysShopId());
			extShopAppointServiceDTO.setSearchStartTime(DateUtils.StrToDate(searchDate + " 00:00:00", "datetime"));
			extShopAppointServiceDTO.setSearchEndTime(DateUtils.StrToDate(searchDate + " 23:59:59", "datetime"));
			Integer numberByCriteria = appointmentService.getShopClerkAppointNumberByCriteria(extShopAppointServiceDTO);
			hashMap.put("bossRelationDTO", bossRelationDTO);
			hashMap.put("appointmentNum", numberByCriteria);
			arrayList.add(hashMap);
		}

		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(arrayList);
		logger.info("获取某个老板下面的店的预约信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
		return responseDTO;
	}

	/**
	 * 查询某个美容院某个时间预约个数或某个店员的预约用户列表
	 *
	 * @param sysShopId         店铺id
	 * @param sysClerkId        店员id
	 * @param appointStartTimeS 预约开始时间（范围起始值）
	 * @param appointStartTimeE appointStartTimeE（范围结束值）
	 * @return 某一个时间段的用户列表
	 * @autuor zhangchao
	 */
	@RequestMapping(value = "findUserInfoForShopByTimeControl", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<PageParamDTO<List<ExtShopAppointServiceDTO>>> findUserInfoForShopByTimeControl(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn, String sysShopId, String sysClerkId, String appointStartTimeS, String appointStartTimeE) {

		logger.info("根据预约主键修改此次预约信息传入参数={}", "sysShopId = [" + sysShopId + "], sysClerkId = [" + sysClerkId + "] ,appointStartTimeS = [" + appointStartTimeS + "]", "appointStartTimeE = [" + appointStartTimeE + "]");
		long timeMillis = System.currentTimeMillis();

		PageParamDTO<ExtShopAppointServiceDTO> pageParamDTO = new PageParamDTO<>();
		pageParamDTO.setPageNo(pn);
		pageParamDTO.setPageSize(10);

		//将查询信息放入分页里面
		ExtShopAppointServiceDTO shopAppointServiceDTO = new ExtShopAppointServiceDTO();
		shopAppointServiceDTO.setSysShopId(sysShopId);
		shopAppointServiceDTO.setSysClerkId(sysClerkId);
		shopAppointServiceDTO.setAppointStartTimeS(appointStartTimeS);
		shopAppointServiceDTO.setAppointEndTimeE(appointStartTimeE);

		pageParamDTO.setRequestData(shopAppointServiceDTO);

		PageParamDTO<List<ExtShopAppointServiceDTO>> shopAppointmentUserInfo = appointmentService.findUserInfoForShopByTimeService(pageParamDTO);

		logger.info("获取某次预约详情传入参数耗时{}毫秒", (System.currentTimeMillis() - timeMillis));
		ResponseDTO<PageParamDTO<List<ExtShopAppointServiceDTO>>> responseDTO = new ResponseDTO<>();
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(shopAppointmentUserInfo);
		return responseDTO;
	}

	/**
	 * 获取某个老板下面的某个店下的某个美容师的预约列表
	 *
	 * @param searchDate 2018-09-09
	 * @return
	 */
	@RequestMapping(value = "/getShopClerkAppointmentInfo", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<Object> getShopClerkAppointmentInfo(@RequestParam String searchDate, @RequestParam String sysShopId, @RequestParam String sysClerkId) {
		long currentTimeMillis = System.currentTimeMillis();
		logger.info("获取某个老板下面的店的预约信息传入参数={}", "searchDate = [" + searchDate + "]");
		SysBossDTO bossInfo = UserUtils.getBossInfo();

		//查询店铺下的预约信息
		ExtShopAppointServiceDTO extShopAppointServiceDTO = new ExtShopAppointServiceDTO();
		extShopAppointServiceDTO.setSysBossCode(bossInfo.getId());
		extShopAppointServiceDTO.setSysShopId(sysShopId);
		extShopAppointServiceDTO.setSysClerkId(sysClerkId);
		extShopAppointServiceDTO.setSearchStartTime(DateUtils.StrToDate(searchDate + " 00:00:00", "datetime"));
		extShopAppointServiceDTO.setSearchEndTime(DateUtils.StrToDate(searchDate + " 23:59:59", "datetime"));
		//缓存返回结果
		ArrayList<Object> arrayList = new ArrayList<>();

		List<ShopAppointServiceDTO> list = appointmentService.getShopClerkAppointListByCriteria(extShopAppointServiceDTO);
		if (CommonUtils.objectIsNotEmpty(list)) {
			for (ShopAppointServiceDTO serviceDTO : list) {
				HashMap<Object, Object> hashMap = new HashMap<>(2);
				hashMap.put("sysClerkInfo", redisUtils.getSysClerkDTO(serviceDTO.getSysClerkId()));
				hashMap.put("appointmentInfo", serviceDTO);
				hashMap.put("projectNumber", StringUtils.isBlank(serviceDTO.getShopProjectId()) ? "0" : serviceDTO.getShopProjectId().split(";").length);
				arrayList.add(hashMap);
			}
		}

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(arrayList);
		logger.info("获取某个老板下面的店的预约信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
		return responseDTO;
	}

	/**
	 * 根据预约状态获取某个老板下面的某个店的预约列表
	 *
	 * @param searchDate 2018-09-09
	 * @return
	 */
	@RequestMapping(value = "/getShopAppointmentInfoByStatus", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<Object> getShopAppointmentInfoByStatus(@RequestParam String searchDate, @RequestParam String sysShopId, @RequestParam String sysClerkId, @RequestParam String status) {
		long currentTimeMillis = System.currentTimeMillis();
		logger.info("获取某个老板下面的店的预约信息传入参数={}", "searchDate = [" + searchDate + "]");
		SysBossDTO bossInfo = UserUtils.getBossInfo();

		//查询店铺下的预约信息
		ExtShopAppointServiceDTO extShopAppointServiceDTO = new ExtShopAppointServiceDTO();
		extShopAppointServiceDTO.setSysBossCode(bossInfo.getId());
		extShopAppointServiceDTO.setSysShopId(sysShopId);
		extShopAppointServiceDTO.setStatus(status);
		extShopAppointServiceDTO.setSysClerkId(sysClerkId);
		extShopAppointServiceDTO.setSearchStartTime(DateUtils.StrToDate(searchDate + " 00:00:00", "datetime"));
		extShopAppointServiceDTO.setSearchEndTime(DateUtils.StrToDate(searchDate + " 23:59:59", "datetime"));
		//缓存返回结果
		ArrayList<Object> arrayList = new ArrayList<>();

		List<ShopAppointServiceDTO> list = appointmentService.getShopClerkAppointListByCriteria(extShopAppointServiceDTO);
		if (CommonUtils.objectIsNotEmpty(list)) {
			for (ShopAppointServiceDTO serviceDTO : list) {
				HashMap<Object, Object> hashMap = new HashMap<>(2);
				hashMap.put("sysClerkInfo", redisUtils.getSysClerkDTO(serviceDTO.getSysClerkId()));
				hashMap.put("appointmentInfo", serviceDTO);
				hashMap.put("appointmentNumber", StringUtils.isBlank(serviceDTO.getShopProjectId()) ? "0" : serviceDTO.getShopProjectId().split(";").length);
				arrayList.add(hashMap);
			}
		}

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(arrayList);
		logger.info("获取某个老板下面的店的预约信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
		return responseDTO;
	}


}
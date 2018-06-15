package com.wisdom.beauty.controller.appointment;

import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.beauty.api.dto.ShopBossRelationDTO;
import com.wisdom.beauty.api.dto.ShopProjectInfoDTO;
import com.wisdom.beauty.api.dto.ShopScheduleSettingDTO;
import com.wisdom.beauty.api.enums.ScheduleTypeEnum;
import com.wisdom.beauty.api.errorcode.BusinessErrorCode;
import com.wisdom.beauty.api.extDto.ExtShopAppointServiceDTO;
import com.wisdom.beauty.api.extDto.ShopUserLoginDTO;
import com.wisdom.beauty.client.UserServiceClient;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.*;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.LunarUtils;
import com.wisdom.common.util.RedisLock;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * FileName: AppointmentServiceImpl
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
@Controller
@LoginAnnotations
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

	@Resource
	private ShopClerkScheduleService shopClerkScheduleService;

	@Value("${test.msg}")
	private String msg;


	/**
	 * 根据时间查询某个美容店预约列表
	 *
	 * @param startDate
	 * @return
	 */
	@RequestMapping(value = "shopDayAppointmentInfoByDate", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
    ResponseDTO<Map<String, Object>> shopDayAppointmentInfoByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {

		String preLog = "根据时间查询某个美容店预约列表,";
        String sysShopId = redisUtils.getShopId();

		ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
		ExtShopAppointServiceDTO extShopAppointServiceDTO = new ExtShopAppointServiceDTO();
		extShopAppointServiceDTO.setSearchStartTime(DateUtils.StrToDate(DateUtils.getDateStartTime(startDate), "datetimesec"));
		extShopAppointServiceDTO.setSearchEndTime(DateUtils.StrToDate(DateUtils.getDateEndTime(startDate), "datetimesec"));
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
			responseMap.put("startTime", ScheduleTypeEnum.ALL.getDefaultStartTime());
			responseMap.put("endTime", ScheduleTypeEnum.ALL.getDefaultEndTime());
		} else {
			for (ShopScheduleSettingDTO settingDTO : shopScheduleSettingInfo) {
				if (ScheduleTypeEnum.ALL.getCode().equals(settingDTO.getTypeName())) {
					responseMap.put("startTime", settingDTO.getStartTime());
					responseMap.put("endTime", settingDTO.getEndTime());
				} else {
					responseMap.put("startTime", ScheduleTypeEnum.ALL.getDefaultStartTime());
					responseMap.put("endTime", ScheduleTypeEnum.ALL.getDefaultEndTime());
				}
			}
		}

		//遍历美容师获取预约详情
		for (SysClerkDTO SysClerkDTO : clerkInfo) {

			HashMap<String, Object> shopAppointMap = new HashMap<>(16);

			//查询某个美容师的预约列表
			extShopAppointServiceDTO.setSysClerkId(SysClerkDTO.getId());
			List<ShopAppointServiceDTO> shopAppointServiceDTOS = appointmentService.getShopClerkAppointListByCriteria(extShopAppointServiceDTO);
			extShopAppointServiceDTO.setSysShopId(sysShopId);
			ArrayList<Object> appointInfoList = new ArrayList<>();
			if (CommonUtils.objectIsEmpty(shopAppointServiceDTOS)) {
				logger.info(preLog + "美容师预约列表为空");
				shopAppointMap.put("appointmentInfo",appointInfoList);
				shopAppointMap.put("point", 0);
			} else {
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
		return responseDTO;
	}

	/**
	 * 获取美容店周预约列表
	 *
	 * @param startDate
	 * @param startDate
	 * @return
	 */
	@RequestMapping(value = "shopWeekAppointmentInfoByDate", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
    ResponseDTO<Map<String, Object>> shopWeekAppointmentInfoByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
		ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
		Date startTime = DateUtils.StrToDate(DateUtils.getDateStartTime(startDate), "datetimesec");
		Date endTime = DateUtils.dateIncDays(startTime, 7);
        String sysShopId = redisUtils.getShopId();
		String preLog = "根据时间查询某个美容店周预约列表";
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
			ArrayList<Object> oneClerkList = new ArrayList<>();

            //查询某个美容师7天内的预约列表
			ExtShopAppointServiceDTO extShopAppointServiceDTO = new ExtShopAppointServiceDTO();
			extShopAppointServiceDTO.setSysShopId(sysShopId);
			extShopAppointServiceDTO.setSearchStartTime(loopDate);
			extShopAppointServiceDTO.setSearchEndTime(endTime);
			extShopAppointServiceDTO.setSysClerkId(clerkDTO.getId());
			List<ShopAppointServiceDTO> shopAppointServiceDTOS = appointmentService.getShopClerkAppointListByCriteria(extShopAppointServiceDTO);

			while (loopDate.getTime() < endTime.getTime()) {

				HashMap<Object, Object> map = new HashMap<>(16);
				map.put("week", DateUtils.getWeek(loopDate));
				map.put("day", DateUtils.getDay(loopDate));
				map.put("info", "");
				try {
                    LunarUtils lunarUtils = new LunarUtils(loopDate);
					map.put("lunar", LunarUtils.getChinaDayString(lunarUtils.day));
				} catch (Exception e) {
					logger.error("获取农历失败，失败原因为：" + e.getMessage(), e);
				}

				if (CommonUtils.objectIsEmpty(shopAppointServiceDTOS)) {
					oneClerkList.add(map);
                    loopDate = DateUtils.dateInc(loopDate);
					continue;
				} else {
					List<ShopAppointServiceDTO> projectList = new ArrayList<>();
					for (ShopAppointServiceDTO dto : shopAppointServiceDTOS) {
						if (dto.getAppointStartTime().getTime() > DateUtils.getStartTime(loopDate).getTime() && dto.getAppointStartTime().getTime() < DateUtils.getEndTime(loopDate).getTime()) {
							projectList.add(dto);
						}
					}
					map.put("info", projectList);
                    loopDate = DateUtils.dateInc(loopDate);
                }
				oneClerkList.add(map);
			}
			returnMap.put(clerkDTO.getName(), oneClerkList);
		}

		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(returnMap);
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
	public
	@ResponseBody
	ResponseDTO<ExtShopAppointServiceDTO> getAppointmentInfoById(@RequestParam String shopAppointServiceId) {

		ResponseDTO<ExtShopAppointServiceDTO> responseDTO = new ResponseDTO<>();
		ShopAppointServiceDTO shopAppointInfoFromRedis = redisUtils.getShopAppointInfoFromRedis(shopAppointServiceId);
		ExtShopAppointServiceDTO extShopAppointServiceDTO = new ExtShopAppointServiceDTO();
		if (Objects.nonNull(shopAppointInfoFromRedis)) {
			BeanUtils.copyProperties(shopAppointInfoFromRedis, extShopAppointServiceDTO);
			SysClerkDTO sysClerkDTO = redisUtils.getSysClerkDTO(extShopAppointServiceDTO.getSysClerkId());
			extShopAppointServiceDTO.setSysClerkName(sysClerkDTO.getName());
			extShopAppointServiceDTO.setScore(sysClerkDTO.getScore());
			extShopAppointServiceDTO.setAppointEndTimeE(DateUtils.DateToStr(extShopAppointServiceDTO.getAppointEndTime(), "datetime"));
			extShopAppointServiceDTO.setClerkImage(sysClerkDTO.getPhoto());
			String[] split = extShopAppointServiceDTO.getShopProjectId().split(";");
			List<String> list = Arrays.asList(split);
			List<ShopProjectInfoDTO> projectList = shopProjectService.getProjectDetails(list);
			extShopAppointServiceDTO.setShopProjectInfoDTOS(projectList);
		}

		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(extShopAppointServiceDTO);
		return responseDTO;
	}

	/**
	 * 根据预约主键修改此次预约信息（修改预约状态等）
	 *
	 * @param shopAppointServiceId
	 * @return
	 */
	@RequestMapping(value = "updateAppointmentInfoById", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<ShopAppointServiceDTO> updateAppointmentInfoById(@RequestParam String shopAppointServiceId, @RequestParam String status) {
		ResponseDTO<ShopAppointServiceDTO> responseDTO = new ResponseDTO<>();

		ShopAppointServiceDTO shopAppointServiceDTO = new ShopAppointServiceDTO();
		shopAppointServiceDTO.setId(shopAppointServiceId);
		shopAppointServiceDTO.setStatus(status);
		int info = appointmentService.updateAppointmentInfo(shopAppointServiceDTO);
		logger.debug("根据预约主键修改此次预约信息，执行结果为{}", info);

		//更新redis
		redisUtils.updateShopAppointInfoToRedis(shopAppointServiceDTO);

		responseDTO.setResult(info > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
		return responseDTO;
	}

	/**
	 * 获取我的预约列表（修改预约状态等）
	 *
	 * @return
	 */
	@RequestMapping(value = "getMyAppointInfoList", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<Object> getMyAppointInfoList(@RequestParam String status) {
		ResponseDTO<Object> responseDTO = new ResponseDTO<>();
		UserInfoDTO userInfo = UserUtils.getUserInfo();
		ExtShopAppointServiceDTO shopAppointServiceDTO = new ExtShopAppointServiceDTO();
		shopAppointServiceDTO.setSysUserId(userInfo.getId());
		shopAppointServiceDTO.setStatus(status);
		ShopUserLoginDTO userLoginShop = redisUtils.getUserLoginShop(userInfo.getId());
		if(null == userLoginShop){
			responseDTO.setResult(StatusConstant.FAILURE);
			responseDTO.setErrorInfo("未获取到关联店铺信息");
			return responseDTO;
		}
		String sysShopId = userLoginShop.getSysShopId();
		shopAppointServiceDTO.setSysShopId(sysShopId);
		List<ShopAppointServiceDTO> shopAppointServiceDTOS = appointmentService.getShopClerkAppointListByCriteria(shopAppointServiceDTO);

		logger.debug("获取我的预约列表预约信息，执行结果为{}", shopAppointServiceDTOS);

		responseDTO.setResponseData(shopAppointServiceDTOS);
		responseDTO.setResult(StatusConstant.SUCCESS);
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
	public
	@ResponseBody
	ResponseDTO<List<SysClerkDTO>> getShopClerkList(@RequestParam String pageNo, @RequestParam String pageSize) {

		ResponseDTO<List<SysClerkDTO>> responseDTO = new ResponseDTO<>();

        String sysShopId = redisUtils.getShopId();

		if (StringUtils.isBlank(sysShopId)) {
			responseDTO.setResult(StatusConstant.FAILURE);
			responseDTO.setErrorInfo("获取店铺主键为空");
			return responseDTO;
		}
		List<SysClerkDTO> clerkDTOS = userServiceClient.getClerkInfo(sysShopId);
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(clerkDTOS);
		return responseDTO;
	}


    /**
	 * 保存用户的预约信息
	 */
	@RequestMapping(value = "saveUserAppointInfo", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<Map> saveUserAppointInfo(@RequestBody ExtShopAppointServiceDTO shopAppointServiceDTO) {
		ResponseDTO<Map> responseDTO = new ResponseDTO<>();
		//针对于美容师加锁
		RedisLock redisLock = new RedisLock("appointBeauty" + shopAppointServiceDTO.getSysClerkId());
		try {
			redisLock.lock();
			responseDTO = appointmentService.saveUserShopAppointInfo(shopAppointServiceDTO);
		} catch (Throwable e) {
			logger.error("保存用户的预约信息失败，失败信息为" + e.getMessage(), e);
			responseDTO.setResult(StatusConstant.FAILURE);
			responseDTO.setErrorInfo("数据异常,请联系客服");
			return responseDTO;
		}finally {
			redisLock.unlock();
		}
		return responseDTO;
	}

	/**
	 * 修改用户的预约信息
	 */
	@RequestMapping(value = "updateUserAppointInfo", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<Map> updateUserAppointInfo(@RequestBody ExtShopAppointServiceDTO shopAppointServiceDTO) {
		//针对于美容师加锁
		RedisLock redisLock = new RedisLock("appointBeauty"+shopAppointServiceDTO.getSysClerkId());
		ResponseDTO<Map> responseDTO = null;
		try {
			redisLock.lock();
			responseDTO = appointmentService.updateUserAppointInfo(shopAppointServiceDTO);
		} catch (Throwable e) {
			logger.error("修改用户的预约信息失败，失败信息为" + e.getMessage(), e);
			responseDTO.setResult(StatusConstant.FAILURE);
			return responseDTO;
		}finally {
			redisLock.unlock();
		}
		return responseDTO;
	}

	/**
	 * 查询某个美容院某个时间预约个数或某个店员的预约数量
	 *
	 * @param sysClerkId        店员id
	 * @param appointStartTimeS 预约开始时间（范围起始值）
	 * @param appointStartTimeE appointStartTimeE（范围结束值）
	 * @return 某一个时间段的数量数量
	 * @autur zhangchao
	 */
	@RequestMapping(value = "findNumForShopByTimeControl", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
    ResponseDTO<HashMap<String, String>> findNumForShopByTimeControl(@RequestParam(required = false) String sysShopId, String sysClerkId, String appointStartTimeS, String appointStartTimeE) {

		HashMap<String, String> shopAppointmentNum = appointmentService.findNumForShopByTimeService(sysShopId, sysClerkId, appointStartTimeS, appointStartTimeE);
		ResponseDTO<HashMap<String, String>> responseDTO = new ResponseDTO<>();
		//判断查询是否成功
		if (("success").equals(shopAppointmentNum.get("resultCode"))) {
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

		SysBossDTO bossInfo = UserUtils.getBossInfo();

		//查询老板下的店铺信息
		ResponseDTO<Object> responseDTO = new ResponseDTO<>();
		ShopBossRelationDTO shopBossRelationDTO = new ShopBossRelationDTO();
        shopBossRelationDTO.setSysBossCode(bossInfo.getSysBossCode());
		List<ShopBossRelationDTO> shopBossRelationDTOS = shopBossService.shopBossRelationList(shopBossRelationDTO);

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
    ResponseDTO<Object> getShopClerkAppointmentInfo(@RequestParam String searchDate, @RequestParam String sysClerkId) {

		SysBossDTO bossInfo = UserUtils.getBossInfo();
        String sysShopId = redisUtils.getShopId();
		//查询店铺下的预约信息
		ExtShopAppointServiceDTO extShopAppointServiceDTO = new ExtShopAppointServiceDTO();
        extShopAppointServiceDTO.setSysBossCode(bossInfo.getSysBossCode());
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
				int length = serviceDTO.getShopProjectId().split(";").length;
				hashMap.put("projectNumber", length-1);
				arrayList.add(hashMap);
			}
		}

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(arrayList);
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
	ResponseDTO<Object> getShopAppointmentInfoByStatus(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date searchDate, @RequestParam(required = false) String sysShopId, @RequestParam(required = false) String sysClerkId, @RequestParam(required = false) String status) {

		//查询店铺下的预约信息
		ExtShopAppointServiceDTO extShopAppointServiceDTO = new ExtShopAppointServiceDTO();
        extShopAppointServiceDTO.setSysBossCode(redisUtils.getBossCode());
        if (StringUtils.isBlank(sysShopId)){
            sysShopId = redisUtils.getShopId();
        }
        //员工端查询的话，如果sysClerkId为空
        if(StringUtils.isBlank(sysClerkId) && null != UserUtils.getClerkInfo()){
            sysClerkId = UserUtils.getClerkInfo().getId();
        }
		extShopAppointServiceDTO.setSysShopId(sysShopId);
		extShopAppointServiceDTO.setSysClerkId(sysClerkId);
		extShopAppointServiceDTO.setStatus(status);
		extShopAppointServiceDTO.setSearchStartTime(DateUtils.getStartTime(searchDate));
		extShopAppointServiceDTO.setSearchEndTime(DateUtils.getEndTime(searchDate));

		if (StringUtils.isBlank(extShopAppointServiceDTO.getSysShopId())) {
			SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
			extShopAppointServiceDTO.setSysShopId(clerkInfo.getSysShopId());
		}
		if (StringUtils.isBlank(extShopAppointServiceDTO.getSysClerkId())) {
			SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
			extShopAppointServiceDTO.setSysClerkId(clerkInfo.getId());
		}
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
		return responseDTO;
	}

	/**
	 * 获取客户的预约详情（员工端）
	 * add by 盛小龙@2018.05.23
	 *
	 * @return
	 */
	@RequestMapping(value = "getClerkAppointmentInfoById", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<ExtShopAppointServiceDTO> getClerkAppointmentInfoById(@RequestParam String shopAppointServiceId) {
		ResponseDTO<ExtShopAppointServiceDTO> responseDTO = new ResponseDTO<>();
		ShopAppointServiceDTO shopAppointInfoFromRedis = redisUtils.getShopAppointInfoFromRedis(shopAppointServiceId);
		ExtShopAppointServiceDTO extShopAppointServiceDTO = new ExtShopAppointServiceDTO();
		if (null != shopAppointInfoFromRedis) {
			BeanUtils.copyProperties(shopAppointInfoFromRedis, extShopAppointServiceDTO);
			UserInfoDTO userInfoDTO = userServiceClient .getUserInfoFromUserId(shopAppointInfoFromRedis.getSysUserId());
			extShopAppointServiceDTO.setClerkImage(userInfoDTO.getPhoto());
		}
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(extShopAppointServiceDTO);
		return responseDTO;
	}

}
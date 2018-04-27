package com.wisdom.beauty.controller.appointment;

import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.beauty.api.dto.ShopScheduleSettingDTO;
import com.wisdom.beauty.api.errorcode.BusinessErrorCode;
import com.wisdom.beauty.api.extDto.ExtShopAppointServiceDTO;
import com.wisdom.beauty.client.UserServiceClient;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.ShopAppointmentService;
import com.wisdom.beauty.core.service.ShopWorkService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.LunarUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private RedisUtils redisUtils;

	/**
	 * 根据时间查询某个美容店预约列表
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
		logger.info(preLog+"美容店主键为={}",sysShopId);

		ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
		ExtShopAppointServiceDTO extShopAppointServiceDTO = new ExtShopAppointServiceDTO();
		extShopAppointServiceDTO.setSearchStartTime(DateUtils.StrToDate(startDate,"datetime"));
		extShopAppointServiceDTO.setSearchEndTime(DateUtils.StrToDate(endDate,"datetime"));
		extShopAppointServiceDTO.setSysShopId(sysShopId);

		//根据时间查询当前店下所有美容师
		List<SysClerkDTO> clerkInfo = userServiceClient.getClerkInfo(sysShopId);

		if (judgeNull(responseDTO, preLog, clerkInfo)) return responseDTO;
		logger.info(preLog+"根据时间查询当前店下所有美容师个数={}",clerkInfo.size());

		HashMap<String, Object> responseMap = new HashMap<>(32);

		//查询某个店的排班信息
		ShopScheduleSettingDTO shopScheduleSettingDTO = new ShopScheduleSettingDTO();
		shopScheduleSettingDTO.setSysShopId(sysShopId);
		List<ShopScheduleSettingDTO> shopScheduleSettingInfo = workService.getShopScheduleSettingInfo(shopScheduleSettingDTO);
		if (shopScheduleSettingInfo == null) {
			logger.error("查询某个店的排班信息为空,美容店主键为{}","sysShopId = [" + sysShopId + "]");
			//早晚班默认值
			responseMap.put("startTime", "09:00");
			responseMap.put("endTime","23:00");
		}else{
			for (ShopScheduleSettingDTO settingDTO :shopScheduleSettingInfo) {
				if("3".equals(settingDTO.getTypeName())){
					responseMap.put("startTime",settingDTO.getStartTime());
					responseMap.put("endTime",settingDTO.getEndTime());
				} else {
					responseMap.put("startTime", "9:00");
					responseMap.put("endTime", "23:00");
				}
			}
		}

		//遍历美容师获取预约详情
		for (SysClerkDTO SysClerkDTO : clerkInfo) {

			HashMap<String, Object> shopAppointMap = new HashMap<>(16);

			//查询某个美容师的预约列表
			extShopAppointServiceDTO.setSysClerkId(SysClerkDTO.getId());
			List<ShopAppointServiceDTO> shopAppointServiceDTOS = appointmentService.getShopClerkAppointListByCriteria(extShopAppointServiceDTO);

			if(CommonUtils.objectIsEmpty(shopAppointServiceDTOS)){
				logger.info(preLog+"美容师预约列表为空");
				shopAppointMap.put("appointmentInfo","");
				shopAppointMap.put("point",0);
			}else{
				ArrayList<Object> appointInfoList = new ArrayList<>();
				for (ShopAppointServiceDTO serviceDTO : shopAppointServiceDTOS) {
					try {
						HashMap<String, Object> hashMap = CommonUtils.beanToMap(serviceDTO);
						String str = CommonUtils.getArrayNo(DateUtils.DateToStr(serviceDTO.getAppointStartTime(),"time"),
								DateUtils.DateToStr(serviceDTO.getAppointEndTime(),"time"));
						hashMap.put("scheduling",str);
						appointInfoList.add(hashMap);
					} catch (Exception e) {
						logger.error(preLog+"异常，异常信息为{}"+e.getMessage(),e);
					}
				}
				shopAppointMap.put("appointmentInfo",appointInfoList);
				shopAppointMap.put("point",shopAppointServiceDTOS.size());
			}
			responseMap.put(SysClerkDTO.getName(), shopAppointMap);
		}

		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(responseMap);
		logger.info(preLog+"耗时{}毫秒",(System.currentTimeMillis()-startTime));
		return  responseDTO;
	}

	/**
	 * 获取美容店周预约列表
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
																		  @RequestParam String startDate,@RequestParam String endDate) {
        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
        Date startTime = DateUtils.StrToDate(startDate, "datetime");
        Date endTime = DateUtils.StrToDate(endDate, "datetime");


        String preLog = "根据时间查询某个美容店周预约列表,";
        long start = System.currentTimeMillis();
        logger.info(preLog + "美容店主键为={}", sysShopId);

        //根据时间查询当前店下所有美容师
		List<SysClerkDTO> clerkInfo = userServiceClient.getClerkInfo(sysShopId);

        if (judgeNull(responseDTO, preLog, clerkInfo)) return responseDTO;
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
	 * @param shopAppointServiceId
	 * @return
	 */
	@RequestMapping(value = "getAppointmentInfoById", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<ShopAppointServiceDTO> getAppointmentInfoById(@RequestParam String shopAppointServiceId) {

        long startTime = System.currentTimeMillis();
        logger.info("获取某次预约详情传入参数={}", "shopAppointServiceId = [" + shopAppointServiceId + "]");

		ResponseDTO<ShopAppointServiceDTO> responseDTO = new ResponseDTO<>();
		ShopAppointServiceDTO shopAppointInfoFromRedis = redisUtils.getShopAppointInfoFromRedis(shopAppointServiceId);

        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(shopAppointInfoFromRedis);
        logger.info("获取某次预约详情传入参数耗时{}毫秒", (System.currentTimeMillis() - startTime));

		return  responseDTO;
	}

	/**
	 * 根据预约主键修改此次预约信息（修改预约状态等）
	 * @param shopAppointServiceId
	 * @return
	 */
	@RequestMapping(value = "updateAppointmentInfoById", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<ShopAppointServiceDTO> updateAppointmentInfoById(@RequestParam String shopAppointServiceId, String status) {
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

}

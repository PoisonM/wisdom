package com.wisdom.beauty.controller.appointment;

import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.beauty.api.errorcode.BusinessErrorCode;
import com.wisdom.beauty.api.extDto.ExtShopAppointServiceDTO;
import com.wisdom.beauty.core.service.AppointmentService;
import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Resource
	private AppointmentService appointmentService;

	/**
	 * 获取美容店预约列表
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
		ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
		ExtShopAppointServiceDTO extShopAppointServiceDTO = new ExtShopAppointServiceDTO();
		extShopAppointServiceDTO.setSearchStartTime(DateUtils.StrToDate(startDate,"datetime"));
		extShopAppointServiceDTO.setSearchEndTime(DateUtils.StrToDate(endDate,"datetime"));
		extShopAppointServiceDTO.setSysShopId(sysShopId);

		//根据时间查询当前店下有预约记录的所有美容师
		List<ShopAppointServiceDTO> dtos = appointmentService.getShopAppointClerkInfoByCriteria(extShopAppointServiceDTO);

		if(CommonUtils.objectIsEmpty(dtos)){
			responseDTO.setResult(StatusConstant.FAILURE);
			responseDTO.setErrorInfo(BusinessErrorCode.ERROR_NULL_RECORD.getCode());
			return responseDTO;
		}

		HashMap<String, Object> responseMap = new HashMap<>(32);

		//todo responseMap中保存上班日期
		responseMap.put("startTime","9:00");
		responseMap.put("endTime","23:00");

		//遍历美容师获取预约详情
		for (ShopAppointServiceDTO shopAppointServiceDTO : dtos) {
			HashMap<String, Object> shopAppointMap = new HashMap<>(16);
			extShopAppointServiceDTO.setSysClerkId(shopAppointServiceDTO.getSysClerkId());
			List<ShopAppointServiceDTO> shopAppointServiceDTOS = appointmentService.getShopClerkAppointListByCriteria(extShopAppointServiceDTO);

			ArrayList<Object> objects = new ArrayList<>();
			for(ShopAppointServiceDTO serviceDTO :shopAppointServiceDTOS){
				try {
					HashMap<String, Object> hashMap = CommonUtils.beanToMap(serviceDTO);
					String str = CommonUtils.getArrayNo(DateUtils.DateToStr(serviceDTO.getAppointStartTime(),"time"),
							DateUtils.DateToStr(serviceDTO.getAppointEndTime(),"time"));
					hashMap.put("scheduling",str);
					objects.add(hashMap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			shopAppointMap.put("appointmentInfo",objects);
			shopAppointMap.put("point",shopAppointServiceDTOS.size());

			responseMap.put(shopAppointServiceDTO.getSysClerkName(),shopAppointMap);
		}

		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(responseMap);
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
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopAppointServiceDTO>> shopWeekAppointmentInfoByDate(@RequestParam String sysShopId,
																		  @RequestParam String startDate,@RequestParam String endDate) {
		ResponseDTO<List<ShopAppointServiceDTO>> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}

	/**
	 * 获取某次预约详情
	 * @param shopAppointServiceId
	 * @return
	 */
	@RequestMapping(value = "getAppointmentInfoById", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<ShopAppointServiceDTO> getAppointmentInfoById(@RequestParam String shopAppointServiceId) {
		ResponseDTO<ShopAppointServiceDTO> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}

	/**
	 * 根据预约主键修改此次预约信息（修改预约状态等）
	 * @param shopAppointServiceId
	 * @return
	 */
	@RequestMapping(value = "updateAppointmentInfoById", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<ShopAppointServiceDTO> updateAppointmentInfoById(@RequestParam String shopAppointServiceId) {
		ResponseDTO<ShopAppointServiceDTO> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}

}

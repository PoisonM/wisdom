package com.wisdom.beauty.controller.appointment;

import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.common.dto.system.ResponseDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
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

	/**
	 * 获取美容店日预约列表
	 * @param sysShopId
	 * @param date
	 * @return
	 */
	@RequestMapping(value = "shopDayAppointmentInfoByDate", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopAppointServiceDTO>> shopDayAppointmentInfoByDate(@RequestParam String sysShopId,
																		  @RequestParam String date) {
		ResponseDTO<List<ShopAppointServiceDTO>> responseDTO = new ResponseDTO<>();

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

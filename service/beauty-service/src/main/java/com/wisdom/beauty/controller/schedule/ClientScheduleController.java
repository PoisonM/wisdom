package com.wisdom.beauty.controller.schedule;

import com.wisdom.beauty.api.dto.ShopClerkScheduleDTO;
import com.wisdom.beauty.api.enums.ScheduleTypeEnum;
import com.wisdom.beauty.core.service.ShopClerkScheduleService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.IdGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhanghuan on 2018/6/26.
 */
@Controller
@RequestMapping(value = "clerkSchedule")
public class ClientScheduleController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShopClerkScheduleService shopClerkScheduleService;

	/**
	 * @Author:zhanghuan
	 * @Param:
	 * @Return:
	 * @Description: 创建家人的时候，调用此接口，设置该家人的默认排班
	 * @Date:2018/6/26 10:39
	 */
	@RequestMapping(value = "/saveOneClerkSchedule", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	ResponseDTO<Object> saveOneClerkSchedule(@RequestBody SysClerkDTO sysClerkDTO) {

		List clerkScheduleList = new ArrayList<>();
		// 获取某个月的所有天的集合
		Date searchDate = new Date();
		List<String> monthFullDay = DateUtils.getMonthFullDay(Integer.parseInt(DateUtils.getYear(searchDate)),
				Integer.parseInt(DateUtils.getMonth(searchDate)), 0);
		for (String string : monthFullDay) {
			ShopClerkScheduleDTO scheduleDTO = new ShopClerkScheduleDTO();
			scheduleDTO.setId(IdGen.uuid());
			scheduleDTO.setScheduleDate(DateUtils.StrToDate(string, "date"));
			scheduleDTO.setSysShopId(sysClerkDTO.getSysShopId());
			scheduleDTO.setCreateDate(new Date());
			scheduleDTO.setScheduleType(ScheduleTypeEnum.ALL.getCode());

			scheduleDTO.setSysClerkId(sysClerkDTO.getId());
			scheduleDTO.setSysClerkName(sysClerkDTO.getName());
			scheduleDTO.setSysBossCode(sysClerkDTO.getSysBossCode());
			clerkScheduleList.add(scheduleDTO);
		}
		// 批量插入
		int number = shopClerkScheduleService.saveShopClerkScheduleList(clerkScheduleList);
		logger.info("批量插入{}条数据", number);
		ResponseDTO<Object> responseDTO = new ResponseDTO<>();
		responseDTO.setResponseData(number);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}
}

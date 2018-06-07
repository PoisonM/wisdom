package com.wisdom.timer.controller;

import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wisdom.timer.service.business.BusinessRunTimeService;

import java.util.Date;

@RestController
public class TimeServiceController {
	Logger logger = LoggerFactory.getLogger(TimeServiceController.class);

	@Autowired
	private BusinessRunTimeService businessRunTimeService;


	@RequestMapping(value = "/MTMonthlyIncomeCalc",method=RequestMethod.POST)
	void MTMonthlyIncomeCalc(@RequestParam("businessType") String businessType, @RequestParam("startDateM") Date startDateM , @RequestParam("endDateM") Date endDateM,@RequestParam("isPullMessage") String isPullMessage) {
		try{
			businessRunTimeService.MTMonthlyIncomeCalc(businessType,startDateM,endDateM,isPullMessage);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
	}

}

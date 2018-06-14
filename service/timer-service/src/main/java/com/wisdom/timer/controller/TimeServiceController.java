package com.wisdom.timer.controller;

import com.wisdom.timer.service.business.BusinessRunTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class TimeServiceController {
	Logger logger = LoggerFactory.getLogger(TimeServiceController.class);

	@Autowired
	private BusinessRunTimeService businessRunTimeService;


	@RequestMapping(value = "/MTMonthlyIncomeCalc",method=RequestMethod.POST)
	void MTMonthlyIncomeCalc(@RequestParam("businessType") String businessType, @RequestParam("startDateM") Date startDateM , @RequestParam("endDateM") Date endDateM,@RequestParam("isPullMessage") String isPullMessage,@RequestParam("key") String key) {
		try{
			businessRunTimeService.MTMonthlyIncomeCalc(businessType,startDateM,endDateM,isPullMessage,key);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
	}

}

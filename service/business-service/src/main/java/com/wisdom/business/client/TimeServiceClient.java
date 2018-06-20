package com.wisdom.business.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@FeignClient("timer-service")
public interface TimeServiceClient {

    @RequestMapping(value = "/MTMonthlyIncomeCalc",method=RequestMethod.POST)
    void MTMonthlyIncomeCalc(@RequestParam("businessType") String businessType, @RequestParam("startDateM") Date startDateM, @RequestParam("endDateM") Date endDateM, @RequestParam("isPullMessage") String isPullMessage,@RequestParam("key") String key);

}

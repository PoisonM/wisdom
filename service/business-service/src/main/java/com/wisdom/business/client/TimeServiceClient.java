package com.wisdom.business.client;

import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.user.RealNameInfoDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@FeignClient("timer-service")
public interface TimeServiceClient {

    @RequestMapping(value = "/monthlyIncomeCalcM",method=RequestMethod.POST)
    void monthlyIncomeCalcM(@RequestParam("businessType") String businessType, @RequestParam("startDateM") Date startDateM, @RequestParam("endDateM") Date endDateM, @RequestParam("isPullMessage") String isPullMessage);
}

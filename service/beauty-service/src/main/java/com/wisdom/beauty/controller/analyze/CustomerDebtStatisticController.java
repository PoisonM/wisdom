package com.wisdom.beauty.controller.analyze;

import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.common.dto.beauty.CustomerDebtDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@LoginAnnotations
@RequestMapping(value = "analyze")
public class CustomerDebtStatisticController {

	//获取门店某天的业绩
	@RequestMapping(value = "customerDebtAnalyze", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<CustomerDebtDTO>> customerDebtAnalyze(@RequestParam String shopId,@RequestParam String date) {

		ResponseDTO<List<CustomerDebtDTO>> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}

}

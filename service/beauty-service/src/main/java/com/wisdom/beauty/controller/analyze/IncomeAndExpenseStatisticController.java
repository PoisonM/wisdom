package com.wisdom.beauty.controller.analyze;

import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.common.dto.beauty.ShopAchievementDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "analyze")
public class IncomeAndExpenseStatisticController {

	//获取某个boss下的所有门店的收支分析
	@RequestMapping(value = "shopIncomeAndOutcomeAnalyzeByDate", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopAchievementDTO>> shopIncomeAnalyzeByDate(@RequestParam String date) {
		ResponseDTO<List<ShopAchievementDTO>> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}

}

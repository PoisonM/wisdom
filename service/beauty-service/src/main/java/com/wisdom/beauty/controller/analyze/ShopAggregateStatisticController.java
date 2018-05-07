package com.wisdom.beauty.controller.analyze;

import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.common.dto.beauty.ShopAchievementDTO;
import com.wisdom.common.dto.system.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "analyze")
public class ShopAggregateStatisticController {

	//获取门店某天的业绩
	@RequestMapping(value = "shopIncomeByDate", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<String> shopIncomeByDate(@RequestParam String shopId,@RequestParam String date) {

		ResponseDTO<String> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}

	//获取门店某天的耗卡
	@RequestMapping(value = "cardExpenseByDate", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<String> cardExpenseByDate(@RequestParam String shopId,@RequestParam String date) {

		ResponseDTO<String> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}

	//获取门店某天的人头数
	@RequestMapping(value = "customerArriveNumByDate", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<String> customerArriveNumByDate(@RequestParam String shopId,@RequestParam String date) {

		ResponseDTO<String> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}

	//获取门店某天的人次数
	@RequestMapping(value = "customerArriveTimesByDate", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<String> customerArriveTimesByDate(@RequestParam String shopId,@RequestParam String date) {

		ResponseDTO<String> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}

	//获取门店某天的新客
	@RequestMapping(value = "customerNewAddNumByDate", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<String> customerNewAddNumByDate(@RequestParam String shopId,@RequestParam String date) {

		ResponseDTO<String> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}

	//获取门店某天的服务次数
	@RequestMapping(value = "customerServiceTimesByDate", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<String> customerServiceTimesByDate(@RequestParam String shopId,@RequestParam String date) {

		ResponseDTO<String> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}

	//获取某个boss下的所有门店的业绩和耗卡
	@RequestMapping(value = "shopAggregateAnalyzeByDate", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopAchievementDTO>> shopAggregateAnalyzeByDate(@RequestParam String date) {

		ResponseDTO<List<ShopAchievementDTO>> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}


}

package com.wisdom.beauty.controller.analyze;

import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.beauty.BeautyShopDTO;
import com.wisdom.common.dto.beauty.ShopAchievementDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.dto.system.*;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.excel.ExportExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

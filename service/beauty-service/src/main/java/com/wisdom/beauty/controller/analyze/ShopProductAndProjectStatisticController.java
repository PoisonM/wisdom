package com.wisdom.beauty.controller.analyze;

import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.common.dto.beauty.ShopProjectAndProductAnalyzeDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "analyze")
public class ShopProductAndProjectStatisticController {

	//获取门店某天的业绩
	@RequestMapping(value = "shopProjectAndProductAnalyzeByDate", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopProjectAndProductAnalyzeDTO>> shopProjectAndProductAnalyzeByDate(@RequestParam String shopId,
																						  @RequestParam String date,
																						  @RequestParam String type) {

		ResponseDTO<List<ShopProjectAndProductAnalyzeDTO>> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}

}

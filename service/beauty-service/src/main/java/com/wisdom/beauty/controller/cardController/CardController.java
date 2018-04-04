package com.wisdom.beauty.controller.cardController;

import com.wisdom.beauty.api.dto.ShopCustomerRechargeCardDTO;
import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.common.dto.system.ResponseDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * FileName: cardController
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
@Controller
@RequestMapping(value = "cardInfo")
public class CardController {

	/**
	 * 查询某个用户的充值卡列表信息
	 * @param sysCustomerId
	 * @param sysShopId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "getCustomerRechargeCardList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopCustomerRechargeCardDTO>> getCustomerRechargeCardList(@RequestParam String sysCustomerId,
																			   @RequestParam String sysShopId,
																			   @RequestParam String startDate,
																			   @RequestParam String endDate) {
		ResponseDTO<List<ShopCustomerRechargeCardDTO>> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}


}

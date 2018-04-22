package com.wisdom.beauty.controller.account;

import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;
import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.common.dto.system.ResponseDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * FileName: AccountController
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
@Controller
@RequestMapping(value = "accountInfo")
public class AccountController {

	/**
     * 根据条件查询某个美容院某个用户的账户记录，包括收银记录和划卡记录,账户记录列表
	 * @param type
     * @param startDate
     * @param endDate
     * @return
     */
	@RequestMapping(value = "getCustomerConsumeRecord", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopUserConsumeRecordDTO>> getCustomerConsumeRecord(@RequestParam String type,
																		 @RequestParam String startDate,
																		 @RequestParam String endDate,
																		 @RequestParam String sysShopId) {
		ResponseDTO<List<ShopUserConsumeRecordDTO>> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}

	/**
     * 根据账户记录id查询某次账户记录的详细信息
	 * @param shopCustomerConsumeRecordId
     * @return
     */
	@RequestMapping(value = "getCustomerConsumeRecordInfo", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<ShopUserConsumeRecordDTO> getCustomerConsumeRecord(@RequestParam String shopCustomerConsumeRecordId) {
		ResponseDTO<ShopUserConsumeRecordDTO> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}



}

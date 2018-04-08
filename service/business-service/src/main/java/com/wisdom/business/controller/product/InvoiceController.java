package com.wisdom.business.controller.product;

import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.service.product.InvoiceService;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.product.InvoiceDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 关于账户管理
 * @author frank
 * @date 2015-10-14
 */
@Controller
@RequestMapping(value = "product")
public class InvoiceController {

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private UserServiceClient userServiceClient;

	/**
	 * 发票
	 *
	 * input PageParamDto
	 *
	 */
	@RequestMapping(value = "addInvoiceInfo", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO addInvoiceInfo(@RequestBody InvoiceDTO invoiceDTO) {
		ResponseDTO responseDTO = new ResponseDTO<>();
		UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
		if (userInfoDTO!=null)
		{
			invoiceDTO.setUserId(userInfoDTO.getId());
			invoiceService.addInvoiceInfo(invoiceDTO);
			responseDTO.setResult(StatusConstant.SUCCESS);
		}
		else
		{
			responseDTO.setResult(StatusConstant.FAILURE);
		}

		return responseDTO;
	}

	/**
	 * 查询发票
	 *
	 * input PageParamDto
	 *
	 *
	 */
	@RequestMapping(value = "getInvoiceDetailById", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<InvoiceDTO> getInvoiceDetailById(@RequestParam String transactionId) {
		ResponseDTO<InvoiceDTO> responseDTO = new ResponseDTO<>();
		UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
		InvoiceDTO invoiceDTO = invoiceService.getInvoiceDetailById(userInfoDTO.getId(),transactionId);
		responseDTO.setResponseData(invoiceDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}


}


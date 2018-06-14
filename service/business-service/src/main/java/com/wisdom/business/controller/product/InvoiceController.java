package com.wisdom.business.controller.product;

import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.controller.account.AccountController;
import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.service.product.InvoiceService;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.product.InvoiceDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 关于账户管理
 * @author
 * @date 2015-10-14
 */
@Controller
@RequestMapping(value = "product")
public class InvoiceController {

	@Autowired
	private InvoiceService invoiceService;

	Logger logger = LoggerFactory.getLogger(InvoiceController.class);

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
		long startTime = System.currentTimeMillis();
		logger.info("发票==={}开始" , startTime);
		ResponseDTO responseDTO = new ResponseDTO<>();
		UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
		logger.info("用户添加发票信息=="+userInfoDTO.getMobile());
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
		logger.info("发票,耗时{}毫秒", (System.currentTimeMillis() - startTime));
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
		long startTime = System.currentTimeMillis();
		logger.info("查询发票==={}开始" , startTime);
		ResponseDTO<InvoiceDTO> responseDTO = new ResponseDTO<>();
		UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
		logger.info("查询发票用户手机号==={},transaction==={}" , userInfoDTO.getId(),transactionId);
		InvoiceDTO invoiceDTO = invoiceService.getInvoiceDetailById(userInfoDTO.getId(),transactionId);
		responseDTO.setResponseData(invoiceDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
		logger.info("查询发票,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

}


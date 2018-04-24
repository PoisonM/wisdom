package com.wisdom.beauty.controller.card;

import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.api.errorcode.BusinessErrorCode;
import com.wisdom.beauty.core.service.ShopCardService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * FileName: card
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
@Controller
@RequestMapping(value = "cardInfo")
public class CardController {

	@Resource
	private ShopCardService cardService;
	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 查询某个用户的充值卡列表信息
	 * @param sysUserId
	 * @param sysShopId
	 * @return
	 */
	@RequestMapping(value = "getUserRechargeCardList", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopUserRechargeCardDTO>> getUserRechargeCardList(@RequestParam String sysUserId,
																	   @RequestParam String sysShopId) {
		long currentTimeMillis = System.currentTimeMillis();

		logger.info("查询某个用户的充值卡列表信息传入参数={}", "sysUserId = [" + sysUserId + "], sysShopId = [" + sysShopId + "]");
		ResponseDTO<List<ShopUserRechargeCardDTO>> responseDTO = new ResponseDTO<>();

		if (StringUtils.isBlank(sysUserId) || StringUtils.isBlank(sysShopId)) {
			logger.debug("传入参数为空， {}", "sysUserId = [" + sysUserId + "], sysShopId = [" + sysShopId + "]");
			return null;
		}

		ShopUserRechargeCardDTO shopUserRechargeCardDTO = new ShopUserRechargeCardDTO();
		shopUserRechargeCardDTO.setSysUserId(sysUserId);
		shopUserRechargeCardDTO.setSysShopId(sysShopId);
		List<ShopUserRechargeCardDTO> userRechargeCardList = cardService.getUserRechargeCardList(shopUserRechargeCardDTO);
		if (CommonUtils.objectIsEmpty(userRechargeCardList)) {
			logger.debug("查询某个用户的充值卡列表信息查询结果为空，参数 {}", "sysUserId = [" + sysUserId + "], sysShopId = [" + sysShopId + "]");
			responseDTO.setResult(StatusConstant.SUCCESS);
			responseDTO.setErrorInfo(BusinessErrorCode.ERROR_NULL_RECORD.getCode());
			return responseDTO;
		}

		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(userRechargeCardList);

		logger.info("查询某个用户的充值卡列表信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
		return  responseDTO;
	}


}

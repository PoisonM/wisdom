package com.wisdom.beauty.controller.account;

import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;
import com.wisdom.beauty.api.responseDto.CustomerAccountResponseDto;
import com.wisdom.beauty.core.service.SysUserAccountService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FileName: AccountController
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
@Controller
@LoginAnnotations
@RequestMapping(value = "accountInfo")
public class AccountController {

    @Autowired
    private SysUserAccountService sysUserAccountService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 获取用户在美容院的账户信息
     * @Date:2018/4/8
     */
    @RequestMapping(value = "/findShopUserInfo/{userId}", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<CustomerAccountResponseDto> findShopUserInfo(@PathVariable String userId) {
        ResponseDTO<CustomerAccountResponseDto> responseDTO = new ResponseDTO<>();
		SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
		CustomerAccountResponseDto customerAccountResponseDto = sysUserAccountService.getSysAccountListByUserId(userId,clerkInfo.getSysShopId());
        if (customerAccountResponseDto != null) {
            responseDTO.setResponseData(customerAccountResponseDto);
        }
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

	/**
     * 根据条件查询某个美容院某个用户的账户记录，包括收银记录和划卡记录,账户记录列表
	 * @param type
     * @param startDate
     * @param endDate
     * @return
     */
	@RequestMapping(value = "getCustomerConsumeRecord", method = {RequestMethod.POST, RequestMethod.GET})
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
	public
	@ResponseBody
	ResponseDTO<ShopUserConsumeRecordDTO> getCustomerConsumeRecord(@RequestParam String shopCustomerConsumeRecordId) {
		ResponseDTO<ShopUserConsumeRecordDTO> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}



}

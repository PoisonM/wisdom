package com.wisdom.business.controller.level;

import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.CommonUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

/**
 * 直播板块
 * @author frank
 * @date 2015-10-14
 */

@Controller
@RequestMapping(value = "userType")
public class UserTypeController {

	private  static Logger logger = LoggerFactory.getLogger(UserTypeController.class);

	@Autowired
	private UserServiceClient userServiceClient;

	@RequestMapping(value = "getUserNextLevelStruct", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<UserInfoDTO>> getUserNextLevelStruct(HttpSession session, HttpServletRequest request) {
		long startTime = System.currentTimeMillis();
		logger.info("getUserNextLevelStruct==={}开始" , startTime);
		ResponseDTO<List<UserInfoDTO>> responseDTO = new ResponseDTO<>();

		UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
		List<UserInfoDTO> userInfoDTOList = new ArrayList<>();

		logger.info("当前用户id{}登录用户级别为{}",userInfoDTO.getId(),userInfoDTO.getUserType());

		if(userInfoDTO.getUserType().equals(ConfigConstant.businessA1))
		{
			UserInfoDTO userInfo = new UserInfoDTO();
			userInfo.setParentUserId(userInfoDTO.getId());
			userInfo.setUserType(ConfigConstant.businessB1);
			List<UserInfoDTO> userInfoDTOS = userServiceClient.getUserInfo(userInfo);

			userInfoDTOList.addAll(userInfoDTOS);

			for(UserInfoDTO userInfoDTO1:userInfoDTOS)
			{
				userInfo.setParentUserId(userInfoDTO1.getId());
				userInfo.setUserType(ConfigConstant.businessC1);
				List<UserInfoDTO> userInfoDTOS1 = userServiceClient.getUserInfo(userInfo);
				userInfoDTOList.addAll(userInfoDTOS1);
			}
		}
		else if(userInfoDTO.getUserType().equals(ConfigConstant.businessB1))
		{
			UserInfoDTO userInfo = new UserInfoDTO();
			userInfo.setParentUserId(userInfoDTO.getId());
			userInfo.setUserType(ConfigConstant.businessC1);
			List<UserInfoDTO> userInfoDTOS = userServiceClient.getUserInfo(userInfo);
			userInfoDTOList.addAll(userInfoDTOS);
		}

		if(userInfoDTOList.size()>0)
		{
			for(UserInfoDTO userInfoDTO1:userInfoDTOList)
			{
				userInfoDTO1.setNickname(CommonUtils.nameDecoder(userInfoDTO1.getNickname()));
			}
		}

		responseDTO.setResponseData(userInfoDTOList);
		responseDTO.setResult(StatusConstant.SUCCESS);
		logger.info("getUserNextLevelStruct,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

}

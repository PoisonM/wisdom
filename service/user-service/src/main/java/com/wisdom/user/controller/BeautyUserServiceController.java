package com.wisdom.user.controller;

import com.wisdom.common.dto.user.RealNameInfoDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.user.service.BeautyUserInfoService;
import com.wisdom.user.service.RealNameAuthService;
import com.wisdom.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class BeautyUserServiceController {

	@Autowired
	private BeautyUserInfoService beautyUserInfoService;

	@RequestMapping(value = "/getBeautyUserInfo",method=RequestMethod.POST)
	@ResponseBody
	List<UserInfoDTO> getBeautyUserInfo(@RequestBody UserInfoDTO userInfoDTO){
		return beautyUserInfoService.getBeautyUserInfo(userInfoDTO);
	}

	@RequestMapping(value = "/getBeautyUserInfoFromUserId",method=RequestMethod.GET)
	@ResponseBody
	UserInfoDTO getBeautyUserInfoFromUserId(@RequestParam(value="userId") String userId) {
		return beautyUserInfoService.getBeautyUserInfoFromUserId(userId);
	}

	@RequestMapping(value = "/updateBeautyUserInfo",method=RequestMethod.POST)
	@ResponseBody
	void updateBeautyUserInfo(@RequestBody UserInfoDTO userInfoDTO)
	{
		beautyUserInfoService.updateBeautyUserInfo(userInfoDTO);
	}

	@RequestMapping(value = "/insertBeautyUserInfo",method=RequestMethod.POST)
	@ResponseBody
	void insertBeautyUserInfo(@RequestBody UserInfoDTO userInfoDTO)
	{
		beautyUserInfoService.insertBeautyUserInfo(userInfoDTO);
	}
}
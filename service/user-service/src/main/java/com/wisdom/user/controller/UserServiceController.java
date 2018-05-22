package com.wisdom.user.controller;

import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.user.RealNameInfoDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.user.service.RealNameAuthService;
import com.wisdom.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
public class UserServiceController {

	@Autowired
	private UserInfoService customerInfoService;

	@Autowired
	private RealNameAuthService realNameAuthService;

	@RequestMapping(value = "/getUserInfo",method=RequestMethod.POST)
	@ResponseBody
	List<UserInfoDTO> getUserInfo(@RequestBody UserInfoDTO userInfoDTO){
		return customerInfoService.getUserInfo(userInfoDTO);
	}

	@RequestMapping(value = "/getUserInfoFromUserId",method=RequestMethod.GET)
	@ResponseBody
	UserInfoDTO getUserInfoFromUserId(@RequestParam(value="userId") String userId) {
		return customerInfoService.getUserInfoFromUserId(userId);
	}

	@RequestMapping(value = "/updateUserInfo",method=RequestMethod.POST)
	@ResponseBody
	void updateUserInfo(@RequestBody UserInfoDTO userInfoDTO)
	{
		customerInfoService.updateUserInfo(userInfoDTO);
	}

	@RequestMapping(value = "/insertUserInfo",method=RequestMethod.POST)
	@ResponseBody
	void insertUserInfo(@RequestBody UserInfoDTO userInfoDTO)
	{
		customerInfoService.insertUserInfo(userInfoDTO);
	}

	@RequestMapping(value = "/queryNextUserByUserId",method=RequestMethod.GET)
	@ResponseBody
	List<UserInfoDTO> queryNextUserByUserId(@RequestParam(value = "userId") String userId) {
		return customerInfoService.queryNextUserById(userId);
	}

	@RequestMapping(value = "/getUserInfoListFromUserId",method=RequestMethod.GET)
	@ResponseBody
	List<UserInfoDTO> getUserInfoFromUserId(@RequestParam String[] userIds,
											@RequestParam(required = false) String searchFile) {
		List<String> list= Arrays.asList(userIds);
		return customerInfoService.getUserInfoFromUserId(list ,searchFile);
	}

	@RequestMapping(value = "/verifyUserIdentify",method=RequestMethod.GET)
	@ResponseBody
	RealNameInfoDTO verifyUserIdentify(@RequestParam(value="idCard") String idCard,@RequestParam(value="name") String name) {
		return realNameAuthService.getRealNameInfoDTO(idCard,name);
	}

	@RequestMapping(value = "/queryUserInfoDTOByParameters",method=RequestMethod.POST)
	@ResponseBody
	PageParamDTO<List<UserInfoDTO>> queryUserInfoDTOByParameters(@RequestBody PageParamVoDTO<UserInfoDTO> pageParamVoDTO) {
		return customerInfoService.queryUserInfoDTOByParameters(pageParamVoDTO);
	}

}

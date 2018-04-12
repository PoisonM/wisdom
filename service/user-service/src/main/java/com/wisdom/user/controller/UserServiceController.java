package com.wisdom.user.controller;

import com.wisdom.common.dto.user.RealNameInfoDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.user.service.RealNameAuthService;
import com.wisdom.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserServiceController {

	@Autowired
	private UserInfoService customerInfoService;

	@Resource
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

	/**
	 * 用户实名认证接口
	 *
	 * @param cardNo
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/queryRealNameAuthentication", method = RequestMethod.GET)
	@ResponseBody
	RealNameInfoDTO queryRealNameAuthentication(@RequestParam(value = "cardNo") String cardNo, @RequestParam(value = "name") String name) {
		return realNameAuthService.getRealNameInfoDTO(cardNo, name);
	}
}

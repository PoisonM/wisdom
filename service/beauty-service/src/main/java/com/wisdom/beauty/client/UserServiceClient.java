package com.wisdom.beauty.client;

import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "user-service")
public interface UserServiceClient {

	/**
	 * 查询变量信息
	 *
	 * @param userInfoDTO
	 * @return
	 */
	@RequestMapping(value = "/beauty/getUserInfo", method = RequestMethod.POST)
	List<UserInfoDTO> getUserInfo(@RequestBody UserInfoDTO userInfoDTO);


	/**
	 * 更新用户信息
	 * @param userInfoDTO
	 */
	@RequestMapping(value = "/updateBeautyUserInfo", method = RequestMethod.POST)
	void updateBeautyUserInfo(@RequestBody UserInfoDTO userInfoDTO);

	/**
	 * 更新老板信息
	 *
	 * @param sysBossDTO
	 * @return
	 */
	@RequestMapping(value = "/updateBossInfo", method = RequestMethod.POST)
	ResponseDTO<Object> updateBossInfo(@RequestBody SysBossDTO sysBossDTO);

	/**
	 * 获取老板信息
	 *
	 * @param sysBossDTO
	 * @return
	 */
	@RequestMapping(value = "/getBossInfo", method = RequestMethod.GET)
	SysBossDTO getBossInfo(@RequestBody SysBossDTO sysBossDTO);

	/**
	 * 获取店员、美容师相关信息
	 *
	 * @param shopId
	 * @return
	 */
	@RequestMapping(value = "/getClerkInfo", method = RequestMethod.POST)
	List<SysClerkDTO> getClerkInfo(@RequestParam(value = "shopId") String shopId);

	/**
	 * 根据用户id获取用户信息
	 *
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/beauty/getUserInfoFromUserId", method = RequestMethod.GET)
	UserInfoDTO getUserInfoFromUserId(@RequestParam(value = "userId") String userId);

	/**
	 * 根据店员id获取用户信息
	 *
	 * @param clerkId
	 * @return
	 */
	@RequestMapping(value = "/clerkInfo/{clerkId}", method = RequestMethod.GET)
	List<SysClerkDTO> getClerkInfoByClerkId(@PathVariable(value = "clerkId") String clerkId);

	/**
	 * @Return:
	 * @Description: 根据多个userid查询
	 * @Date:2018/4/18 14:15
	 */
	@RequestMapping(value = "/beauty/insertUserInfo", method = RequestMethod.POST)
	void insertUserInfo(@RequestBody UserInfoDTO userInfoDTO);

	/**
	 * @Author:zhanghuan
	 * @Param:
	 * @Return:
	 * @Description: 根据条件获取店员信息
	 * @Date:2018/4/25 18:32
	 */
	@RequestMapping(value = "/getClerkInfoList", method = RequestMethod.GET)
	ResponseDTO<List<SysClerkDTO>>   getClerkInfoList(@RequestParam(value = "sysBossCode") String sysBossCode,
			                           @RequestParam(value = "startTime") String startTime,
									   @RequestParam(value = "endTime") String endTime,
									   @RequestParam(value = "pageSize") int pageSize);

	/**
	 * @Author:huan
	 * @Param:
	 * @Return:
	 * @Description: 根据多个userid查询
	 * @Date:2018/4/18 14:15
	 */
	@RequestMapping(value = "/beauty/getUserInfoListFromUserId", method = RequestMethod.GET)
	List<UserInfoDTO> getUserInfoListFromUserId(@RequestParam(value = "userIds") String[] userIds,
			                                    @RequestParam(required = false, value = "searchFile") String searchFile);

	/**
	 * @Author:zhanghuan
	 * @Param:
	 * @Return:
	 * @Description: 根据条件获取店员信息
	 * @Date:2018/4/25 18:32
	 */
	@RequestMapping(value = "/getClerkInfoListByClerkIds", method = RequestMethod.GET)
	ResponseDTO<List<SysClerkDTO>>   getClerkInfoListByClerkIds(@RequestParam(value = "clerkIds") List<String> clerkIds);




}

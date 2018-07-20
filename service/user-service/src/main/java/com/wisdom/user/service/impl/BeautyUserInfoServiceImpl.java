package com.wisdom.user.service.impl;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.RedisLock;
import com.wisdom.common.util.StringUtils;
import com.wisdom.user.mapper.BeautyUserInfoMapper;
import com.wisdom.user.service.BeautyUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Transactional(readOnly = false)
public class BeautyUserInfoServiceImpl implements BeautyUserInfoService {

	@Autowired
	private BeautyUserInfoMapper beautyUserInfoMapper;

	@Autowired
	protected MongoTemplate mongoTemplate;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private static ExecutorService threadExecutorCached = Executors.newCachedThreadPool();

	public List<UserInfoDTO> getBeautyUserInfo(UserInfoDTO userInfoDTO) {
		List<UserInfoDTO> userInfoDTOS = beautyUserInfoMapper.getBeautyUserByInfo(userInfoDTO);
		if (CommonUtils.objectIsEmpty(userInfoDTOS)) {
			logger.info("查询的用户信息为空");
			return userInfoDTOS;
		}
		for (UserInfoDTO user : userInfoDTOS) {
			if (StringUtils.isNotBlank(user.getNickname())) {
				String nickNameW = user.getNickname().replaceAll("%", "%25");
				while (true) {
					logger.info("用户进行编码操作");
					if (StringUtils.isNotBlank(nickNameW)) {
						if (nickNameW.contains("%25")) {
							nickNameW = CommonUtils.nameDecoder(nickNameW);
						} else {
							nickNameW = CommonUtils.nameDecoder(nickNameW);
							break;
						}
					} else {
						break;
					}
				}
				user.setNickname(nickNameW);
			}
		}
		return userInfoDTOS;
	}

	public void updateBeautyUserInfo(UserInfoDTO userInfoDTO) {
		RedisLock redisLock = new RedisLock("userInfo" + userInfoDTO.getId());
		try {
			redisLock.lock();
			beautyUserInfoMapper.updateBeautyUserInfo(userInfoDTO);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			redisLock.unlock();
		}
	}

	public void insertBeautyUserInfo(UserInfoDTO userInfoDTO) {
		RedisLock redisLock = new RedisLock("userInfo" + userInfoDTO.getId());
		try {
			redisLock.lock();
			userInfoDTO.setCreateDate(new Date());
			beautyUserInfoMapper.insertBeautyUserInfo(userInfoDTO);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			redisLock.unlock();
		}
	}

	public UserInfoDTO getBeautyUserInfoFromRedis() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Map<String, String> tokenValue = getHeadersInfo(request);
		String token = tokenValue.get("logintoken");
		String userInfoStr = JedisUtils.get(token);
		UserInfoDTO userInfoDTO = (new Gson()).fromJson(userInfoStr, UserInfoDTO.class);

		// 开启一个线程，主要用来同步userType信息到redis中
		Runnable processBeautyUserInfoSynchronize = new processBeautyUserInfoSynchronize(token, userInfoDTO);
		threadExecutorCached.execute(processBeautyUserInfoSynchronize);

		return userInfoDTO;
	}

	@Override
	public UserInfoDTO getBeautyUserInfoFromUserId(String userId) {

		UserInfoDTO userInfoDTO = new UserInfoDTO();
		userInfoDTO.setId(userId);
		List<UserInfoDTO> userInfoDTOList = beautyUserInfoMapper.getBeautyUserByInfo(userInfoDTO);
		if (userInfoDTOList.size() > 0) {
			return userInfoDTOList.get(0);
		} else {
			return new UserInfoDTO();
		}

	}

	@Override
	public List<UserInfoDTO> getUserInfoFromUserId(List<String> sysUserIds, String searchFile) {
		Map<String, Object> map = new HashMap<>(16);
		map.put("list", sysUserIds);
		map.put("searchFile", searchFile);
		List<UserInfoDTO> userInfoDTOS = beautyUserInfoMapper.getUserByInfoList(map);
		return userInfoDTOS;
	}
	@Override
	public void updateUserInfo(UserInfoDTO userInfoDTO) {
		RedisLock redisLock = new RedisLock("userInfo"+userInfoDTO.getId());
		try{
			redisLock.lock();
			beautyUserInfoMapper.updateUserInfo(userInfoDTO);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			redisLock.unlock();
		}
	}


	private class processBeautyUserInfoSynchronize extends Thread {

		private String logintoken;
		private UserInfoDTO userInfoDTO;

		public processBeautyUserInfoSynchronize(String logintoken, UserInfoDTO userInfoDTO) {
			this.logintoken = logintoken;
			this.userInfoDTO = userInfoDTO;
		}

		@Override
		public void run() {
			UserInfoDTO userInfoDTO1 = new UserInfoDTO();
			userInfoDTO1.setId(userInfoDTO.getId());
			List<UserInfoDTO> userInfoDTOS = getBeautyUserInfo(userInfoDTO1);
			if (userInfoDTOS.size() > 0) {
				String userInfoStr = (new com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson())
						.toJson(userInfoDTOS.get(0));
				JedisUtils.set(logintoken, userInfoStr, ConfigConstant.logintokenPeriod);
			}
		}
	}

	public static Map<String, String> getHeadersInfo(HttpServletRequest request) {
		Map<String, String> map = new HashMap<>();
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		return map;
	}
}

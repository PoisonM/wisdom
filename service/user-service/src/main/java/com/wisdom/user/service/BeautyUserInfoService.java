package com.wisdom.user.service;

import com.wisdom.common.dto.user.UserInfoDTO;

import java.util.List;

public interface BeautyUserInfoService {

	List<UserInfoDTO> getBeautyUserInfo(UserInfoDTO userInfoDTO);

	void updateBeautyUserInfo(UserInfoDTO userInfoDTO);

	void insertBeautyUserInfo(UserInfoDTO userInfoDTO);

	UserInfoDTO getBeautyUserInfoFromRedis();

	UserInfoDTO getBeautyUserInfoFromUserId(String userId);

	// 多个sysUserId查询UserInfoDTO信息
	List<UserInfoDTO> getUserInfoFromUserId(List<String> sysUserIds, String searchFile);
}

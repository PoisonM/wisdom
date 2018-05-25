/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.user.mapper;

import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户DAO接口
 * 
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
@Repository
public interface BeautyUserInfoMapper {

	List<UserInfoDTO> getBeautyUserByInfo(UserInfoDTO userInfoDTO);

	void updateBeautyUserInfo(UserInfoDTO userInfoDTO);

	void insertBeautyUserInfo(UserInfoDTO userInfoDTO);

	// 更加多个id查询用户信息集合
	List<UserInfoDTO> getUserByInfoList(Map<String, Object> userIdMap);

}

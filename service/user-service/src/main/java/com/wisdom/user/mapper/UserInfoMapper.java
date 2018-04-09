/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.user.mapper;

import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
@Repository
public interface UserInfoMapper {

	List<UserInfoDTO> getUserByInfo(UserInfoDTO userInfoDTO);

	void updateUserInfo(UserInfoDTO userInfoDTO);

	void insertUserInfo(UserInfoDTO userInfoDTO);

	List<UserBusinessTypeDTO> queryUserBusinessById(@Param("sysUserId") String sysUserId);

	int queryUserInfoDTOCountByParameters(PageParamVoDTO<UserInfoDTO> pageParamVoDTO);

	List<UserInfoDTO> queryUserInfoDTOByParameters(PageParamVoDTO<UserInfoDTO> pageParamVoDTO);

	//根据用户id查询下级代理
	List<UserInfoDTO> queryNextUserById(@Param("sysUserId") String sysUserId);

	//根据用户id查询上级代理
    List<UserInfoDTO> queryParentUserById(@Param("parentUserId") String parentUserId);
}

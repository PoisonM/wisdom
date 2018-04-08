package com.wisdom.user.service;

import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import com.wisdom.common.dto.system.UserInfoDTO;

import java.util.List;

public interface UserInfoService {

    List<UserInfoDTO> getUserInfo(UserInfoDTO userInfoDTO);
    
    void updateUserInfo(UserInfoDTO userInfoDTO);
    
    void insertUserInfo(UserInfoDTO userInfoDTO);

    PageParamDTO<List<UserBusinessTypeDTO>> queryUserBusinessById(String sysUserId);

    PageParamDTO<List<UserInfoDTO>> queryUserInfoDTOByParameters(PageParamVoDTO<UserInfoDTO> pageParamVoDTO);

    //根据用户id查询下级代理
    List<UserInfoDTO> queryNextUserById(String sysUserId);

    UserInfoDTO getUserInfoFromUserId(String sysUserId);

    //根据parentId查询上级信息
    List<UserInfoDTO> queryParentUserById(String parentUserId);

    UserInfoDTO getUserInfoFromRedis();
}

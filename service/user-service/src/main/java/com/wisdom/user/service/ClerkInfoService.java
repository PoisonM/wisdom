package com.wisdom.user.service;

import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.dto.user.UserInfoDTO;

import java.util.List;

public interface ClerkInfoService {

    List<SysClerkDTO> getClerkInfo(SysClerkDTO SysClerk);
    /**
    *@Author:Administrator
    *@Param: pageParamVoDTO
    *@Return:
    *@Description:分页查询店员信息
    *@Date:2018/4/25 18:43
    */
    List<SysClerkDTO> getClerkInfoList(PageParamVoDTO<SysClerkDTO> pageParamVoDTO);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description: 更新信息SysClerkDTO
    *@Date:2018/5/2 10:44
    */
    int updateSysClerk(SysClerkDTO sysClerkDTO);
}

package com.wisdom.user.service;

import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.dto.user.UserInfoDTO;

import java.util.List;

public interface ClerkInfoService {

    List<SysClerkDTO> getClerkInfo(SysClerkDTO SysClerk);
    /**
    *@Author:zhanghuan
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
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description:
    *@Date:2018/5/11 11:25
    */
    int saveSysClerk(SysClerkDTO sysClerkDTO);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description:根据姓名或者手机查询clerk信息
    *@Date:2018/6/5 11:10
    */
    List<SysClerkDTO> getClerkBySearchFile(SysClerkDTO sysClerkDTO);
}

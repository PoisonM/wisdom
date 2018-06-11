package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.SysUserAccountDTO;
import com.wisdom.beauty.api.responseDto.CustomerAccountResponseDto;

/**
 * ClassName: ${CLASS_NAME}
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/8 17:02
 * @since JDK 1.8
 */
public interface SysUserAccountService {
     /**
     *@Author:huan
      *@Param: userId
      *@Return: CustomerAccountResponseDto
      *@Description: 获取用户在美容院的账户信息
     *@Date:2018/4/8 17:03
     */
     CustomerAccountResponseDto getSysAccountListByUserId(String userId, String sysShopId);

    /**
     * 获取用户的账户信息
     *
     * @param sysUserAccountDTO
     * @return
     */
    SysUserAccountDTO getSysUserAccountDTO(SysUserAccountDTO sysUserAccountDTO);

    /**
     * 更新用户的账户信息
     *
     * @param sysUserAccountDTO
     * @return
     */
    int updateSysUserAccountDTO(SysUserAccountDTO sysUserAccountDTO);

    /**
     * 创建用户账户信息
     * @param sysUserAccountDTO
     * @return
     */
    int saveSysUserAccountDTO(SysUserAccountDTO sysUserAccountDTO);
}

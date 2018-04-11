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
     *@Description: 根据用户id查询档案信息
     *@Date:2018/4/8 17:03
     */
     CustomerAccountResponseDto getSysAccountListByUserId(String userId);

    /**
     * 获取用户的账户信息
     *
     * @param userId
     * @return
     */
    SysUserAccountDTO getSysUserAccountDTO(String userId);

    /**
     * 更新用户的账户信息
     *
     * @param sysUserAccountDTO
     * @return
     */
    int updateSysUserAccountDTO(SysUserAccountDTO sysUserAccountDTO);
}

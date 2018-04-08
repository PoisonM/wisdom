package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.responseDto.CustomerAccountResponseDto;

/**
 * ClassName: ${CLASS_NAME}
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/8 17:02
 * @since JDK 1.8
 */
public interface SysUserAccountServcie {
     /**
     *@Author:huan
     *@Param:
     *@Return:
     *@Description: 根据用户id查询档案信息
     *@Date:2018/4/8 17:03
     */
     CustomerAccountResponseDto getSysAccountListByUserId(String userId);
}

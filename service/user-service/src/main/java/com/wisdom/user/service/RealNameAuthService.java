package com.wisdom.user.service;

import com.wisdom.common.dto.user.RealNameInfoDTO;

/**
 * FileName: RealNameAuthService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 实名认证相关
 */
public interface RealNameAuthService {

    /**
     * 用户实名认证接口
     *
     * @param idCard
     * @param name
     * @return
     */
    RealNameInfoDTO getRealNameInfoDTO(String idCard, String name);
}

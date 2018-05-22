package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;
import com.wisdom.beauty.api.dto.SysClerkFlowAccountDTO;

/**
 * FileName: ShopClerkService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 店员相关
 */
public interface ShopClerkService {

    /**
     * 记录店员的流水信息
     *
     * @param dto
     */
    void saveSysClerkFlowAccountInfo(ShopUserConsumeRecordDTO dto);

    /**
     * 保存店员的流水信息
     */
    public int saveSysClerkFlowAccountInfo(SysClerkFlowAccountDTO sysClerkFlowAccountDTO);

}

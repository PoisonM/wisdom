package com.wisdom.user.service;

import com.wisdom.common.dto.user.SysBossDTO;

public interface BossInfoService {
    /**
     * 更新老板信息
     *
     * @param sysBossDTO
     * @return
     */
    int updateBossInfo(SysBossDTO sysBossDTO);
}

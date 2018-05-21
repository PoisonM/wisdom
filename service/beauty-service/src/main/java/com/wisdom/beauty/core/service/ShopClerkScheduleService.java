package com.wisdom.beauty.core.service;


import com.wisdom.beauty.api.dto.ShopClerkScheduleDTO;
import com.wisdom.beauty.api.dto.ShopScheduleSettingDTO;

import java.util.List;

/**
 * FileName: ShopClerkScheduleService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 排班相关
 */
public interface ShopClerkScheduleService {

    /**
     * 根据条件查询排班信息
     * @param shopClerkScheduleDTO
     * @return
     */
    List<ShopClerkScheduleDTO> getShopClerkScheduleList(ShopClerkScheduleDTO shopClerkScheduleDTO);

    /**
     * 批量生成店员的排班信息
     * @param scheduleDTOS
     * @return
     */
    int saveShopClerkScheduleList(List<ShopClerkScheduleDTO> scheduleDTOS);

    /**
     * 批量更新店员的排班信息
     * @param scheduleDTOS
     * @return
     */
    int updateShopClerkScheduleList(List<ShopClerkScheduleDTO> scheduleDTOS);

    /**
     * 获取老板的排班设置信息
     *
     * @param shopScheduleSettingDTO
     * @return
     */
    List<ShopScheduleSettingDTO> getBossShopScheduleSetting(ShopScheduleSettingDTO shopScheduleSettingDTO);

    /**
     * 修改老板的排班设置信息
     *
     * @param shopScheduleSettingDTO
     * @return
     */
    List<ShopScheduleSettingDTO> updateBossShopScheduleSetting(ShopScheduleSettingDTO shopScheduleSettingDTO);
}

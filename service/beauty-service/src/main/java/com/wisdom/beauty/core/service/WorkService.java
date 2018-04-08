package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopScheduleSettingDTO;

import java.util.List;

/**
 * FileName: ShopClerkService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 店员相关
 */
public interface WorkService {

    /**
     * 查看某个店的上下班时间
     * @param shopScheduleSettingDTO
     * @return
     */

    List<ShopScheduleSettingDTO> getShopScheduleSettingInfo(ShopScheduleSettingDTO shopScheduleSettingDTO);

    /**
     * 查看某个点的店员列表
     */
//    public List<>

}

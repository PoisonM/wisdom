package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopRemindSettingDTO;

/**
 * FileName: ShopRemindService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 提醒相关
 */
public interface ShopRemindService {

    ShopRemindSettingDTO getShopRemindSetting(ShopRemindSettingDTO shopRemindSettingDTO);

    /**
     * 保存老板端推送消息设置
     *
     * @param shopRemindSettingDTO
     * @return
     */
    int saveShopRemindSetting(ShopRemindSettingDTO shopRemindSettingDTO);

    /**
     * 修改老板端推送消息设置
     *
     * @param shopRemindSettingDTO
     * @return
     */
    int updateShopRemindSetting(ShopRemindSettingDTO shopRemindSettingDTO);
}

package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.SysShopDTO;

import java.util.List;

/**
 * FileName: ShopBossService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 老板相关
 */
public interface ShopService {

    /**
     * 根据条件查询shop相关信息
     *
     * @param id
     * @return
     */
    SysShopDTO getShopInfoByPrimaryKey(String id);

    /**
     * 根据条件查询shop相关信息
     *
     * @return
     */
    List<SysShopDTO> getShopInfo(SysShopDTO sysShopDTO);
}

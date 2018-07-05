package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopNameListDTO;

/**
 * FileName: ShopNameListService
 *
 * @author: zhaodeliang
 * Date:     2018/4/3 0003 15:06
 * Description: 查询名单相关
 */
public interface ShopNameListService {

    /**
     * 查询名单
     *
     * @param shopNameListDTO
     * @return
     */
    ShopNameListDTO getShopNameListDTO(ShopNameListDTO shopNameListDTO);
}

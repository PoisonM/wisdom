package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopBossRelationDTO;

import java.util.List;

/**
 * FileName: ShopBossService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 老板相关
 */
public interface ShopBossService {

    /**
     * 根据条件查询boss相关信息
     *
     * @param shopBossRelationDTO
     * @return
     */
    List<ShopBossRelationDTO> ShopBossRelationList(ShopBossRelationDTO shopBossRelationDTO);
}

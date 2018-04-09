package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopProductInfoDTO;
import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;

import java.util.List;

/**
 * ClassName: ShopProductInfoService
 *
 * @Author： huan
 * @Description: 产品相关的接口
 * @Date:Created in 2018/4/3 19:32
 * @since JDK 1.8
 */
public interface ShopProductInfoService {
    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 根据产品id获取产品的详细信息
     * @Date:2018/4/3 19:35
     */
    ShopProductInfoDTO getShopProductInfo(String id);

    /**
     * 获取用户的产品信息
     *
     * @param shopUserProductRelationDTO
     * @return
     */
    List<ShopUserProductRelationDTO> getUserProductInfoList(ShopUserProductRelationDTO shopUserProductRelationDTO);
}

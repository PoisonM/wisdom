package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopProjectProductCardRelationDTO;

import java.util.List;

/**
 * FileName: ShopClerkService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 店员相关
 */
public interface DiscountService {

    /**
     * 查询折扣信息
     *
     * @param shopProjectProductCardRelation
     * @return
     */
    ShopProjectProductCardRelationDTO getShopUserProductRelationInfo(ShopProjectProductCardRelationDTO shopProjectProductCardRelation);

    /**
     * 查询折扣信息列表
     *
     * @param shopProjectProductCardRelation
     * @return
     */
    List<ShopProjectProductCardRelationDTO> getShopUserProductRelationInfoList(ShopProjectProductCardRelationDTO shopProjectProductCardRelation);

}

package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopUserProjectRelationDTO;

/**
 * ClassName: ShopCustomerProjectRelationService
 *
 * @Author： huan
 * @Description: 用户和充值卡相关的接口
 * @Date:Created in 2018/4/4 11:45
 * @since JDK 1.8
 */
public interface ShopCustomerProjectRelationService {
    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 保存用户的充值信息
     * @Date:2018/4/4 11:55
     */
    void savaShopCustomerProjectRelation(ShopUserProjectRelationDTO shopUserProjectRelationDTO);

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 获取用户充值卡的客户信息
     * @Date:2018/4/4 14:25
     */
    ShopUserProjectRelationDTO getShopCustomerProjectRelationList(ShopUserProjectRelationDTO ShopUserProjectRelationDTO);

}

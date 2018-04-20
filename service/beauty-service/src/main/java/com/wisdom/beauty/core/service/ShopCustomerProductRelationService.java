package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;
import com.wisdom.beauty.api.responseDto.UserProductRelationResponseDTO;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ShopCustomerProductRelationService
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/3 19:39
 * @since JDK 1.8
 */
public interface ShopCustomerProductRelationService {
    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 根据产品id获取产品的详细信息
     * @Date:2018/4/3 19:35
     */
    ShopUserProductRelationDTO getShopProductInfo(String shopProductId);

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 根据条件查询用户和产品关系列表
     * @Date:2018/4/18 10:15
     */
    Map<String,Object> getShopUserProductRelations(String sysClerkId, String searchFile);
}

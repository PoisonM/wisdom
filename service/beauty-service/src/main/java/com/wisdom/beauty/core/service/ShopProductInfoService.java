package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.common.dto.account.PageParamVoDTO;

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
    /**
     *@Author:huan
     *@Param: sysShopId
     *@Return:
     *@Description: 获取一级产品列表
     *@Date:2018/4/10 15:59
     */
    List<ShopProductTypeDTO> getOneLevelProductList(String sysShopId);
    /**
     *@Author:huan
     *@Param:
     *@Return:
     *@Description: 获取二级产品列表
     *@Date:2018/4/10 16:15
     */
    List<ShopProductTypeDTO> getTwoLevelProductList(ShopProductTypeDTO shopProductTypeDTO);
    /**
     *@Author:huan
     *@Param:
     *@Return:
     *@Description: 获取三级产品列表
     *@Date:2018/4/10 16:21
     */
    List<ShopProductInfoDTO> getThreeLevelProductList(PageParamVoDTO<ShopProductInfoDTO> pageParamVoDTO);
    /**
     *@Author:huan
     *@Param:
     *@Return:
     *@Description: 获取产品的详细信息
     *@Date:2018/4/10 16:59
     */
    ShopProductInfoDTO getProductDetail(String id);
}

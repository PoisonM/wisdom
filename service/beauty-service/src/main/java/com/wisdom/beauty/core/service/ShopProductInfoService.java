package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopProductInfoDTO;
import com.wisdom.beauty.api.dto.ShopProductTypeDTO;
import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;
import com.wisdom.beauty.api.extDto.ExtShopProductInfoDTO;
import com.wisdom.beauty.api.responseDto.ProductTypeResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopProductInfoResponseDTO;
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
     * @Description: 根据条件查询产品列表
     * @Date:2018/4/3 19:35
     */
    List<ShopProductInfoDTO> getShopProductInfo(ShopProductInfoDTO shopProductInfoDTO);

    /**
     * 获取用户的产品信息
     *
     * @param shopUserProductRelationDTO
     * @return
     */
    List<ShopUserProductRelationDTO> getUserProductInfoList(ShopUserProductRelationDTO shopUserProductRelationDTO);

    /**
     * 更新用户与产品的关系
     *
     * @param shopUserProductRelationDTO
     * @return
     */
    int updateShopUserProductRelation(ShopUserProductRelationDTO shopUserProductRelationDTO);

    /**
     * @Author:huan
     * @Param: sysShopId
     * @Return:
     * @Description: 获取一级产品列表
     * @Date:2018/4/10 15:59
     */
    List<ShopProductTypeDTO> getOneLevelProductList(ShopProductTypeDTO shopProductTypeDTO);

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 获取二级产品列表
     * @Date:2018/4/10 16:15
     */
    List<ShopProductTypeDTO> getTwoLevelProductList(ShopProductTypeDTO shopProductTypeDTO);

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 获取三级产品列表
     * @Date:2018/4/10 16:21
     */
    List<ShopProductInfoResponseDTO> getThreeLevelProductList(PageParamVoDTO<ShopProductInfoDTO> pageParamVoDTO);

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 获取产品的详细信息
     * @Date:2018/4/10 16:59
     */
    ShopProductInfoResponseDTO getProductDetail(String id);

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 根据多个id 查询产品信息
     * @Date:2018/4/18 19:30
     */
    List<ShopProductInfoResponseDTO> getProductInfoList(List<String> ids);
    /**
    *@Author:huan
    *@Param:
    *@Return:
    *@Description: 获取一级二级产品列表
    *@Date:2018/4/20 11:50
    */
    List<ProductTypeResponseDTO>  getAllProducts(String sysShopId);

    /**
     * 保存用户与产品的关系
     *
     * @param shopUserProductRelationDTO
     * @return
     */
    int saveShopUserProductRelation(ShopUserProductRelationDTO shopUserProductRelationDTO);

    /**
     * 更新某个店的产品类别列表
     *
     * @param shopProductTypeDTOS
     */
    int updateProductTypeListInfo(List<ShopProductTypeDTO> shopProductTypeDTOS);

    /**
     * 更新某个店的某个产品类别
     *
     * @param shopProductTypeDTOS
     */
    int updateProductTypeInfo(ShopProductTypeDTO shopProductTypeDTOS);

    /**
     * 更新产品信息
     *
     * @param extShopProductInfoDTO
     * @return
     */
    int updateProductInfo(ExtShopProductInfoDTO extShopProductInfoDTO);

    /**
     * 添加某个店的某个产品类别
     *
     * @param shopProductTypeDTOS
     */
    int saveProductTypeInfo(ShopProductTypeDTO shopProductTypeDTOS);

    /**
     * 保存产品信息
     *
     * @param shopProductInfoDTO
     * @return
     */
    int saveProductInfo(ExtShopProductInfoDTO shopProductInfoDTO);
}

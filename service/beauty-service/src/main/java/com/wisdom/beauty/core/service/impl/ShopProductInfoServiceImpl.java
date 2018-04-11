package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.core.mapper.ShopProductInfoMapper;
import com.wisdom.beauty.core.mapper.ShopProductTypeMapper;
import com.wisdom.beauty.core.mapper.ShopUserProductRelationMapper;
import com.wisdom.beauty.core.service.ShopProductInfoService;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FileName: ShopProductInfoServiceImpl
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 产品相关
 */
@Service("shopProductInfoService")
public class ShopProductInfoServiceImpl implements ShopProductInfoService {

    @Autowired
    private ShopUserProductRelationMapper shopUserProductRelationMapper;

    @Autowired
    private ShopProductTypeMapper shopProductTypeMapper;

    @Autowired
    private ShopProductInfoMapper shopProductInfoMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ShopProductInfoDTO getShopProductInfo(String id) {
        return null;
    }

    /**
     * 获取用户的产品信息
     *
     * @param shopUserProductRelationDTO
     * @return
     */
    @Override
    public List<ShopUserProductRelationDTO> getUserProductInfoList(ShopUserProductRelationDTO shopUserProductRelationDTO) {

        if (shopUserProductRelationDTO == null) {
            logger.error("获取用户的产品信息为空{}", "shopUserProductRelationDTO = [" + shopUserProductRelationDTO + "]");
            return null;
        }
        ShopUserProductRelationCriteria shopUserProductRelationCriteria = new ShopUserProductRelationCriteria();
        ShopUserProductRelationCriteria.Criteria criteria = shopUserProductRelationCriteria.createCriteria();

        if (StringUtils.isNotBlank(shopUserProductRelationDTO.getSysShopId())) {
            criteria.andSysShopIdEqualTo(shopUserProductRelationDTO.getSysShopId());
        }

        if (StringUtils.isNotBlank(shopUserProductRelationDTO.getSysUserId())) {
            criteria.andSysUserIdEqualTo(shopUserProductRelationDTO.getSysUserId());
        }

        List<ShopUserProductRelationDTO> shopUserProductRelationDTOS = shopUserProductRelationMapper.selectByCriteria(shopUserProductRelationCriteria);

        logger.debug("获取用户的产品信息大小为， {}", shopUserProductRelationDTOS != null ? shopUserProductRelationDTOS.size() : 0);
        return shopUserProductRelationDTOS;
    }

    @Override
    public List<ShopProductTypeDTO> getOneLevelProductList(String sysShopId) {
        logger.info("getOneLevelProjectList传入的参数,sysShopId={}", sysShopId);
        if (StringUtils.isBlank(sysShopId)) {
            return null;
        }
        ShopProductTypeCriteria shopProductTypeCriteria = new ShopProductTypeCriteria();
        ShopProductTypeCriteria.Criteria criteria = shopProductTypeCriteria.createCriteria();
        criteria.andSysShopIdEqualTo(sysShopId);
        criteria.andParentIdIsNull();
        List<ShopProductTypeDTO> list = shopProductTypeMapper.selectByCriteria(shopProductTypeCriteria);
        return list;
    }

    @Override
    public List<ShopProductTypeDTO> getTwoLevelProductList(ShopProductTypeDTO shopProductTypeDTO) {
        logger.info("getTwoLevelProductList传入的参数,id={}", shopProductTypeDTO.getId());

        if (StringUtils.isBlank(shopProductTypeDTO.getSysShopId()) || StringUtils.isBlank(shopProductTypeDTO.getId())) {
            return null;
        }
        ShopProductTypeCriteria shopProductTypeCriteria = new ShopProductTypeCriteria();
        ShopProductTypeCriteria.Criteria criteria = shopProductTypeCriteria.createCriteria();
        criteria.andParentIdEqualTo(shopProductTypeDTO.getId());
        List<ShopProductTypeDTO> list = shopProductTypeMapper.selectByCriteria(shopProductTypeCriteria);
        return list;
    }

    @Override
    public List<ShopProductInfoDTO> getThreeLevelProductList(PageParamVoDTO<ShopProductInfoDTO> pageParamVoDTO) {
        ShopProductInfoDTO shopProductInfoDTO = pageParamVoDTO.getRequestData();
        logger.info("getThreeLevelProjectList传入的参数,sysShopId={},productTypeOneId={},productTypeTwoId={}", shopProductInfoDTO.getSysShopId()
                , shopProductInfoDTO.getProductTypeOneId(), shopProductInfoDTO.getProductTypeTwoId());

        if (StringUtils.isBlank(shopProductInfoDTO.getSysShopId())) {
            return null;
        }

        ShopProductInfoCriteria shopProductInfoCriteria = new ShopProductInfoCriteria();
        ShopProductInfoCriteria.Criteria criteria = shopProductInfoCriteria.createCriteria();
        // 排序
        shopProductInfoCriteria.setOrderByClause("create_date");
        // 分页
        shopProductInfoCriteria.setLimitStart(pageParamVoDTO.getPageNo());
        shopProductInfoCriteria.setPageSize(pageParamVoDTO.getPageSize());
        criteria.andSysShopIdEqualTo(shopProductInfoDTO.getSysShopId());
        if (StringUtils.isNotBlank(shopProductInfoDTO.getProductTypeOneId())) {
            criteria.andProductTypeOneIdEqualTo(shopProductInfoDTO.getProductTypeOneId());
        }
        if (StringUtils.isNotBlank(shopProductInfoDTO.getProductTypeTwoId())) {
            criteria.andProductTypeTwoIdEqualTo(shopProductInfoDTO.getProductTypeTwoId());
        }

        List<ShopProductInfoDTO> list = shopProductInfoMapper.selectByCriteria(shopProductInfoCriteria);
        return list;
    }

    @Override
    public ShopProductInfoDTO getProductDetail(String id) {
        logger.info("getProductDetail传入的参数,id={}", id);

        if (StringUtils.isBlank(id)) {
            return null;
        }
        ShopProductInfoCriteria shopProductInfoCriteria = new ShopProductInfoCriteria();
        ShopProductInfoCriteria.Criteria criteria = shopProductInfoCriteria.createCriteria();

        criteria.andIdEqualTo(id);
        List<ShopProductInfoDTO> list = shopProductInfoMapper.selectByCriteria(shopProductInfoCriteria);
        if (CollectionUtils.isEmpty(list)) {
            logger.info("getProductDetail返回的结果为空");
            return null;
        }
        return list.get(0);
    }
}

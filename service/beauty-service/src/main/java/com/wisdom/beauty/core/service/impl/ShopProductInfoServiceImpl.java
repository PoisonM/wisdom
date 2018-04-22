package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopProductInfoDTO;
import com.wisdom.beauty.api.dto.ShopUserProductRelationCriteria;
import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;
import com.wisdom.beauty.core.mapper.ShopUserProductRelationMapper;
import com.wisdom.beauty.core.service.ShopProductInfoService;
import com.wisdom.common.util.StringUtils;
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
            criteria.andSysShopIdEqualTo(shopUserProductRelationDTO.getShopProductId());
        }

        if (StringUtils.isNotBlank(shopUserProductRelationDTO.getSysUserId())) {
            criteria.andSysUserIdEqualTo(shopUserProductRelationDTO.getSysUserId());
        }

        List<ShopUserProductRelationDTO> shopUserProductRelationDTOS = shopUserProductRelationMapper.selectByCriteria(shopUserProductRelationCriteria);

        return shopUserProductRelationDTOS;
    }
}

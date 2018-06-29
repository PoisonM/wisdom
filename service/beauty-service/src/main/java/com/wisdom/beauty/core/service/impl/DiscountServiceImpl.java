package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopProjectProductCardRelationCriteria;
import com.wisdom.beauty.api.dto.ShopProjectProductCardRelationDTO;
import com.wisdom.beauty.core.mapper.ShopProjectProductCardRelationMapper;
import com.wisdom.beauty.core.service.DiscountService;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FileName: DiscountService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 折扣相关
 */
@Service("discountService")
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private ShopProjectProductCardRelationMapper shopProjectProductCardRelationMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询折扣信息列表
     *
     * @param relation
     * @return
     */
    @Override
    public List<ShopProjectProductCardRelationDTO> getShopUserProductRelationInfoList(ShopProjectProductCardRelationDTO relation) {

        logger.info("查询折扣信息列表传入参数={}", "shopProjectProductCardRelation = [" + relation + "]");
        if (CommonUtils.objectIsEmpty(relation)) {
            logger.error("传入参数为空{}", "shopProjectProductCardRelation = [" + relation + "]");
            return null;
        }

        ShopProjectProductCardRelationCriteria shopProjectProductCardRelationCriteria = new ShopProjectProductCardRelationCriteria();
        ShopProjectProductCardRelationCriteria.Criteria criteria = shopProjectProductCardRelationCriteria.createCriteria();

        if (StringUtils.isNotBlank(relation.getGoodsType())) {
            criteria.andGoodsTypeEqualTo(relation.getGoodsType());
        }

        if (StringUtils.isNotBlank(relation.getShopRechargeCardId())) {
            criteria.andShopRechargeCardIdEqualTo(relation.getShopRechargeCardId());
        }

        if (StringUtils.isNotBlank(relation.getShopGoodsTypeId())) {
            criteria.andShopGoodsTypeIdEqualTo(relation.getShopGoodsTypeId());
        }

        List<ShopProjectProductCardRelationDTO> shopProjectProductCardRelationDTOS = shopProjectProductCardRelationMapper.selectByCriteria(shopProjectProductCardRelationCriteria);
        return shopProjectProductCardRelationDTOS;
    }

    /**
     * 查询折扣信息
     *
     * @param relation
     * @return
     */
    @Override
    public ShopProjectProductCardRelationDTO getShopUserProductRelationInfo(ShopProjectProductCardRelationDTO relation) {

        logger.info("查询折扣信息传入参数={}", "shopProjectProductCardRelation = [" + relation + "]");
        if (CommonUtils.objectIsEmpty(relation)) {
            logger.error("传入参数为空{}", "shopProjectProductCardRelation = [" + relation + "]");
            return null;
        }

        List<ShopProjectProductCardRelationDTO> shopProjectProductCardRelationDTOS = getShopUserProductRelationInfoList(relation);

        return (shopProjectProductCardRelationDTOS == null || shopProjectProductCardRelationDTOS.size() == 0) ? null : shopProjectProductCardRelationDTOS.get(0);
    }
}

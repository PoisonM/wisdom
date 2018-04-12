package com.wisdom.beauty.core.service.impl;


import com.wisdom.beauty.api.dto.ShopProjectProductCardRelation;
import com.wisdom.beauty.api.dto.ShopProjectProductCardRelationCriteria;
import com.wisdom.beauty.api.dto.ShopRechargeCardCriteria;
import com.wisdom.beauty.api.dto.ShopRechargeCardDTO;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.core.mapper.ShopProjectProductCardRelationMapper;
import com.wisdom.beauty.core.mapper.ShopRechargeCardMapper;
import com.wisdom.beauty.core.service.ShopRechargeCardService;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: ShopRechargeCardServiceImpl
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/11 11:41
 * @since JDK 1.8
 */
@Service("shopRechargeCardService")
public class ShopRechargeCardServiceImpl implements ShopRechargeCardService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopRechargeCardMapper shopRechargeCardMapper;

    @Autowired
    private ShopProjectProductCardRelationMapper shopProjectProductCardRelationMapper;

    @Override
    public List<ShopRechargeCardDTO> getShopRechargeCardList(PageParamVoDTO<ShopRechargeCardDTO> pageParamVoDTO) {
        ShopRechargeCardDTO shopRechargeCardDTO = pageParamVoDTO.getRequestData();
        logger.info("getThreeLevelProjectList传入的参数,sysShopId={},name={}", shopRechargeCardDTO.getSysShopId(), shopRechargeCardDTO.getName());

        if (StringUtils.isBlank(shopRechargeCardDTO.getSysShopId())) {
            return null;
        }

        ShopRechargeCardCriteria shopRechargeCardCriteria = new ShopRechargeCardCriteria();
        ShopRechargeCardCriteria.Criteria criteria = shopRechargeCardCriteria.createCriteria();
        // 排序
        shopRechargeCardCriteria.setOrderByClause("create_date");
        // 分页
        shopRechargeCardCriteria.setLimitStart(pageParamVoDTO.getPageNo());
        shopRechargeCardCriteria.setPageSize(pageParamVoDTO.getPageSize());
        criteria.andSysShopIdEqualTo(shopRechargeCardDTO.getSysShopId());

        if (StringUtils.isNotBlank(shopRechargeCardDTO.getName())) {
            criteria.andNameEqualTo(shopRechargeCardDTO.getName());
        }

        List<ShopRechargeCardDTO> list = shopRechargeCardMapper.selectByCriteria(shopRechargeCardCriteria);
        return list;
    }

    @Override
    public ShopRechargeCardDTO getShopRechargeCard(String id) {
        logger.info("getThreeLevelProjectList传入的参数,id={}", id);

        if (StringUtils.isBlank(id)) {
            return null;
        }

        ShopRechargeCardCriteria shopRechargeCardCriteria = new ShopRechargeCardCriteria();
        ShopRechargeCardCriteria.Criteria criteria = shopRechargeCardCriteria.createCriteria();

        criteria.andIdEqualTo(id);
        List<ShopRechargeCardDTO> list = shopRechargeCardMapper.selectByCriteria(shopRechargeCardCriteria);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Float getDiscount(String queryCriteria, String rechargeCardId, String type) {
        logger.info("getDiscount传入的参数,queryCriteria={},rechargeCardId={}", queryCriteria, rechargeCardId);

        if (StringUtils.isBlank(queryCriteria) || StringUtils.isBlank(rechargeCardId)) {
            logger.info("getDiscount传入的参数方法传入的参数为空");
            return null;
        }

        ShopProjectProductCardRelationCriteria shopProjectProductCardRelationCriteria = new ShopProjectProductCardRelationCriteria();
        ShopProjectProductCardRelationCriteria.Criteria criteria = shopProjectProductCardRelationCriteria.createCriteria();

        criteria.andShopRechargeCardIdEqualTo(rechargeCardId);
        //如果是产品类型的
        if (GoodsTypeEnum.PRODUCT.getCode().equals(type)) {
            criteria.andShopProductIdEqualTo(queryCriteria);
        } else {
            //项目类型
            criteria.andSysShopProjectIdEqualTo(queryCriteria);
        }
        List<ShopProjectProductCardRelation> list = shopProjectProductCardRelationMapper.selectByCriteria(shopProjectProductCardRelationCriteria);
        if (CollectionUtils.isEmpty(list)) {
            logger.info("接口shopRechargeCardMapper#selectByCriteria()查询结果为空");
            return null;
        }
        ShopProjectProductCardRelation shopProjectProductCardRelation = list.get(0);
        Float discount = shopProjectProductCardRelation.getDiscount();
        logger.info("getDiscount接口获取到的折扣信息={}", discount);
        return discount;
    }
}

package com.wisdom.beauty.core.service.impl;


import com.wisdom.beauty.api.dto.ShopRechargeCardCriteria;
import com.wisdom.beauty.api.dto.ShopRechargeCardDTO;
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
        if(CollectionUtils.isEmpty(list)){
            return  null;
        }
        return list.get(0);
    }
}

package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopBossRelationCriteria;
import com.wisdom.beauty.api.dto.ShopBossRelationDTO;
import com.wisdom.beauty.core.mapper.ShopBossRelationMapper;
import com.wisdom.beauty.core.service.ShopBossService;
import com.wisdom.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FileName: ShopBossService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 老板与店相关
 */
@Service("shopBossService")
public class ShopBossServiceImpl implements ShopBossService {


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopBossRelationMapper shopBossRelationMapper;

    /**
     * 根据条件查询boss相关信息
     *
     * @param shopBossRelationDTO
     * @return
     */
    @Override
    public List<ShopBossRelationDTO> ShopBossRelationList(ShopBossRelationDTO shopBossRelationDTO) {

        logger.info("根据条件查询boss相关信息传入参数={}", "shopBossRelationDTO = [" + shopBossRelationDTO + "]");

        if (CommonUtils.objectIsEmpty(shopBossRelationDTO)) {
            logger.error("根据条件查询boss相关信息,传入参数为空，{}", "shopBossRelationDTO = [" + shopBossRelationDTO + "]");
            return null;
        }

        ShopBossRelationCriteria shopBossRelationCriteria = new ShopBossRelationCriteria();
        ShopBossRelationCriteria.Criteria criteria = shopBossRelationCriteria.createCriteria();
        if (StringUtils.isNotBlank(shopBossRelationDTO.getSysBossCode())) {
            criteria.andSysBossCodeEqualTo(shopBossRelationDTO.getSysBossCode());
        }

        List<ShopBossRelationDTO> shopBossRelationDTOS = shopBossRelationMapper.selectByCriteria(shopBossRelationCriteria);
        return shopBossRelationDTOS;
    }
}

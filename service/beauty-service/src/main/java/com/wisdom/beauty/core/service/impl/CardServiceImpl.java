package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopUserRechargeCardCriteria;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.core.mapper.ShopUserRechargeCardMapper;
import com.wisdom.beauty.core.service.ShopCardService;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FileName: workService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 工作相关
 */
@Service("cardService")
public class CardServiceImpl implements ShopCardService {

    @Autowired
    public ShopUserRechargeCardMapper shopUserRechargeCardMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public List<ShopUserRechargeCardDTO> getUserRechargeCardList(ShopUserRechargeCardDTO shopUserRechargeCardDTO) {

        logger.info("获取用户的充值卡信息传入参数={}", "shopUserRechargeCardDTO = [" + shopUserRechargeCardDTO + "]");

        if (shopUserRechargeCardDTO == null) {
            logger.debug("获取用户的充值卡信息参数为空， {}", "shopUserRechargeCardDTO = [" + shopUserRechargeCardDTO + "]");
            return null;
        }

        ShopUserRechargeCardCriteria shopUserRechargeCardCriteria = new ShopUserRechargeCardCriteria();
        ShopUserRechargeCardCriteria.Criteria criteria = shopUserRechargeCardCriteria.createCriteria();

        if (StringUtils.isNotBlank(shopUserRechargeCardDTO.getSysUserId())) {
            criteria.andSysUserIdEqualTo(shopUserRechargeCardDTO.getSysUserId());
        }

        if (StringUtils.isNotBlank(shopUserRechargeCardDTO.getSysShopId())) {
            criteria.andSysShopIdEqualTo(shopUserRechargeCardDTO.getSysBossId());
        }

        List<ShopUserRechargeCardDTO> shopUserRechargeCardDTOS = shopUserRechargeCardMapper.selectByCriteria(shopUserRechargeCardCriteria);

        return shopUserRechargeCardDTOS;
    }
}

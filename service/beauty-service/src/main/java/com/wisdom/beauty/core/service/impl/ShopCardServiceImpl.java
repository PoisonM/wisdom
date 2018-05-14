package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopUserRechargeCardCriteria;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.core.mapper.ShopUserRechargeCardMapper;
import com.wisdom.beauty.core.service.ShopCardService;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * FileName: workService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 工作相关
 */
@Service("cardService")
public class ShopCardServiceImpl implements ShopCardService {

    @Autowired
    public ShopUserRechargeCardMapper shopUserRechargeCardMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询某个用户的充值卡列表
     *
     * @param shopUserRechargeCardDTO
     * @return
     */
    @Override
    public List<ShopUserRechargeCardDTO> getUserRechargeCardList(ShopUserRechargeCardDTO shopUserRechargeCardDTO) {

        logger.info("获取用户的充值卡信息传入参数={}", "shopUserRechargeCardDTO = [" + shopUserRechargeCardDTO + "]");

        if (shopUserRechargeCardDTO == null) {
            logger.debug("获取用户的充值卡信息参数为空");
            return null;
        }

        ShopUserRechargeCardCriteria shopUserRechargeCardCriteria = new ShopUserRechargeCardCriteria();
        ShopUserRechargeCardCriteria.Criteria criteria = shopUserRechargeCardCriteria.createCriteria();

        if (StringUtils.isNotBlank(shopUserRechargeCardDTO.getSysUserId())) {
            criteria.andSysUserIdEqualTo(shopUserRechargeCardDTO.getSysUserId());
        }

        if (StringUtils.isNotBlank(shopUserRechargeCardDTO.getSysShopId())) {
            criteria.andSysShopIdEqualTo(shopUserRechargeCardDTO.getSysShopId());
        }

        if (StringUtils.isNotBlank(shopUserRechargeCardDTO.getId())) {
            criteria.andIdEqualTo(shopUserRechargeCardDTO.getId());
        }

        if (StringUtils.isNotBlank(shopUserRechargeCardDTO.getShopRechargeCardId())) {
            criteria.andShopRechargeCardIdEqualTo(shopUserRechargeCardDTO.getShopRechargeCardId());
        }

        shopUserRechargeCardCriteria.setOrderByClause("recharge_card_type asc");

        List<ShopUserRechargeCardDTO> shopUserRechargeCardDTOS = shopUserRechargeCardMapper.selectByCriteria(shopUserRechargeCardCriteria);

        logger.debug("查询某个用户的充值卡列表，查询结果大小为 {}", "size = [" + shopUserRechargeCardDTOS == null ? "0" : shopUserRechargeCardDTOS.size() + "]");

        return shopUserRechargeCardDTOS;
    }

    /**
     * 更新用户充值卡信息
     *
     * @param shopUserRechargeCardDTO
     * @return
     */
    @Override
    public int updateUserRechargeCard(ShopUserRechargeCardDTO shopUserRechargeCardDTO) {

        logger.info("更新用户充值卡信息传入参数={}", "shopUserRechargeCardDTO = [" + shopUserRechargeCardDTO + "]");
        if (StringUtils.isBlank(shopUserRechargeCardDTO.getId())) {
            logger.error("更新用户充值卡信息，主键为空，{}", "shopUserRechargeCardDTO = [" + shopUserRechargeCardDTO + "]");
            return 0;
        }
        return shopUserRechargeCardMapper.updateByPrimaryKeySelective(shopUserRechargeCardDTO);
    }

    /**
     * 获取充值卡总金额
     *
     * @param shopUserRechargeCardDTO
     * @return
     */
    @Override
    public BigDecimal getUserRechargeCardSumAmount(ShopUserRechargeCardDTO shopUserRechargeCardDTO) {

        BigDecimal sumAmount = new BigDecimal(0);

        List<ShopUserRechargeCardDTO> userRechargeCardList = getUserRechargeCardList(shopUserRechargeCardDTO);

        if (CommonUtils.objectIsNotEmpty(userRechargeCardList)) {
            for (ShopUserRechargeCardDTO userRechargeCardDTO : userRechargeCardList) {
                BigDecimal surplusAmount = userRechargeCardDTO.getSurplusAmount();
                sumAmount = sumAmount.add(surplusAmount);
            }
        }

        return sumAmount;
    }
}

package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopProjectProductCardRelationCriteria;
import com.wisdom.beauty.api.dto.ShopProjectProductCardRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardCriteria;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.enums.RechargeCardTypeEnum;
import com.wisdom.beauty.api.extDto.ExtShopRechargeCardDTO;
import com.wisdom.beauty.core.mapper.ShopProjectProductCardRelationMapper;
import com.wisdom.beauty.core.mapper.ShopRechargeCardMapper;
import com.wisdom.beauty.core.mapper.ShopUserRechargeCardMapper;
import com.wisdom.beauty.core.redis.MongoUtils;
import com.wisdom.beauty.core.service.ShopCardService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.IdGen;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
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

    @Autowired
    public ShopRechargeCardMapper shopRechargeCardMapper;

    @Autowired
    public MongoUtils mongoUtils;

    @Autowired
    public ShopProjectProductCardRelationMapper shopProjectProductCardRelationMapper;

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

        shopUserRechargeCardCriteria.setOrderByClause("recharge_card_type asc");

        List<ShopUserRechargeCardDTO> shopUserRechargeCardDTOS = shopUserRechargeCardMapper.selectByCriteria(shopUserRechargeCardCriteria);

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
     * 获取用户充值卡总金额
     *
     * @param shopUserRechargeCardDTO
     * @return
     */
    @Override
    public BigDecimal getUserRechargeCardSumAmount(ShopUserRechargeCardDTO shopUserRechargeCardDTO) {
        logger.info("传入参数={}", "shopUserRechargeCardDTO = [" + shopUserRechargeCardDTO + "]");

        BigDecimal bigDecimal = new BigDecimal(0);
        List<ShopUserRechargeCardDTO> rechargeCardList = getUserRechargeCardList(shopUserRechargeCardDTO);
        if (CommonUtils.objectIsNotEmpty(rechargeCardList)) {
            for (ShopUserRechargeCardDTO userRechargeCardDTO : rechargeCardList) {
                bigDecimal = bigDecimal.add(userRechargeCardDTO.getSurplusAmount());
            }
        }
        return bigDecimal;
    }

    /**
     * 保存充值卡信息
     *
     * @param extShopRechargeCardDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveRechargeCardInfo(ExtShopRechargeCardDTO extShopRechargeCardDTO) {
        logger.info("保存充值卡信息传入参数={}", "extShopRechargeCardDTO = [" + extShopRechargeCardDTO + "]");
        //保存充值卡信息
        String rechargeCardId = IdGen.uuid();
        extShopRechargeCardDTO.setId(rechargeCardId);
        extShopRechargeCardDTO.setRechargeCardType(RechargeCardTypeEnum.COMMON.getCode());
        extShopRechargeCardDTO.setCreateDate(new Date());
        String currentShopId = UserUtils.getBossInfo().getCurrentShopId();
        extShopRechargeCardDTO.setSysShopId(currentShopId);
        extShopRechargeCardDTO.setCreateBy(UserUtils.getBossInfo().getId());
        List<String> imageUrls = extShopRechargeCardDTO.getImageList();
        if (CommonUtils.objectIsNotEmpty(imageUrls)) {
            extShopRechargeCardDTO.setImageUrl(imageUrls.get(0));
        }
        //保存图片信息
        mongoUtils.saveImageUrl(imageUrls, rechargeCardId);
        int insert = shopRechargeCardMapper.insert(extShopRechargeCardDTO);
        logger.info("保存充值卡信息执行结果={}", insert > 0 ? "成功" : "失败");
        //保存项目的适用范围
        useScope(extShopRechargeCardDTO);
        return insert;
    }


    /**
     * 修改充值卡信息
     *
     * @param extShopRechargeCardDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRechargeCardInfo(ExtShopRechargeCardDTO extShopRechargeCardDTO) {
        //保存图片信息
        if (CommonUtils.objectIsNotEmpty(extShopRechargeCardDTO.getImageList())) {
            extShopRechargeCardDTO.setImageUrl(extShopRechargeCardDTO.getImageList().get(0));
        } else {
            extShopRechargeCardDTO.setImageUrl("");
        }
        mongoUtils.saveImageUrl(extShopRechargeCardDTO.getImageList(), extShopRechargeCardDTO.getId());
        int update = shopRechargeCardMapper.updateByPrimaryKey(extShopRechargeCardDTO);
        //删除充值卡适用范围信息
        ShopProjectProductCardRelationCriteria criteria = new ShopProjectProductCardRelationCriteria();
        ShopProjectProductCardRelationCriteria.Criteria c = criteria.createCriteria();
        c.andShopRechargeCardIdEqualTo(extShopRechargeCardDTO.getId());
        shopProjectProductCardRelationMapper.deleteByCriteria(criteria);
        //保存项目的适用范围
        useScope(extShopRechargeCardDTO);
        logger.info("保存充值卡信息执行结果={}", update > 0 ? "成功" : "失败");
        return update;
    }

    /**
     * 根据虚拟商品id获取虚拟商品列表
     *
     * @param relationDTO
     * @return
     */
    @Override
    public List<ShopProjectProductCardRelationDTO> getShopProjectProductCardRelationList(ShopProjectProductCardRelationDTO relationDTO) {

        if (null == relationDTO) {
            logger.error("根据虚拟商品id获取虚拟商品列表");
            return null;
        }
        ShopProjectProductCardRelationCriteria criteria = new ShopProjectProductCardRelationCriteria();
        ShopProjectProductCardRelationCriteria.Criteria c = criteria.createCriteria();
        if (StringUtils.isNotBlank(relationDTO.getShopRechargeCardId())) {
            c.andShopRechargeCardIdEqualTo(relationDTO.getShopRechargeCardId());
        }

        List<ShopProjectProductCardRelationDTO> relationDTOList = shopProjectProductCardRelationMapper.selectByCriteria(criteria);
        logger.info("根据虚拟商品id获取虚拟商品列表查询大小为={}", relationDTOList.size());
        return relationDTOList;
    }


    /**
     * 充值卡适用范围
     *
     * @param extShopRechargeCardDTO
     */
    private void useScope(ExtShopRechargeCardDTO extShopRechargeCardDTO) {
        //保存单次卡的适用范围
        List<ShopProjectProductCardRelationDTO> timesList = extShopRechargeCardDTO.getTimesList();
        if (CommonUtils.objectIsNotEmpty(timesList)) {
            for (ShopProjectProductCardRelationDTO relationDTO : timesList) {
                relationDTO.setId(IdGen.uuid());
                relationDTO.setShopRechargeCardId(extShopRechargeCardDTO.getId());
                relationDTO.setShopGoodsTypeId(relationDTO.getShopGoodsTypeId());
                relationDTO.setGoodsType(GoodsTypeEnum.TIME_CARD.getCode());
                relationDTO.setCreateDate(new Date());
                relationDTO.setDiscount(extShopRechargeCardDTO.getPeriodDiscount());
                shopProjectProductCardRelationMapper.insertSelective(relationDTO);
            }
        }
        //保存疗程卡的适用范围
        List<ShopProjectProductCardRelationDTO> periodList = extShopRechargeCardDTO.getPeriodList();
        if (CommonUtils.objectIsNotEmpty(periodList)) {
            for (ShopProjectProductCardRelationDTO relationDTO : periodList) {
                relationDTO.setId(IdGen.uuid());
                relationDTO.setShopRechargeCardId(extShopRechargeCardDTO.getId());
                relationDTO.setShopGoodsTypeId(relationDTO.getShopGoodsTypeId());
                relationDTO.setGoodsType(GoodsTypeEnum.TREATMENT_CARD.getCode());
                relationDTO.setCreateDate(new Date());
                relationDTO.setDiscount(extShopRechargeCardDTO.getPeriodDiscount());
                shopProjectProductCardRelationMapper.insertSelective(relationDTO);
            }
        }
        //保存产品的适用范围
        List<ShopProjectProductCardRelationDTO> productList = extShopRechargeCardDTO.getProductList();
        if (CommonUtils.objectIsNotEmpty(productList)) {
            for (ShopProjectProductCardRelationDTO relationDTO : productList) {
                relationDTO.setId(IdGen.uuid());
                relationDTO.setShopRechargeCardId(extShopRechargeCardDTO.getId());
                relationDTO.setShopGoodsTypeId(relationDTO.getShopGoodsTypeId());
                relationDTO.setGoodsType(GoodsTypeEnum.PRODUCT.getCode());
                relationDTO.setCreateDate(new Date());
                relationDTO.setDiscount(extShopRechargeCardDTO.getPeriodDiscount());
                shopProjectProductCardRelationMapper.insertSelective(relationDTO);
            }
        }
    }
}

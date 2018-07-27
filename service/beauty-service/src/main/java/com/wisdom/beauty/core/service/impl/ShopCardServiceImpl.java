package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.CardTypeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.enums.ImageEnum;
import com.wisdom.beauty.api.enums.RechargeCardTypeEnum;
import com.wisdom.beauty.api.extDto.ExtShopProjectInfoDTO;
import com.wisdom.beauty.api.extDto.ExtShopRechargeCardDTO;
import com.wisdom.beauty.core.mapper.ShopProjectProductCardRelationMapper;
import com.wisdom.beauty.core.mapper.ShopRechargeCardMapper;
import com.wisdom.beauty.core.mapper.ShopUserRechargeCardMapper;
import com.wisdom.beauty.core.redis.MongoUtils;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.ShopCardService;
import com.wisdom.beauty.core.service.ShopProductInfoService;
import com.wisdom.beauty.core.service.ShopProjectService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.CommonCodeEnum;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.IdGen;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    public RedisUtils redisUtils;

    @Autowired
    public ShopProjectService shopProjectService;

    @Autowired
    public ShopProductInfoService shopProductInfoService;

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

        //根据创建时间排序
        shopUserRechargeCardCriteria.setOrderByClause("create_date desc");

        if (StringUtils.isNotBlank(shopUserRechargeCardDTO.getSysUserId())) {
            criteria.andSysUserIdEqualTo(shopUserRechargeCardDTO.getSysUserId());
        }

        if (StringUtils.isNotBlank(shopUserRechargeCardDTO.getShopRechargeCardId())) {
            criteria.andShopRechargeCardIdEqualTo(shopUserRechargeCardDTO.getShopRechargeCardId());
        }

        if (StringUtils.isNotBlank(shopUserRechargeCardDTO.getSysShopId())) {
            criteria.andSysShopIdEqualTo(shopUserRechargeCardDTO.getSysShopId());
        }

        if (StringUtils.isNotBlank(shopUserRechargeCardDTO.getRechargeCardType())) {
            criteria.andRechargeCardTypeEqualTo(shopUserRechargeCardDTO.getRechargeCardType());
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
        extShopRechargeCardDTO.setCreateBy(UserUtils.getBossInfo().getName());
        List<String> imageUrls = extShopRechargeCardDTO.getImageList();
        if (CommonUtils.objectIsNotEmpty(imageUrls)) {
            extShopRechargeCardDTO.setImageUrl(imageUrls.get(0));
        }else{
            imageUrls = new ArrayList<>();
            imageUrls.add(ImageEnum.COMMON_CARD.getDesc());
            extShopRechargeCardDTO.setImageUrl(ImageEnum.COMMON_CARD.getDesc());
        }
        if(null == extShopRechargeCardDTO.getTimeDiscount()){
            extShopRechargeCardDTO.setTimeDiscount(1f);
        }
        if(null == extShopRechargeCardDTO.getPeriodDiscount()){
            extShopRechargeCardDTO.setPeriodDiscount(1f);
        }
        if(null == extShopRechargeCardDTO.getProductDiscount()){
            extShopRechargeCardDTO.setProductDiscount(1f);
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
     * 查询某个店的充值卡列表
     * @param shopRechargeCardDTO
     * @return
     */
    @Override
    public List<ShopRechargeCardDTO> getShopRechargeCardInfo(ShopRechargeCardDTO shopRechargeCardDTO) {
        logger.info("传入参数={}","shopRechargeCardDTO = [" + shopRechargeCardDTO + "]");
        if(null == shopRechargeCardDTO){
            logger.error("查询某个店的充值卡列表传入参数为空");
            return null;
        }

        ShopRechargeCardCriteria shopRechargeCardCriteria = new ShopRechargeCardCriteria();
        ShopRechargeCardCriteria.Criteria c = shopRechargeCardCriteria.createCriteria();

        if(StringUtils.isNotBlank(shopRechargeCardDTO.getId())){
            c.andIdEqualTo(shopRechargeCardDTO.getId());
        }

        if(StringUtils.isNotBlank(shopRechargeCardDTO.getRechargeCardType())){
            c.andRechargeCardTypeEqualTo(shopRechargeCardDTO.getRechargeCardType());
        }

        if(StringUtils.isNotBlank(shopRechargeCardDTO.getSysShopId())){
            c.andSysShopIdEqualTo(shopRechargeCardDTO.getSysShopId());
        }

        return shopRechargeCardMapper.selectByCriteria(shopRechargeCardCriteria);
    }

    /**
     * 保存用户余额充值账号
     *
     * @param shopUserProjectRelationDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveUserSpecialRechargeCardInfo(ShopUserRechargeCardDTO shopUserProjectRelationDTO) {
        logger.info("保存用户余额充值账号传入参数={}", "shopUserProjectRelationDTO = [" + shopUserProjectRelationDTO + "]");
        //查询某个店的特殊账号
        ShopRechargeCardDTO shopRechargeCardDTO = new ShopRechargeCardDTO();
        shopRechargeCardDTO.setRechargeCardType(RechargeCardTypeEnum.SPECIAL.getCode());
        String sysShopId = shopUserProjectRelationDTO.getSysShopId();
        if(StringUtils.isBlank(shopUserProjectRelationDTO.getSysShopId())){
            throw new RuntimeException("店铺为空");
        }
        shopRechargeCardDTO.setSysShopId(sysShopId);
        List<ShopRechargeCardDTO> shopRechargeCardInfo = getShopRechargeCardInfo(shopRechargeCardDTO);
        if(CommonUtils.objectIsEmpty(shopRechargeCardInfo)){
            throw new ServiceException(sysShopId+"为空");
        }
        //构建用户余额充值账号
        shopRechargeCardDTO = shopRechargeCardInfo.get(0);
        shopUserProjectRelationDTO.setId(IdGen.uuid());
        shopUserProjectRelationDTO.setSurplusAmount(new BigDecimal(0));
        shopUserProjectRelationDTO.setShopRechargeCardName(shopRechargeCardDTO.getName());
        shopUserProjectRelationDTO.setInitAmount(new BigDecimal(0));
        shopUserProjectRelationDTO.setPeriodDiscount(1f);
        shopUserProjectRelationDTO.setTimeDiscount(1f);
        shopUserProjectRelationDTO.setProductDiscount(1f);
        shopUserProjectRelationDTO.setSurplusAmount(new BigDecimal(0));
        shopUserProjectRelationDTO.setSysShopId(shopRechargeCardDTO.getSysShopId());
        shopUserProjectRelationDTO.setShopRechargeCardId(shopRechargeCardDTO.getId());
        if(null != UserUtils.getClerkInfo()){
            shopUserProjectRelationDTO.setSysClerkId(UserUtils.getClerkInfo().getId());
        }
        shopUserProjectRelationDTO.setCreateDate(new Date());
        shopUserProjectRelationDTO.setSysBossCode(redisUtils.getBossCode());
        shopUserProjectRelationDTO.setRechargeCardType(RechargeCardTypeEnum.SPECIAL.getCode());
        //保存用户与特殊账号的关系
        int selective = shopUserRechargeCardMapper.insertSelective(shopUserProjectRelationDTO);
        return selective;
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
        mongoUtils.updateImageUrl(extShopRechargeCardDTO.getImageList(), extShopRechargeCardDTO.getId());
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
        String sysShopId = extShopRechargeCardDTO.getSysShopId();
        if (CommonUtils.objectIsNotEmpty(timesList)) {
            logger.info("单次卡传入数据为不空");
            for (ShopProjectProductCardRelationDTO relationDTO : timesList) {
                relationDTO.setId(IdGen.uuid());
                relationDTO.setShopRechargeCardId(extShopRechargeCardDTO.getId());
                relationDTO.setShopGoodsTypeId(relationDTO.getShopGoodsTypeId());
                relationDTO.setGoodsType(GoodsTypeEnum.TIME_CARD.getCode());
                relationDTO.setCreateDate(new Date());
                relationDTO.setDiscount(extShopRechargeCardDTO.getTimeDiscount());
                shopProjectProductCardRelationMapper.insertSelective(relationDTO);
            }
        }else{
            logger.info("单次卡传入数据为空");
            //默认适用范围为全部
            ExtShopProjectInfoDTO extShopProjectInfoDTO = new ExtShopProjectInfoDTO();
            extShopProjectInfoDTO.setSysShopId(sysShopId);
            extShopProjectInfoDTO.setUseStyle(CardTypeEnum.TIME_CARD.getCode());
            extShopProjectInfoDTO.setStatus(CommonCodeEnum.ADD.getCode());
            List<ShopProjectInfoDTO> shopCourseProjectList = shopProjectService.getShopCourseProjectList(extShopProjectInfoDTO);
            //生成适用范围
            saveProjectTypeScope(extShopRechargeCardDTO, shopCourseProjectList);
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
        }else{
            logger.info("单次卡传入数据为空");
            //默认适用范围为全部 ,查询店铺的所有二级类别
            ExtShopProjectInfoDTO extShopProjectInfoDTO = new ExtShopProjectInfoDTO();
            extShopProjectInfoDTO.setSysShopId(sysShopId);
            extShopProjectInfoDTO.setUseStyle(CardTypeEnum.MONTH_CARD.getCode());
            extShopProjectInfoDTO.setStatus(CommonCodeEnum.ADD.getCode());
            List<ShopProjectInfoDTO> shopCourseProjectList = shopProjectService.getShopCourseProjectList(extShopProjectInfoDTO);
            //生成适用范围
            saveProjectTypeScope(extShopRechargeCardDTO, shopCourseProjectList);
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
        }else{
            ShopProductInfoDTO shopProductInfo = new ShopProductInfoDTO();
            shopProductInfo.setSysShopId(sysShopId);
            List<ShopProductInfoDTO> productInfo = shopProductInfoService.getShopProductInfo(shopProductInfo);
            if(CommonUtils.objectIsNotEmpty(productInfo)){
                HashMap<String, String> timeProductTemp = new HashMap<>(16);
                for(ShopProductInfoDTO dto:productInfo){
                    //去重
                    if(StringUtils.isBlank(timeProductTemp.get(dto.getProductTypeTwoId()))){
                        ShopProjectProductCardRelationDTO relationDTO = new ShopProjectProductCardRelationDTO();
                        relationDTO.setId(IdGen.uuid());
                        relationDTO.setShopRechargeCardId(extShopRechargeCardDTO.getId());
                        relationDTO.setShopGoodsTypeId(dto.getProductTypeTwoId());
                        relationDTO.setGoodsType(GoodsTypeEnum.PRODUCT.getCode());
                        relationDTO.setCreateDate(new Date());
                        relationDTO.setDiscount(extShopRechargeCardDTO.getTimeDiscount());
                        shopProjectProductCardRelationMapper.insertSelective(relationDTO);
                    }
                    timeProductTemp.put(dto.getProductTypeTwoId(),dto.getProductTypeTwoId());
                }
            }
        }
    }

    /**
     * 生成项目适用范围
     * @param extShopRechargeCardDTO
     * @param shopCourseProjectList
     */
    private void saveProjectTypeScope(ExtShopRechargeCardDTO extShopRechargeCardDTO, List<ShopProjectInfoDTO> shopCourseProjectList) {
        if(CommonUtils.objectIsNotEmpty(shopCourseProjectList)){
            HashMap<String, String> timeProjectTemp = new HashMap<>(16);
            for(ShopProjectInfoDTO dto:shopCourseProjectList){
                //去重
                if(StringUtils.isBlank(timeProjectTemp.get(dto.getProjectTypeTwoId()))){
                    ShopProjectProductCardRelationDTO relationDTO = new ShopProjectProductCardRelationDTO();
                    relationDTO.setId(IdGen.uuid());
                    relationDTO.setShopRechargeCardId(extShopRechargeCardDTO.getId());
                    relationDTO.setShopGoodsTypeId(dto.getProjectTypeTwoId());
                    relationDTO.setGoodsType(dto.getUseStyle());
                    relationDTO.setCreateDate(new Date());
                    relationDTO.setDiscount(extShopRechargeCardDTO.getTimeDiscount());
                    shopProjectProductCardRelationMapper.insertSelective(relationDTO);
                }
                timeProjectTemp.put(dto.getProjectTypeTwoId(),dto.getProjectTypeTwoId());
            }
        }
    }
}

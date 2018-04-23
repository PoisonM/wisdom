package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.ConsumeTypeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.enums.PayTypeEnum;
import com.wisdom.beauty.api.extDto.ShopUserOrderDTO;
import com.wisdom.beauty.api.extDto.ShopUserPayDTO;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.*;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.IdGen;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * FileName: ShopUserConsumService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 用户消费相关
 */
@Service("shopUserConsumeService")
public class ShopUserConsumeServiceImpl implements ShopUserConsumeService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ShopClerkService shopClerkService;

    @Resource
    private ShopUerConsumeRecordService shopUerConsumeRecordService;

    @Resource
    private SysUserAccountService sysUserAccountService;

    @Resource
    private ShopCardService shopCardService;

    @Autowired
    private ShopCustomerArchivesService shopCustomerArchivesService;

    @Resource
    private ShopProductInfoService shopProductInfoService;

    @Resource
    private ShopProjectService shopProjectService;

    @Resource
    private ShopProjectGroupService shopProjectGroupService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ShopUserConsumeService shopUserConsumeService;

    /**
     * 用户消费充值卡信息
     *
     * @param shopUserConsumeRecordDTO
     * @return
     */
    @Override
    public int userConsumeRechargeCard(ShopUserConsumeRecordDTO shopUserConsumeRecordDTO) {
        if (shopUserConsumeRecordDTO == null || StringUtils.isBlank(shopUserConsumeRecordDTO.getFlowId())) {
            logger.error("用户消费充值卡信息传入参数为空，{}", "shopUserConsumeRecordDTO = [" + shopUserConsumeRecordDTO + "]");
            throw new RuntimeException();
        }
        //更新用户充值卡记录信息
        ShopUserRechargeCardDTO shopUserRechargeCardDTO = new ShopUserRechargeCardDTO();
        shopUserConsumeRecordDTO.setId(shopUserConsumeRecordDTO.getFlowId());
        List<ShopUserRechargeCardDTO> userRechargeCardList = shopCardService.getUserRechargeCardList(shopUserRechargeCardDTO);
        if (CommonUtils.objectIsEmpty(userRechargeCardList)) {
            logger.error("用户消费充值卡信息,根据主键插叙用户充值卡为空，{}", "userRechargeCardList = [" + userRechargeCardList + "]");
            throw new RuntimeException();
        }
        shopUserRechargeCardDTO = userRechargeCardList.get(0);
        BigDecimal surplus = shopUserRechargeCardDTO.getSurplusAmount().subtract(shopUserConsumeRecordDTO.getPrice());
        shopUserRechargeCardDTO.setSurplusAmount(surplus);
        shopCardService.updateUserRechargeCard(shopUserRechargeCardDTO);
        //生成消费记录
        shopUerConsumeRecordService.saveCustomerConsumeRecord(shopUserConsumeRecordDTO);
        //更新用户的账户信息
        SysUserAccountDTO sysUserAccountDTO = new SysUserAccountDTO();
        sysUserAccountDTO.setSysUserId(shopUserConsumeRecordDTO.getSysUserId());
        sysUserAccountDTO.setSysShopId(shopUserConsumeRecordDTO.getSysShopId());
        sysUserAccountDTO = sysUserAccountService.getSysUserAccountDTO(sysUserAccountDTO);
        if (sysUserAccountDTO == null) {
            logger.error("查询用户的账户信息为空，{}", "shopUserConsumeRecordDTO = [" + shopUserConsumeRecordDTO + "]");
        }
        sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().subtract(shopUserConsumeRecordDTO.getPrice()));
        try {
            sysUserAccountService.updateSysUserAccountDTO(sysUserAccountDTO);
        } catch (Exception e) {
            logger.error("更新账户信息失败，失败信息为{}" + e.getMessage(), e);
            throw new RuntimeException();
        }
        //更新店员的账户信息
        shopClerkService.saveSysClerkFlowAccountInfo(shopUserConsumeRecordDTO);
        return 0;
    }

    /**
     * 用户充值操作
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int userRechargeOperation(ShopUserOrderDTO shopUserOrderDTO, ShopUserPayDTO shopUserPayDTO) {

        if (CommonUtils.objectIsEmpty(shopUserOrderDTO) || CommonUtils.objectIsEmpty(shopUserPayDTO)) {
            logger.debug("传入参数为空，{}", "shopUserOrderDTO = [" + shopUserOrderDTO + "], shopUserPayDTO = [" + shopUserPayDTO + "]");
            return 0;
        }
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();

        //同一笔订单号为交易流水号
        String transactionCodeNumber = shopUserOrderDTO.getOrderId();
        //获取用户的账户信息
        SysUserAccountDTO sysUserAccountDTO = new SysUserAccountDTO();
        sysUserAccountDTO.setShopUserArchivesId(shopUserOrderDTO.getShopUserArchivesId());
        sysUserAccountDTO = sysUserAccountService.getSysUserAccountDTO(sysUserAccountDTO);
        //获取用户的档案信息
        ShopUserArchivesDTO archivesDTO = new ShopUserArchivesDTO();
        archivesDTO.setId(shopUserOrderDTO.getShopUserArchivesId());
        ShopUserArchivesDTO archivesInfo = shopCustomerArchivesService.getShopUserArchivesInfo(archivesDTO);

        //产品列表相关操作
        List<ShopUserProductRelationDTO> productRelationDTOS = shopUserOrderDTO.getShopUserProductRelationDTOS();
        if (CommonUtils.objectIsNotEmpty(productRelationDTOS)) {
            for (ShopUserProductRelationDTO dto : productRelationDTOS) {
                //生成用户跟产品的关系
                String uuid = IdGen.uuid();
                dto.setId(uuid);
                dto.setSysShopId(clerkInfo.getSysShopId());
                dto.setSysShopName(clerkInfo.getSysShopName());
                shopProductInfoService.saveShopUserProductRelation(dto);

                ShopUserConsumeRecordDTO userConsumeRecordDTO = new ShopUserConsumeRecordDTO();
                userConsumeRecordDTO.setFlowName(dto.getShopProductName());
                userConsumeRecordDTO.setDiscount(new BigDecimal(dto.getDiscount()));
                userConsumeRecordDTO.setPrice(dto.getInitAmount());
                userConsumeRecordDTO.setConsumeNumber(dto.getInitTimes());
                userConsumeRecordDTO.setGoodsType(GoodsTypeEnum.PRODUCT.getCode());
                //生成充值记录
                ShopUserConsumeRecordDTO consumeRecordDTO = saveCustomerConsumeRecord(userConsumeRecordDTO, shopUserOrderDTO, shopUserPayDTO, clerkInfo, transactionCodeNumber, archivesInfo);
                shopClerkService.saveSysClerkFlowAccountInfo(consumeRecordDTO);
                //dto.getPrice()为此次交易的总价格
                sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().add(userConsumeRecordDTO.getPrice()));

                //更新用户的账户信息
                try {
                    sysUserAccountDTO.setFlowNo(transactionCodeNumber);
                    sysUserAccountService.updateSysUserAccountDTO(sysUserAccountDTO);
                } catch (Exception e) {
                    logger.error("更新账户信息失败，失败信息为{}" + e.getMessage(), e);
                    throw new RuntimeException();
                }
            }
        }

        //项目列表相关操作
        List<ShopUserProjectRelationDTO> projectRelationDTOS = shopUserOrderDTO.getShopUserProjectRelationDTOS();
        if (CommonUtils.objectIsNotEmpty(projectRelationDTOS)) {
            for (ShopUserProjectRelationDTO dto : projectRelationDTOS) {
                dto.setCreateDate(new Date());
                String uuid = IdGen.uuid();
                dto.setId(uuid);
                dto.setSysShopName(dto.getSysShopName());
                shopProjectService.saveUserProjectRelation(dto);

                ShopUserConsumeRecordDTO userConsumeRecordDTO = new ShopUserConsumeRecordDTO();
                userConsumeRecordDTO.setFlowName(dto.getSysShopProjectName());
                userConsumeRecordDTO.setDiscount(new BigDecimal(dto.getDiscount()));
                userConsumeRecordDTO.setPrice(dto.getSysShopProjectInitAmount());
                userConsumeRecordDTO.setConsumeNumber(dto.getSysShopProjectInitTimes());
                userConsumeRecordDTO.setGoodsType(dto.getUseStyle());
                //生成充值记录
                ShopUserConsumeRecordDTO consumeRecordDTO = saveCustomerConsumeRecord(userConsumeRecordDTO, shopUserOrderDTO, shopUserPayDTO, clerkInfo, transactionCodeNumber, archivesInfo);
                shopClerkService.saveSysClerkFlowAccountInfo(consumeRecordDTO);
            }
        }

        //套卡列表相关操作
        List<ShopUserProjectGroupRelRelationDTO> groupRelRelationDTOS = shopUserOrderDTO.getProjectGroupRelRelationDTOS();
        if (CommonUtils.objectIsNotEmpty(groupRelRelationDTOS)) {
            for (ShopUserProjectGroupRelRelationDTO dto : groupRelRelationDTOS) {
                //根据套卡id查询项目列表
                ShopProjectInfoGroupRelationDTO shopProjectInfoGroupRelationDTO = new ShopProjectInfoGroupRelationDTO();
                shopProjectInfoGroupRelationDTO.setShopProjectGroupId(dto.getShopProjectGroupId());
                shopProjectInfoGroupRelationDTO.setSysShopId(shopProjectInfoGroupRelationDTO.getSysShopId());
                List<ShopProjectInfoGroupRelationDTO> groupRelations = shopProjectService.getShopProjectInfoGroupRelations(shopProjectInfoGroupRelationDTO);

                if (null == groupRelations) {
                    logger.error("根据项目套卡主键查询出来的项目列表为空，{}", groupRelations);
                    throw new RuntimeException();
                }

                //生成用户跟套卡与项目的关系的关系
                for (ShopProjectInfoGroupRelationDTO dt : groupRelations) {
                    //查询项目信息
                    ShopProjectInfoDTO shopProjectInfoDTO = redisUtils.getShopProjectInfoFromRedis(dt.getShopProjectInfoId());
                    ShopUserProjectGroupRelRelationDTO groupRelRelationDTO = new ShopUserProjectGroupRelRelationDTO();
                    groupRelRelationDTO.setSysShopId(clerkInfo.getSysShopId());
                    groupRelRelationDTO.setSysUserId(archivesInfo.getSysUserId());
                    groupRelRelationDTO.setId(IdGen.uuid());
                    groupRelRelationDTO.setShopProjectGroupId(dto.getId());
                    groupRelRelationDTO.setShopProjectInfoGroupRelationId(dt.getId());
                    groupRelRelationDTO.setSysBossId(shopProjectInfoDTO.getSysBossId());
                    shopProjectGroupService.saveShopUserProjectGroupRelRelation(groupRelRelationDTO);
                    //生成充值记录
                    ShopUserConsumeRecordDTO userConsumeRecordDTO = new ShopUserConsumeRecordDTO();
                    userConsumeRecordDTO.setFlowName(groupRelRelationDTO.getShopProjectGroupName());
                    userConsumeRecordDTO.setFlowId(groupRelRelationDTO.getId());
                    userConsumeRecordDTO.setDiscount(new BigDecimal(groupRelRelationDTO.getDiscount()));
                    userConsumeRecordDTO.setPrice(groupRelRelationDTO.getProjectInitAmount());
                    userConsumeRecordDTO.setConsumeNumber(groupRelRelationDTO.getProjectInitTimes());
                    userConsumeRecordDTO.setGoodsType(GoodsTypeEnum.COLLECTION_CARD.getCode());
                    ShopUserConsumeRecordDTO consumeRecordDTO = saveCustomerConsumeRecord(userConsumeRecordDTO, shopUserOrderDTO, shopUserPayDTO, clerkInfo, transactionCodeNumber, archivesInfo);
                    shopClerkService.saveSysClerkFlowAccountInfo(consumeRecordDTO);
                }

            }
        }
        //充值卡列表相关操作
        List<ShopUserRechargeCardDTO> rechargeCardDTOS = shopUserOrderDTO.getShopUserRechargeCardDTOS();
        if (CommonUtils.objectIsNotEmpty(rechargeCardDTOS)) {
            for (ShopUserRechargeCardDTO dto : rechargeCardDTOS) {
                ShopUserConsumeRecordDTO userConsumeRecordDTO = new ShopUserConsumeRecordDTO();
                String uuid = IdGen.uuid();
                userConsumeRecordDTO.setFlowId(uuid);
                userConsumeRecordDTO.setId(uuid);
                userConsumeRecordDTO.setConsumeType(ConsumeTypeEnum.CONSUME.getCode());
                userConsumeRecordDTO.setCreateBy(clerkInfo.getSysUserId());
                userConsumeRecordDTO.setCreateDate(new Date());
                userConsumeRecordDTO.setFlowNo(transactionCodeNumber);
                userConsumeRecordDTO.setGoodsType(GoodsTypeEnum.RECHARGE_CARD.getCode());
                userConsumeRecordDTO.setSysUserName(archivesInfo.getSysUserName());
                userConsumeRecordDTO.setSysUserId(archivesInfo.getSysUserId());
                userConsumeRecordDTO.setSysShopName(archivesInfo.getSysShopName());
                userConsumeRecordDTO.setSysShopId(clerkInfo.getSysShopId());
                userConsumeRecordDTO.setSysClerkId(clerkInfo.getId());
                userConsumeRecordDTO.setSysBossId(clerkInfo.getSysBossId());
                userConsumeRecordDTO.setDetail(shopUserOrderDTO.getDetail());
                userConsumeRecordDTO.setPayType(PayTypeEnum.judgeValue(shopUserPayDTO.getPayType()).getCode());
                shopUserConsumeService.userConsumeRechargeCard(userConsumeRecordDTO);
            }
        }

//        try {
//            //遍历记录
//            for (ExtShopUserConsumeRecordDTO dto : extShopUserConsumeRecordDTOS) {
//                //虚拟商品类型
//                String goodsType = dto.getGoodsType();
//
//                //获取用户的账户信息
//                SysUserAccountDTO sysUserAccountDTO = sysUserAccountService.getSysUserAccountDTO(dto.getSysUserId());
//
//                //如果是次卡、疗程卡相关操作
//                if (GoodsTypeEnum.TIME_CARD.getCode().equals(goodsType) || GoodsTypeEnum.TREATMENT_CARD.getCode().equals(goodsType)) {
//
//                    if (dto.getShopUserProjectRelationDTO() == null) {
//                        logger.error("用户充值操作用户与卡的关系为空,{}", "ShopUserProjectRelationDTO = [" + dto.getShopUserProjectRelationDTO() + "]");
//                        throw new RuntimeException();
//                    }
//                    //生成用户与项目的关系
//                    ShopUserProjectRelationDTO shopUserRelationDTO = dto.getShopUserProjectRelationDTO();
//                    shopUserRelationDTO.setCreateDate(new Date());
//                    String uuid = IdGen.uuid();
//                    shopUserRelationDTO.setId(uuid);
//                    shopUserRelationDTO.setSysShopName(dto.getSysShopName());
//                    //流水id
//                    dto.setFlowId(uuid);
//                    dto.setFlowName(GoodsTypeEnum.TIME_CARD.getDesc());
//                    //如果用充值卡抵扣的话
//                    if (null != dto.getShopUserRechargeCardDTO()) {
//                        ShopUserRechargeCardDTO shopUserRechargeCardDTO = dto.getShopUserRechargeCardDTO();
//                        if (StringUtils.isBlank(shopUserRechargeCardDTO.getId())) {
//                            logger.error("用户充值操作,充值卡参数异常{}", "extShopUserConsumeRecordDTOS = [" + extShopUserConsumeRecordDTOS + "]");
//                            throw new RuntimeException();
//                        }
//                        ShopUserConsumeRecordDTO userConsumeRecordDTO = new ShopUserConsumeRecordDTO();
//                        userConsumeRecordDTO.setFlowId(dto.getShopUserRechargeCardDTO().getId());
//                        shopUserConsumeService.userConsumeRechargeCard(userConsumeRecordDTO);
//                    }
//                    shopProjectService.saveUserProjectRelation(shopUserRelationDTO);
//                }
//                //如果是套卡相关操作
//                else if (GoodsTypeEnum.COLLECTION_CARD.getCode().equals(goodsType)) {
//                    if (dto.getShopProjectGroupDTO() == null) {
//                        logger.error("用户充值操作用户与卡的关系为空,{}", "ShopUserProjectRelationDTO = [" + dto.getShopUserProjectRelationDTO() + "]");
//                        throw new RuntimeException();
//                    }
//                    ShopProjectGroupDTO shopProjectGroupDTO = dto.getShopProjectGroupDTO();
//
//                    //根据套卡id查询项目列表
//                    ShopProjectInfoGroupRelationDTO shopProjectInfoGroupRelationDTO = new ShopProjectInfoGroupRelationDTO();
//                    shopProjectInfoGroupRelationDTO.setShopProjectGroupId(shopProjectGroupDTO.getId());
//                    shopProjectInfoGroupRelationDTO.setSysShopId(shopProjectInfoGroupRelationDTO.getSysShopId());
//                    List<ShopProjectInfoGroupRelationDTO> groupRelations = shopProjectService.getShopProjectInfoGroupRelations(shopProjectInfoGroupRelationDTO);
//
//                    if (null == groupRelations) {
//                        logger.error("根据项目套卡主键查询出来的项目列表为空，{}", groupRelations);
//                        throw new RuntimeException();
//                    }
//                    //如果套卡能买多套
//                    for (int i = 0; i < dto.getConsumeNumber(); i++) {
//                        //生成用户跟套卡与项目的关系的关系
//                        for (ShopProjectInfoGroupRelationDTO dt : groupRelations) {
//                            //查询项目信息
//                            ShopProjectInfoDTO shopProjectInfoDTO = redisUtils.getShopProjectInfoFromRedis(dt.getShopProjectInfoId());
//                            ShopUserProjectGroupRelRelationDTO groupRelRelationDTO = new ShopUserProjectGroupRelRelationDTO();
//                            groupRelRelationDTO.setSysShopId(dto.getSysShopId());
//                            groupRelRelationDTO.setSysUserId(dto.getSysUserId());
//                            groupRelRelationDTO.setId(IdGen.uuid());
//                            groupRelRelationDTO.setProjectInitAmount(shopProjectInfoDTO.getMarketPrice());
//                            groupRelRelationDTO.setProjectInitTimes(shopProjectInfoDTO.getMaxContainTimes());
//                            groupRelRelationDTO.setProjectSurplusAmount(shopProjectInfoDTO.getMarketPrice());
//                            groupRelRelationDTO.setProjectSurplusTimes(shopProjectInfoDTO.getMaxContainTimes());
//                            groupRelRelationDTO.setShopProjectGroupId(shopProjectGroupDTO.getId());
//                            groupRelRelationDTO.setShopProjectGroupName(shopProjectGroupDTO.getProjectGroupName());
//                            groupRelRelationDTO.setShopProjectInfoGroupRelationId(dt.getId());
//                            groupRelRelationDTO.setSysBossId(shopProjectInfoDTO.getSysBossId());
//                            shopProjectGroupService.saveShopUserProjectGroupRelRelation(groupRelRelationDTO);
//                        }
//                    }
//                    dto.setFlowId(shopProjectGroupDTO.getId());
//                    dto.setFlowName(GoodsTypeEnum.COLLECTION_CARD.getDesc());
//                }
//                //如果是产品相关
//                else if (GoodsTypeEnum.PRODUCT.getCode().equals(goodsType)) {
//                    //生成用户跟产品的关系
//                    ShopUserProductRelationDTO userProductRelationDTO = dto.getShopUserProductRelationDTO();
//                    String uuid = IdGen.uuid();
//                    userProductRelationDTO.setId(uuid);
//                    dto.setFlowId(uuid);
//                    dto.setFlowName(GoodsTypeEnum.PRODUCT.getCode());
//                }
//                //dto.getPrice()为此次交易的总价格
//                sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().add(dto.getPrice()));
//
//                //更新用户的账户信息
//                try {
//                    sysUserAccountDTO.setFlowNo(transactionCodeNumber);
//                    sysUserAccountService.updateSysUserAccountDTO(sysUserAccountDTO);
//                } catch (Exception e) {
//                    logger.error("更新账户信息失败，失败信息为{}" + e.getMessage(), e);
//                    throw new RuntimeException();
//                }
//                //记录店员的流水信息
//                shopClerkService.saveSysClerkFlowAccountInfo(dto);
//
//                dto.setFlowNo(transactionCodeNumber);
//                dto.setOperDate(new Date());
//                dto.setConsumeType(ConsumeTypeEnum.CONSUME.getCode());
//                int record = shopUerConsumeRecordService.saveCustomerConsumeRecord(dto);
//                logger.debug("保存用户充值或消费操作返回结果 {}", record > 0 ? "成功" : "失败");
//            }
//        } catch (Exception e) {
//            logger.error("充值失败，失败原因为，{}" + e.getMessage(), e);
//            responseDTO.setResult(StatusConstant.FAILURE);
//            responseDTO.setResponseData("failure");
//        }
//        //保存用户的操作记录
//        responseDTO.setResult(StatusConstant.SUCCESS);
//        responseDTO.setResponseData("success");
//        logger.info("用户充值操作耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
//        return responseDTO;
        return 0;
    }

    /**
     * 生成充值记录
     *
     * @param shopUserPayDTO
     * @param clerkInfo
     * @param transactionCodeNumber
     * @param archivesInfo
     * @param userConsumeRecordDTO
     */
    private ShopUserConsumeRecordDTO saveCustomerConsumeRecord(ShopUserConsumeRecordDTO userConsumeRecordDTO, ShopUserOrderDTO shopUserOrderDTO, ShopUserPayDTO shopUserPayDTO, SysClerkDTO clerkInfo, String transactionCodeNumber, ShopUserArchivesDTO archivesInfo) {
        String uuid = IdGen.uuid();
        userConsumeRecordDTO.setFlowId(uuid);
        userConsumeRecordDTO.setId(uuid);
        userConsumeRecordDTO.setConsumeType(ConsumeTypeEnum.RECHARGE.getCode());
        userConsumeRecordDTO.setCreateBy(clerkInfo.getSysUserId());
        userConsumeRecordDTO.setCreateDate(new Date());
        userConsumeRecordDTO.setFlowNo(transactionCodeNumber);

        userConsumeRecordDTO.setSysUserName(archivesInfo.getSysUserName());
        userConsumeRecordDTO.setSysUserId(archivesInfo.getSysUserId());
        userConsumeRecordDTO.setSysShopName(archivesInfo.getSysShopName());
        userConsumeRecordDTO.setSysShopId(clerkInfo.getSysShopId());
        userConsumeRecordDTO.setSysClerkId(clerkInfo.getId());
        userConsumeRecordDTO.setSysBossId(clerkInfo.getSysBossId());
        userConsumeRecordDTO.setDetail(shopUserOrderDTO.getDetail());
        userConsumeRecordDTO.setPayType(PayTypeEnum.judgeValue(shopUserPayDTO.getPayType()).getCode());
        shopUerConsumeRecordService.saveCustomerConsumeRecord(userConsumeRecordDTO);
        return userConsumeRecordDTO;
    }


}

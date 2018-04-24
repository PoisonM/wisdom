package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.ConsumeTypeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.enums.PayTypeEnum;
import com.wisdom.beauty.api.extDto.ShopUserOrderDTO;
import com.wisdom.beauty.api.extDto.ShopUserPayDTO;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.*;
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
    public int userRechargeOperation(ShopUserOrderDTO shopUserOrderDTO, ShopUserPayDTO shopUserPayDTO, SysClerkDTO clerkInfo) {

        if (CommonUtils.objectIsEmpty(shopUserOrderDTO) || CommonUtils.objectIsEmpty(shopUserPayDTO)) {
            logger.debug("传入参数为空，{}", "shopUserOrderDTO = [" + shopUserOrderDTO + "], shopUserPayDTO = [" + shopUserPayDTO + "]");
            return 0;
        }

        try {
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
                    //更新用户的账户信息
                    updateUserAccount(transactionCodeNumber, sysUserAccountDTO, userConsumeRecordDTO);
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

                    //更新用户的账户信息
                    updateUserAccount(transactionCodeNumber, sysUserAccountDTO, userConsumeRecordDTO);
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

                        //更新用户的账户信息
                        updateUserAccount(transactionCodeNumber, sysUserAccountDTO, userConsumeRecordDTO);
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
                    userConsumeRechargeCard(userConsumeRecordDTO);

                    //更新用户的账户信息
                    try {
                        //dto.getPrice()为此次交易的总价格
                        sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().subtract(userConsumeRecordDTO.getPrice()));
                        sysUserAccountDTO.setFlowNo(transactionCodeNumber);
                        sysUserAccountService.updateSysUserAccountDTO(sysUserAccountDTO);
                    } catch (Exception e) {
                        logger.error("更新账户信息失败，失败信息为{}" + e.getMessage(), e);
                        throw new RuntimeException();
                    }
                }
            }
        } catch (RuntimeException e) {
            logger.error("用户充值操作异常，异常原因为" + e.getMessage(), e);
            throw new RuntimeException();
        }
        return 1;
    }

    /**
     * 更新用户的账户信息
     *
     * @param transactionCodeNumber
     * @param sysUserAccountDTO
     * @param userConsumeRecordDTO
     */
    private void updateUserAccount(String transactionCodeNumber, SysUserAccountDTO sysUserAccountDTO, ShopUserConsumeRecordDTO userConsumeRecordDTO) {
        //更新用户的账户信息
        try {
            //dto.getPrice()为此次交易的总价格
            sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().add(userConsumeRecordDTO.getPrice()));
            sysUserAccountDTO.setFlowNo(transactionCodeNumber);
            sysUserAccountService.updateSysUserAccountDTO(sysUserAccountDTO);
        } catch (Exception e) {
            logger.error("更新账户信息失败，失败信息为{}" + e.getMessage(), e);
            throw new RuntimeException();
        }
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

package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.ConsumeTypeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.enums.OrderStatusEnum;
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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    private MongoTemplate mongoTemplate;

    /**
     * 用户消费充值卡信息
     *
     * @param userRechargeCardDTO
     * @return
     */
    @Override
    public int userConsumeRechargeCard(ShopUserRechargeCardDTO userRechargeCardDTO) {
        if (userRechargeCardDTO == null || StringUtils.isBlank(userRechargeCardDTO.getId())) {
            logger.error("用户消费充值卡信息传入参数为空");
            throw new RuntimeException();
        }
        //更新用户充值卡记录信息
        List<ShopUserRechargeCardDTO> userRechargeCardList = shopCardService.getUserRechargeCardList(userRechargeCardDTO);
        if (CommonUtils.objectIsEmpty(userRechargeCardList)) {
            logger.error("用户消费充值卡信息,根据主键插叙用户充值卡为空，{}", "userRechargeCardList = [" + userRechargeCardList + "]");
            throw new RuntimeException();
        }
        userRechargeCardDTO = userRechargeCardList.get(0);
        BigDecimal surplus = userRechargeCardDTO.getSurplusAmount().subtract(userRechargeCardDTO.getSurplusAmount());
        userRechargeCardDTO.setSurplusAmount(surplus);
        return shopCardService.updateUserRechargeCard(userRechargeCardDTO);
    }

    /**
     * 用户充值操作
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int userRechargeOperation(ShopUserOrderDTO shopUserOrderDTO, ShopUserPayDTO shopUserPayDTO, SysClerkDTO clerkInfo) {

        if (CommonUtils.objectIsEmpty(shopUserOrderDTO) || CommonUtils.objectIsEmpty(shopUserPayDTO)) {
            logger.info("传入参数为空，{}", "shopUserOrderDTO = [" + shopUserOrderDTO + "], shopUserPayDTO = [" + shopUserPayDTO + "]");
            return 0;
        }

        try {
            //同一笔订单号为交易流水号
            String transactionCodeNumber = shopUserOrderDTO.getOrderId();
            String orderId = shopUserPayDTO.getOrderId();
            logger.info("订单号={}，支付操作产生的交易流水号={}", orderId, transactionCodeNumber);

            //获取用户的账户信息
            SysUserAccountDTO sysUserAccountDTO = new SysUserAccountDTO();
            sysUserAccountDTO.setSysUserId(shopUserOrderDTO.getUserId());
            sysUserAccountDTO.setSysShopId(shopUserOrderDTO.getShopId());
            sysUserAccountDTO = sysUserAccountService.getSysUserAccountDTO(sysUserAccountDTO);
            logger.info("订单号={}，查询用户的账户信息={}", orderId, sysUserAccountDTO);
            if (CommonUtils.objectIsEmpty(sysUserAccountDTO)) {
                logger.info("订单号={}，查询用户的账户信息为空", orderId);
                throw new ServiceException("订单信息为空");
            }

            //获取用户的档案信息
            ShopUserArchivesDTO archivesDTO = new ShopUserArchivesDTO();
            archivesDTO.setId(shopUserOrderDTO.getShopUserArchivesId());
            ShopUserArchivesDTO archivesInfo = shopCustomerArchivesService.getShopUserArchivesInfo(archivesDTO);
            if (CommonUtils.objectIsEmpty(archivesInfo)) {
                logger.info("订单号={}，查询用户的档案信息为空", orderId);
                throw new ServiceException("查询用户的档案信息为空");
            }

            //产品列表相关操作
            List<ShopUserProductRelationDTO> productRelationDTOS = shopUserOrderDTO.getShopUserProductRelationDTOS();
            if (CommonUtils.objectIsNotEmpty(productRelationDTOS)) {
                for (ShopUserProductRelationDTO dto : productRelationDTOS) {

                    String uuid = IdGen.uuid();
                    dto.setId(uuid);
                    dto.setSysShopId(clerkInfo.getSysShopId());
                    dto.setSysShopName(clerkInfo.getSysShopName());
                    dto.setSurplusTimes(dto.getInitTimes());
                    dto.setSurplusAmount(dto.getSurplusAmount());
                    logger.info("订单号={}，生成用户跟产品的关系={}", orderId, dto);
                    shopProductInfoService.saveShopUserProductRelation(dto);

                    ShopUserConsumeRecordDTO userConsumeRecordDTO = new ShopUserConsumeRecordDTO();
                    userConsumeRecordDTO.setFlowName(dto.getShopProductName());
                    if (null != dto.getDiscount()) {
                        userConsumeRecordDTO.setDiscount(new BigDecimal(dto.getDiscount()));
                    }
                    userConsumeRecordDTO.setPrice(dto.getInitAmount());
                    userConsumeRecordDTO.setConsumeType(ConsumeTypeEnum.RECHARGE.getCode());
                    userConsumeRecordDTO.setConsumeNumber(dto.getInitTimes());
                    userConsumeRecordDTO.setGoodsType(GoodsTypeEnum.PRODUCT.getCode());
                    ShopUserConsumeRecordDTO consumeRecordDTO = saveCustomerConsumeRecord(userConsumeRecordDTO, shopUserOrderDTO, shopUserPayDTO, clerkInfo, transactionCodeNumber, archivesInfo);

                    logger.info("订单号={}，生成店员流水记录={}", orderId, consumeRecordDTO);
                    shopClerkService.saveSysClerkFlowAccountInfo(consumeRecordDTO);
                    //更新用户的账户信息
                    sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().add(userConsumeRecordDTO.getPrice()));
                    logger.info("订单号={}，用户的账户金额={}", orderId, sysUserAccountDTO.getSumAmount());
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
                    dto.setSysShopId(clerkInfo.getSysShopId());
                    dto.setSysShopProjectSurplusTimes(dto.getSysShopProjectInitTimes());
                    dto.setSysShopProjectSurplusAmount(dto.getSysShopProjectInitAmount());
                    dto.setCreateDate(new Date());
                    logger.info("订单号={}，生成用户跟项目的关系={}", orderId, dto);
                    shopProjectService.saveUserProjectRelation(dto);

                    ShopUserConsumeRecordDTO userConsumeRecordDTO = new ShopUserConsumeRecordDTO();
                    userConsumeRecordDTO.setFlowName(dto.getSysShopProjectName());
                    userConsumeRecordDTO.setDiscount(new BigDecimal(dto.getDiscount()));
                    userConsumeRecordDTO.setPrice(dto.getSysShopProjectInitAmount());
                    userConsumeRecordDTO.setConsumeNumber(dto.getSysShopProjectInitTimes());
                    userConsumeRecordDTO.setGoodsType(dto.getUseStyle());
                    userConsumeRecordDTO.setConsumeType(ConsumeTypeEnum.RECHARGE.getCode());
                    //生成充值记录
                    ShopUserConsumeRecordDTO consumeRecordDTO = saveCustomerConsumeRecord(userConsumeRecordDTO, shopUserOrderDTO, shopUserPayDTO, clerkInfo, transactionCodeNumber, archivesInfo);
                    logger.info("订单号={}，生成店员流水记录={}", orderId, consumeRecordDTO);
                    shopClerkService.saveSysClerkFlowAccountInfo(consumeRecordDTO);

                    //更新用户的账户信息
                    sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().add(userConsumeRecordDTO.getPrice()));
                    logger.info("订单号={}，用户的账户金额={}", orderId, sysUserAccountDTO.getSumAmount());
                }
            }

            //套卡列表相关操作
            List<ShopUserProjectGroupRelRelationDTO> groupRelRelationDTOS = shopUserOrderDTO.getProjectGroupRelRelationDTOS();
            if (CommonUtils.objectIsNotEmpty(groupRelRelationDTOS)) {
                for (ShopUserProjectGroupRelRelationDTO dto : groupRelRelationDTOS) {
                    //根据套卡id查询项目列表
                    ShopProjectInfoGroupRelationDTO shopProjectInfoGroupRelationDTO = new ShopProjectInfoGroupRelationDTO();
                    shopProjectInfoGroupRelationDTO.setShopProjectGroupId(dto.getShopProjectGroupId());
                    shopProjectInfoGroupRelationDTO.setSysShopId(clerkInfo.getSysShopId());
                    List<ShopProjectInfoGroupRelationDTO> groupRelations = shopProjectService.getShopProjectInfoGroupRelations(shopProjectInfoGroupRelationDTO);

                    if (null == groupRelations) {
                        logger.error("订单号={}，根据项目套卡主键查询出来的项目列表为空，{}", orderId, groupRelations);
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
                        groupRelRelationDTO.setProjectSurplusTimes(dto.getProjectInitTimes());
                        groupRelRelationDTO.setProjectSurplusAmount(dto.getProjectInitAmount());
                        logger.info("订单号={}，生成用户跟套卡的关系的关系记录={}", orderId, groupRelRelationDTO);
                        shopProjectGroupService.saveShopUserProjectGroupRelRelation(groupRelRelationDTO);
                    }

                    //生成充值记录
                    ShopUserConsumeRecordDTO userConsumeRecordDTO = new ShopUserConsumeRecordDTO();
                    userConsumeRecordDTO.setFlowName(dto.getShopProjectGroupName());
                    userConsumeRecordDTO.setFlowId(dto.getId());
                    if (null != dto.getDiscount()) {
                        userConsumeRecordDTO.setDiscount(new BigDecimal(dto.getDiscount()));
                    }
                    userConsumeRecordDTO.setPrice(dto.getProjectInitAmount());
                    userConsumeRecordDTO.setConsumeNumber(dto.getProjectInitTimes());
                    userConsumeRecordDTO.setGoodsType(GoodsTypeEnum.COLLECTION_CARD.getCode());
                    userConsumeRecordDTO.setConsumeType(ConsumeTypeEnum.RECHARGE.getCode());
                    ShopUserConsumeRecordDTO consumeRecordDTO = saveCustomerConsumeRecord(userConsumeRecordDTO, shopUserOrderDTO, shopUserPayDTO, clerkInfo, transactionCodeNumber, archivesInfo);
                    logger.info("订单号={}，生成店员流水记录={}", orderId, consumeRecordDTO);
                    shopClerkService.saveSysClerkFlowAccountInfo(consumeRecordDTO);

                    //更新用户的账户信息
                    sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().add(userConsumeRecordDTO.getPrice()));
                    logger.info("订单号={}，用户的账户金额={}", orderId, sysUserAccountDTO.getSumAmount());

                }
            }
            //充值卡列表相关操作
            List<ShopUserRechargeCardDTO> rechargeCardDTOS = shopUserPayDTO.getShopUserRechargeCardDTOS();
            if (CommonUtils.objectIsNotEmpty(rechargeCardDTOS)) {
                for (ShopUserRechargeCardDTO dto : rechargeCardDTOS) {
                    //更新用户的充值卡记录
                    ShopUserConsumeRecordDTO userConsumeRecordDTO = new ShopUserConsumeRecordDTO();

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
                    logger.info("订单号={},更新用户的充值卡信息={}", orderId, userConsumeRecordDTO);
                    userConsumeRechargeCard(dto);

                    userConsumeRecordDTO.setGoodsType(GoodsTypeEnum.COLLECTION_CARD.getCode());
                    ShopUserConsumeRecordDTO consumeRecordDTO = saveCustomerConsumeRecord(userConsumeRecordDTO, shopUserOrderDTO, shopUserPayDTO, clerkInfo, transactionCodeNumber, archivesInfo);
                    logger.info("订单号={}，生成店员流水记录={}", orderId, consumeRecordDTO);
                    shopClerkService.saveSysClerkFlowAccountInfo(consumeRecordDTO);

                    //更新用户的账户信息
                    sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().subtract(userConsumeRecordDTO.getPrice()));
                    logger.info("订单号={}，用户的账户金额={}", orderId, sysUserAccountDTO.getSumAmount());
                }
            }

            sysUserAccountService.updateSysUserAccountDTO(sysUserAccountDTO);
            //订单已支付，更新用户订单信息
            Query query = new Query().addCriteria(Criteria.where("orderId").is(shopUserOrderDTO.getOrderId()));
            Update update = new Update();
            update.set("status", OrderStatusEnum.ALREADY_PAY.getCode());
            update.set("cashPayPrice", shopUserPayDTO.getCashPayPrice());
            update.set("surplusPayPrice", shopUserPayDTO.getSurplusPayPrice());
            update.set("payType", shopUserPayDTO.getPayType());
            mongoTemplate.upsert(query, update, "shopUserOrderDTO");
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
        logger.info("订单号={}，生成充值记录,{}", shopUserOrderDTO.getOrderId(), userConsumeRecordDTO);
        shopUerConsumeRecordService.saveCustomerConsumeRecord(userConsumeRecordDTO);
        return userConsumeRecordDTO;
    }


}

package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.*;
import com.wisdom.beauty.api.extDto.ShopRechargeCardOrderDTO;
import com.wisdom.beauty.api.extDto.ShopUserConsumeDTO;
import com.wisdom.beauty.api.extDto.ShopUserOrderDTO;
import com.wisdom.beauty.api.extDto.ShopUserPayDTO;
import com.wisdom.beauty.core.service.*;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
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

import static java.math.BigDecimal.ROUND_HALF_DOWN;

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
    private MongoTemplate mongoTemplate;

    @Autowired
    private ShopRechargeCardService shopRechargeCardService;


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
            ShopUserArchivesDTO archivesInfo = shopCustomerArchivesService.getShopUserArchivesInfo(archivesDTO).get(0);
            if (CommonUtils.objectIsEmpty(archivesInfo)) {
                logger.info("订单号={}，查询用户的档案信息为空", orderId);
                throw new ServiceException("查询用户的档案信息为空");
            }

            //产品列表相关操作
            List<ShopUserProductRelationDTO> productRelationDTOS = shopUserOrderDTO.getShopUserProductRelationDTOS();
            purchaseProduct(shopUserOrderDTO, shopUserPayDTO, clerkInfo, transactionCodeNumber, orderId, sysUserAccountDTO, archivesInfo, productRelationDTOS);

            //项目列表相关操作
            List<ShopUserProjectRelationDTO> projectRelationDTOS = shopUserOrderDTO.getShopUserProjectRelationDTOS();
            purchaseProject(shopUserOrderDTO, shopUserPayDTO, clerkInfo, transactionCodeNumber, orderId, sysUserAccountDTO, archivesInfo, projectRelationDTOS);

            //套卡列表相关操作
            List<ShopUserProjectGroupRelRelationDTO> groupRelRelationDTOS = shopUserOrderDTO.getProjectGroupRelRelationDTOS();
            purchaseProjectGroup(shopUserOrderDTO, shopUserPayDTO, clerkInfo, transactionCodeNumber, orderId, sysUserAccountDTO, archivesInfo, groupRelRelationDTOS);

            //充值卡列表相关操作
            List<ShopUserRechargeCardDTO> rechargeCardDTOS = shopUserPayDTO.getShopUserRechargeCardDTOS();
            useRechargeCard(shopUserOrderDTO, shopUserPayDTO, clerkInfo, transactionCodeNumber, orderId, sysUserAccountDTO, archivesInfo, rechargeCardDTOS);

            sysUserAccountService.updateSysUserAccountDTO(sysUserAccountDTO);
            //订单已支付，更新用户订单信息
            Query query = new Query().addCriteria(Criteria.where("orderId").is(shopUserOrderDTO.getOrderId()));
            Update update = new Update();
            update.set("status", OrderStatusEnum.ALREADY_PAY.getCode());
            update.set("cashPayPrice", shopUserPayDTO.getCashPayPrice());
            update.set("surplusPayPrice", shopUserPayDTO.getSurplusPayPrice());
            update.set("payType", shopUserPayDTO.getPayType());
            update.set("balancePay", shopUserPayDTO.getBalancePay());
            update.set("detail", shopUserPayDTO.getDetail());
            mongoTemplate.upsert(query, update, "shopUserOrderDTO");
        } catch (RuntimeException e) {
            logger.error("用户充值操作异常，异常原因为" + e.getMessage(), e);
            throw new RuntimeException();
        }
        return 1;
    }

    /**
     * 用户使用充值卡抵扣支付
     *
     * @param shopUserOrderDTO
     * @param shopUserPayDTO
     * @param clerkInfo
     * @param transactionCodeNumber
     * @param orderId
     * @param sysUserAccountDTO
     * @param archivesInfo
     * @param rechargeCardDTOS
     */
    private void useRechargeCard(ShopUserOrderDTO shopUserOrderDTO, ShopUserPayDTO shopUserPayDTO, SysClerkDTO clerkInfo, String transactionCodeNumber, String orderId, SysUserAccountDTO sysUserAccountDTO, ShopUserArchivesDTO archivesInfo, List<ShopUserRechargeCardDTO> rechargeCardDTOS) {
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
                userConsumeRecordDTO.setPrice(dto.getSurplusAmount());
                userConsumeRecordDTO.setDetail(shopUserOrderDTO.getDetail());
                userConsumeRecordDTO.setPayType(PayTypeEnum.judgeValue(shopUserPayDTO.getPayType()).getCode());
                logger.info("订单号={},更新用户的充值卡信息={}", orderId, userConsumeRecordDTO);
                userConsumeRechargeCard(dto);

                userConsumeRecordDTO.setGoodsType(GoodsTypeEnum.COLLECTION_CARD.getCode());
                saveCustomerConsumeRecord(userConsumeRecordDTO, shopUserOrderDTO, shopUserPayDTO, clerkInfo, transactionCodeNumber, archivesInfo);

                //更新用户的账户信息
                sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().subtract(userConsumeRecordDTO.getPrice()));
                logger.info("订单号={}，用户的账户金额={}", orderId, sysUserAccountDTO.getSumAmount());
            }
        }
    }

    /**
     * 用户购买套卡
     *
     * @param shopUserOrderDTO
     * @param shopUserPayDTO
     * @param clerkInfo
     * @param transactionCodeNumber
     * @param orderId
     * @param sysUserAccountDTO
     * @param archivesInfo
     * @param groupRelRelationDTOS
     */
    private void purchaseProjectGroup(ShopUserOrderDTO shopUserOrderDTO, ShopUserPayDTO shopUserPayDTO, SysClerkDTO clerkInfo, String transactionCodeNumber, String orderId, SysUserAccountDTO sysUserAccountDTO, ShopUserArchivesDTO archivesInfo, List<ShopUserProjectGroupRelRelationDTO> groupRelRelationDTOS) {
        if (CommonUtils.objectIsNotEmpty(groupRelRelationDTOS)) {
            for (ShopUserProjectGroupRelRelationDTO dto : groupRelRelationDTOS) {
                if (null == dto.getProjectInitTimes()) {
                    dto.setProjectInitTimes(1);
                }
                //用户一次性购买多个
                for (int i = 0; i < dto.getProjectInitTimes(); i++) {
                    //购买一个套卡的金额
                    BigDecimal price = dto.getProjectInitAmount();
                    BigDecimal discount = dto.getShopGroupPuchasePrice().divide(dto.getProjectInitAmount(), 2, ROUND_HALF_DOWN);
                    dto.setDiscount(discount.floatValue());
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
                        ShopUserProjectGroupRelRelationDTO groupRelRelationDTO = new ShopUserProjectGroupRelRelationDTO();
                        groupRelRelationDTO.setSysShopId(clerkInfo.getSysShopId());
                        groupRelRelationDTO.setSysUserId(archivesInfo.getSysUserId());
                        groupRelRelationDTO.setId(IdGen.uuid());
                        groupRelRelationDTO.setShopProjectGroupId(dto.getId());
                        groupRelRelationDTO.setShopProjectInfoGroupRelationId(dt.getId());
                        groupRelRelationDTO.setProjectSurplusTimes(dt.getShopProjectServiceTimes());
                        groupRelRelationDTO.setProjectSurplusAmount(dt.getShopProjectPrice());
                        logger.info("订单号={}，生成用户跟套卡的关系的关系记录={}", orderId, groupRelRelationDTO);
                        shopProjectGroupService.saveShopUserProjectGroupRelRelation(groupRelRelationDTO);
                    }
                    //生成充值记录
                    ShopUserConsumeRecordDTO userConsumeRecordDTO = new ShopUserConsumeRecordDTO();
                    userConsumeRecordDTO.setFlowName(dto.getShopProjectGroupName());
                    //存储套卡id
                    userConsumeRecordDTO.setFlowId(dto.getId());
                    if (null != dto.getDiscount()) {
                        userConsumeRecordDTO.setDiscount(new BigDecimal(dto.getDiscount()));
                    }
                    userConsumeRecordDTO.setPrice(price);
                    userConsumeRecordDTO.setDiscount(new BigDecimal(dto.getDiscount()));
                    userConsumeRecordDTO.setConsumeNumber(dto.getProjectInitTimes());
                    userConsumeRecordDTO.setGoodsType(GoodsTypeEnum.COLLECTION_CARD.getCode());
                    userConsumeRecordDTO.setConsumeType(ConsumeTypeEnum.RECHARGE.getCode());
                    saveCustomerConsumeRecord(userConsumeRecordDTO, shopUserOrderDTO, shopUserPayDTO, clerkInfo, transactionCodeNumber, archivesInfo);

                    //更新用户的账户信息
                    sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().add(userConsumeRecordDTO.getPrice()));
                    logger.info("订单号={}，用户的账户金额={}", orderId, sysUserAccountDTO.getSumAmount());
                }
            }
        }
    }

    /**
     * 用户购买项目
     *
     * @param shopUserOrderDTO
     * @param shopUserPayDTO
     * @param clerkInfo
     * @param transactionCodeNumber
     * @param orderId
     * @param sysUserAccountDTO
     * @param archivesInfo
     * @param projectRelationDTOS
     */
    private void purchaseProject(ShopUserOrderDTO shopUserOrderDTO, ShopUserPayDTO shopUserPayDTO, SysClerkDTO clerkInfo, String transactionCodeNumber, String orderId, SysUserAccountDTO sysUserAccountDTO, ShopUserArchivesDTO archivesInfo, List<ShopUserProjectRelationDTO> projectRelationDTOS) {
        if (CommonUtils.objectIsNotEmpty(projectRelationDTOS)) {

            for (ShopUserProjectRelationDTO dto : projectRelationDTOS) {
                //前台传过来的为多个数量的价格总计
                Integer sysShopProjectInitTimes = dto.getSysShopProjectInitTimes();
                if (null == sysShopProjectInitTimes) {
                    dto.setSysShopProjectInitTimes(1);
                }
                //项目有可能购买多个
                for (int i = 0; i < sysShopProjectInitTimes; i++) {
                    dto.setCreateDate(new Date());
                    String uuid = IdGen.uuid();
                    dto.setId(uuid);
                    dto.setSysShopName(dto.getSysShopName());
                    dto.setSysShopId(clerkInfo.getSysShopId());
                    dto.setSysBossId(clerkInfo.getSysBossId());
                    //单个项目的价格 = 总金额/购买了多少个
                    BigDecimal price = dto.getSysShopProjectInitAmount();
                    BigDecimal divide = dto.getSysShopProjectPurchasePrice().divide(dto.getSysShopProjectInitAmount(), 2, ROUND_HALF_DOWN);
                    dto.setDiscount(divide.toString());
                    //如果是次卡的话
                    if (GoodsTypeEnum.TIME_CARD.getCode().equals(dto.getUseStyle())) {
                        dto.setSysShopProjectSurplusAmount(new BigDecimal(0));
                        dto.setSysShopProjectSurplusTimes(0);
                    } else {
                        dto.setSysShopProjectSurplusAmount(price);
                        dto.setSysShopProjectSurplusTimes(dto.getServiceTime());
                    }

                    dto.setSysShopProjectInitAmount(price);
                    dto.setCreateDate(new Date());
                    //划卡次数即为服务次数
                    dto.setSysShopProjectInitTimes(dto.getServiceTime());
                    logger.info("订单号={}，生成用户跟项目的关系={}", orderId, dto);
                    shopProjectService.saveUserProjectRelation(dto);
                    //构造消费记录
                    ShopUserConsumeRecordDTO userConsumeRecordDTO = new ShopUserConsumeRecordDTO();
                    userConsumeRecordDTO.setConsumeType(ConsumeTypeEnum.RECHARGE.getCode());
                    userConsumeRecordDTO.setFlowName(dto.getSysShopProjectName());
                    userConsumeRecordDTO.setDiscount(new BigDecimal(dto.getDiscount()));
                    userConsumeRecordDTO.setPrice(price);
                    userConsumeRecordDTO.setConsumeNumber(sysShopProjectInitTimes);
                    userConsumeRecordDTO.setGoodsType(dto.getUseStyle());
                    //更新用户的账户信息
                    sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().add(userConsumeRecordDTO.getPrice()));
                    //生成充值记录
                    saveCustomerConsumeRecord(userConsumeRecordDTO, shopUserOrderDTO, shopUserPayDTO, clerkInfo, transactionCodeNumber, archivesInfo);

                }
                logger.info("订单号={}，用户的账户金额={}", orderId, sysUserAccountDTO.getSumAmount());
            }
        }
    }

    /**
     * 用户购买产品操作
     *
     * @param shopUserOrderDTO
     * @param shopUserPayDTO
     * @param clerkInfo
     * @param transactionCodeNumber
     * @param orderId
     * @param sysUserAccountDTO
     * @param archivesInfo
     * @param productRelationDTOS
     */
    private void purchaseProduct(ShopUserOrderDTO shopUserOrderDTO, ShopUserPayDTO shopUserPayDTO, SysClerkDTO clerkInfo, String transactionCodeNumber, String orderId, SysUserAccountDTO sysUserAccountDTO, ShopUserArchivesDTO archivesInfo, List<ShopUserProductRelationDTO> productRelationDTOS) {
        if (CommonUtils.objectIsNotEmpty(productRelationDTOS)) {

            for (ShopUserProductRelationDTO dto : productRelationDTOS) {
                String uuid = IdGen.uuid();
                dto.setId(uuid);
                dto.setSysShopId(clerkInfo.getSysShopId());
                dto.setSysShopName(clerkInfo.getSysShopName());
                dto.setSysBossId(clerkInfo.getSysBossId());
                dto.setSurplusTimes(dto.getInitTimes());
                dto.setSurplusAmount(dto.getInitAmount());
                BigDecimal divide = dto.getPurchasePrice().divide(dto.getInitAmount(), 2, ROUND_HALF_DOWN);
                dto.setDiscount(divide.floatValue());
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
                userConsumeRecordDTO.setDiscount(new BigDecimal(dto.getDiscount()));
                ShopUserConsumeRecordDTO consumeRecordDTO = saveCustomerConsumeRecord(userConsumeRecordDTO, shopUserOrderDTO, shopUserPayDTO, clerkInfo, transactionCodeNumber, archivesInfo);

                logger.info("订单号={}，生成店员流水记录={}", orderId, consumeRecordDTO);
                shopClerkService.saveSysClerkFlowAccountInfo(consumeRecordDTO);
                //更新用户的账户信息
                sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().add(userConsumeRecordDTO.getPrice()));
                logger.info("订单号={}，用户的账户金额={}", orderId, sysUserAccountDTO.getSumAmount());
            }
        }
    }

    /**
     * 用户划疗程卡
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int consumeCourseCard(List<ShopUserConsumeDTO> shopUserConsumeDTO, SysClerkDTO clerkInfo) {

        String transactionCodeNumber = DateUtils.DateToStr(new Date(), "dateMillisecond");
        if (CommonUtils.objectIsEmpty(shopUserConsumeDTO)) {
            logger.info("用户划疗程卡传入参数={}", "shopUserConsumeDTO = [" + shopUserConsumeDTO + "], clerkInfo = [" + clerkInfo + "]");
            return 0;
        }
        // 查询用户的账户信息
        SysUserAccountDTO sysUserAccountDTO = new SysUserAccountDTO();
        sysUserAccountDTO.setSysUserId(shopUserConsumeDTO.get(0).getSysUserId());
        sysUserAccountDTO.setSysShopId(clerkInfo.getSysShopId());
        sysUserAccountDTO = sysUserAccountService.getSysUserAccountDTO(sysUserAccountDTO);

        for (ShopUserConsumeDTO dto : shopUserConsumeDTO) {

            logger.info("开始更新用户的疗程卡={}", dto.toString());
            ShopUserProjectRelationDTO shopUserProjectRelationDTO = new ShopUserProjectRelationDTO();
            shopUserProjectRelationDTO.setId(dto.getConsumeId());
            List<ShopUserProjectRelationDTO> userProjectList = shopProjectService.getUserProjectList(shopUserProjectRelationDTO);
            if (CommonUtils.objectIsEmpty(userProjectList)) {
                throw new ServiceException("根据主键查询项目列表为空");
            }
            shopUserProjectRelationDTO = userProjectList.get(0);

            //更新用户与卡表的数量
            shopUserProjectRelationDTO.setSysShopProjectSurplusTimes(shopUserProjectRelationDTO.getSysShopProjectSurplusTimes() - dto.getConsumeNum());
            //更新用户与卡表的关系的金额
            shopUserProjectRelationDTO.setSysShopProjectSurplusAmount(shopUserProjectRelationDTO.getSysShopProjectSurplusAmount().subtract(dto.getConsumePrice()));

            shopProjectService.updateUserAndProjectRelation(shopUserProjectRelationDTO);

            ShopUserConsumeRecordDTO consumeRecordDTO = new ShopUserConsumeRecordDTO();
            String uuid = IdGen.uuid();
            consumeRecordDTO.setFlowId(uuid);
            consumeRecordDTO.setId(uuid);
            consumeRecordDTO.setCreateBy(clerkInfo.getSysUserId());
            consumeRecordDTO.setCreateDate(new Date());
            consumeRecordDTO.setFlowNo(transactionCodeNumber);
            consumeRecordDTO.setSysUserId(dto.getSysUserId());
            consumeRecordDTO.setSysShopName(clerkInfo.getSysShopName());
            consumeRecordDTO.setSysShopId(clerkInfo.getSysShopId());
            consumeRecordDTO.setSysClerkId(clerkInfo.getId());
            consumeRecordDTO.setSysBossId(clerkInfo.getSysBossId());
            consumeRecordDTO.setFlowName(shopUserProjectRelationDTO.getSysShopProjectName());
            consumeRecordDTO.setStatus(CommonCodeEnum.SUCCESS.getCode());
            consumeRecordDTO.setOperDate(new Date());
            consumeRecordDTO.setCreateBy(clerkInfo.getId());
            consumeRecordDTO.setConsumeNumber(dto.getConsumeNum());
            consumeRecordDTO.setConsumeType(ConsumeTypeEnum.CONSUME.getCode());
            consumeRecordDTO.setPrice(dto.getConsumePrice());
            consumeRecordDTO.setCreateDate(new Date());
            consumeRecordDTO.setGoodsType(GoodsTypeEnum.TREATMENT_CARD.getCode());
            shopUerConsumeRecordService.saveCustomerConsumeRecord(consumeRecordDTO);
            logger.info("更新用户的账户信息");
            sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().subtract(dto.getConsumePrice()));
        }
        sysUserAccountService.updateSysUserAccountDTO(sysUserAccountDTO);
        return 1;
    }

    /**
     * 用户划套卡
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int consumesDaughterCard(List<ShopUserConsumeDTO> shopUserConsumeDTOS, SysClerkDTO clerkInfo) {

        if (CommonUtils.objectIsEmpty(shopUserConsumeDTOS)) {
            logger.info("用户划疗程卡传入参数={}", "shopUserConsumeDTO = [" + shopUserConsumeDTOS + "], clerkInfo = [" + clerkInfo + "]");
            return 0;
        }
        String transactionCodeNumber = DateUtils.DateToStr(new Date(), "dateMillisecond");

        ShopUserProjectGroupRelRelationDTO relation = new ShopUserProjectGroupRelRelationDTO();

        //更新用户与套卡与项目关系的关系表
        ShopUserConsumeDTO consumeDTO = shopUserConsumeDTOS.get(0);
        relation.setId(consumeDTO.getConsumeId());
        relation = shopProjectGroupService.getShopUserProjectGroupRelRelation(relation).get(0);
        relation.setProjectSurplusAmount(relation.getProjectSurplusAmount().subtract(consumeDTO.getConsumePrice()));
        relation.setProjectSurplusTimes(relation.getProjectSurplusTimes() - consumeDTO.getConsumeNum());
        shopProjectGroupService.updateShopUserProjectGroupRelRelation(relation);

        // 更新用户的账户信息
        return updateUserAccountDTO(clerkInfo, transactionCodeNumber, consumeDTO);
    }

    /**
     * 用户领取产品
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int consumesUserProduct(List<ShopUserConsumeDTO> shopUserConsumeDTOS, SysClerkDTO clerkInfo) {

        if (CommonUtils.objectIsEmpty(shopUserConsumeDTOS)) {
            logger.info("用户领取产品传入参数={}", "shopUserConsumeDTO = [" + shopUserConsumeDTOS + "], clerkInfo = [" + clerkInfo + "]");
            return 0;
        }
        String transactionCodeNumber = DateUtils.DateToStr(new Date(), "dateMillisecond");

        ShopUserProductRelationDTO relation = new ShopUserProductRelationDTO();

        //更新用户与套卡与项目关系的关系表
        ShopUserConsumeDTO consumeDTO = shopUserConsumeDTOS.get(0);
        relation.setId(consumeDTO.getConsumeId());
        //计算单件产品的价格
        ShopUserProductRelationDTO shopUserProductRelationDTO = new ShopUserProductRelationDTO();
        shopUserProductRelationDTO.setId(consumeDTO.getConsumeId());
        List<ShopUserProductRelationDTO> productInfoList = shopProductInfoService.getUserProductInfoList(shopUserProductRelationDTO);
        ShopUserProductRelationDTO userProductRelationDTO = productInfoList.get(0);
        BigDecimal onePrice = userProductRelationDTO.getInitAmount().divide(new BigDecimal(userProductRelationDTO.getInitTimes()), 2, ROUND_HALF_DOWN);
        logger.info("单件产品的价格为={}", onePrice);
        BigDecimal consumeAmount = onePrice.multiply(new BigDecimal(consumeDTO.getConsumeNum()));
        consumeDTO.setConsumePrice(consumeAmount);
        logger.info("领取产品价值={}", consumeAmount);

        relation = shopProductInfoService.getUserProductInfoList(relation).get(0);
        relation.setSurplusAmount(relation.getSurplusAmount().subtract(consumeAmount));
        relation.setSurplusTimes(relation.getSurplusTimes() - consumeDTO.getConsumeNum());
        shopProductInfoService.updateShopUserProductRelation(relation);
        return updateUserAccountDTO(clerkInfo, transactionCodeNumber, consumeDTO);


    }

    private int updateUserAccountDTO(SysClerkDTO clerkInfo, String transactionCodeNumber, ShopUserConsumeDTO consumeDTO) {
        // 更新用户的账户信息
        SysUserAccountDTO sysUserAccountDTO = new SysUserAccountDTO();
        sysUserAccountDTO.setSysUserId(consumeDTO.getSysUserId());
        sysUserAccountDTO.setSysShopId(clerkInfo.getSysShopId());
        sysUserAccountDTO = sysUserAccountService.getSysUserAccountDTO(sysUserAccountDTO);
        sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().subtract(consumeDTO.getConsumePrice()));
        sysUserAccountService.updateSysUserAccountDTO(sysUserAccountDTO);

        //保存消费记录
        ShopUserConsumeRecordDTO consumeRecordDTO = new ShopUserConsumeRecordDTO();
        String uuid = IdGen.uuid();
        consumeRecordDTO.setFlowId(uuid);
        consumeRecordDTO.setId(uuid);
        consumeRecordDTO.setCreateBy(clerkInfo.getSysUserId());
        consumeRecordDTO.setCreateDate(new Date());
        consumeRecordDTO.setFlowNo(transactionCodeNumber);
        consumeRecordDTO.setSysUserId(consumeRecordDTO.getSysUserId());
        consumeRecordDTO.setSysShopName(clerkInfo.getSysShopName());
        consumeRecordDTO.setSysShopId(clerkInfo.getSysShopId());
        consumeRecordDTO.setSysClerkId(clerkInfo.getId());
        consumeRecordDTO.setSysBossId(clerkInfo.getSysBossId());
        consumeRecordDTO.setFlowName(consumeRecordDTO.getFlowName());
        consumeRecordDTO.setStatus(CommonCodeEnum.SUCCESS.getCode());
        consumeRecordDTO.setOperDate(new Date());
        consumeRecordDTO.setCreateBy(clerkInfo.getId());
        consumeRecordDTO.setConsumeNumber(consumeRecordDTO.getConsumeNumber());
        consumeRecordDTO.setConsumeType(ConsumeTypeEnum.CONSUME.getCode());
        consumeRecordDTO.setPrice(consumeRecordDTO.getPrice());
        consumeRecordDTO.setCreateDate(new Date());
        consumeRecordDTO.setGoodsType(GoodsTypeEnum.TREATMENT_CARD.getCode());
        return shopUerConsumeRecordService.saveCustomerConsumeRecord(consumeRecordDTO);
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

    /**
     * 充值卡充值操作
     *
     * @param transactionId  @Transactional(rollbackFor = Throwable.class)
     * @param imageUrl
     * @return
     */
    @Override
    public ResponseDTO rechargeRechargeCrad(String transactionId, String imageUrl) {
        logger.info("充值卡充值操作传入参数={}", "transactionId = [" + transactionId + "], imageUrl = [" + imageUrl + "]");

        ResponseDTO responseDTO = new ResponseDTO();
        Query query = new Query().addCriteria(Criteria.where("transactionId").is(transactionId));
        Update update = new Update();
        update.set("imageUrl", imageUrl);
        update.set("status", OrderStatusEnum.ALREADY_PAY.getCode());
        mongoTemplate.upsert(query, update, "extShopUserRechargeCardDTO");

        //查询用户的充值卡信息
        ShopRechargeCardOrderDTO orderDTO = mongoTemplate.findOne(query, ShopRechargeCardOrderDTO.class, "extShopUserRechargeCardDTO");
        if (null == orderDTO || StringUtils.isBlank(orderDTO.getId())) {
            logger.error("mongo查询充值卡信息为空,{}", "transactionId = [" + transactionId + "], imageUrl = [" + imageUrl + "]");
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setResponseData("mongo查询充值卡信息为空");
            return responseDTO;
        }

        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();

        //针对特殊卡进行更新操作
        if (orderDTO.getPayType().equals(RechargeCardTypeEnum.SPECIAL.getCode())) {
            logger.info("针对特殊卡进行更新操作交易号，{},订单对象为", transactionId, orderDTO);
            //查询某个用户的特殊充值卡
            ShopUserRechargeCardDTO userRechargeCardDTO = new ShopUserRechargeCardDTO();
            userRechargeCardDTO.setSysUserId(orderDTO.getSysUserId());
            userRechargeCardDTO.setShopRechargeCardId(orderDTO.getId());
            ShopUserRechargeCardDTO shopUserRechargeInfo = shopRechargeCardService.getShopUserRechargeInfo(userRechargeCardDTO);
            if (null == shopUserRechargeInfo) {
                logger.error("针对特殊卡进行更新操作交易号，{}，查询用户的充值卡={}", orderDTO, shopUserRechargeInfo);
                throw new ServiceException("查询用户的充值卡为空");
            }
            //剩余金额 = 已存剩余金额 + 充值金额
            shopUserRechargeInfo.setSurplusAmount(shopUserRechargeInfo.getSurplusAmount().add(new BigDecimal(orderDTO.getRechargeAmount())));
            shopUserRechargeInfo.setTimeDiscount(orderDTO.getTimeDiscount());
            shopUserRechargeInfo.setProductDiscount(orderDTO.getPeriodDiscount());
            shopUserRechargeInfo.setProductDiscount(orderDTO.getProductDiscount());
            int i = shopRechargeCardService.updateRechargeCard(shopUserRechargeInfo);
            logger.info("更新用户的充值卡记录操作{}", i > 0 ? "成功" : "失败");
        }
        //针对普通卡进行插入操作
        else {
            logger.info("针对普通卡进行插入操作交易号，{},订单对象为", transactionId, orderDTO);
            ShopUserRechargeCardDTO rechargeCardDTO = new ShopUserRechargeCardDTO();
            rechargeCardDTO.setId(IdGen.uuid());
            rechargeCardDTO.setShopRechargeCardId(orderDTO.getId());
            rechargeCardDTO.setShopRechargeCardName("余额充值");
            rechargeCardDTO.setProductDiscount(orderDTO.getProductDiscount());
            rechargeCardDTO.setPeriodDiscount(orderDTO.getPeriodDiscount());
            rechargeCardDTO.setTimeDiscount(orderDTO.getTimeDiscount());
            rechargeCardDTO.setSysShopId(clerkInfo.getSysShopId());
            rechargeCardDTO.setSysUserId(orderDTO.getSysUserId());
            //总金额 = 充值金额
            String rechargeAmount = orderDTO.getRechargeAmount();
            rechargeCardDTO.setSurplusAmount(new BigDecimal(rechargeAmount));
            rechargeCardDTO.setCreateBy(clerkInfo.getId());
            rechargeCardDTO.setSysClerkName(clerkInfo.getName());
            rechargeCardDTO.setSysBossId(clerkInfo.getSysBossId());
            rechargeCardDTO.setRechargeCardType(RechargeCardTypeEnum.COMMON.getCode());
            int updateRechargeCard = shopRechargeCardService.saveShopUserRechargeCardInfo(rechargeCardDTO);
            logger.info("充值卡充值操作传入参数执行结果={}", updateRechargeCard > 0 ? "成功" : "失败");
        }

        //插入用户的消费记录
        ShopUserConsumeRecordDTO shopUserConsumeRecordDTO = new ShopUserConsumeRecordDTO();
        shopUserConsumeRecordDTO.setId(IdGen.uuid());
        shopUserConsumeRecordDTO.setFlowId(orderDTO.getId());
        shopUserConsumeRecordDTO.setFlowNo(DateUtils.DateToStr(new Date(), "dateMillisecond"));
        shopUserConsumeRecordDTO.setFlowName(orderDTO.getName());
        shopUserConsumeRecordDTO.setGoodsType(GoodsTypeEnum.RECHARGE_CARD.getCode());
        if (null != orderDTO.getRechargeAmount()) {
            shopUserConsumeRecordDTO.setPrice(new BigDecimal(orderDTO.getRechargeAmount()));
        }
        shopUserConsumeRecordDTO.setPayType(orderDTO.getPayType());
        shopUserConsumeRecordDTO.setCreateDate(new Date());
        shopUserConsumeRecordDTO.setSysClerkId(clerkInfo.getId());
        shopUserConsumeRecordDTO.setTimeDiscount(orderDTO.getTimeDiscount());
        shopUserConsumeRecordDTO.setProductDiscount(orderDTO.getProductDiscount());
        shopUserConsumeRecordDTO.setPeriodDiscount(shopUserConsumeRecordDTO.getPeriodDiscount());
        shopUserConsumeRecordDTO.setSysUserName(orderDTO.getUserName());
        shopUserConsumeRecordDTO.setSysUserId(orderDTO.getSysUserId());
        shopUserConsumeRecordDTO.setSysShopId(orderDTO.getSysShopId());
        shopUserConsumeRecordDTO.setDetail(shopUserConsumeRecordDTO.getDetail());
        shopUserConsumeRecordDTO.setSignUrl(orderDTO.getSignUrl());
        shopUserConsumeRecordDTO.setSysBossId(clerkInfo.getSysBossId());
        shopUserConsumeRecordDTO.setStatus(shopUserConsumeRecordDTO.getStatus());
        shopUserConsumeRecordDTO.setOperDate(new Date());
        shopUserConsumeRecordDTO.setConsumeNumber(1);
        shopUserConsumeRecordDTO.setConsumeType(ConsumeTypeEnum.RECHARGE.getCode());
        int record = shopUerConsumeRecordService.saveCustomerConsumeRecord(shopUserConsumeRecordDTO);
        logger.info("充值卡充值操作流水号={}，生成充值记录,{}", transactionId, record > 0 ? "成功" : "失败");
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

}

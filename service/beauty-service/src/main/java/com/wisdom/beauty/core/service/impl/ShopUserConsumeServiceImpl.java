package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.*;
import com.wisdom.beauty.api.extDto.*;
import com.wisdom.beauty.api.requestDto.ShopStockRequestDTO;
import com.wisdom.beauty.api.responseDto.ShopProductInfoResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopProjectInfoResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopRechargeCardResponseDTO;
import com.wisdom.beauty.client.UserServiceClient;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.*;
import com.wisdom.beauty.core.service.stock.ShopStockService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.CommonCodeEnum;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.IdGen;
import com.wisdom.common.util.StringUtils;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.ArrayList;
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
    private UserServiceClient userServiceClient;

    @Resource
    private ShopUerConsumeRecordService shopUerConsumeRecordService;

    @Resource
    private SysUserAccountService sysUserAccountService;

    @Resource
    private ShopCardService cardService;

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
    @Resource
    private ShopService shopService;

    @Autowired
    private ShopRechargeCardService shopRechargeCardService;

    @Autowired
    private CashService cashService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ShopStockService shopStockService;

    /**
     * 用户充值操作
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO userRechargeOperation(ShopUserOrderDTO shopUserOrderDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResult(StatusConstant.FAILURE);
        //支付对象
        ShopUserPayDTO shopUserPayDTO = shopUserOrderDTO.getShopUserPayDTO();
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        if (CommonUtils.objectIsEmpty(shopUserOrderDTO) || CommonUtils.objectIsEmpty(shopUserPayDTO)) {
            logger.info("传入参数为空，{}", "shopUserOrderDTO = [" + shopUserOrderDTO + "], shopUserPayDTO = [" + shopUserPayDTO + "]");
            responseDTO.setErrorInfo("传入参数异常！");
            return responseDTO;
        }
        shopUserOrderDTO.setSignUrl(shopUserPayDTO.getSignUrl());

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
            archivesDTO.setSysUserId(shopUserOrderDTO.getUserId());
            archivesDTO.setSysShopId(shopUserOrderDTO.getShopId());
            ShopUserArchivesDTO archivesInfo = shopCustomerArchivesService.getShopUserArchivesInfo(archivesDTO).get(0);
            if (CommonUtils.objectIsEmpty(archivesInfo)) {
                logger.info("订单号={}，查询用户的档案信息为空", orderId);
                throw new ServiceException("查询用户的档案信息为空");
            }
            String balancePay = shopUserPayDTO.getBalancePay();
            //扣减特殊账户
            shopUserOrderDTO.setUserName(archivesDTO.getSysUserName());
            if (subSpecialRechargeCard(shopUserOrderDTO, responseDTO, balancePay)){
                return responseDTO;
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
            List<ExtShopUserRechargeCardDTO> rechargeCardDTOS = shopUserOrderDTO.getUserPayRechargeCardList();
            BigDecimal totalAmount = useRechargeCard(shopUserOrderDTO, shopUserPayDTO, clerkInfo, transactionCodeNumber, orderId, sysUserAccountDTO, archivesInfo, rechargeCardDTOS);

            //更改账户金额
            int flag = sysUserAccountService.updateSysUserAccountDTO(sysUserAccountDTO);
            logger.info("更新账户信息传入参数={},执行结果={}", sysUserAccountDTO, flag > 0 ? "成功" : "失败");


            ShopUserConsumeRecordDTO shopUserConsumeRecordDTO = new ShopUserConsumeRecordDTO();
            //消费记录表中添加签字图片
            shopUserConsumeRecordDTO.setFlowNo(shopUserOrderDTO.getOrderId());
            shopUserConsumeRecordDTO.setSignUrl(shopUserPayDTO.getSignUrl());
            shopUserConsumeRecordDTO.setStatus(OrderStatusEnum.CONFIRM_PAY.getCode());
            shopUerConsumeRecordService.updateConsumeRecord(shopUserConsumeRecordDTO);

            //保存资金流水记录
            saveCashFlowInfo(shopUserOrderDTO, shopUserPayDTO, transactionCodeNumber, sysUserAccountDTO, totalAmount);
            //订单已支付，更新用户订单信息
            Query query = new Query().addCriteria(Criteria.where("orderId").is(shopUserOrderDTO.getOrderId()));
            Update update = new Update();
            update.set("status", OrderStatusEnum.CONFIRM_PAY.getCode());
            update.set("statusDesc", OrderStatusEnum.CONFIRM_PAY.getDesc());
            update.set("cashPayPrice", shopUserPayDTO.getCashPayPrice());
            update.set("surplusPayPrice", shopUserPayDTO.getSurplusPayPrice());
            update.set("payType", shopUserPayDTO.getPayType());
            update.set("balancePay", balancePay);
            update.set("updateDate",new Date());
            update.set("shopUserPayDTO", shopUserPayDTO);
            update.set("archivesInfo", archivesInfo);
            mongoTemplate.upsert(query, update, "shopUserOrderDTO");
        } catch (RuntimeException e) {
            logger.error("用户充值操作异常，异常原因为" + e.getMessage(), e);
            throw new RuntimeException();
        }
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

    /**
     * 扣减特殊账户
     * @param shopUserOrderDTO
     * @param responseDTO
     * @param balancePay
     * @return
     */
    private boolean subSpecialRechargeCard(ShopUserOrderDTO shopUserOrderDTO, ResponseDTO responseDTO, String balancePay) {
        if(StringUtils.isNotBlank(balancePay) && !"0".equals(balancePay)){
            //查询用户的特殊账户
            ShopUserRechargeCardDTO specialCard = new ShopUserRechargeCardDTO();
            specialCard.setSysShopId(shopUserOrderDTO.getShopId());
            specialCard.setSysUserId(shopUserOrderDTO.getUserId());
            specialCard.setRechargeCardType(RechargeCardTypeEnum.SPECIAL.getCode());
            List<ShopUserRechargeCardDTO> cardList = cardService.getUserRechargeCardList(specialCard);
            if(CommonUtils.objectIsEmpty(cardList) || cardList.size()>1){
                logger.error("余额充值账户为空，userId={}",shopUserOrderDTO.getUserId());
                responseDTO.setErrorInfo("数据异常，余额充值账户为空！");
                return true;
            }
            specialCard = cardList.get(0);
            if(specialCard.getSurplusAmount().doubleValue()<new BigDecimal(balancePay).doubleValue()){
                logger.error("账户余额不足，userId={}",shopUserOrderDTO.getUserId());
                responseDTO.setErrorInfo("数据异常，账户余额不足！");
                return true;
            }
            specialCard.setSurplusAmount(specialCard.getSurplusAmount().subtract(new BigDecimal(balancePay)));
            int info = cardService.updateUserRechargeCard(specialCard);

            ShopUserConsumeRecordDTO recordDTO = new ShopUserConsumeRecordDTO();
            recordDTO.setId(IdGen.uuid());
            recordDTO.setSignUrl(shopUserOrderDTO.getSignUrl());
            recordDTO.setFlowName(specialCard.getShopRechargeCardName());
            recordDTO.setFlowId(specialCard.getId());
            recordDTO.setGoodsType(GoodsTypeEnum.RECHARGE_CARD.getCode());
            recordDTO.setSysUserId(shopUserOrderDTO.getUserId());
            recordDTO.setSysClerkName(shopUserOrderDTO.getSysClerkName());
            recordDTO.setSysClerkId(shopUserOrderDTO.getSysClerkId());
            recordDTO.setFlowNo(shopUserOrderDTO.getOrderId());
            recordDTO.setSysBossCode(UserUtils.getClerkInfo().getSysBossCode());
            recordDTO.setSysShopId(shopUserOrderDTO.getShopId());
            recordDTO.setPrice(new BigDecimal(balancePay));
            recordDTO.setConsumeNumber(1);
            recordDTO.setDetail(shopUserOrderDTO.getDetail());
            recordDTO.setSysUserName(shopUserOrderDTO.getUserName());
            recordDTO.setConsumeType(ConsumeTypeEnum.CONSUME.getCode());
            shopUerConsumeRecordService.saveCustomerConsumeRecord(recordDTO);
            logger.info("扣减余额账户={}",info);
        }
        return false;
    }

    /**
     * 保存资金流水记录
     *
     * @param shopUserOrderDTO
     * @param shopUserPayDTO
     * @param transactionCodeNumber
     * @param sysUserAccountDTO
     * @param totalAmount
     */
    private void saveCashFlowInfo(ShopUserOrderDTO shopUserOrderDTO, ShopUserPayDTO shopUserPayDTO, String transactionCodeNumber, SysUserAccountDTO sysUserAccountDTO, BigDecimal totalAmount) {
        ShopCashFlowDTO shopCashFlowDTO = new ShopCashFlowDTO();
        shopCashFlowDTO.setId(IdGen.uuid());
        shopCashFlowDTO.setCreateDate(new Date());
        if (StringUtils.isNotBlank(shopUserPayDTO.getSurplusPayPrice())) {
            shopCashFlowDTO.setPayTypeAmount(new BigDecimal(shopUserPayDTO.getSurplusPayPrice()));
        }
        shopCashFlowDTO.setPayType(shopUserPayDTO.getPayType());
        if (StringUtils.isNotBlank(shopUserPayDTO.getBalancePay())) {
            shopCashFlowDTO.setBalanceAmount(new BigDecimal(shopUserPayDTO.getBalancePay()));
        }
        shopCashFlowDTO.setSysShopId(shopUserOrderDTO.getShopId());
        shopCashFlowDTO.setSysBossCode(sysUserAccountDTO.getSysBossCode());
        shopCashFlowDTO.setRechargeCardAmount(totalAmount);
        if (StringUtils.isNotBlank(shopUserPayDTO.getOweAmount())) {
            shopCashFlowDTO.setOweAmount(new BigDecimal(shopUserPayDTO.getOweAmount()));
        }
        shopCashFlowDTO.setFlowNo(transactionCodeNumber);
        if (StringUtils.isNotBlank(shopUserPayDTO.getCashPayPrice())) {
            shopCashFlowDTO.setCashAmount(new BigDecimal(shopUserPayDTO.getCashPayPrice()));
        }
        int shopCashFlow = cashService.saveShopCashFlow(shopCashFlowDTO);
        logger.info("保存资金流水={}，执行结果={}", shopCashFlow, shopCashFlow > 0 ? "成功" : "失败");
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
    private BigDecimal useRechargeCard(ShopUserOrderDTO shopUserOrderDTO, ShopUserPayDTO shopUserPayDTO, SysClerkDTO clerkInfo, String transactionCodeNumber, String orderId, SysUserAccountDTO sysUserAccountDTO, ShopUserArchivesDTO archivesInfo, List<ExtShopUserRechargeCardDTO> rechargeCardDTOS) {
        BigDecimal totalAmount = new BigDecimal(0);
        if (CommonUtils.objectIsNotEmpty(rechargeCardDTOS)) {
            for (ExtShopUserRechargeCardDTO dto : rechargeCardDTOS) {
                BigDecimal consumeAmount = new BigDecimal(dto.getConsumePrice());
                if(consumeAmount.doubleValue()<=0){
                    continue;
                }
                //更新用户的充值卡记录,先查询再更新
                ShopUserRechargeCardDTO shopUserRechargeCardDTOById = shopRechargeCardService.getShopUserRechargeCardDTOById(dto.getId());
                shopUserRechargeCardDTOById.setSurplusAmount(shopUserRechargeCardDTOById.getSurplusAmount().subtract(consumeAmount));
                shopRechargeCardService.updateRechargeCard(shopUserRechargeCardDTOById);

                ShopUserConsumeRecordDTO userConsumeRecordDTO = new ShopUserConsumeRecordDTO();
                userConsumeRecordDTO.setConsumeType(ConsumeTypeEnum.CONSUME.getCode());
                userConsumeRecordDTO.setCreateBy(clerkInfo.getName());
                userConsumeRecordDTO.setCreateDate(new Date());
                userConsumeRecordDTO.setFlowId(dto.getId());
                userConsumeRecordDTO.setFlowName(dto.getShopRechargeCardName());
                userConsumeRecordDTO.setFlowNo(transactionCodeNumber);
                userConsumeRecordDTO.setGoodsType(GoodsTypeEnum.RECHARGE_CARD.getCode());
                userConsumeRecordDTO.setSysUserName(archivesInfo.getSysUserName());
                userConsumeRecordDTO.setSysUserId(archivesInfo.getSysUserId());
                userConsumeRecordDTO.setSysShopName(archivesInfo.getSysShopName());
                userConsumeRecordDTO.setSysShopId(clerkInfo.getSysShopId());
                userConsumeRecordDTO.setSysClerkId(clerkInfo.getId());
                userConsumeRecordDTO.setSignUrl(shopUserPayDTO.getSignUrl());
                userConsumeRecordDTO.setSysBossCode(clerkInfo.getSysBossCode());
                userConsumeRecordDTO.setPrice(consumeAmount);
                userConsumeRecordDTO.setDetail(shopUserOrderDTO.getDetail());
                userConsumeRecordDTO.setPayType(PayTypeEnum.judgeValue(shopUserPayDTO.getPayType()).getCode());
                logger.info("订单号={},更新用户的充值卡信息={}", orderId, userConsumeRecordDTO);

                userConsumeRecordDTO.setGoodsType(GoodsTypeEnum.COLLECTION_CARD.getCode());
                saveCustomerConsumeRecord(userConsumeRecordDTO, shopUserOrderDTO, shopUserPayDTO, clerkInfo, transactionCodeNumber, archivesInfo);

                //更新用户的账户信息
                sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().subtract(userConsumeRecordDTO.getPrice()));

                logger.info("订单号={}，用户的账户金额={}", orderId, sysUserAccountDTO.getSumAmount());
                totalAmount = totalAmount.add(userConsumeRecordDTO.getPrice());
            }
        }
        return totalAmount;
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
            //遍历每种套卡信息
            for (ShopUserProjectGroupRelRelationDTO groupDto : groupRelRelationDTOS) {
                //默认一种套卡只购买一个
                if (null == groupDto.getProjectInitTimes()) {
                    groupDto.setProjectInitTimes(1);
                }
                //查询套卡信息
                ShopProjectGroupDTO shopProjectGroupDTO = shopProjectGroupService.getShopProjectGroupDTO(groupDto.getShopProjectGroupId());
                String consumeId = IdGen.uuid();
                //购买一个套卡的金额
                BigDecimal discountPrice = groupDto.getDiscountPrice();
                //同一种套卡购买多个，groupDto.getProjectInitTimes()存储的是购买了几个同一种套卡
                for (int i = 0; i < groupDto.getProjectInitTimes(); i++) {
                    //根据套卡id查询项目列表
                    ShopProjectInfoGroupRelationDTO shopProjectInfoGroupRelationDTO = new ShopProjectInfoGroupRelationDTO();
                    shopProjectInfoGroupRelationDTO.setShopProjectGroupId(groupDto.getShopProjectGroupId());
                    shopProjectInfoGroupRelationDTO.setSysShopId(clerkInfo.getSysShopId());
                    List<ShopProjectInfoGroupRelationDTO> groupRelations = shopProjectService.getShopProjectInfoGroupRelations(shopProjectInfoGroupRelationDTO);

                    if (null == groupRelations) {
                        logger.error("订单号={}，根据项目套卡主键查询出来的项目列表传入参数为空");
                        throw new RuntimeException();
                    }

                    //生成用户跟套卡与项目的关系的关系
                    for (ShopProjectInfoGroupRelationDTO dt : groupRelations) {
                        //每个项目的购买金额 = 这种套卡的折扣金额/套卡的购买数量/项目的个数
                        BigDecimal oneProjectPrice = discountPrice.divide(new BigDecimal(groupRelations.size()),2, ROUND_HALF_DOWN);
                        ShopUserProjectGroupRelRelationDTO groupRelRelationDTO = new ShopUserProjectGroupRelRelationDTO();
                        groupRelRelationDTO.setSysShopId(clerkInfo.getSysShopId());
                        groupRelRelationDTO.setSysUserId(archivesInfo.getSysUserId());
                        groupRelRelationDTO.setId(IdGen.uuid());
                        groupRelRelationDTO.setShopProjectInfoId(dt.getShopProjectInfoId());
                        groupRelRelationDTO.setProjectInitAmount(oneProjectPrice);
                        groupRelRelationDTO.setShopProjectGroupId(shopProjectGroupDTO.getId());
                        groupRelRelationDTO.setShopProjectInfoGroupRelationId(dt.getId());
                        groupRelRelationDTO.setProjectSurplusTimes(dt.getShopProjectServiceTimes());
                        groupRelRelationDTO.setProjectInitTimes(dt.getShopProjectServiceTimes());
                        groupRelRelationDTO.setProjectSurplusAmount(oneProjectPrice);
                        groupRelRelationDTO.setShopProjectInfoName(dt.getShopProjectInfoName());
                        groupRelRelationDTO.setSysClerkId(groupDto.getSysClerkId());
                        groupRelRelationDTO.setConsumeRecordId(consumeId);
                        groupRelRelationDTO.setSysClerkName(groupDto.getSysClerkName());
                        groupRelRelationDTO.setSysBossCode(clerkInfo.getSysBossCode());
                        groupRelRelationDTO.setCreateBy(clerkInfo.getName());
                        groupRelRelationDTO.setCreateDate(new Date());
                        groupRelRelationDTO.setShopGroupPuchasePrice(shopProjectGroupDTO.getMarketPrice());
                        groupRelRelationDTO.setShopProjectGroupName(shopProjectGroupDTO.getProjectGroupName());
                        logger.info("订单号={}，生成用户跟套卡的关系的关系记录={}", orderId, groupRelRelationDTO);
                        shopProjectGroupService.saveShopUserProjectGroupRelRelation(groupRelRelationDTO);
                    }
                }
                //每一种套卡生成一条充值记录
                ShopUserConsumeRecordDTO userConsumeRecordDTO = new ShopUserConsumeRecordDTO();
                userConsumeRecordDTO.setFlowName(groupDto.getShopProjectGroupName());
                if (null != groupDto.getDiscount()) {
                    userConsumeRecordDTO.setDiscount(groupDto.getDiscount());
                }
                //购买每种套卡的总金额
                userConsumeRecordDTO.setPrice(discountPrice.multiply(new BigDecimal(groupDto.getProjectInitTimes())));
                userConsumeRecordDTO.setDiscount(groupDto.getDiscount());
                userConsumeRecordDTO.setConsumeNumber(groupDto.getProjectInitTimes());
                userConsumeRecordDTO.setId(consumeId);
                userConsumeRecordDTO.setGoodsType(GoodsTypeEnum.COLLECTION_CARD.getCode());
                userConsumeRecordDTO.setSysClerkId(groupDto.getSysClerkId());
                userConsumeRecordDTO.setTimeDiscount(groupDto.getDiscount());
                userConsumeRecordDTO.setProductDiscount(groupDto.getDiscount());
                userConsumeRecordDTO.setPeriodDiscount(groupDto.getDiscount());
                userConsumeRecordDTO.setSysClerkName(groupDto.getSysClerkName());
                userConsumeRecordDTO.setConsumeType(ConsumeTypeEnum.RECHARGE.getCode());
                userConsumeRecordDTO.setFlowId(shopProjectGroupDTO.getId());
                userConsumeRecordDTO.setFlowName(groupDto.getShopProjectGroupName());
                //生成充值记录
                saveCustomerConsumeRecord(userConsumeRecordDTO, shopUserOrderDTO, shopUserPayDTO, clerkInfo, transactionCodeNumber, archivesInfo);
                //更新用户的账户信息
                sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().add(userConsumeRecordDTO.getPrice()));
                logger.info("订单号={}，用户的账户金额={}", orderId, sysUserAccountDTO.getSumAmount());
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
                //默认一种类型的项目购买一个
                Integer sysShopProjectInitTimes = dto.getSysShopProjectInitTimes();
                if (null == sysShopProjectInitTimes) {
                    dto.setSysShopProjectInitTimes(1);
                    return;
                }
                //项目有可能购买多个
                for (int i = 0; i < sysShopProjectInitTimes; i++) {
                    dto.setCreateDate(new Date());
                    String uuid = IdGen.uuid();
                    dto.setId(uuid);
                    SysShopDTO shop = shopService.getShopInfoByPrimaryKey(clerkInfo.getSysShopId());
                    dto.setSysShopName(shop.getName());
                    dto.setSysShopId(clerkInfo.getSysShopId());
                    dto.setSysBossCode(clerkInfo.getSysBossCode());
                    //根据项目id查询项目信息
                    if(StringUtils.isBlank(dto.getSysShopProjectId())){
                        logger.error("项目主键为空");
                        throw new ServiceException("项目主键为空");
                    }
                    ShopProjectInfoResponseDTO projectDetail = shopProjectService.getProjectDetail(dto.getSysShopProjectId());

                    //购买价格
                    BigDecimal discountPrice = dto.getDiscountPrice();
                    //此次购买初始价格
                    dto.setSysShopProjectInitAmount(discountPrice);
                    //如果是次卡的话
                    if (GoodsTypeEnum.TIME_CARD.getCode().equals(dto.getUseStyle())) {
                        dto.setSysShopProjectSurplusAmount(new BigDecimal(0));
                        dto.setSysShopProjectSurplusTimes(0);
                    } else {
                        dto.setSysShopProjectSurplusAmount(discountPrice);
                        dto.setSysShopProjectSurplusTimes(dto.getServiceTime());
                    }

                    dto.setCreateDate(new Date());
                    //划卡次数即为服务次数
                    dto.setSysShopProjectInitTimes(dto.getServiceTime());
                    String cardType = projectDetail.getCardType();
                    //次卡，当天失效
                    int effectiveDate = CardTypeEnum.judgeValue(cardType).getDate();
                    dto.setEffectiveDate(new Date());
                    dto.setEffectiveDays(effectiveDate);
                    dto.setInvalidDays(DateUtils.dateIncDays(new Date(), effectiveDate));
                    logger.info("订单号={}，生成用户跟项目的关系={}", orderId, dto);
                    shopProjectService.saveUserProjectRelation(dto);
                    //构造消费记录
                }
                //重新构造消费记录
                ShopUserConsumeRecordDTO userConsumeRecordDTO = new ShopUserConsumeRecordDTO();
                userConsumeRecordDTO.setConsumeType(ConsumeTypeEnum.RECHARGE.getCode());
                userConsumeRecordDTO.setDiscount(dto.getDiscount());
                userConsumeRecordDTO.setTimeDiscount(dto.getDiscount());
                userConsumeRecordDTO.setPeriodDiscount(dto.getDiscount());
                userConsumeRecordDTO.setProductDiscount(dto.getDiscount());
                userConsumeRecordDTO.setPrice(dto.getDiscountPrice().multiply(new BigDecimal(sysShopProjectInitTimes)));
                userConsumeRecordDTO.setConsumeNumber(sysShopProjectInitTimes);
                userConsumeRecordDTO.setGoodsType(dto.getUseStyle());
                userConsumeRecordDTO.setFlowId(dto.getId());
                userConsumeRecordDTO.setFlowName(dto.getSysShopProjectName());
                userConsumeRecordDTO.setSysClerkId(dto.getSysClerkId());
                //更新用户的账户信息
                sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().add(userConsumeRecordDTO.getPrice()));
                //生成充值记录
                saveCustomerConsumeRecord(userConsumeRecordDTO, shopUserOrderDTO, shopUserPayDTO, clerkInfo, transactionCodeNumber, archivesInfo);
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
                //根据产品id查询产品信息
                ShopProductInfoResponseDTO productDetail = shopProductInfoService.getProductDetail(dto.getShopProductId());
                if(null == productDetail){
                    logger.error("根据产品id查询产品信息为空");
                    return;
                }

                String uuid = IdGen.uuid();
                String sysClerkId = dto.getSysClerkId();
                dto.setId(uuid);
                dto.setSysShopId(clerkInfo.getSysShopId());
                dto.setSysShopName(clerkInfo.getSysShopName());
                dto.setSysBossCode(clerkInfo.getSysBossCode());
                dto.setInitAmount(dto.getDiscountPrice().multiply(new BigDecimal(dto.getInitTimes())));
                dto.setSurplusAmount(dto.getInitAmount());
                dto.setSurplusTimes(dto.getInitTimes());
                dto.setCreateDate(new Date());
                logger.info("订单号={}，生成用户跟产品的关系={}", orderId, dto);
                shopProductInfoService.saveShopUserProductRelation(dto);

                ShopUserConsumeRecordDTO userConsumeRecordDTO = new ShopUserConsumeRecordDTO();
                userConsumeRecordDTO.setFlowName(productDetail.getProductName());
                Float discount = dto.getDiscount();
                if (null != discount) {
                    userConsumeRecordDTO.setDiscount(discount);
                    userConsumeRecordDTO.setTimeDiscount(discount);
                    userConsumeRecordDTO.setPeriodDiscount(discount);
                    userConsumeRecordDTO.setProductDiscount(discount);
                }
                userConsumeRecordDTO.setPrice(dto.getInitAmount());
                userConsumeRecordDTO.setConsumeType(ConsumeTypeEnum.RECHARGE.getCode());
                userConsumeRecordDTO.setConsumeNumber(dto.getInitTimes());
                userConsumeRecordDTO.setGoodsType(GoodsTypeEnum.PRODUCT.getCode());
                userConsumeRecordDTO.setSysClerkId(sysClerkId);
                userConsumeRecordDTO.setSysClerkId(dto.getSysClerkId());
                userConsumeRecordDTO.setSysClerkName(dto.getSysClerkName());
                userConsumeRecordDTO.setFlowId(dto.getId());
                saveCustomerConsumeRecord(userConsumeRecordDTO, shopUserOrderDTO, shopUserPayDTO, clerkInfo, transactionCodeNumber, archivesInfo);

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

        for (ShopUserConsumeDTO dto : shopUserConsumeDTO) {
            logger.info("开始更新用户的疗程卡={}", dto.toString());
            ShopUserProjectRelationDTO shopUserProjectRelationDTO = new ShopUserProjectRelationDTO();
            shopUserProjectRelationDTO.setId(dto.getConsumeId());
            List<ShopUserProjectRelationDTO> userProjectList = shopProjectService.getUserProjectList(shopUserProjectRelationDTO);
            if (CommonUtils.objectIsEmpty(userProjectList)) {
                throw new ServiceException("根据主键查询项目列表为空");
            }
            shopUserProjectRelationDTO = userProjectList.get(0);
            //划卡的数量大于剩余了卡数，不做操作
            if(shopUserProjectRelationDTO.getSysShopProjectSurplusTimes()<dto.getConsumeNum()){
                logger.error("划卡的数量大于剩余了卡数,用户卡主键={},消费数量={}",shopUserProjectRelationDTO.getId(),dto.getConsumeNum());
                return 2;
            }

            //更新用户与卡表的数量
            shopUserProjectRelationDTO.setSysShopProjectSurplusTimes(shopUserProjectRelationDTO.getSysShopProjectSurplusTimes() - dto.getConsumeNum());
            //更新用户与卡表的关系的金额
            shopUserProjectRelationDTO.setSysShopProjectSurplusAmount(shopUserProjectRelationDTO.getSysShopProjectSurplusAmount().subtract(dto.getConsumePrice()));

            shopUserProjectRelationDTO.setUpdateDate(new Date());
            shopUserProjectRelationDTO.setUpdateUser(clerkInfo.getId());
            shopProjectService.updateUserAndProjectRelation(shopUserProjectRelationDTO);
            sysUserAccountDTO.setSysUserId(shopUserProjectRelationDTO.getSysUserId());
            sysUserAccountDTO.setSysShopId(clerkInfo.getSysShopId());
            sysUserAccountDTO = sysUserAccountService.getSysUserAccountDTO(sysUserAccountDTO);

            ShopUserConsumeRecordDTO consumeRecordDTO = new ShopUserConsumeRecordDTO();
            String uuid = IdGen.uuid();
            consumeRecordDTO.setFlowId(shopUserProjectRelationDTO.getId());
            consumeRecordDTO.setId(uuid);
            consumeRecordDTO.setCreateBy(clerkInfo.getName());
            consumeRecordDTO.setFlowNo(transactionCodeNumber);
            consumeRecordDTO.setSysUserId(dto.getSysUserId());
            consumeRecordDTO.setSysShopName(clerkInfo.getSysShopName());
            consumeRecordDTO.setSysShopId(clerkInfo.getSysShopId());
            consumeRecordDTO.setSysClerkId(dto.getSysClerkId());
            consumeRecordDTO.setSysClerkName(dto.getSysClerkName());
            consumeRecordDTO.setSysBossCode(clerkInfo.getSysBossCode());
            consumeRecordDTO.setFlowName(shopUserProjectRelationDTO.getSysShopProjectName());
            consumeRecordDTO.setStatus(CommonCodeEnum.SUCCESS.getCode());
            consumeRecordDTO.setCreateBy(clerkInfo.getName());
            consumeRecordDTO.setSignUrl(dto.getImageUrl());
            consumeRecordDTO.setConsumeNumber(dto.getConsumeNum());
            consumeRecordDTO.setDetail(dto.getDetail());
            consumeRecordDTO.setSysUserId(shopUserProjectRelationDTO.getSysUserId());
            consumeRecordDTO.setConsumeType(ConsumeTypeEnum.CONSUME.getCode());
            consumeRecordDTO.setPrice(dto.getConsumePrice());
            consumeRecordDTO.setGoodsType(GoodsTypeEnum.TREATMENT_CARD.getCode());
            shopUerConsumeRecordService.saveCustomerConsumeRecord(consumeRecordDTO);
            logger.info("更新用户的账户信息");
            sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().subtract(dto.getConsumePrice()));
            sysUserAccountService.updateSysUserAccountDTO(sysUserAccountDTO);
        }
        return 1;
    }

    /**
     * 用户划套卡
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String consumesDaughterCard(List<ShopUserConsumeDTO> shopUserConsumeDTOS, SysClerkDTO clerkInfo) {

        if (CommonUtils.objectIsEmpty(shopUserConsumeDTOS)) {
            logger.info("用户划疗程卡传入参数={}", "shopUserConsumeDTO = [" + shopUserConsumeDTOS + "], clerkInfo = [" + clerkInfo + "]");
            return null;
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

        consumeDTO.setSysUserId(relation.getSysUserId());
        // 更新用户的账户信息
        consumeDTO.setGoodsType(GoodsTypeEnum.TREATMENT_CARD.getCode());
        consumeDTO.setFlowId(relation.getId());
        consumeDTO.setConsumeName(relation.getShopProjectInfoName());
        return updateUserAccountDTO(clerkInfo, transactionCodeNumber, consumeDTO);
    }

    /**
     * 用户领取产品
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO consumesUserProduct(ShopUserConsumeDTO consumeDTO, SysClerkDTO clerkInfo) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResult(StatusConstant.FAILURE);
        if (CommonUtils.objectIsEmpty(consumeDTO)) {
            logger.info("用户领取产品传入参数={}", "consumeDTO = [" + consumeDTO + "], clerkInfo = [" + clerkInfo + "]");
            responseDTO.setResult(StatusConstant.FAILURE);
            return responseDTO;
        }
        String transactionCodeNumber = DateUtils.DateToStr(new Date(), "dateMillisecond");

        //计算单件产品的价格
        ShopUserProductRelationDTO shopUserProductRelationDTO = new ShopUserProductRelationDTO();
        shopUserProductRelationDTO.setId(consumeDTO.getConsumeId());
        List<ShopUserProductRelationDTO> productInfoList = shopProductInfoService.getUserProductInfoList(shopUserProductRelationDTO);
        if(CommonUtils.objectIsEmpty(productInfoList)){
            logger.error("用户与产品关系主键={},consumeDTO={}",consumeDTO.getConsumeId(),consumeDTO);
            responseDTO.setErrorInfo("未查询到用户这件产品记录");
            return responseDTO;
        }
        ShopUserProductRelationDTO relationDTO = productInfoList.get(0);
        if(relationDTO.getSurplusTimes()<consumeDTO.getConsumeNum()){
            logger.error("用户领取产品的数量大于待领取的数量");
            responseDTO.setErrorInfo("用户领取产品的数量大于待领取的数量");
            return responseDTO;
        }
        BigDecimal onePrice = relationDTO.getInitAmount().divide(new BigDecimal(relationDTO.getInitTimes()), 2, ROUND_HALF_DOWN);
        logger.info("单件产品的价格为={}", onePrice);
        BigDecimal consumeAmount = onePrice.multiply(new BigDecimal(consumeDTO.getConsumeNum()));
        consumeDTO.setConsumePrice(consumeAmount);
        logger.info("领取产品价值={}", consumeAmount);

        relationDTO.setSurplusAmount(relationDTO.getSurplusAmount().subtract(consumeAmount));
        relationDTO.setSurplusTimes(relationDTO.getSurplusTimes() - consumeDTO.getConsumeNum());
        shopProductInfoService.updateShopUserProductRelation(relationDTO);

        //更新库存
        List<ShopStockRequestDTO> stockList = new ArrayList<>();
        ShopStockRequestDTO shopStockRequestDTO = new ShopStockRequestDTO();
        //根据sysUserId查询领取人名称
        ShopUserArchivesDTO shopUserArchivesDTO=new ShopUserArchivesDTO();
        shopUserArchivesDTO.setSysUserId(consumeDTO.getSysUserId());
        List<ShopUserArchivesDTO> shopUserArchivesDTOs=shopCustomerArchivesService.getShopUserArchivesInfo(shopUserArchivesDTO);
        if(CollectionUtils.isNotEmpty(shopUserArchivesDTOs)){
            shopStockRequestDTO.setReceiver(shopUserArchivesDTOs.get(0).getSysUserName());//领取人,前端显示档案
        }
        shopStockRequestDTO.setShopStoreId(clerkInfo.getSysShopId());
        shopStockRequestDTO.setShopProcId(consumeDTO.getShopProductId());
        shopStockRequestDTO.setStockOutNumber(consumeDTO.getConsumeNum());
        shopStockRequestDTO.setStockType(StockTypeEnum.DEPOSIT_OUT_STORAGE.getCode());
        shopStockRequestDTO.setStockStyle(StockStyleEnum.MANUAL_OUT_STORAGE.getCode());

        stockList.add(shopStockRequestDTO);

        JSONArray json = JSONArray.fromObject(stockList);
        String toJSONString = json.toString();//把json转换为String
        shopStockService.insertShopStockDTO(toJSONString);
        consumeDTO.setSysUserId(relationDTO.getSysUserId());
        consumeDTO.setGoodsType(GoodsTypeEnum.PRODUCT.getCode());
        consumeDTO.setFlowId(relationDTO.getId());
        consumeDTO.setConsumeName(relationDTO.getShopProductName());
        updateUserAccountDTO(clerkInfo, transactionCodeNumber, consumeDTO);
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

    private String updateUserAccountDTO(SysClerkDTO clerkInfo, String transactionCodeNumber, ShopUserConsumeDTO consumeDTO) {
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
        consumeRecordDTO.setId(uuid);
        consumeRecordDTO.setFlowId(consumeDTO.getFlowId());
        consumeRecordDTO.setFlowName(consumeDTO.getConsumeName());
        consumeRecordDTO.setCreateBy(clerkInfo.getName());
        consumeRecordDTO.setCreateDate(new Date());
        consumeRecordDTO.setFlowNo(transactionCodeNumber);
        consumeRecordDTO.setSysUserId(consumeDTO.getSysUserId());
        consumeRecordDTO.setDetail(consumeDTO.getDetail());
        consumeRecordDTO.setSysShopName(clerkInfo.getSysShopName());
        consumeRecordDTO.setSysShopId(clerkInfo.getSysShopId());
        consumeRecordDTO.setSysClerkId(consumeDTO.getSysClerkId());
        consumeRecordDTO.setSysClerkName(consumeDTO.getSysClerkName());
        consumeRecordDTO.setSysBossCode(clerkInfo.getSysBossCode());
        consumeRecordDTO.setFlowName(consumeDTO.getConsumeName());
        consumeRecordDTO.setStatus(CommonCodeEnum.SUCCESS.getCode());
        consumeRecordDTO.setOperDate(new Date());
        consumeRecordDTO.setCreateBy(clerkInfo.getName());
        consumeRecordDTO.setSignUrl(consumeDTO.getImageUrl());
        consumeRecordDTO.setConsumeNumber(consumeDTO.getConsumeNum());
        consumeRecordDTO.setConsumeType(ConsumeTypeEnum.CONSUME.getCode());
        consumeRecordDTO.setPrice(consumeDTO.getConsumePrice());
        consumeRecordDTO.setCreateDate(new Date());
        consumeRecordDTO.setSysUserId(consumeDTO.getSysUserId());
        consumeRecordDTO.setGoodsType(consumeDTO.getGoodsType());
        shopUerConsumeRecordService.saveCustomerConsumeRecord(consumeRecordDTO);
        return consumeRecordDTO.getId();
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
        if(StringUtils.isBlank(userConsumeRecordDTO.getId())){
            userConsumeRecordDTO.setId(uuid);
        }
        userConsumeRecordDTO.setCreateBy(clerkInfo.getName());
        userConsumeRecordDTO.setFlowNo(transactionCodeNumber);
        userConsumeRecordDTO.setSysUserName(archivesInfo.getSysUserName());
        userConsumeRecordDTO.setSysUserId(archivesInfo.getSysUserId());
        userConsumeRecordDTO.setSysShopName(archivesInfo.getSysShopName());
        userConsumeRecordDTO.setSysShopId(clerkInfo.getSysShopId());
        userConsumeRecordDTO.setSignUrl(shopUserPayDTO.getSignUrl());
        userConsumeRecordDTO.setSysBossCode(clerkInfo.getSysBossCode());
        userConsumeRecordDTO.setDetail(shopUserOrderDTO.getDetail());
        if(StringUtils.isNotBlank(shopUserPayDTO.getPayType())){
            userConsumeRecordDTO.setPayType(PayTypeEnum.judgeValue(shopUserPayDTO.getPayType()).getCode());
        }
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
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO rechargeRechargeCard(String transactionId, String imageUrl) {
        logger.info("充值卡充值操作传入参数={}", "transactionId = [" + transactionId + "], imageUrl = [" + imageUrl + "]");

        ResponseDTO responseDTO = new ResponseDTO();
        Query query = new Query().addCriteria(Criteria.where("transactionId").is(transactionId));
        ShopRechargeCardOrderDTO orderDTO = mongoTemplate.findOne(query, ShopRechargeCardOrderDTO.class, "shopRechargeCardOrderDTO");
        if(null == orderDTO){
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setErrorInfo("查无此订单");
            return responseDTO;
        }
        if(!OrderStatusEnum.WAIT_SIGN.getCode().equals(orderDTO.getOrderStatus())){
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setErrorInfo("此订单已经作废！"+orderDTO.getOrderStatusDesc());
            return responseDTO;
        }
        if(StringUtils.isBlank(orderDTO.getSysUserId())){
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setErrorInfo("获取到的用户为空");
            return responseDTO;
        }
        orderDTO.setImageUrl(imageUrl);

        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        //查询用户与当前充值卡的关系
        ShopUserRechargeCardDTO userRechargeCardDTO = new ShopUserRechargeCardDTO();
        userRechargeCardDTO.setSysUserId(orderDTO.getSysUserId());
        userRechargeCardDTO.setShopRechargeCardId(orderDTO.getId());
        ShopUserRechargeCardDTO shopUserRechargeInfo = shopRechargeCardService.getShopUserRechargeInfo(userRechargeCardDTO);

        //查询用户的信息
        UserInfoDTO userInfoDTO = userServiceClient.getUserInfoFromUserId(orderDTO.getSysUserId());

        //查询某个充值卡信息
        ShopRechargeCardDTO shopRechargeCardDTO = new ShopRechargeCardDTO();
        shopRechargeCardDTO.setId(orderDTO.getId());
        ShopRechargeCardResponseDTO shopRechargeCard = shopRechargeCardService.getShopRechargeCard(shopRechargeCardDTO);
        //针对特殊卡进行更新操作
        if (orderDTO.getRechargeCardType().equals(RechargeCardTypeEnum.SPECIAL.getCode())) {
            logger.info("针对特殊卡进行更新操作交易号，{},订单对象为", transactionId, orderDTO);
            //某个用户特殊充值卡不存在，执行插入操作
            if (null == shopUserRechargeInfo) {
                logger.error("特殊充值卡为空，自动生成");
                shopUserRechargeInfo = saveUserRechargeCard(userInfoDTO,shopRechargeCard.getImageUrl(),orderDTO, clerkInfo,"余额充值",RechargeCardTypeEnum.SPECIAL);
            }
            //已存在特殊充值卡，执行更新操作
            else{
                //剩余金额 = 已存剩余金额 + 充值金额
                shopUserRechargeInfo.setSurplusAmount(shopUserRechargeInfo.getSurplusAmount().add(orderDTO.getAmount()));
                shopUserRechargeInfo.setTimeDiscount(orderDTO.getTimeDiscount());
                shopUserRechargeInfo.setPeriodDiscount(orderDTO.getPeriodDiscount());
                shopUserRechargeInfo.setProductDiscount(orderDTO.getProductDiscount());
                shopUserRechargeInfo.setUpdateDate(new Date());
                int i = shopRechargeCardService.updateRechargeCard(shopUserRechargeInfo);
                logger.info("更新用户的充值卡记录操作{}", i > 0 ? "成功" : "失败");
            }
        }
        //针对普通卡进行插入操作
        else {
            logger.info("针对普通卡进行插入操作交易号，{},订单对象为", transactionId, orderDTO);
            String image = StringUtils.isBlank(shopRechargeCard.getImageUrl()) ? ImageEnum.COMMON_CARD.getDesc() : shopRechargeCard.getImageUrl();
            shopUserRechargeInfo = saveUserRechargeCard(userInfoDTO,image,orderDTO, clerkInfo,orderDTO.getName(),RechargeCardTypeEnum.COMMON);
        }

        //插入用户的消费记录
        orderDTO.setImageUrl(imageUrl);
        orderDTO.setSignUrl(imageUrl);
        int record = saveRechargeCardConsumeRecord(userInfoDTO,orderDTO, clerkInfo, shopUserRechargeInfo);
        logger.info("充值卡充值操作流水号={}，生成充值记录,{}", transactionId, record > 0 ? "成功" : "失败");

        //更新用户的账户信息
        SysUserAccountDTO sysUserAccountDTO = new SysUserAccountDTO();
        sysUserAccountDTO.setSysUserId(userInfoDTO.getId());
        sysUserAccountDTO.setSysShopId(orderDTO.getSysShopId());
        sysUserAccountDTO = sysUserAccountService.getSysUserAccountDTO(sysUserAccountDTO);
        if(null == sysUserAccountDTO){
            logger.error("查无此用户账户");
            throw new ServiceException("查无此用户账户");
        }
        //记录资金流水
        saveCashFlowInfo(orderDTO);
        Update update = new Update();
        update.set("imageUrl", imageUrl);
        update.set("orderStatusDesc", OrderStatusEnum.CONFIRM_PAY.getDesc());
        update.set("orderStatus", OrderStatusEnum.CONFIRM_PAY.getCode());
        mongoTemplate.upsert(query, update, "shopRechargeCardOrderDTO");

        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

    /**
     * 保存资金流水记录
     *
     * @param orderDTO
     */
    private void saveCashFlowInfo(ShopRechargeCardOrderDTO orderDTO) {
        ShopCashFlowDTO shopCashFlowDTO = new ShopCashFlowDTO();
        shopCashFlowDTO.setId(IdGen.uuid());
        shopCashFlowDTO.setCreateDate(new Date());
        if(StringUtils.isNotBlank(orderDTO.getSurplusPayPrice())){
            shopCashFlowDTO.setPayTypeAmount(new BigDecimal(orderDTO.getSurplusPayPrice()));
        }
        if(StringUtils.isNotBlank(orderDTO.getCashPay())){
            shopCashFlowDTO.setCashAmount(new BigDecimal(orderDTO.getCashPay()));
        }
        shopCashFlowDTO.setPayType(orderDTO.getPayType());
        shopCashFlowDTO.setSysShopId(redisUtils.getShopId());
        shopCashFlowDTO.setSysBossCode(redisUtils.getBossCode());
        shopCashFlowDTO.setFlowNo(orderDTO.getTransactionId());
        if (StringUtils.isNotBlank(orderDTO.getCashPay())) {
            shopCashFlowDTO.setCashAmount(new BigDecimal(orderDTO.getCashPay()));
        }
        int shopCashFlow = cashService.saveShopCashFlow(shopCashFlowDTO);
        logger.info("保存资金流水={}，执行结果={}", shopCashFlow, shopCashFlow > 0 ? "成功" : "失败");
    }

    private int saveRechargeCardConsumeRecord(UserInfoDTO userInfoDTO,ShopRechargeCardOrderDTO orderDTO, SysClerkDTO clerkInfo, ShopUserRechargeCardDTO shopUserRechargeInfo) {
        ShopUserConsumeRecordDTO shopUserConsumeRecordDTO = new ShopUserConsumeRecordDTO();
        shopUserConsumeRecordDTO.setId(IdGen.uuid());
        shopUserConsumeRecordDTO.setFlowId(shopUserRechargeInfo.getId());
        shopUserConsumeRecordDTO.setFlowNo(orderDTO.getTransactionId());
        shopUserConsumeRecordDTO.setFlowName(orderDTO.getName());
        shopUserConsumeRecordDTO.setGoodsType(GoodsTypeEnum.RECHARGE_CARD.getCode());
        if (null != orderDTO.getAmount()) {
            shopUserConsumeRecordDTO.setPrice(orderDTO.getAmount());
        }
        shopUserConsumeRecordDTO.setPayType(orderDTO.getPayType());
        shopUserConsumeRecordDTO.setCreateDate(new Date());
        shopUserConsumeRecordDTO.setSysClerkId(orderDTO.getSysClerkId());
        shopUserConsumeRecordDTO.setTimeDiscount(orderDTO.getTimeDiscount());
        shopUserConsumeRecordDTO.setProductDiscount(orderDTO.getProductDiscount());
        shopUserConsumeRecordDTO.setDetail(orderDTO.getDetail());
        shopUserConsumeRecordDTO.setPeriodDiscount(shopUserConsumeRecordDTO.getPeriodDiscount());
        shopUserConsumeRecordDTO.setSysUserName(orderDTO.getUserName());
        shopUserConsumeRecordDTO.setSysUserId(orderDTO.getSysUserId());
        shopUserConsumeRecordDTO.setSysClerkName(orderDTO.getSysClerkName());
        shopUserConsumeRecordDTO.setSysShopId(orderDTO.getSysShopId());
        shopUserConsumeRecordDTO.setDetail(shopUserConsumeRecordDTO.getDetail());
        shopUserConsumeRecordDTO.setSignUrl(orderDTO.getSignUrl());
        shopUserConsumeRecordDTO.setSysBossCode(clerkInfo.getSysBossCode());
        shopUserConsumeRecordDTO.setDiscount(orderDTO.getTimeDiscount());
        shopUserConsumeRecordDTO.setSysShopName(clerkInfo.getSysShopName());
        shopUserConsumeRecordDTO.setStatus(shopUserConsumeRecordDTO.getStatus());
        shopUserConsumeRecordDTO.setOperDate(new Date());
        shopUserConsumeRecordDTO.setPeriodDiscount(orderDTO.getPeriodDiscount());
        shopUserConsumeRecordDTO.setConsumeNumber(1);
        shopUserConsumeRecordDTO.setConsumeType(ConsumeTypeEnum.RECHARGE.getCode());
        shopUserConsumeRecordDTO.setCreateBy(clerkInfo.getName());
        return shopUerConsumeRecordService.saveCustomerConsumeRecord(shopUserConsumeRecordDTO);
    }

    /**
     * 保存用户充值卡
     * @param orderDTO
     * @param clerkInfo
     */
    private ShopUserRechargeCardDTO saveUserRechargeCard(UserInfoDTO userInfoDTO,String imageUrl,ShopRechargeCardOrderDTO orderDTO, SysClerkDTO clerkInfo,String cardName,RechargeCardTypeEnum rechargeCardTypeEnum) {
        ShopUserRechargeCardDTO rechargeCardDTO = new ShopUserRechargeCardDTO();
        rechargeCardDTO.setId(IdGen.uuid());
        rechargeCardDTO.setShopRechargeCardId(orderDTO.getId());
        rechargeCardDTO.setShopRechargeCardName(cardName);
        rechargeCardDTO.setProductDiscount(orderDTO.getProductDiscount());
        rechargeCardDTO.setPeriodDiscount(orderDTO.getPeriodDiscount());
        rechargeCardDTO.setTimeDiscount(orderDTO.getTimeDiscount());
        rechargeCardDTO.setSysShopId(clerkInfo.getSysShopId());
        rechargeCardDTO.setSysUserId(orderDTO.getSysUserId());
        rechargeCardDTO.setImageUrl(imageUrl);
        //总金额 = 充值金额
        BigDecimal rechargeAmount = orderDTO.getAmount();
        rechargeCardDTO.setSurplusAmount(rechargeAmount);
        rechargeCardDTO.setCreateBy(clerkInfo.getName());
        rechargeCardDTO.setSysClerkName(clerkInfo.getName());
        rechargeCardDTO.setSysClerkId(clerkInfo.getId());
        rechargeCardDTO.setSysBossCode(clerkInfo.getSysBossCode());
        rechargeCardDTO.setInitAmount(orderDTO.getAmount());
        rechargeCardDTO.setRechargeCardType(rechargeCardTypeEnum.getCode());

        rechargeCardDTO.setSysUserName(userInfoDTO.getNickname());
        rechargeCardDTO.setCreateDate(new Date());
        int updateRechargeCard = shopRechargeCardService.saveShopUserRechargeCardInfo(rechargeCardDTO);
        logger.info("充值卡充值操作传入参数执行结果={}", updateRechargeCard > 0 ? "成功" : "失败");
        return rechargeCardDTO;
    }

}

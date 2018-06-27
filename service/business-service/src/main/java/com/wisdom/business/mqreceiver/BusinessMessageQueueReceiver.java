package com.wisdom.business.mqreceiver;

import com.aliyuncs.exceptions.ClientException;
import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.mqsender.BusinessMessageQueueSender;
import com.wisdom.business.service.transaction.PayFunction;
import com.wisdom.business.service.transaction.PayRecordService;
import com.wisdom.business.service.transaction.TransactionService;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.specialShop.SpecialShopBusinessOrderDTO;
import com.wisdom.common.dto.specialShop.SpecialShopInfoDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.InstanceReturnMoneySignalDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.ObjectUtils;
import com.wisdom.common.util.SMSUtil;
import com.wisdom.common.util.WeixinTemplateMessageUtil;
import com.wisdom.common.util.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
public class BusinessMessageQueueReceiver {

    Logger logger = LoggerFactory.getLogger(BusinessMessageQueueReceiver.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private PayRecordService payRecordService;

    @Autowired
    private PayFunction payFunction;

    private static Gson gson = new Gson();

    @RabbitListener(queues = "notifySpecialShopBossCustomerTransaction")
    @RabbitHandler
    public void processNotifySpecialShopBossCustomerTransaction(String notifySpecialShopBossCustomerTransaction) throws ClientException {

        logger.info("Receiver  : " + notifySpecialShopBossCustomerTransaction);
        BusinessOrderDTO businessOrderDTO = gson.fromJson(notifySpecialShopBossCustomerTransaction,BusinessOrderDTO.class);
        String token = WeixinUtil.getUserToken();
        PayRecordDTO payRecordDTO = new PayRecordDTO();
        payRecordDTO.setOrderId(businessOrderDTO.getBusinessOrderId());
        List<PayRecordDTO> payRecordDTOS = payRecordService.getUserPayRecordList(payRecordDTO);
        if(payRecordDTOS.size()>0)
        {
            //若购买的是跨境商品，告知店主，用户购买的情况
            Query query = new Query(Criteria.where("orderId").is(businessOrderDTO.getBusinessOrderId()));
            SpecialShopBusinessOrderDTO specialShopBusinessOrderDTO = mongoTemplate.findOne(query, SpecialShopBusinessOrderDTO.class, "specialShopBusinessOrder");
            if (specialShopBusinessOrderDTO != null) {
                logger.info("购买的是跨境商品");
                String shopId = specialShopBusinessOrderDTO.getShopId();
                query = new Query(Criteria.where("shopId").is(shopId));
                SpecialShopInfoDTO specialShopInfoDTO = mongoTemplate.findOne(query, SpecialShopInfoDTO.class, "specialShopInfo");
                UserInfoDTO userInfoDTO = new UserInfoDTO();
                userInfoDTO.setMobile(specialShopInfoDTO.getShopBossMobile());
                List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);
                if (userInfoDTOList.size() > 0) {
                    logger.info("直接给店主={}发送推送消息",businessOrderDTO, userInfoDTOList.get(0).getUserOpenid());
                    WeixinTemplateMessageUtil.sendSpecialShopBossUserBuyTemplateWXMessage(token, payRecordDTO.getAmount() + "元", businessOrderDTO, userInfoDTOList.get(0).getUserOpenid(), specialShopInfoDTO);
                    SMSUtil.sendSpecialShopBossTransactionInfo(specialShopInfoDTO.getShopBossMobile(), payRecordDTO.getAmount() + "元", businessOrderDTO, specialShopInfoDTO);
                } else {
                    //直接给店主发送短信
                    logger.info("直接给店主={}发送短信",specialShopInfoDTO.getShopBossMobile());
                    SMSUtil.sendSpecialShopBossTransactionInfo(specialShopInfoDTO.getShopBossMobile(), payRecordDTO.getAmount() + "元", businessOrderDTO, specialShopInfoDTO);
                }
            }
        }
    }

    @RabbitListener(queues = "handleInstanceReturnMoney")
    @RabbitHandler
    public void processHandleInstanceReturnMoney(String handleInstanceReturnMoney){
        logger.info("receive handleInstanceReturnMoney==="+handleInstanceReturnMoney);
        String messageValue[] = handleInstanceReturnMoney.split("########");
        UserInfoDTO userInfoDTO = gson.fromJson(messageValue[0],UserInfoDTO.class);
        InstanceReturnMoneySignalDTO instanceReturnMoneySignalDTO = gson.fromJson(messageValue[1],InstanceReturnMoneySignalDTO.class);

        //todo 此处的逻辑主要是用来实现即时返现功能，此时即时返现的资金是冻结的，需要用户收货之后，获得返现的用户才能拿到钱
        if(!ObjectUtils.isNullOrEmpty(userInfoDTO.getParentUserId()))
        {
            UserInfoDTO parentUserInfoDTO = userServiceClient.getUserInfoFromUserId(userInfoDTO.getParentUserId());
            if(ConfigConstant.businessA1.equals(userInfoDTO.getUserType())){
                //父类用户也为A级执行永久返利
                if(ConfigConstant.businessA1.equals(parentUserInfoDTO.getUserType())){
                    payFunction.flatRebate(userInfoDTO.getUserType(),parentUserInfoDTO.getId(),ConfigConstant.businessA1, instanceReturnMoneySignalDTO);
                    if(!ObjectUtils.isNullOrEmpty(parentUserInfoDTO.getParentUserId())){
                        //查询祖父级是否存在并且是否为A
                        UserInfoDTO grandpaUserInfoDTO = userServiceClient.getUserInfoFromUserId(parentUserInfoDTO.getParentUserId());
                        if(grandpaUserInfoDTO!=null){
                            if(ConfigConstant.businessA1.equals(grandpaUserInfoDTO.getUserType())){
                                //执行平级返利
                                payFunction.flatRebate(userInfoDTO.getUserType(),grandpaUserInfoDTO.getId(),"A1A1", instanceReturnMoneySignalDTO);
                            }
                        }
                    }
                }
            }

            if(ConfigConstant.businessB1.equals(userInfoDTO.getUserType()))
            {
                //走B店用户消费返现逻辑
                //1、查询父一级用户是否是A店用户
                if(ConfigConstant.businessA1.equals(parentUserInfoDTO.getUserType()))
                {
                    payFunction.updateReturnMoneyDataBase(parentUserInfoDTO.getId(),ConfigConstant.businessB1,ConfigConstant.businessA1, instanceReturnMoneySignalDTO);

                    if(!ObjectUtils.isNullOrEmpty(parentUserInfoDTO.getParentUserId())){
                        //查询祖父级是否存在并且是否为A
                        UserInfoDTO grandpaUserInfoDTO = userServiceClient.getUserInfoFromUserId(parentUserInfoDTO.getParentUserId());
                        if(grandpaUserInfoDTO!=null){
                            if(ConfigConstant.businessA1.equals(grandpaUserInfoDTO.getUserType())){
                                //执行平级返利
                                payFunction.flatRebate(userInfoDTO.getUserType(),grandpaUserInfoDTO.getId(),"A1A1", instanceReturnMoneySignalDTO);
                            }
                        }
                    }
                }
                //查询父类是否是B店用户
                if(ConfigConstant.businessB1.equals(parentUserInfoDTO.getUserType()))
                {
                    payFunction.flatRebate(userInfoDTO.getUserType(),parentUserInfoDTO.getId(),ConfigConstant.businessB1, instanceReturnMoneySignalDTO);

                    if(!ObjectUtils.isNullOrEmpty(parentUserInfoDTO.getParentUserId())){
                        //查询祖父级是否存在并且是否为B
                        UserInfoDTO grandpaUserInfoDTO = userServiceClient.getUserInfoFromUserId(parentUserInfoDTO.getParentUserId());
                        if(grandpaUserInfoDTO!=null){
                            if(ConfigConstant.businessB1.equals(grandpaUserInfoDTO.getUserType())){
                                //执行平级返利
                                payFunction.flatRebate(userInfoDTO.getUserType(),grandpaUserInfoDTO.getId(),"B1B1", instanceReturnMoneySignalDTO);
                            }else if(ConfigConstant.businessA1.equals(grandpaUserInfoDTO.getUserType())){

                                //执行平级返利
                                payFunction.flatRebate(userInfoDTO.getUserType(),grandpaUserInfoDTO.getId(),"B1A1", instanceReturnMoneySignalDTO);
                            }
                        }
                    }
                }
            }
            else if(ConfigConstant.businessC1.equals(userInfoDTO.getUserType()))
            {
                //1、查询父一级的用户信息
                if(ConfigConstant.businessA1.equals(parentUserInfoDTO.getUserType()))  //2、如果父一级用户为A店用户
                {
                    payFunction.updateReturnMoneyDataBase(parentUserInfoDTO.getId(),ConfigConstant.businessC1,ConfigConstant.businessA1, instanceReturnMoneySignalDTO);
                    if(!ObjectUtils.isNullOrEmpty(parentUserInfoDTO.getParentUserId())){
                        //查询祖父级是否存在并且是否为A
                        UserInfoDTO grandpaUserInfoDTO = userServiceClient.getUserInfoFromUserId(parentUserInfoDTO.getParentUserId());
                        if(grandpaUserInfoDTO!=null){
                            if(ConfigConstant.businessA1.equals(grandpaUserInfoDTO.getUserType())){
                                //执行平级返利
                                payFunction.flatRebate(userInfoDTO.getUserType(),grandpaUserInfoDTO.getId(),"A1A1", instanceReturnMoneySignalDTO);
                            }
                        }
                    }
                }
                else if(ConfigConstant.businessB1.equals(parentUserInfoDTO.getUserType())) //3、如果父一级用户为B店用户
                {
                    //先更新用户上一级B店用户的返现金额
                    payFunction.updateReturnMoneyDataBase(parentUserInfoDTO.getId(),ConfigConstant.businessC1,ConfigConstant.businessB1, instanceReturnMoneySignalDTO);
                    if(!ObjectUtils.isNullOrEmpty(parentUserInfoDTO.getParentUserId()))
                    {
                        UserInfoDTO grandpaUserInfoDTO = userServiceClient.getUserInfoFromUserId(parentUserInfoDTO.getParentUserId());

                        //在更新爷爷一级用户的类型为A店店主的返现金额
                        if(ConfigConstant.businessA1.equals(grandpaUserInfoDTO.getUserType()))
                        {
                            payFunction.updateReturnMoneyDataBase(grandpaUserInfoDTO.getId(),ConfigConstant.businessC1,"A1B1", instanceReturnMoneySignalDTO);
                        }else if(ConfigConstant.businessB1.equals(grandpaUserInfoDTO.getUserType())){
                            payFunction.flatRebate(userInfoDTO.getUserType(),grandpaUserInfoDTO.getId(),"B1B1", instanceReturnMoneySignalDTO);
                        }
                    }
                }
            }
        }

    }

    @RabbitListener(queues = "recordMonthTransaction")
    @RabbitHandler
    public void processRecordMonthTransaction(String recordMonthTransaction) throws UnsupportedEncodingException {
        logger.info("receive recordMonthTransaction==="+recordMonthTransaction);
        String messageValue[] = recordMonthTransaction.split("########");
        UserInfoDTO userInfoDTO = gson.fromJson(messageValue[0],UserInfoDTO.class);
        InstanceReturnMoneySignalDTO instanceReturnMoneySignalDTO = gson.fromJson(messageValue[1],InstanceReturnMoneySignalDTO.class);
        float expenseMoney = Float.parseFloat(messageValue[2]);

        logger.info("获取支付交易的消费金额=="+expenseMoney);
        logger.info("进行月度流水统计");
        logger.info("用户等级："+userInfoDTO.getUserType());
        if(userInfoDTO.getUserType().equals(ConfigConstant.businessA1)||userInfoDTO.getUserType().equals(ConfigConstant.businessB1)||userInfoDTO.getUserType().equals(ConfigConstant.businessC1))
        {
            //如果用户为c是用户c的升级单则计入
            if(userInfoDTO.getUserType().equals(ConfigConstant.businessC1)){
                if(expenseMoney>=ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE){
                    payFunction.recordMonthTransaction(userInfoDTO.getId(),instanceReturnMoneySignalDTO,expenseMoney,"self");
                }
            }else{
                payFunction.recordMonthTransaction(userInfoDTO.getId(),instanceReturnMoneySignalDTO,expenseMoney,"self");
            }
        }
    }

    @RabbitListener(queues = "handleUserLevelPromotion")
    @RabbitHandler
    public void processHandleUserLevelPromotion(String handleUserLevelPromotion) throws UnsupportedEncodingException {
        logger.info("receive handleUserLevelPromotion==="+handleUserLevelPromotion);
        String messageValue[] = handleUserLevelPromotion.split("########");
        UserInfoDTO userInfoDTO = gson.fromJson(messageValue[0],UserInfoDTO.class);
        float expenseMoney = Float.parseFloat(messageValue[1]);

        logger.info("根据消费金额处理用户的等级提升=="+userInfoDTO.getMobile());
        //判断，消费额度是否可以提升用户的等级
        if(ConfigConstant.businessC1.equals(userInfoDTO.getUserType()))
        {
            //如果是C用户
            if(expenseMoney>=ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE&&expenseMoney<=ConfigConstant.PROMOTE_B1_LEVEL_MAX_EXPENSE)
            {
                //消费金额在B的区间段，升级为B
                payFunction.promoteUserBusinessTypeForExpenseSecond(userInfoDTO,ConfigConstant.businessB1,ConfigConstant.livingPeriodYear);
            }
            else if(expenseMoney>=ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE)
            {
                //消费金额在A的区间段，升级为A
                payFunction.promoteUserBusinessTypeForExpenseSecond(userInfoDTO,ConfigConstant.businessA1,ConfigConstant.livingPeriodYear);
            }
        }
        else if(ConfigConstant.businessB1.equals(userInfoDTO.getUserType()))
        {
            if(expenseMoney>=ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE)
            {
                //消费金额在A的区间段，升级为A
                payFunction.promoteUserBusinessTypeForExpenseSecond(userInfoDTO,ConfigConstant.businessA1,ConfigConstant.livingPeriodYear);
            }
            if(expenseMoney>=ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE&&expenseMoney<=ConfigConstant.PROMOTE_B1_LEVEL_MAX_EXPENSE)
            {
                //判断用户是否为B1的冻结状态，如果冻结状态，则解冻
                payFunction.deFrozenUserType(userInfoDTO);
            }
        }
        else if(ConfigConstant.businessA1.equals(userInfoDTO.getUserType()))
        {
            if(expenseMoney>=ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE)
            {
                //判断用户是否为A1的冻结状态，如果冻结状态，则解冻
                payFunction.deFrozenUserType(userInfoDTO);
            }
        }
        logger.info("处理用户消费特殊商品后的等级提升=="+userInfoDTO.getMobile());

    }
}

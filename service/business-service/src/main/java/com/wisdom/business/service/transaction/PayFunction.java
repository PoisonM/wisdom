package com.wisdom.business.service.transaction;

import com.aliyuncs.exceptions.ClientException;
import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.mapper.level.UserTypeMapper;
import com.wisdom.business.mapper.transaction.PromotionTransactionRelationMapper;
import com.wisdom.business.mapper.transaction.TransactionMapper;
import com.wisdom.business.mqsender.BusinessMessageQueueSender;
import com.wisdom.business.service.account.AccountService;
import com.wisdom.business.service.account.IncomeService;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.specialShop.SpecialShopBusinessOrderDTO;
import com.wisdom.common.dto.specialShop.SpecialShopInfoDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.InstanceReturnMoneySignalDTO;
import com.wisdom.common.dto.transaction.MonthTransactionRecordDTO;
import com.wisdom.common.dto.transaction.PromotionTransactionRelation;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.SMSUtil;
import com.wisdom.common.util.WeixinTemplateMessageUtil;
import com.wisdom.common.util.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PayFunction {

    Logger logger = LoggerFactory.getLogger(PayFunction.class);

    @Autowired
    private PayRecordService payRecordService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private AccountService accountService;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private UserTypeMapper userTypeMapper;


    @Autowired
    private PromotionTransactionRelationMapper promotionTransactionRelationMapper;

    @Autowired
    private BusinessMessageQueueSender businessMessageQueueSender;

    @Transactional(rollbackFor = Exception.class)
    public void processPayStatus(List<PayRecordDTO> payRecordDTOList) throws ClientException {
        logger.info("service == 处理支付状态processPayStatus");
        try {
            float totalMoney = 0;
            String productName = "";
            String token = WeixinUtil.getUserToken();
            String url = ConfigConstant.USER_WEB_URL + "orderManagement/1";
            logger.info("处理支付状态token={},url={}",token,url);
            String userId = "";
            for (PayRecordDTO payRecordDTO : payRecordDTOList) {
                //修改payRecord的订单状态，表示已支付
                payRecordDTO.setStatus("1");
                payRecordDTO.setPayDate(new Date());
                payRecordService.updatePayRecordStatus(payRecordDTO);

                totalMoney = totalMoney + payRecordDTO.getAmount();

                //修改business_order的状态，表示已支付
                logger.info("修改payRecord,business_order=={}的状态，表示已支付",payRecordDTO.getOrderId());

                //此处对orderId加锁
                BusinessOrderDTO businessOrderDTO = transactionService.getBusinessOrderDetailInfoByOrderId(payRecordDTO.getOrderId());
                businessOrderDTO.setStatus("1");
                businessOrderDTO.setUpdateDate(new Date());
                transactionService.updateBusinessOrder(businessOrderDTO);

                productName = productName + businessOrderDTO.getBusinessProductName() +
                        "(" + businessOrderDTO.getProductSpec() + ")" + businessOrderDTO.getBusinessProductNum() + "套" + ";";
                userId = businessOrderDTO.getSysUserId();

                businessMessageQueueSender.sendNotifySpecialShopBossCustomerTransaction(businessOrderDTO);
            }

            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId(userId);
            userInfoDTO.setDelFlag("0");
            List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);
            if (userInfoDTOList.size() > 0) {
                userInfoDTO = userInfoDTOList.get(0);
            }
            WeixinTemplateMessageUtil.sendOrderPaySuccessTemplateWXMessage((int) totalMoney + "元", productName, token, url, userInfoDTO.getUserOpenid());
        } catch (Exception e) {
            logger.error("处理支付状态processPayStatus异常,异常信息为{}"+e.getMessage(),e);
            e.printStackTrace();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void promoteUserBusinessTypeForExpenseSecond(UserInfoDTO userInfoDTO, String businessType, int livingPeriod) {

        //sys_user表也需要更新
        logger.info("service == 更新sys_user表用户=={},等级=={},时效={}",userInfoDTO.getMobile(),businessType,livingPeriod);
        userInfoDTO.setUserType(businessType);
        userServiceClient.updateUserInfo(userInfoDTO);

        //更新user_business_type表的数据
        logger.info("更新user_business_type表的数据,把老级别变为失效");
        //1、把老级别变为失效
        UserBusinessTypeDTO userBusinessTypeDTO = new UserBusinessTypeDTO();
        userBusinessTypeDTO.setSysUserId(userInfoDTO.getId());
        userBusinessTypeDTO.setStatus("1");
        List<UserBusinessTypeDTO> userBusinessTypeDTOS = userTypeMapper.getUserBusinessType(userBusinessTypeDTO);

        UserBusinessTypeDTO userBusinessTypeDTO1 = new UserBusinessTypeDTO();
        userBusinessTypeDTO1.setSysUserId(userInfoDTO.getId());
        userBusinessTypeDTO1.setStatus("2");
        List<UserBusinessTypeDTO> userBusinessTypeDTOS1 = userTypeMapper.getUserBusinessType(userBusinessTypeDTO);

        if (userBusinessTypeDTOS.size() > 0 || userBusinessTypeDTOS1.size() > 0) {
            userBusinessTypeDTO = userBusinessTypeDTOS.get(0);
            userBusinessTypeDTO.setStatus("0");
            userTypeMapper.updateUserBusinessType(userBusinessTypeDTO);
        }

        //2、级别更新创建新的记录
        logger.info("级别更新创建新的记录UserBusinessType表");
        userBusinessTypeDTO = new UserBusinessTypeDTO();
        userBusinessTypeDTO.setId(UUID.randomUUID().toString());
        userBusinessTypeDTO.setParentUserId(userInfoDTO.getParentUserId());
        userBusinessTypeDTO.setSysUserId(userInfoDTO.getId());
        userBusinessTypeDTO.setUserType(businessType);
        userBusinessTypeDTO.setCreateDate(new Date());
        userBusinessTypeDTO.setLivingPeriod(livingPeriod);
        userBusinessTypeDTO.setStatus("1");
        userTypeMapper.insertUserBusinessType(userBusinessTypeDTO);

    }

    @Transactional(rollbackFor = Exception.class)
    public void updateReturnMoneyDataBase(String parentUserId, String userRuleType, String parentRuleType, InstanceReturnMoneySignalDTO instanceReturnMoneySignalDTO) {
        logger.info("service -- updateReturnMoneyDataBase,参数parentUserId={},userRuleType={},parentRuleType={}方法执行",parentUserId,userRuleType,parentRuleType);
        String token = WeixinUtil.getUserToken();
        try {

            //计算提成给上一级用户ruleType对应店的钱
            PayRecordDTO payRecordDTO = new PayRecordDTO();
            payRecordDTO.setTransactionId(instanceReturnMoneySignalDTO.getTransactionId());
            payRecordDTO.setOutTradeNo(instanceReturnMoneySignalDTO.getOutTradeNo());
            payRecordDTO.setSysUserId(instanceReturnMoneySignalDTO.getSysUserId());
            payRecordDTO.setStatus("1");
            List<PayRecordDTO> payRecordDTOList = payRecordService.getUserPayRecordList(payRecordDTO);

            UserInfoDTO nextUserInfoDTO = new UserInfoDTO();
            nextUserInfoDTO.setId(instanceReturnMoneySignalDTO.getSysUserId());
            List<UserInfoDTO> nextUserInfoDTOList = userServiceClient.getUserInfo(nextUserInfoDTO);
            UserInfoDTO userInfoDTO = nextUserInfoDTOList.get(0);
            String userType = "";
            if (userInfoDTO.getUserType().equalsIgnoreCase(ConfigConstant.businessB1)) {
                userType = ConfigConstant.LEVE_IMPORT_B;
            } else if (userInfoDTO.getUserType().equalsIgnoreCase(ConfigConstant.businessA1)) {
                userType = ConfigConstant.LEVE_IMPORT_A;
            }

            //该交易的交易金额
            float expenseAmount = 0;
            for (PayRecordDTO payRecord : payRecordDTOList) {
                expenseAmount = expenseAmount + payRecord.getAmount();
            }
            logger.info("service -- updateReturnMoneyDataBase 本人交易金额==={}",expenseAmount);
            //即时返现的金额
            float returnMoney = 0;

            //永久性奖励的金额
            float permanentReward = 0;

            //升级
            String isImportLevel = ConfigConstant.LEVE_IMPORT;

            //逻辑先写死
            if (userRuleType.equals(ConfigConstant.businessC1)) {
                if (parentRuleType.equals(ConfigConstant.businessA1)) {

                    float amount = 0;
                    if (expenseAmount >= ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE) {
                        //用户升级A时即时返利的给父类的钱
                        returnMoney = ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE * 5 / 100 + (expenseAmount - ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE) * 2 / 100;

                        //用户升级A时永久性奖励
                        permanentReward = this.getPermanentReward(userRuleType,expenseAmount);

                        //记录月度交易流水
                        amount = expenseAmount - ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE;

                        //记录此单是用户升级订单
                        if (!userType.equalsIgnoreCase(ConfigConstant.LEVE_IMPORT_A)) {
                            isImportLevel = ConfigConstant.LEVE_IMPORT_A;
                        }
                        logger.info("service -- updateReturnMoneyDataBase,本人C,上级A,交易金额大于{},即时返现的金额={},永久性奖励的金额={},记录月度交易流水={},升级={}",ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE,returnMoney,permanentReward,amount,isImportLevel);
                    } else if (expenseAmount >= ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE && expenseAmount <= ConfigConstant.PROMOTE_B1_LEVEL_MAX_EXPENSE) {
                        //用户升级成B时即时返利给父类的钱
                        returnMoney = ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE * 10 / 100 + (expenseAmount - ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE) * 2 / 100;

                        //用户升级B时永久性奖励
                        permanentReward = this.getPermanentReward(userRuleType,expenseAmount);

                        //记录月度交易流水
                        amount = expenseAmount - ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE;

                        //记录此单是用户升级单
                        if (!userType.equalsIgnoreCase(ConfigConstant.LEVE_IMPORT_B) && !userType.equalsIgnoreCase(ConfigConstant.LEVE_IMPORT_A)) {
                            isImportLevel = ConfigConstant.LEVE_IMPORT_B;
                        }
                        logger.info("service -- updateReturnMoneyDataBase,本人C,上级A,交易金额在{}和{}之间,即时返现的金额={},永久性奖励的金额={},记录月度交易流水={},升级={}",ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE,ConfigConstant.PROMOTE_B1_LEVEL_MAX_EXPENSE,returnMoney,permanentReward,amount,isImportLevel);
                    } else {
                        //用户即时提现返给父类的钱
                        returnMoney = expenseAmount * 2 / 100;

                        //用户消费时永久性奖励
                        permanentReward = this.getPermanentReward(userRuleType,expenseAmount);

                        //记录月度交易流水
                        amount = expenseAmount;
                        logger.info("service -- updateReturnMoneyDataBase,本人C,上级A,交易金额低于B,即时返现的金额={},永久性奖励的金额={},记录月度交易流水={},升级={}",returnMoney,permanentReward,amount,isImportLevel);
                    }
                    recordMonthTransaction(parentUserId, instanceReturnMoneySignalDTO, amount, parentRuleType);

                } else if (parentRuleType.equals(ConfigConstant.businessB1)) {
                    float amount = 0;
                    if (expenseAmount >= ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE && expenseAmount <= ConfigConstant.PROMOTE_B1_LEVEL_MAX_EXPENSE) {
                        //用户即时提现返给父类的
                        returnMoney = ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE * 10 / 100 + (expenseAmount - ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE) * 5 / 100;

                        //用户消费时永久性奖励
                        permanentReward = this.getPermanentReward(userRuleType,expenseAmount);

                        //记录月度交易流水
                        amount = expenseAmount - ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE;

                        //记录此单是用户升级单
                        if (!userType.equalsIgnoreCase(ConfigConstant.LEVE_IMPORT_B) && !userType.equalsIgnoreCase(ConfigConstant.LEVE_IMPORT_A)) {
                            isImportLevel = ConfigConstant.LEVE_IMPORT_B;
                        }
                        logger.info("service -- updateReturnMoneyDataBase,本人C,上级B,交易金额在{}和{}之间,即时返现的金额={},永久性奖励的金额={},记录月度交易流水={},升级={}",ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE,ConfigConstant.PROMOTE_B1_LEVEL_MAX_EXPENSE,returnMoney,permanentReward,amount,isImportLevel);
                    } else if (expenseAmount >= ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE) {

                        returnMoney = ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE * 5 / 100 + (expenseAmount - ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE) * 5 / 100;

                        //用户消费时永久性奖励
                        permanentReward = this.getPermanentReward(userRuleType,expenseAmount);

                        //记录月度交易流水
                        amount = expenseAmount - ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE;

                        //记录此单是用户升级单
                        if (!userType.equalsIgnoreCase(ConfigConstant.LEVE_IMPORT_A)) {
                            isImportLevel = ConfigConstant.LEVE_IMPORT_A;
                        }
                        logger.info("service -- updateReturnMoneyDataBase,本人C,上级B,交易金额大于{},即时返现的金额={},永久性奖励的金额={},记录月度交易流水={},升级={}",ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE,returnMoney,permanentReward,amount,isImportLevel);
                    } else {

                        //b的下级消费返5%的即时
                        returnMoney = expenseAmount * 5 / 100;

                        //用户消费时永久性奖励
                        permanentReward = this.getPermanentReward(userRuleType,expenseAmount);

                        //记录月度交易流水
                        amount = expenseAmount;
                        logger.info("service -- updateReturnMoneyDataBase,本人C,上级B,交易金额低于B,即时返现的金额={},永久性奖励的金额={},记录月度交易流水={},升级={}",returnMoney,permanentReward,amount,isImportLevel);
                    }
                    recordMonthTransaction(parentUserId, instanceReturnMoneySignalDTO, amount, parentRuleType);
                } else if (parentRuleType.equals("A1B1")) {
                    float amount = 0;
                    if (expenseAmount >= ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE && expenseAmount <= ConfigConstant.PROMOTE_B1_LEVEL_MAX_EXPENSE) {
                        returnMoney = (expenseAmount - ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE) * 2 / 100;

                        //用户消费时永久性奖励
                        permanentReward = this.getPermanentReward(userRuleType,expenseAmount);

                        //记录月度交易流水
                        amount = expenseAmount - ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE;

                        //记录此单是用户升级单
                        if (!userType.equalsIgnoreCase(ConfigConstant.LEVE_IMPORT_B) && !userType.equalsIgnoreCase(ConfigConstant.LEVE_IMPORT_A)) {
                            isImportLevel = ConfigConstant.LEVE_IMPORT_B;
                        }
                        logger.info("service -- updateReturnMoneyDataBase,本人C,上上级关系,交易金额在{}和{}之间,即时返现的金额={},永久性奖励的金额={},记录月度交易流水={},升级={}",ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE,ConfigConstant.PROMOTE_B1_LEVEL_MAX_EXPENSE,returnMoney,permanentReward,amount,isImportLevel);
                    } else if (expenseAmount >= ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE) {
                        returnMoney = (expenseAmount - ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE) * 2 / 100;

                        //用户消费时永久性奖励
                        permanentReward = this.getPermanentReward(userRuleType,expenseAmount);

                        //记录月度交易流水
                        amount = expenseAmount - ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE;

                        //记录此单是用户升级单
                        if (!userType.equalsIgnoreCase(ConfigConstant.LEVE_IMPORT_A)) {
                            isImportLevel = ConfigConstant.LEVE_IMPORT_A;
                        }
                        logger.info("service -- updateReturnMoneyDataBase,本人C,上上级关系,交易金额大于{},即时返现的金额={},永久性奖励的金额={},记录月度交易流水={},升级={}",ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE,returnMoney,permanentReward,amount,isImportLevel);
                    } else {
                        returnMoney = expenseAmount * 2 / 100;

                        //用户消费时永久性奖励
                        permanentReward = this.getPermanentReward(userRuleType,expenseAmount);

                        //记录月度交易流水
                        amount = expenseAmount;
                        logger.info("service -- updateReturnMoneyDataBase,本人C,上上级关系,交易金额低于B,即时返现的金额={},永久性奖励的金额={},记录月度交易流水={},升级={}",returnMoney,permanentReward,amount,isImportLevel);
                    }
                    recordMonthTransaction(parentUserId, instanceReturnMoneySignalDTO, amount, "A1B1");
                }
            } else if (userRuleType.equals(ConfigConstant.businessB1)) {
                if (parentRuleType.equals(ConfigConstant.businessA1)) {
                    float amount = 0;
                    if (expenseAmount >= ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE) {
                        returnMoney = ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE * 5 / 100 + (expenseAmount - ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE) * 2 / 100;

                        //用户消费时永久性奖励
                        permanentReward = this.getPermanentReward(userRuleType,expenseAmount);

                        //记录月度交易流水
                        amount = expenseAmount - ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE;

                        //记录此单是用户升级单
                        if (!userType.equalsIgnoreCase(ConfigConstant.LEVE_IMPORT_A)) {
                            isImportLevel = ConfigConstant.LEVE_IMPORT_A;
                        }
                        logger.info("service -- updateReturnMoneyDataBase,本人B,上级A,交易金额大于{},即时返现的金额={},永久性奖励的金额={},记录月度交易流水={},升级={}",ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE,returnMoney,permanentReward,amount,isImportLevel);
                    } else {
                        returnMoney = expenseAmount * 2 / 100;

                        //用户消费时永久性奖励
                        permanentReward = this.getPermanentReward(userRuleType,expenseAmount);

                        //记录月度交易流水
                        amount = expenseAmount;
                        logger.info("service -- updateReturnMoneyDataBase,本人B,上级A,交易金额低于A,即时返现的金额={},永久性奖励的金额={},记录月度交易流水={},升级={}",returnMoney,permanentReward,amount,isImportLevel);
                    }
                    recordMonthTransaction(parentUserId, instanceReturnMoneySignalDTO, amount, parentRuleType);
                } else if (parentRuleType.equals(ConfigConstant.businessB1)) {
                    //平级无即时和月度返利 用户消费时永久性奖励
                    permanentReward = this.getPermanentReward(userRuleType,expenseAmount);
                    logger.info("service -- updateReturnMoneyDataBase,本人B,上级B,平级无即时和月度返利 用户消费时永久性奖励,即时返现的金额={},永久性奖励的金额={},记录月度交易流水={},升级={}",returnMoney,permanentReward,isImportLevel);
                }

            } else if (userRuleType.equals(ConfigConstant.businessA1)) {
                if (parentRuleType.equals(ConfigConstant.businessA1)) {
                    //平级无即时和月度返利 用户消费时永久性奖励
                    permanentReward = this.getPermanentReward(userRuleType,expenseAmount);
                    logger.info("service -- updateReturnMoneyDataBase,本人A,上级A,平级无即时和月度返利 用户消费时永久性奖励,即时返现的金额={},永久性奖励的金额={},记录月度交易流水={},升级={}",returnMoney,permanentReward,isImportLevel);
                }
            }
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setSysUserId(parentUserId);

            if (returnMoney >= 0) {
                logger.info("service -- updateReturnMoneyDataBase,将returnMoney={}去更新要返现的用户ID的account和income两个表的数据",returnMoney);

                //将returnMoney去更新要返现的用户ID的account和income两个表的数据
                accountDTO = this.updateUserAccount(accountDTO,parentUserId, returnMoney);

                this.insertIncomeServiceIm(instanceReturnMoneySignalDTO,parentUserId,returnMoney,expenseAmount,parentRuleType,ConfigConstant.INCOME_TYPE_I);
               // WeixinTemplateMessageUtil.sendLowLevelBusinessExpenseTemplateWXMessage(nextUserInfoDTOList.get(0).getNickname(), expenseAmount + "", DateUtils.DateToStr(new Date()), token, "", accountDTO.getUserOpenId());
            }
            //永久性奖励
            if(permanentReward>=0){
                logger.info("service -- updateReturnMoneyDataBase,永久性奖励,更新要返现的用户ID的account和income两个表的数据",permanentReward);

                accountDTO = this.updateUserAccount(accountDTO,parentUserId, permanentReward);
                this.insertIncomeServiceIm(instanceReturnMoneySignalDTO,parentUserId,permanentReward,expenseAmount,parentRuleType,ConfigConstant.INCOME_TYPE_P);

            }

            if(permanentReward>=0||returnMoney >=0){
                //判断该交易号记录是否存在
                int size = promotionTransactionRelationMapper.isExistence(instanceReturnMoneySignalDTO.getTransactionId());
                if(size == 0){
                    this.insertPromotionTransactionRelation(isImportLevel,instanceReturnMoneySignalDTO.getSysUserId(),instanceReturnMoneySignalDTO.getTransactionId());
                    logger.info("用户购买单号：{}，",instanceReturnMoneySignalDTO.getTransactionId());
                }
                WeixinTemplateMessageUtil.sendLowLevelBusinessExpenseTemplateWXMessage(userInfoDTO.getNickname(), expenseAmount + "", DateUtils.DateToStr(new Date()), token, "", accountDTO.getUserOpenId());
            }

        } catch (Exception e) {
            logger.error("service -- updateReturnMoneyDataBase,出现异常,异常信息为{}"+e.getMessage(),e);
            e.printStackTrace();
        }
    }

    /**
     * 平级返利
     * */
    @Transactional(rollbackFor = Exception.class)
    public void flatRebate(String userRuleType ,String parentUserId,String parentRuleType, InstanceReturnMoneySignalDTO instanceReturnMoneySignalDTO){
        logger.info("service -- flatRebate,平级返利,参数userRuleType={},parentUserId={},parentRuleType={}",userRuleType,parentUserId,parentRuleType);

        String token = WeixinUtil.getUserToken();
        logger.info("service -- flatRebate,平级返利,token={}",token);

        PayRecordDTO payRecordDTO = new PayRecordDTO();
        payRecordDTO.setTransactionId(instanceReturnMoneySignalDTO.getTransactionId());
        payRecordDTO.setOutTradeNo(instanceReturnMoneySignalDTO.getOutTradeNo());
        payRecordDTO.setSysUserId(instanceReturnMoneySignalDTO.getSysUserId());
        payRecordDTO.setStatus("1");
        List<PayRecordDTO> payRecordDTOList = payRecordService.getUserPayRecordList(payRecordDTO);

        //该交易流水的交易金额
        float expenseAmount = 0;
        for (PayRecordDTO payRecord : payRecordDTOList) {
            expenseAmount = expenseAmount + payRecord.getAmount();
        }
        logger.info("service -- 平级返利,该交易流水的交易金额,expenseAmount={}",expenseAmount);

        //永久性奖励的金额
        float permanentReward = 0;
        permanentReward = this.getPermanentReward(userRuleType,expenseAmount);
        logger.info("service -- 平级返利,永久性奖励的金额,permanentReward={}",permanentReward);

        String isImportLevel = ConfigConstant.LEVE_IMPORT;

        UserInfoDTO nextUserInfoDTO = new UserInfoDTO();
        nextUserInfoDTO.setId(instanceReturnMoneySignalDTO.getSysUserId());
        List<UserInfoDTO> nextUserInfoDTOList = userServiceClient.getUserInfo(nextUserInfoDTO);
        UserInfoDTO userInfoDTO = nextUserInfoDTOList.get(0);
        String userType = "";
        if (userInfoDTO.getUserType().equalsIgnoreCase(ConfigConstant.businessB1)) {
            userType = ConfigConstant.LEVE_IMPORT_B;
        } else if (userInfoDTO.getUserType().equalsIgnoreCase(ConfigConstant.businessA1)) {
            userType = ConfigConstant.LEVE_IMPORT_A;
        }

        if (expenseAmount >= ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE && expenseAmount < ConfigConstant.PROMOTE_B1_LEVEL_MAX_EXPENSE) {
            //记录此单是用户升级单
            if (!userType.equalsIgnoreCase(ConfigConstant.LEVE_IMPORT_B) && !userType.equalsIgnoreCase(ConfigConstant.LEVE_IMPORT_A)) {
                isImportLevel = ConfigConstant.LEVE_IMPORT_B;
                logger.info("用户当前的等级={},升级记录为={}", userType, isImportLevel);
            }
        } else if (expenseAmount >= ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE) {
            //记录此单是用户升级单
            if (!userType.equalsIgnoreCase(ConfigConstant.LEVE_IMPORT_A)) {
                isImportLevel = ConfigConstant.LEVE_IMPORT_A;
                logger.info("用户当前的等级={},升级记录为={}", userType, isImportLevel);
            }
        }

        try{
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setSysUserId(parentUserId);
            if(permanentReward>=0){
                //更新用户账户金额
                logger.info("service -- 平级返利,更新用户账户金额,");
                accountDTO = this.updateUserAccount(accountDTO,parentUserId, permanentReward);
                //插入永久奖励信息
                logger.info("service -- 平级返利,插入永久奖励信息,");
                this.insertIncomeServiceIm(instanceReturnMoneySignalDTO,parentUserId,permanentReward,expenseAmount,parentRuleType,ConfigConstant.INCOME_TYPE_P);
                //给用户推送下级消费信息
                logger.info("service -- 平级返利,给用户推送下级消费信息,");
                int size = promotionTransactionRelationMapper.isExistence(instanceReturnMoneySignalDTO.getTransactionId());
                if(size == 0){
                    this.insertPromotionTransactionRelation(isImportLevel,instanceReturnMoneySignalDTO.getSysUserId(),instanceReturnMoneySignalDTO.getTransactionId());
                }
                WeixinTemplateMessageUtil.sendLowLevelBusinessExpenseTemplateWXMessage(userInfoDTO.getNickname(), expenseAmount + "", DateUtils.DateToStr(new Date()), token, "", accountDTO.getUserOpenId());
            }
        }catch (Exception e) {
            logger.error("service -- 平级返利异常,异常信息为{}"+e.getMessage(),e);
            e.printStackTrace();
        }
    }

    public void recordMonthTransaction(String userId, InstanceReturnMoneySignalDTO instanceReturnMoneySignalDTO, float amount, String parentRelation) throws UnsupportedEncodingException {
        logger.info("service == 进行月度流水统计,用户id={},金额amount={},关系={}",userId,amount,parentRelation);
        MonthTransactionRecordDTO monthTransactionRecordDTO = new MonthTransactionRecordDTO();
        monthTransactionRecordDTO.setId(UUID.randomUUID().toString());
        monthTransactionRecordDTO.setTransactionId(instanceReturnMoneySignalDTO.getTransactionId());
        monthTransactionRecordDTO.setAmount(amount);
        monthTransactionRecordDTO.setUserId(userId);
        monthTransactionRecordDTO.setCreateDate(new Date());
        monthTransactionRecordDTO.setUpdateDate(new Date());

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        UserInfoDTO nextUserInfoDTO = new UserInfoDTO();
        userInfoDTO.setId(userId);
        nextUserInfoDTO.setId(instanceReturnMoneySignalDTO.getSysUserId());

        List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);
        List<UserInfoDTO> nextUserInfoDTOList = userServiceClient.getUserInfo(nextUserInfoDTO);

        monthTransactionRecordDTO.setNextUserId(instanceReturnMoneySignalDTO.getSysUserId());
        monthTransactionRecordDTO.setUserType(userInfoDTOList.get(0).getUserType());
        monthTransactionRecordDTO.setNextUserType(nextUserInfoDTOList.get(0).getUserType());
        monthTransactionRecordDTO.setParentRelation(parentRelation);
        monthTransactionRecordDTO.setMobile(userInfoDTOList.get(0).getMobile());
        monthTransactionRecordDTO.setNextUserMobile(nextUserInfoDTOList.get(0).getMobile());
        monthTransactionRecordDTO.setIdentifyNumber(userInfoDTOList.get(0).getIdentifyNumber());
        monthTransactionRecordDTO.setNextUserIdentifyNumber(nextUserInfoDTOList.get(0).getIdentifyNumber());
        monthTransactionRecordDTO.setNickName(URLEncoder.encode(userInfoDTOList.get(0).getNickname(), "utf-8"));
        monthTransactionRecordDTO.setNextUserNickName(URLEncoder.encode(nextUserInfoDTOList.get(0).getNickname(), "utf-8"));

        transactionMapper.recordMonthTransaction(monthTransactionRecordDTO);
    }

    /**
     * 根据消费金额计算出永久奖励
     * @param expenseAmount
     * @return
     */
    public Float getPermanentReward(String userRuleType,float expenseAmount) {
        logger.info("service == 根据消费金额计算出永久奖励,参数userRuleType={},expenseAmount={}",userRuleType,expenseAmount);

        Float permantReward = (float)0;
        if(userRuleType.equals(ConfigConstant.businessC1)){
            if (expenseAmount >= ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE){

                permantReward = (expenseAmount - ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE) * 5/100;

            }else if(expenseAmount >= ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE && expenseAmount <= ConfigConstant.PROMOTE_B1_LEVEL_MAX_EXPENSE){

                permantReward = (expenseAmount - ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE) * 5/100;

            }else{
                permantReward = expenseAmount * 5/100;
            }
        }else if(userRuleType.equals(ConfigConstant.businessB1)){
            if (expenseAmount >= ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE){

                permantReward = (expenseAmount - ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE) * 5/100;

            }else{
                permantReward = expenseAmount * 5/100;
            }
        }else{
            permantReward = expenseAmount * 5/100;
        }
        logger.info("service == 根据消费金额计算出永久奖励={}",permantReward);
        return permantReward;
    }

    /**
     * 给即时返利表插入数据
     * @param instanceReturnMoneySignalDTO (即时返利表dto)
     */

    public void insertIncomeServiceIm(InstanceReturnMoneySignalDTO instanceReturnMoneySignalDTO,String parentUserId,Float returnMoney,Float expenseAmount,String parentRuleType,String incomeType) throws Exception{
        logger.info("service -- insertIncomeServiceIm,给即时返利表插入数据,参数parentUserId={},returnMoney={},expenseAmount={},parentRuleType={},incomeType={}",parentUserId,returnMoney,expenseAmount,parentRuleType,incomeType);
        IncomeRecordDTO incomeRecordDTO = new IncomeRecordDTO();

        //获取子类用户信息
        UserInfoDTO nextUserInfoDTO = new UserInfoDTO();
        nextUserInfoDTO.setId(instanceReturnMoneySignalDTO.getSysUserId());
        List<UserInfoDTO> nextUserInfoDTOList = userServiceClient.getUserInfo(nextUserInfoDTO);
        logger.info("service -- insertIncomeServiceIm,获取子类用户={}信息,",instanceReturnMoneySignalDTO.getSysUserId());

        //获取获得返利人信息
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setId(parentUserId);
        List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);

        String incomeId = UUID.randomUUID().toString();
        incomeRecordDTO.setId(incomeId);
        incomeRecordDTO.setSysUserId(parentUserId);
        if(null != userInfoDTOList){
            incomeRecordDTO.setUserType(userInfoDTOList.get(0).getUserType());
            incomeRecordDTO.setIdentifyNumber(userInfoDTOList.get(0).getIdentifyNumber());
            incomeRecordDTO.setNickName(URLEncoder.encode(userInfoDTOList.get(0).getNickname(), "utf-8"));
            incomeRecordDTO.setMobile(userInfoDTOList.get(0).getMobile());
        }
        if(null != nextUserInfoDTOList){
            incomeRecordDTO.setNextUserId(nextUserInfoDTOList.get(0).getId());
            incomeRecordDTO.setNextUserType(nextUserInfoDTOList.get(0).getUserType());
            incomeRecordDTO.setNextUserIdentifyNumber(nextUserInfoDTOList.get(0).getIdentifyNumber());
            incomeRecordDTO.setNextUserNickName(URLEncoder.encode(nextUserInfoDTOList.get(0).getNickname(), "utf-8"));
            incomeRecordDTO.setNextUserMobile(nextUserInfoDTOList.get(0).getMobile());
        }
        incomeRecordDTO.setAmount(returnMoney);
        incomeRecordDTO.setTransactionAmount(expenseAmount);
        incomeRecordDTO.setTransactionId(instanceReturnMoneySignalDTO.getTransactionId());
        incomeRecordDTO.setUpdateDate(new Date());
        incomeRecordDTO.setCreateDate(new Date());
        incomeRecordDTO.setStatus("0");
        incomeRecordDTO.setIncomeType(incomeType);
        incomeRecordDTO.setParentRelation(parentRuleType);

        incomeService.insertUserIncomeInfo(incomeRecordDTO);
    }


    /**
     * 更新用户账户金额
     * @param parentUserId returnMoney
     *
     * */
    public AccountDTO updateUserAccount(AccountDTO accountDTO,String parentUserId,Float returnMoney)throws Exception{
        logger.info("service -- updateUserAccount,更新用户账户金额,参数parentUserId={},returnMoney={}",parentUserId,returnMoney);

        List<AccountDTO> accountDTOS = accountService.getUserAccountInfo(accountDTO);
        if (accountDTOS.size() == 0) {
            logger.info("service -- updateUserAccount,更新用户账户金额,用户没有账户,为用户创建账户");

            //为用户创建一个账户
            accountDTO = new AccountDTO();
            accountDTO.setId(UUID.randomUUID().toString());
            accountDTO.setSysUserId(parentUserId);

            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId(parentUserId);
            List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);
            accountDTO.setUserOpenId(userInfoDTOList.get(0).getUserOpenid());

            accountDTO.setBalance((float) 0.00);
            accountDTO.setScore((float) 0.00);
            accountDTO.setStatus("normal");
            accountDTO.setUpdateDate(new Date());
            accountDTO.setBalanceDeny((float) 0.00);
            accountService.createUserAccount(accountDTO);

            float balance = accountDTO.getBalance() + returnMoney;
            float balanceDeny = accountDTO.getBalanceDeny() + returnMoney;
            logger.info("service -- updateUserAccount,更新用户账户金额,balance={},balanceDeny={}",balance,balanceDeny);
            accountDTO.setBalance(balance);
            accountDTO.setBalanceDeny(balanceDeny);
            accountDTO.setUpdateDate(new Date());
            accountService.updateUserAccountInfo(accountDTO);
        } else {
            accountDTO = accountDTOS.get(0);
            float balance = accountDTO.getBalance() + returnMoney;
            float balanceDeny = accountDTO.getBalanceDeny() + returnMoney;
            logger.info("service -- updateUserAccount,更新用户账户金额,balance={},balanceDeny={}",balance,balanceDeny);
            accountDTO.setBalance(balance);
            accountDTO.setBalanceDeny(balanceDeny);
            accountDTO.setUpdateDate(new Date());
            accountService.updateUserAccountInfo(accountDTO);
        }
        return accountDTO;
    }

    /**
     * 插入具体是哪个交易流水升级
     * */
    public void insertPromotionTransactionRelation(String promotionLevel,String sysUserId,String transactionId)throws  Exception{
        logger.info("service -- 插入具体是哪个交易流水升级,insertPromotionTransactionRelation方法执行,");
        PromotionTransactionRelation promotionTransactionRelation = new PromotionTransactionRelation();
        promotionTransactionRelation.setPromotionLevelId(UUID.randomUUID().toString());
        promotionTransactionRelation.setPromotionLevel(promotionLevel);
        promotionTransactionRelation.setSysUserId(sysUserId);
        promotionTransactionRelation.setTransactionId(transactionId);
        promotionTransactionRelation.setPromotionLevelTime(new Date());
        promotionTransactionRelationMapper.insertSelective(promotionTransactionRelation);

    }

    //对用户的级别进行解冻处理
    public void deFrozenUserType(UserInfoDTO userInfoDTO) {
        UserBusinessTypeDTO userBusinessTypeDTO = new UserBusinessTypeDTO();
        userBusinessTypeDTO.setSysUserId(userInfoDTO.getId());
        userBusinessTypeDTO.setStatus("2");
        List<UserBusinessTypeDTO> userBusinessTypeDTOS =  userTypeMapper.getUserBusinessType(userBusinessTypeDTO);
        if(userBusinessTypeDTOS.size()>0)
        {
            userBusinessTypeDTO = userBusinessTypeDTOS.get(0);
            userBusinessTypeDTO.setStatus("1");
            userTypeMapper.updateUserBusinessType(userBusinessTypeDTO);
        }

    }

    //计算用户在某笔交易中的消费总金额
    public float calculateUserExpenseMoney(InstanceReturnMoneySignalDTO instanceReturnMoneySignalDTO) {
        //判断此笔交易可否提升用户等级
        PayRecordDTO payRecordDTO = new PayRecordDTO();
        payRecordDTO.setStatus("1");
        payRecordDTO.setTransactionId(instanceReturnMoneySignalDTO.getTransactionId());
        List<PayRecordDTO> payRecordDTOList = payRecordService.getUserPayRecordList(payRecordDTO);
        float expenseMoney = 0;
        for(PayRecordDTO payRecord : payRecordDTOList)
        {
            expenseMoney = expenseMoney + payRecord.getAmount();
        }
        return expenseMoney;
    }

}


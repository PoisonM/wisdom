package com.wisdom.business.service.transaction;

import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.mapper.level.UserTypeMapper;
import com.wisdom.business.mapper.transaction.PromotionTransactionRelationMapper;
import com.wisdom.business.service.account.AccountService;
import com.wisdom.business.service.account.IncomeService;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.dto.transaction.*;
import com.wisdom.common.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.wisdom.common.constant.ConfigConstant.RECOMMEND_PROMOTE_A1_REWARD;

/**
 * Created by sunxiao on 2017/6/26.
 */

@Service
public class PayCoreService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PayRecordService payRecordService;

    @Autowired
    private PayFunction payFunction;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private AccountService accountService;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private UserTypeMapper userTypeMapper;


    Logger logger = LoggerFactory.getLogger(PayCoreService.class);

    private static ExecutorService threadExecutorSingle = Executors.newSingleThreadExecutor();

    @Transactional(rollbackFor = Exception.class)
    public void handleProductPayNotifyInfo(PayRecordDTO payRecordDTO,String notifyType) {

        logger.info("处理微信平台推送的支付成功消息=="+payRecordDTO);

        RedisLock redisLock = new RedisLock("userPay" + payRecordDTO.getOutTradeNo());

        try {
            redisLock.lock();

            List<PayRecordDTO> payRecordDTOList = payRecordService.getUserPayRecordList(payRecordDTO);

            payFunction.processPayStatus(payRecordDTOList);

            if(notifyType.equals("offline"))
            {
                //实时返现不跟交易放在一起，在此设置一个信号灯
                payRecordDTO = payRecordDTOList.get(0);
                InstanceReturnMoneySignalDTO instanceReturnMoneySignalDTO = new InstanceReturnMoneySignalDTO();
                instanceReturnMoneySignalDTO.setSysUserId(payRecordDTO.getSysUserId());
                instanceReturnMoneySignalDTO.setOutTradeNo(payRecordDTO.getOutTradeNo());
                instanceReturnMoneySignalDTO.setTransactionId(payRecordDTO.getTransactionId());
                instanceReturnMoneySignalDTO.setStatus("0");
                mongoTemplate.insert(instanceReturnMoneySignalDTO,"instanceReturnMoneySignal");

                //开启一个线程，进行即时返现和提升处理
                Runnable processInstancePayThread = new ProcessInstancePayThread(instanceReturnMoneySignalDTO);
                threadExecutorSingle.execute(processInstancePayThread);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            redisLock.unlock();
        }
    }

    //进入即时返现和提升处理线程
    private class ProcessInstancePayThread extends Thread {
        private InstanceReturnMoneySignalDTO instanceReturnMoneySignalDTO;

        public ProcessInstancePayThread(InstanceReturnMoneySignalDTO instanceReturnMoneySignalDTO) {
            this.instanceReturnMoneySignalDTO = instanceReturnMoneySignalDTO;
        }

        @Override
        public void run() {
            try
            {
                //todo 开启A、B店的规则，给不同的用户给与不同的金额入账，此处涉及到修改用户的account表和income表
                UserInfoDTO userInfoDTO = userServiceClient.getUserInfoFromUserId(instanceReturnMoneySignalDTO.getSysUserId());

                logger.info("根据支付交易，处理及时返利=="+instanceReturnMoneySignalDTO);
                handleInstanceReturnMoney(userInfoDTO,instanceReturnMoneySignalDTO);

                float expenseMoney = calculateUserExpenseMoney(instanceReturnMoneySignalDTO);
                logger.info("获取支付交易的消费金额=="+expenseMoney);

                logger.info("进行月度流水统计");
                if(userInfoDTO.getUserType().equals(ConfigConstant.businessA1)||userInfoDTO.getUserType().equals(ConfigConstant.businessB1))
                {
                    payFunction.recordMonthTransaction(userInfoDTO.getId(),instanceReturnMoneySignalDTO,expenseMoney,"self");
                }

                logger.info("根据消费金额处理用户的等级提升=="+userInfoDTO.getMobile());
                handleUserLevelPromotion(userInfoDTO,expenseMoney);

                logger.info("处理用户消费特殊商品后的等级提升=="+userInfoDTO.getMobile());
                //handleUserLevelPromotionInSpecialActivity(userInfoDTO,expenseMoney,instanceReturnMoneySignalDTO);
                //handleSpecialActivitySignal(userInfoDTO,instanceReturnMoneySignalDTO);

                //正常完成分账和升级处理后，关闭信号灯
                Query query = new Query().addCriteria(Criteria.where("status").is("0")).addCriteria(Criteria.where("transactionId").is(instanceReturnMoneySignalDTO.getTransactionId()));
                Update update = new Update();
                update.set("status","1");
                mongoTemplate.updateFirst(query,update,"instanceReturnMoneySignal");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    //计算用户在某笔交易中的消费总金额
    private float calculateUserExpenseMoney(InstanceReturnMoneySignalDTO instanceReturnMoneySignalDTO) {
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

    //处理红包领取显示信号量
    private void handleSpecialActivitySignal(UserInfoDTO userInfoDTO, InstanceReturnMoneySignalDTO instanceReturnMoneySignalDTO) {
        PayRecordDTO payRecordDTO = new PayRecordDTO();
        payRecordDTO.setTransactionId(instanceReturnMoneySignalDTO.getTransactionId());
        List<PayRecordDTO> payRecordDTOS = payRecordService.getUserPayRecordList(payRecordDTO);
        if(payRecordDTOS.size()>0)
        {
            for(PayRecordDTO payRecord:payRecordDTOS)
            {
                BusinessOrderDTO businessOrderDTO = transactionService.getBusinessOrderDetailInfoByOrderId(payRecord.getOrderId());
                if(ConfigConstant.promote_businessB1_ProductId_No1.equals(businessOrderDTO.getBusinessProductId()))
                {
                    String productId = ConfigConstant.promote_businessB1_ProductId_No1;
                    Query query = new Query(Criteria.where("userId").is(userInfoDTO.getId()))
                            .addCriteria(Criteria.where("productId").is(productId));
                    BonusFlagDTO bonusFlagDTO = mongoTemplate.findOne(query,BonusFlagDTO.class,"bonusFlag");

                    //todo 此处的作用在于bonusFlagDTO的bounsFlag已经为true的情况下，不再产生和更新此信号灯
                    if(bonusFlagDTO==null)
                    {
                        Calendar ca = Calendar.getInstance();
                        ca.add(Calendar.DATE, ConfigConstant.livingPeriodTripleMonth);
                        Date d = ca.getTime();

                        bonusFlagDTO = new BonusFlagDTO();
                        bonusFlagDTO.setUserId(userInfoDTO.getId());
                        bonusFlagDTO.setBonusEndDate(d);
                        bonusFlagDTO.setProductId(ConfigConstant.promote_businessB1_ProductId_No1);
                        bonusFlagDTO.setBonusFlag("true");
                        bonusFlagDTO.setMessageFlag("true");

                        mongoTemplate.insert(bonusFlagDTO,"bonusFlag");
                    }
                    else if(bonusFlagDTO.getBonusFlag().equals("false"))
                    {
                        Calendar ca = Calendar.getInstance();
                        ca.add(Calendar.DATE, ConfigConstant.livingPeriodTripleMonth);
                        Date d = ca.getTime();

                        Update update = new Update();
                        update.set("messageFlag","true");
                        update.set("bonusFlag","true");
                        update.set("bonusEndDate",d);
                        mongoTemplate.updateFirst(query,update,"bonusFlag");
                    }

                    query = new Query(Criteria.where("userId").is(userInfoDTO.getId()))
                            .addCriteria(Criteria.where("productId").is(productId));
                    TripleMonthBonusDTO tripleMonthBonusDTO = mongoTemplate.findOne(query,TripleMonthBonusDTO.class,"tripleMonthBonus");
                    if(tripleMonthBonusDTO==null)
                    {
                        tripleMonthBonusDTO = new TripleMonthBonusDTO();
                        tripleMonthBonusDTO.setUserId(userInfoDTO.getId());
                        tripleMonthBonusDTO.setLeftDay(String.valueOf(ConfigConstant.livingPeriodTripleMonth));
                        tripleMonthBonusDTO.setProductId(ConfigConstant.promote_businessB1_ProductId_No1);
                        mongoTemplate.insert(tripleMonthBonusDTO,"tripleMonthBonus");
                    }
                }
            }
        }
    }

    //处理用户购买特殊商品的等级提升
    private void handleUserLevelPromotionInSpecialActivity(UserInfoDTO userInfoDTO, float expenseMoney, InstanceReturnMoneySignalDTO instanceReturnMoneySignalDTO) {
        //处理特殊活动下的，用户提升等级
        //1、查询用户所有购买的特殊商品
        if(ConfigConstant.businessC1.equals(userInfoDTO.getUserType()))
        {
            PayRecordDTO payRecordDTO = new PayRecordDTO();
            payRecordDTO.setTransactionId(instanceReturnMoneySignalDTO.getTransactionId());
            List<PayRecordDTO> payRecordDTOS = payRecordService.getUserPayRecordList(payRecordDTO);
            for(PayRecordDTO payRecord:payRecordDTOS)
            {
                BusinessOrderDTO businessOrderDTO = transactionService.getBusinessOrderDetailInfoByOrderId(payRecord.getOrderId());
                if(businessOrderDTO.getBusinessProductId().equals(ConfigConstant.promote_businessB1_ProductId_No1))
                {
                    //只有三个月的有效期
                    payFunction.promoteUserBusinessTypeForExpenseSecond(userInfoDTO,ConfigConstant.businessB1,ConfigConstant.livingPeriodTripleMonth_B1);

                    //判断这个C用户的上一级是否是个3月周期的B用户，如果是，则给上一级50元奖励
                    String parentUserId = userInfoDTO.getParentUserId();

                    if(parentUserId!=null)
                    {
                        //判断父亲用户是否购买过活动商品，且还在30天有效期内
                        String productId = ConfigConstant.promote_businessB1_ProductId_No1;
                        Query query = new Query(Criteria.where("userId").is(parentUserId))
                                .addCriteria(Criteria.where("productId").is(productId));
                        BonusFlagDTO bonusFlagDTO = mongoTemplate.findOne(query,BonusFlagDTO.class,"bonusFlag");

                        AccountDTO accountDTO = new AccountDTO();
                        accountDTO.setSysUserId(parentUserId);
                        List<AccountDTO> accountDTOS = accountService.getUserAccountInfo(accountDTO);

                        if(bonusFlagDTO!=null&&accountDTOS.size()>0)
                        {
                            int leftDay = ConfigConstant.livingPeriodTripleMonth-differentDaysByMillisecond(new Date(),bonusFlagDTO.getBonusEndDate());
                            if(leftDay<ConfigConstant.livingPeriodTripleMonth)
                            {
                                accountDTO = accountDTOS.get(0);
                                float balance = accountDTO.getBalance() + ConfigConstant.nextB1TripleMonthReward;
                                accountDTO.setBalance(balance);
                                accountService.updateUserAccountInfo(accountDTO);

                                IncomeRecordDTO incomeRecordDTO = new IncomeRecordDTO();
                                incomeRecordDTO.setId(UUID.randomUUID().toString());
                                incomeRecordDTO.setSysUserId(parentUserId);
                                incomeRecordDTO.setAmount(ConfigConstant.nextB1TripleMonthReward);
                                incomeRecordDTO.setCreateDate(new Date());
                                incomeRecordDTO.setIncomeType("tripleMonth");
                                incomeRecordDTO.setUpdateDate(new Date());
                                incomeRecordDTO.setTransactionId(instanceReturnMoneySignalDTO.getTransactionId());
                                incomeRecordDTO.setStatus("0");
                                incomeService.insertUserIncomeInfo(incomeRecordDTO);
                            }
                        }
                    }
                }
            }
        }
    }

    private int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

    //根据消费提升用户等级
    private void handleUserLevelPromotion(UserInfoDTO userInfoDTO, float expenseMoney) {
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
                deFrozenUserType(userInfoDTO);
            }
        }
        else if(ConfigConstant.businessA1.equals(userInfoDTO.getUserType()))
        {
            if(expenseMoney>=ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE)
            {
                //判断用户是否为A1的冻结状态，如果冻结状态，则解冻
                deFrozenUserType(userInfoDTO);
            }
        }
    }

    //处理即时返现逻辑
    private void handleInstanceReturnMoney(UserInfoDTO userInfoDTO, InstanceReturnMoneySignalDTO instanceReturnMoneySignalDTO) {

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

    //对用户的级别进行解冻处理
    private void deFrozenUserType(UserInfoDTO userInfoDTO) {
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
}

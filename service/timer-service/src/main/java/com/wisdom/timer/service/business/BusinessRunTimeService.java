package com.wisdom.timer.service.business;

import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.IncomeRecordManagementDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.MonthTransactionRecordDTO;
import com.wisdom.common.dto.transaction.MonthlyIncomeSignalDTO;
import com.wisdom.common.util.*;
import com.wisdom.timer.client.BusinessServiceClient;
import com.wisdom.timer.client.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

import static com.wisdom.common.constant.ConfigConstant.RECOMMEND_PROMOTE_A1_REWARD;

@Service
public class BusinessRunTimeService {
    
    @Autowired
    private BusinessServiceClient businessServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Transactional(rollbackFor = Exception.class)
    public void autoConfirmReceiveProduct() {

        //先获取所有用户已经完成支付，但是没有确认收货的订单
        BusinessOrderDTO businessOrderDTO = new BusinessOrderDTO();
        businessOrderDTO.setStatus("1");
        businessOrderDTO.setType("offline");
        List<BusinessOrderDTO> businessOrderDTOList = businessServiceClient.getBusinessOrderList(businessOrderDTO);

        //已经发货，但是用户没有确认收货的订单
        businessOrderDTO.setStatus("4");
        List<BusinessOrderDTO> businessOrderDTOList1 = businessServiceClient.getBusinessOrderList(businessOrderDTO);

        businessOrderDTOList.addAll(businessOrderDTOList1);

        //遍历所有用户的订单支付时间，todo 考虑加一个锁，避免用户凌晨3点下单的时候，造成business_order表死锁
        for(BusinessOrderDTO businessOrder:businessOrderDTOList)
        {
            Date dt1 = businessOrder.getUpdateDate();
            //获取15天前的日期
            Date dt2 = new Date((new Date()).getTime() - (long) ConfigConstant.AUTO_CONFIRM_RECEIVE_PRODUCT_DAY * 24 * 60 * 60 * 1000);

            //用户在15天前已经支付
            if (dt2.getTime() > dt1.getTime())
            {
                RedisLock redisLock = new RedisLock("businessOrder"+businessOrder.getBusinessOrderId());
                try{
                    redisLock.lock();

                    String sendProductDate = DateUtils.DateToStr(businessOrder.getUpdateDate());
                    businessOrder.setStatus("2");
                    businessOrder.setUpdateDate(new Date());
                    businessServiceClient.updateBusinessOrder(businessOrder);
                    String autoReceiveProductDate = DateUtils.DateToStr(businessOrder.getUpdateDate());

                    String token = WeixinUtil.getUserToken();
                    String url = ConfigConstant.USER_WEB_URL + "orderManagement/1";

                    UserInfoDTO userInfoDTO = new UserInfoDTO();
                    userInfoDTO.setId(businessOrder.getSysUserId());
                    List<UserInfoDTO> userInfoDTOList =  userServiceClient.getUserInfo(userInfoDTO);
                    if(userInfoDTOList.size()>0)
                    {
                        WeixinTemplateMessageUtil.sendOrderConfirmReceiveTemplateWXMessage(businessOrder.getBusinessOrderId(), businessOrder.getBusinessProductName(),
                                sendProductDate,autoReceiveProductDate,token,url,userInfoDTOList.get(0).getUserOpenid());
                    }
                }
                catch (Exception e) {
                    throw e;
                } finally {
                    redisLock.unlock();
                }
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void autoProcessUserAccount() throws UnsupportedEncodingException {

        //查询用户消费的不可提现金额
        IncomeRecordDTO incomeRecordDTO = new IncomeRecordDTO();
        incomeRecordDTO.setStatus("0");
        //incomeRecordDTO.setIncomeType("instance");
        List<IncomeRecordDTO> incomeRecordDTOList = businessServiceClient.getUserIncomeRecordInfo(incomeRecordDTO);
        List<String> transactionIds = new ArrayList<>();

        //用户返现解冻
        this.deFrozenUserReturnMoney(incomeRecordDTOList,transactionIds);

        //同级推荐提升逻辑
        this.promoteUserBusinessTypeForRecommend();
    }

    @Transactional(rollbackFor = Exception.class)
    public void autoMonthlyIncomeCalc() throws UnsupportedEncodingException {

        //加入开关量，证明本月已经完成过月度提成了，不用再次计算
        Query query = new Query(Criteria.where("year").is(DateUtils.getYear())).addCriteria(Criteria.where("month").is(DateUtils.getMonth()));
        MonthlyIncomeSignalDTO monthlyIncomeSignalDTO = mongoTemplate.findOne(query,MonthlyIncomeSignalDTO.class,"monthlyIncomeSignal");
        if(monthlyIncomeSignalDTO==null)
        {
            monthlyIncomeSignalDTO = new MonthlyIncomeSignalDTO();
            monthlyIncomeSignalDTO.setYear(DateUtils.getYear());
            monthlyIncomeSignalDTO.setMonth(DateUtils.getMonth());
            monthlyIncomeSignalDTO.setOnTimeFinish("false");
            mongoTemplate.insert(monthlyIncomeSignalDTO,"monthlyIncomeSignal");

            this.monthlyIncomeCalc(ConfigConstant.businessA1);

            this.monthlyIncomeCalc(ConfigConstant.businessB1);

            //操作完毕后，关闭信号量
            Update update = new Update();
            update.set("onTimeFinish","true");
            mongoTemplate.updateFirst(query,update,"monthlyIncomeSignal");
        }
        else if(monthlyIncomeSignalDTO.getOnTimeFinish().equals("false"))
        {
            this.monthlyIncomeCalc(ConfigConstant.businessA1);

            this.monthlyIncomeCalc(ConfigConstant.businessB1);

            //操作完毕后，关闭信号量
            Update update = new Update();
            update.set("onTimeFinish","true");
            mongoTemplate.updateFirst(query,update,"monthlyIncomeSignal");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void autoCalculateUserType() throws UnsupportedEncodingException {
        this.frozenUserType(ConfigConstant.businessA1);
        this.frozenUserType(ConfigConstant.businessB1);
    }

    public void autoProcessNoPayRecordData() {

        Date dt1 = new Date((new Date()).getTime() - (long) ConfigConstant.AUTO_NOTIFY_PRODUCT_PAY * 24 * 60 * 60 * 1000);
        Date dt2 = new Date((new Date()).getTime() - (long) ConfigConstant.AUTO_DELETE_BUSINESS_ORDER * 24 * 60 * 60 * 1000);

        String token = WeixinUtil.getUserToken();

        //查询系统中所有未支付的订单
        BusinessOrderDTO businessOrderDTO = new BusinessOrderDTO();
        businessOrderDTO.setStatus("0");
        List<BusinessOrderDTO> businessOrderDTOList = businessServiceClient.getBusinessOrderList(businessOrderDTO);
        if(businessOrderDTOList.size()>0)
        {
            for(BusinessOrderDTO businessOrder : businessOrderDTOList)
            {
                if((businessOrder.getCreateDate().getTime()-dt1.getTime())<0)
                {

                    UserInfoDTO userInfoDTO = new UserInfoDTO();
                    userInfoDTO.setId(businessOrder.getSysUserId());
                    userInfoDTO.setDelFlag("0");
                    List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);
                    if(userInfoDTOList.size()>0)
                    {
                        String url = ConfigConstant.USER_WEB_URL + "orderManagement/0";
                        WeixinTemplateMessageUtil.sendOrderNotPayTemplateWXMessage(DateUtils.DateToStr(businessOrder.getCreateDate()),
                                businessOrder.getBusinessOrderId(),token,url,userInfoDTOList.get(0).getUserOpenid());
                    }
                }
                else if((businessOrder.getCreateDate().getTime()-dt2.getTime())<0)
                {
                    businessOrder.setStatus("del");
                    businessServiceClient.updateBusinessOrder(businessOrder);
                    PayRecordDTO payRecordDTO = new PayRecordDTO();
                    payRecordDTO.setSysUserId(businessOrder.getSysUserId());
                    payRecordDTO.setOrderId(businessOrder.getBusinessOrderId());
                    List<PayRecordDTO> payRecordDTOList = businessServiceClient.getPayRecordInfo(payRecordDTO);
                    if(payRecordDTOList.size()>0)
                    {
                        payRecordDTO = payRecordDTOList.get(0);
                        payRecordDTO.setStatus("2");
                        businessServiceClient.updatePayRecordStatus(payRecordDTO);
                    }
                }
            }
        }
    }

    public void frozenUserType(String userType) throws UnsupportedEncodingException {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserType(userType);
        userInfoDTO.setDelFlag("0");
        List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);
        for(UserInfoDTO userInfo : userInfoDTOList)
        {
            UserBusinessTypeDTO userBusinessTypeDTO = new UserBusinessTypeDTO();
            userBusinessTypeDTO.setSysUserId(userInfo.getId());
            userBusinessTypeDTO.setStatus("1");
            List<UserBusinessTypeDTO> userBusinessTypeDTOS = businessServiceClient.getUserBusinessType(userBusinessTypeDTO);

            if(userBusinessTypeDTOS.size()==0)
            {
                //为用户创建一个记录
                userBusinessTypeDTO = new UserBusinessTypeDTO();
                userBusinessTypeDTO.setId(UUID.randomUUID().toString());
                userBusinessTypeDTO.setSysUserId(userInfo.getId());
                userBusinessTypeDTO.setStatus("1");
                userBusinessTypeDTO.setParentUserId(userInfo.getParentUserId());
                userBusinessTypeDTO.setUserType(userInfo.getUserType());
                userBusinessTypeDTO.setCreateDate(new Date());
                businessServiceClient.insertUserBusinessType(userBusinessTypeDTO);
            }
            else
            {
                userBusinessTypeDTO = userBusinessTypeDTOS.get(0);
            }

            Date dt1 = userBusinessTypeDTO.getCreateDate();
            Date dt2 = new Date((new Date()).getTime() - (long) userBusinessTypeDTO.getLivingPeriod() * 24 * 60 * 60 * 1000);
            Date dt3 = new Date((new Date()).getTime() - (long) (userBusinessTypeDTO.getLivingPeriod()-3) * 24 * 60 * 60 * 1000);

            //用户在365天前已经是目前的等级了
            if (dt2.getTime() > dt1.getTime())
            {
                userBusinessTypeDTO.setStatus("2");//2表示为冻结状态
                businessServiceClient.updateUserBusinessType(userBusinessTypeDTO);
            }

            if(dt3.getTime()> dt1.getTime())
            {
                String name = URLDecoder.decode(userInfo.getNickname(),"utf-8");
                String expDate = DateUtils.DateToStr(dt2);
                String token = WeixinUtil.getUserToken();
                String openid = userInfo.getUserOpenid();
                String url = ConfigConstant.USER_WEB_URL + "myselfCenter";
                WeixinTemplateMessageUtil.sendBusinessMemberDeadlineTemplateWXMessage(name,expDate,token,url,openid);
            }
        }

    }

    public void monthlyIncomeCalc(String businessType) throws UnsupportedEncodingException {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserType(businessType);
        userInfoDTO.setDelFlag("0");
        List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);
        String token = WeixinUtil.getUserToken();
        for(UserInfoDTO userInfo:userInfoDTOList)
        {
            float returnMonthlyMoney = 0;
            float returnMonthlyMoney_A = 0;
            float returnMonthlyMoney_B = 0;

            String startDate = "";
            String endDate = DateUtils.getYear()+"-" + DateUtils.getMonth()+"-"+"26";
            if(DateUtils.getMonth().equals("01"))
            {
                int month = 11;
                int year = Integer.parseInt(DateUtils.getYear()) - 1;
                startDate = year + "-" + month + "-26";
            }
            else if(DateUtils.getMonth().equals("02"))
            {
                int month = 12;
                int year = Integer.parseInt(DateUtils.getYear()) - 1;
                startDate = year + "-" + month + "-26";
            }
            else{
                int month = Integer.parseInt(DateUtils.getMonth()) - 2;
                startDate = DateUtils.getYear() + "-" + month + "-26";
            }

            List<MonthTransactionRecordDTO> monthTransactionRecordDTOList =  businessServiceClient.getMonthTransactionRecordByUserId(userInfo.getId(),startDate,endDate);

            for(MonthTransactionRecordDTO monthTransactionRecordDTO:monthTransactionRecordDTOList)
            {
                if(monthTransactionRecordDTO.getUserType().equals(ConfigConstant.businessA1))
                {
                    returnMonthlyMoney_A = returnMonthlyMoney_A + monthTransactionRecordDTO.getAmount();
                }
                else if(monthTransactionRecordDTO.getUserType().equals(ConfigConstant.businessB1))
                {
                    returnMonthlyMoney_B = returnMonthlyMoney_B + monthTransactionRecordDTO.getAmount();
                }
            }

            //计算当前用户本月的消费金额
//            List<PayRecordDTO> payRecordDTOList = businessServiceClient.getUserPayRecordListByDate(userInfo.getId(),startDate,endDate);
//            float userExpenseAmount = 0;
//            for(PayRecordDTO payRecordDTO:payRecordDTOList)
//            {
//                if(payRecordDTO.getStatus().equals("1"))
//                {
//                    userExpenseAmount = userExpenseAmount + payRecordDTO.getAmount();
//                }
//            }

//            if((businessType.equals(ConfigConstant.businessA1)&&userExpenseAmount>=ConfigConstant.MONTH_A_INCOME_MAX_EXPENSE)
//                    ||(businessType.equals(ConfigConstant.businessB1)&&userExpenseAmount>=ConfigConstant.MONTH_B1_INCOME_MAX_EXPENSE))
//            {
//                returnMonthlyMoney = returnMonthlyMoney + userExpenseAmount;
                if(returnMonthlyMoney_A>0||returnMonthlyMoney_B>0)
                {
//                    if(businessType.equals(ConfigConstant.businessA1))
//                    {
//                        returnMonthlyMoney = returnMonthlyMoney * ConfigConstant.MONTH_A_INCOME_PERCENTAGE/100;
//                    }
//                    else if(businessType.equals(ConfigConstant.businessB1))
//                    {
//                        returnMonthlyMoney = returnMonthlyMoney * ConfigConstant.MONTH_B1_INCOME_PERCENTAGE/100;
//                    }

                    returnMonthlyMoney = returnMonthlyMoney_A*ConfigConstant.MONTH_A_INCOME_PERCENTAGE/100 + returnMonthlyMoney_B*ConfigConstant.MONTH_B1_INCOME_PERCENTAGE/100;

                    AccountDTO accountDTO = businessServiceClient.getUserAccountInfo(userInfo.getId());
                    float balance = accountDTO.getBalance() + returnMonthlyMoney;
                    float balanceDeny = accountDTO.getBalanceDeny() + returnMonthlyMoney;
                    accountDTO.setBalance(balance);
                    accountDTO.setBalanceDeny(balanceDeny);
                    accountDTO.setUpdateDate(new Date());
                    businessServiceClient.updateUserAccountInfo(accountDTO);

                    IncomeRecordDTO incomeRecordDTO = new IncomeRecordDTO();
                    incomeRecordDTO.setId(UUID.randomUUID().toString());
                    incomeRecordDTO.setSysUserId(userInfo.getId());
                    incomeRecordDTO.setUserType(userInfo.getUserType());
                    incomeRecordDTO.setNextUserId("");
                    incomeRecordDTO.setNextUserType("");
                    incomeRecordDTO.setAmount(returnMonthlyMoney);
                    incomeRecordDTO.setTransactionAmount(0);
                    incomeRecordDTO.setTransactionId(CodeGenUtil.getTransactionCodeNumber());
                    incomeRecordDTO.setUpdateDate(new Date());
                    incomeRecordDTO.setCreateDate(new Date());
                    incomeRecordDTO.setStatus("0");
                    incomeRecordDTO.setIdentifyNumber(userInfo.getIdentifyNumber());
                    incomeRecordDTO.setNextUserIdentifyNumber("");
                    incomeRecordDTO.setNickName(URLEncoder.encode(userInfo.getNickname(), "utf-8"));
                    incomeRecordDTO.setNextUserNickName("");
                    incomeRecordDTO.setIncomeType("month");
                    incomeRecordDTO.setMobile(userInfo.getMobile());
                    incomeRecordDTO.setNextUserMobile("");
                    incomeRecordDTO.setParentRelation("");
                    businessServiceClient.insertUserIncomeInfo(incomeRecordDTO);
                }
//            }

//            String url = ConfigConstant.USER_WEB_URL + "orderManagement/1";
            WeixinTemplateMessageUtil.sendMonthIncomeTemplateWXMessage(userInfo.getId(),returnMonthlyMoney+"",DateUtils.DateToStr(new Date()),token,"",userInfo.getUserOpenid());
        }
    }

    public void promoteUserBusinessTypeForRecommend() throws UnsupportedEncodingException {
        //根据B用户推荐20个B的逻辑，来实现用户等级提升
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setDelFlag("0");
        userInfoDTO.setUserType(ConfigConstant.businessB1);
        List<UserInfoDTO> userInfoDTOS = userServiceClient.getUserInfo(userInfoDTO);
        String token = WeixinUtil.getUserToken();
        for(UserInfoDTO userInfo : userInfoDTOS)
        {
            //查询所有下一级的情况
            UserInfoDTO nextUserInfoDTO = new UserInfoDTO();
            nextUserInfoDTO.setParentUserId(userInfo.getId());
            List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(nextUserInfoDTO);

            int recommendBNum = 0;
            int recommendANum = 0;
            boolean promoteAFlag = false;
            for(UserInfoDTO user:userInfoDTOList)
            {
                //先确保下线用户的状态没有被冻结，2的话被冻结了
                UserBusinessTypeDTO userBusinessTypeDTO = new UserBusinessTypeDTO();
                userBusinessTypeDTO.setStatus("1");
                userBusinessTypeDTO.setSysUserId(user.getId());
                List<UserBusinessTypeDTO> userBusinessTypeDTOS = businessServiceClient.getUserBusinessType(userBusinessTypeDTO);

                if(userBusinessTypeDTOS.size()>0)
                {
                    if(user.getUserType().equals(ConfigConstant.businessA1))
                    {
                        recommendANum = recommendANum + 1;
                    }else if(user.getUserType().equals(ConfigConstant.businessB1))
                    {
                        recommendBNum = recommendBNum + 1;
                    }
                }
            }

            if((recommendBNum+recommendANum)>=20)
            {
                promoteAFlag = true;
            }

            if(promoteAFlag)
            {
                //更新user_business_type表的数据
                //1、把老级别变为失效
                UserBusinessTypeDTO userBusinessTypeDTO = new UserBusinessTypeDTO();
                userBusinessTypeDTO.setSysUserId(userInfo.getId());
                userBusinessTypeDTO.setStatus("1");
                List<UserBusinessTypeDTO> userBusinessTypeDTOS = businessServiceClient.getUserBusinessType(userBusinessTypeDTO);
                if(userBusinessTypeDTOS.size()==0)
                {
                    return;
                }
                userBusinessTypeDTO = userBusinessTypeDTOS.get(0);
                userBusinessTypeDTO.setStatus("0");
                businessServiceClient.updateUserBusinessType(userBusinessTypeDTO);

                //2、级别更新创建新的记录
                userBusinessTypeDTO = new UserBusinessTypeDTO();
                userBusinessTypeDTO.setId(UUID.randomUUID().toString());
                userBusinessTypeDTO.setParentUserId(userInfo.getParentUserId());
                userBusinessTypeDTO.setSysUserId(userInfo.getId());
                userBusinessTypeDTO.setUserType(ConfigConstant.businessA1);
                userBusinessTypeDTO.setStatus("1");
                userBusinessTypeDTO.setLivingPeriod(ConfigConstant.livingPeriodYear);
                userBusinessTypeDTO.setCreateDate(new Date());
                businessServiceClient.insertUserBusinessType(userBusinessTypeDTO);

                //sys_user表也需要更新
                userInfo.setUserType(ConfigConstant.businessA1);
                userServiceClient.updateUserInfo(userInfo);

                //给用户495的即时奖励
                AccountDTO accountDTO = businessServiceClient.getUserAccountInfo(userInfo.getId());
                float balance  = accountDTO.getBalance() + RECOMMEND_PROMOTE_A1_REWARD;
                accountDTO.setBalance(balance);
                businessServiceClient.updateUserAccountInfo(accountDTO);

                IncomeRecordDTO incomeRecordDTO = new IncomeRecordDTO();
                incomeRecordDTO.setId(UUID.randomUUID().toString());
                incomeRecordDTO.setSysUserId(userInfo.getId());
                incomeRecordDTO.setUserType(userInfo.getUserType());
                incomeRecordDTO.setNextUserId("");
                incomeRecordDTO.setNextUserType("");
                incomeRecordDTO.setAmount(RECOMMEND_PROMOTE_A1_REWARD);
                incomeRecordDTO.setTransactionAmount(0);
                incomeRecordDTO.setTransactionId(CodeGenUtil.getTransactionCodeNumber());
                incomeRecordDTO.setUpdateDate(new Date());
                incomeRecordDTO.setCreateDate(new Date());
                incomeRecordDTO.setStatus("0");
                incomeRecordDTO.setIdentifyNumber(userInfo.getIdentifyNumber());
                incomeRecordDTO.setNextUserIdentifyNumber("");
                incomeRecordDTO.setNickName(URLEncoder.encode(userInfo.getNickname(), "utf-8"));
                incomeRecordDTO.setNextUserNickName("");
                incomeRecordDTO.setIncomeType("recommend");
                incomeRecordDTO.setMobile(userInfo.getMobile());
                incomeRecordDTO.setNextUserMobile("");
                incomeRecordDTO.setParentRelation("");

                Calendar calendar = Calendar.getInstance();
                Date date = new Date();
                calendar.setTime(date);
                calendar.add(Calendar.YEAR, 1);
                date = calendar.getTime();
                WeixinTemplateMessageUtil.sendBusinessPromoteForRecommendTemplateWXMessage(userInfo.getNickname(),DateUtils.DateToStr(date),token, "", userInfo.getUserOpenid());
            }
        }
    }

    public void deFrozenUserReturnMoney(List<IncomeRecordDTO> incomeRecordDTOList, List<String> transactionIds) {
        for(IncomeRecordDTO incomeRecord : incomeRecordDTOList)
        {
            if(!transactionIds.contains(incomeRecord.getTransactionId()))
            {
                transactionIds.add(incomeRecord.getTransactionId());
            }

            boolean operationFlag = true;

            //判断incomeRecord记录中的这个用户的门店是不是处于冻结状态
            UserBusinessTypeDTO userBusinessTypeDTO = new UserBusinessTypeDTO();
            userBusinessTypeDTO.setSysUserId(incomeRecord.getSysUserId());
            userBusinessTypeDTO.setStatus("2");
            List<UserBusinessTypeDTO> userBusinessTypeDTOS = businessServiceClient.getUserBusinessType(userBusinessTypeDTO);
            if(userBusinessTypeDTOS.size()>0)
            {
                break;
            }

            //根据transactionId查询orderId，根据orderId查询business_order
            PayRecordDTO payRecordDTO = new PayRecordDTO();
            payRecordDTO.setTransactionId(incomeRecord.getTransactionId());
            List<PayRecordDTO> payRecordDTOList =  businessServiceClient.getUserPayRecordList(payRecordDTO);

            if(payRecordDTOList.size()==0)
            {
                break;
            }

            //判断某笔交易下订单是否都已经收货
            for(PayRecordDTO payRecord : payRecordDTOList)
            {
                BusinessOrderDTO businessOrderDTO = businessServiceClient.getBusinessOrderByOrderId(payRecord.getOrderId());
                if(!businessOrderDTO.getStatus().equals("2")&&!businessOrderDTO.getStatus().equals("5"))//5代表已退货
                {
                    operationFlag = false;
                    break;
                }
            }

            //判断此记录，是否财务和运营人员，都已经审核通过
            IncomeRecordManagementDTO incomeRecordManagementDTO = new IncomeRecordManagementDTO();
            incomeRecordManagementDTO.setIncomeRecordId(incomeRecord.getId());
            List<IncomeRecordManagementDTO> incomeRecordManagementDTOList = businessServiceClient.getIncomeRecordManagement(incomeRecordManagementDTO);
            if(incomeRecordManagementDTOList.size()!=2)
            {
                operationFlag = false;
            }
            else
            {
                int incomeRecordManagementNum = 0;
                for(IncomeRecordManagementDTO incomeRecordManagement:incomeRecordManagementDTOList)
                {
                    if(incomeRecordManagement.getUserType().equals(ConfigConstant.financeMember))
                    {
                        incomeRecordManagementNum++;
                    }
                    if(incomeRecordManagement.getUserType().equals(ConfigConstant.operationMember))
                    {
                        incomeRecordManagementNum++;
                    }
                }
                if(incomeRecordManagementNum!=2)
                {
                    operationFlag = false;
                }
            }

            if(operationFlag)
            {
                //用户资金解冻和级别提升返现，和推荐奖励，以及月度返利的所有操作，加一把锁
                RedisLock redisLock = new RedisLock("userReturnMoneyDeLock"+incomeRecord.getSysUserId());
                try
                {
                    redisLock.lock();

                    //解冻用户的提成，先找出要解冻返现的用户账户，做资金解冻
                    float balanceDeny = incomeRecord.getAmount();
                    AccountDTO accountDTO = businessServiceClient.getUserAccountInfo(incomeRecord.getSysUserId());
                    balanceDeny = accountDTO.getBalanceDeny() - balanceDeny;
                    if(balanceDeny==0)
                    {
                        balanceDeny = balanceDeny + (float)0.0001;
                    }
                    accountDTO.setBalanceDeny(balanceDeny);
                    accountDTO.setUpdateDate(new Date());
                    businessServiceClient.updateUserAccountInfo(accountDTO);

                    incomeRecord.setStatus("1");
                    incomeRecord.setUpdateDate(new Date());
                    businessServiceClient.updateIncomeInfo(incomeRecord);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    throw e;
                }
                finally {
                    redisLock.unlock();
                }
            }
        }
    }

}

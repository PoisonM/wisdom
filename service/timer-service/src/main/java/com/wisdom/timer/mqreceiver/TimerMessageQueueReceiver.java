package com.wisdom.timer.mqreceiver;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.IncomeRecordManagementDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.*;
import com.wisdom.timer.client.BusinessServiceClient;
import com.wisdom.timer.client.UserServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

import static com.wisdom.common.constant.ConfigConstant.RECOMMEND_PROMOTE_A1_REWARD;

@Component
public class TimerMessageQueueReceiver {

    Logger logger = LoggerFactory.getLogger(TimerMessageQueueReceiver.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BusinessServiceClient businessServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

    private static Gson gson = new Gson();

    @RabbitListener(queues = "confirmReceiveProduct")
    @RabbitHandler
    public void processConfirmReceiveProduct(String confirmReceiveProduct){
        BusinessOrderDTO businessOrder = gson.fromJson(confirmReceiveProduct,BusinessOrderDTO.class);

        Date dt1 = businessOrder.getUpdateDate();
        //获取15天前的日期
        Date dt2 = new Date((new Date()).getTime() - (long) ConfigConstant.AUTO_CONFIRM_RECEIVE_PRODUCT_DAY * 24 * 60 * 60 * 1000);

        //用户在15天前已经支付
        if (dt2.getTime() > dt1.getTime())
        {
            RedisLock redisLock = new RedisLock("businessOrder"+businessOrder.getBusinessOrderId());
            try{
                redisLock.lock();
                logger.info("用户15天后，自动收货订单={}" ,businessOrder.getBusinessOrderId());

                String sendProductDate = DateUtils.DateToStr(businessOrder.getUpdateDate());
                businessOrder.setStatus("2");
                businessOrder.setUpdateDate(new Date());
                businessServiceClient.updateBusinessOrder(businessOrder);
                String autoReceiveProductDate = DateUtils.DateToStr(businessOrder.getUpdateDate());

                String token = WeixinUtil.getUserToken();
                String url = ConfigConstant.USER_BUSINESS_WEB_URL + "orderManagement/all";

                UserInfoDTO userInfoDTO = new UserInfoDTO();
                userInfoDTO.setId(businessOrder.getSysUserId());
                List<UserInfoDTO> userInfoDTOList =  userServiceClient.getUserInfo(userInfoDTO);
                if(userInfoDTOList.size()>0)
                {
                    BusinessOrderDTO businessOrderDTO = businessServiceClient.getProductInfoByOrderId(businessOrder.getOrderId());
                    WeixinTemplateMessageUtil.sendOrderConfirmReceiveTemplateWXMessage(businessOrder.getBusinessOrderId(), businessOrderDTO.getBusinessProductName(),
                            sendProductDate,autoReceiveProductDate,token,url,userInfoDTOList.get(0).getUserOpenid());
                    logger.info("用户15天后，自动收货发送消息,用户openid={}" ,userInfoDTOList.get(0).getUserOpenid());
                }
            }
            catch (Exception e) {
                logger.info("用户15天后，自动转为收到货物异常,异常信息为{}" +e.getMessage(),e);
                throw e;
            } finally {
                redisLock.unlock();
            }
        }
    }

    @RabbitListener(queues = "deFrozenUserReturnMoney")
    @RabbitHandler
    public void processDeFrozenUserReturnMoney(String deFrozenUserReturnMoney){

        IncomeRecordDTO incomeRecord = gson.fromJson(deFrozenUserReturnMoney,IncomeRecordDTO.class);
        boolean operationFlag = true;
        //判断incomeRecord记录中的这个用户的门店是不是处于冻结状态
        UserBusinessTypeDTO userBusinessTypeDTO = new UserBusinessTypeDTO();
        userBusinessTypeDTO.setSysUserId(incomeRecord.getSysUserId());
        userBusinessTypeDTO.setStatus("2");
        List<UserBusinessTypeDTO> userBusinessTypeDTOS = businessServiceClient.getUserBusinessType(userBusinessTypeDTO);
        if(userBusinessTypeDTOS.size()>0)
        {
            logger.info("incomeRecord记录中的这个用户={}的门店处于冻结状态" ,incomeRecord.getSysUserId());
        }
        else
        {
            //判断此记录，是否财务和运营人员，都已经审核通过
            IncomeRecordManagementDTO incomeRecordManagementDTO = new IncomeRecordManagementDTO();
            incomeRecordManagementDTO.setIncomeRecordId(incomeRecord.getId());
            List<IncomeRecordManagementDTO> incomeRecordManagementDTOList = businessServiceClient.getIncomeRecordManagement(incomeRecordManagementDTO);
            if(incomeRecordManagementDTOList.size()!=2)
            {
                logger.info("此记录={}，财务和运营人员，未审核通过" , incomeRecord.getId());
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
                    logger.info("此记录={}，财务和运营人员，未审核通过" , incomeRecord.getId());
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
                    logger.info("balanceDeny={}" , balanceDeny);
                    AccountDTO accountDTO = businessServiceClient.getUserAccountInfo(incomeRecord.getSysUserId());
                    balanceDeny = accountDTO.getBalanceDeny() - balanceDeny;
                    logger.info("balanceDeny={}" , balanceDeny);
                    if(balanceDeny==0)
                    {
                        balanceDeny = balanceDeny + (float)0.0001;
                    }
                    if(balanceDeny<0)
                    {
                        AccountDTO exceptionDTO = null;
                        exceptionDTO.getId();
                    }
                    logger.info("balanceDeny={}" , balanceDeny);
                    accountDTO.setBalanceDeny(balanceDeny);
                    accountDTO.setUpdateDate(new Date());
                    businessServiceClient.updateUserAccountInfo(accountDTO);

                    incomeRecord.setStatus("1");
                    incomeRecord.setUpdateDate(new Date());
                    businessServiceClient.updateIncomeInfo(incomeRecord);
                }catch (Exception e){
                    logger.info("解冻用户的提成，先找出要解冻返现的用户账户，做资金解冻异常,异常信息为={}" +e.getMessage(),e);
                    e.printStackTrace();
                    throw e;
                }
                finally {
                    redisLock.unlock();
                }
            }
        }
    }

    @RabbitListener(queues = "frozenUserType")
    @RabbitHandler
    public void processFrozenUserType(String sendFrozenUserType) {
        UserInfoDTO userInfo = gson.fromJson(sendFrozenUserType, UserInfoDTO.class);
        UserBusinessTypeDTO userBusinessTypeDTO = new UserBusinessTypeDTO();
        userBusinessTypeDTO.setSysUserId(userInfo.getId());
        userBusinessTypeDTO.setStatus("1");
        List<UserBusinessTypeDTO> userBusinessTypeDTOS = businessServiceClient.getUserBusinessType(userBusinessTypeDTO);

        if(userBusinessTypeDTOS.size()==0)
        {
            /*//为用户创建一个记录
            userBusinessTypeDTO = new UserBusinessTypeDTO();
            userBusinessTypeDTO.setId(UUID.randomUUID().toString());
            userBusinessTypeDTO.setSysUserId(userInfo.getId());
            userBusinessTypeDTO.setStatus("1");
            userBusinessTypeDTO.setParentUserId(userInfo.getParentUserId());
            userBusinessTypeDTO.setUserType(userInfo.getUserType());
            userBusinessTypeDTO.setCreateDate(new Date());
            businessServiceClient.insertUserBusinessType(userBusinessTypeDTO);*/
            return;
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
            logger.info("用户在365天前已经是目前的等级了,冻结此账户==={}开始",userBusinessTypeDTO.getSysUserId());
            userBusinessTypeDTO.setStatus("2");//2表示为冻结状态
            businessServiceClient.updateUserBusinessType(userBusinessTypeDTO);
        }

        if(dt3.getTime()> dt1.getTime())
        {
            String name = null;
            try {
                name = URLDecoder.decode(userInfo.getNickname(),"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String expDate = DateUtils.DateToStr(dt2);
            String token = WeixinUtil.getUserToken();
            String openid = userInfo.getUserOpenid();
            String url = ConfigConstant.USER_BUSINESS_WEB_URL + "myselfCenter";
            WeixinTemplateMessageUtil.sendBusinessMemberDeadlineTemplateWXMessage(name,expDate,token,url,openid);
            logger.info("发送用户会员快到期提醒模板");
        }
    }

    @RabbitListener(queues = "promoteUserBusinessTypeForRecommend")
    @RabbitHandler
    public void processPromoteUserBusinessTypeForRecommend(String promoteUserBusinessTypeForRecommend) throws UnsupportedEncodingException {
        UserInfoDTO userInfo = gson.fromJson(promoteUserBusinessTypeForRecommend,UserInfoDTO.class);
        String token = WeixinUtil.getUserToken();

        //查询所有下一级的情况
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
                }
                else if(user.getUserType().equals(ConfigConstant.businessB1))
                {
                    recommendBNum = recommendBNum + 1;
                }
            }
        }
        logger.info("推荐下级个数==={}" , recommendBNum);

        if((recommendBNum+recommendANum)>=ConfigConstant.RECOMMEND_USER_NUM_REWARD)
        {
            promoteAFlag = true;
        }

        if(promoteAFlag)
        {
            logger.info("把老级别变为失效==={}" , userInfo.getId());

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
            logger.info("级别更新创建新的记录SysUserId={}UserType={}LivingPeriod={}" , userInfo.getId(),ConfigConstant.businessA1,ConfigConstant.livingPeriodYear);
            businessServiceClient.insertUserBusinessType(userBusinessTypeDTO);

            //sys_user表也需要更新
            logger.info("sys_user表也需要更新==={}" , userInfo.getId());
            userInfo.setUserType(ConfigConstant.businessA1);
            userInfo.setNickname(URLEncoder.encode(userInfo.getNickname(), "utf-8"));
            userServiceClient.updateUserInfo(userInfo);

            //给B的上一級用户495的即时奖励
            if(StringUtils.isNotNull(userInfo.getParentUserId()))
            {
                logger.info("给B的上一級用户={}495的即时奖励" ,userInfo.getParentUserId());
                UserInfoDTO parentUserInfoDTO = new UserInfoDTO();
                parentUserInfoDTO.setId(userInfo.getParentUserId());
                List<UserInfoDTO> parentUserInfoList = userServiceClient.getUserInfo(parentUserInfoDTO);

                if(parentUserInfoList.size()>0)
                {
                    parentUserInfoDTO = parentUserInfoList.get(0);

                    if(ConfigConstant.businessA1.equals(parentUserInfoDTO.getUserType()))
                    {
                        AccountDTO accountDTO = businessServiceClient.getUserAccountInfo(parentUserInfoDTO.getId());
                        float balance  = accountDTO.getBalance() + RECOMMEND_PROMOTE_A1_REWARD;
                        float balanceDeny  = accountDTO.getBalanceDeny() + RECOMMEND_PROMOTE_A1_REWARD;
                        accountDTO.setBalance(balance);
                        accountDTO.setBalanceDeny(balanceDeny);
                        businessServiceClient.updateUserAccountInfo(accountDTO);

                        IncomeRecordDTO incomeRecordDTO = new IncomeRecordDTO();
                        incomeRecordDTO.setId(UUID.randomUUID().toString());
                        incomeRecordDTO.setSysUserId(parentUserInfoDTO.getId());
                        incomeRecordDTO.setUserType(parentUserInfoDTO.getUserType());
                        incomeRecordDTO.setNextUserId(userInfo.getId());
                        incomeRecordDTO.setNextUserType(userInfo.getUserType());
                        incomeRecordDTO.setAmount(RECOMMEND_PROMOTE_A1_REWARD);
                        incomeRecordDTO.setTransactionAmount(0);
                        incomeRecordDTO.setTransactionId(CodeGenUtil.getTransactionCodeNumber());
                        incomeRecordDTO.setUpdateDate(new Date());
                        incomeRecordDTO.setCreateDate(new Date());
                        incomeRecordDTO.setStatus("0");
                        incomeRecordDTO.setIdentifyNumber(parentUserInfoDTO.getIdentifyNumber());
                        incomeRecordDTO.setNextUserIdentifyNumber(userInfo.getIdentifyNumber());
                        incomeRecordDTO.setNickName(URLEncoder.encode(parentUserInfoDTO.getNickname(), "utf-8"));
                        incomeRecordDTO.setNextUserNickName(URLEncoder.encode(userInfo.getNickname(), "utf-8"));
                        incomeRecordDTO.setIncomeType("recommend");
                        incomeRecordDTO.setMobile(parentUserInfoDTO.getMobile());
                        incomeRecordDTO.setNextUserMobile(userInfo.getMobile());
                        incomeRecordDTO.setParentRelation(ConfigConstant.businessA1);
                        businessServiceClient.insertUserIncomeInfo(incomeRecordDTO);
                    }
                }
            }
            Calendar calendar = Calendar.getInstance();
            Date date = new Date();
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, 1);
            date = calendar.getTime();
            WeixinTemplateMessageUtil.sendBusinessPromoteForRecommendTemplateWXMessage(CommonUtils.nameDecoder(userInfo.getNickname()),DateUtils.DateToStr(date),token, "", userInfo.getUserOpenid());
        }
    }

}

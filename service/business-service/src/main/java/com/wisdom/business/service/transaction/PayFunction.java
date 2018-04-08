package com.wisdom.business.service.transaction;

import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.mapper.level.UserTypeMapper;
import com.wisdom.business.mapper.transaction.TransactionMapper;
import com.wisdom.business.service.account.AccountService;
import com.wisdom.business.service.account.IncomeService;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.util.WeixinTemplateMessageUtil;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import com.wisdom.common.dto.system.UserInfoDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.InstanceReturnMoneySignalDTO;
import com.wisdom.common.dto.transaction.MonthTransactionRecordDTO;
import com.wisdom.common.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PayFunction {

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

    @Transactional(rollbackFor = Exception.class)
    public void processPayStatus(List<PayRecordDTO> payRecordDTOList) {
        try
        {
            float totalMoney = 0;
            String productName = "";
            String token = WeixinUtil.getUserToken();
            String url = ConfigConstant.USER_WEB_URL + "orderManagement/1";
            String userId = "";
            for(PayRecordDTO payRecordDTO:payRecordDTOList)
            {
                //修改payRecord的订单状态，表示已支付
                payRecordDTO.setStatus("1");
                payRecordDTO.setUpdateDate(new Date());
                payRecordService.updatePayRecordStatus(payRecordDTO);

                totalMoney = totalMoney + payRecordDTO.getAmount();

                //修改business_order的状态，表示已支付
                BusinessOrderDTO businessOrderDTO = transactionService.getBusinessOrderDetailInfoByOrderId(payRecordDTO.getOrderId());
                businessOrderDTO.setStatus("1");
                transactionService.updateBusinessOrderStatus(businessOrderDTO);

                //修改商品库存
//                ProductDTO productDTO = new ProductDTO();
//                productDTO.setProductId(businessOrderDTO.getBusinessProductId());
//                productDTO.setProductAmount(businessOrderDTO.getBusinessProductNum());
//                transactionService.updateOfflineProductAmount(productDTO);

                productName = productName + businessOrderDTO.getBusinessProductName() +
                        "(" + businessOrderDTO.getProductSpec() + ")" + (int)businessOrderDTO.getBusinessProductNum() +"套"+";";
                userId = businessOrderDTO.getSysUserId();
            }

            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId(userId);
            userInfoDTO.setDelFlag("0");
            List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);
            if(userInfoDTOList.size()>0)
            {
                userInfoDTO = userInfoDTOList.get(0);
            }
            WeixinTemplateMessageUtil.sendOrderPaySuccessTemplateWXMessage((int)totalMoney+"元",productName,token,url,userInfoDTO.getUserOpenid());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void promoteUserBusinessTypeForExpenseSecond(UserInfoDTO userInfoDTO, String businessType, int livingPeriod) {

        //sys_user表也需要更新
        userInfoDTO.setUserType(businessType);
        userServiceClient.updateUserInfo(userInfoDTO);

        //更新user_business_type表的数据
        //1、把老级别变为失效
        UserBusinessTypeDTO userBusinessTypeDTO = new UserBusinessTypeDTO();
        userBusinessTypeDTO.setSysUserId(userInfoDTO.getId());
        userBusinessTypeDTO.setStatus("1");
        List<UserBusinessTypeDTO> userBusinessTypeDTOS = userTypeMapper.getUserBusinessType(userBusinessTypeDTO);

        UserBusinessTypeDTO userBusinessTypeDTO1 = new UserBusinessTypeDTO();
        userBusinessTypeDTO1.setSysUserId(userInfoDTO.getId());
        userBusinessTypeDTO1.setStatus("2");
        List<UserBusinessTypeDTO> userBusinessTypeDTOS1 = userTypeMapper.getUserBusinessType(userBusinessTypeDTO);

        if(userBusinessTypeDTOS.size()>0||userBusinessTypeDTOS1.size()>0)
        {
            userBusinessTypeDTO = userBusinessTypeDTOS.get(0);
            userBusinessTypeDTO.setStatus("0");
            userTypeMapper.updateUserBusinessType(userBusinessTypeDTO);
        }

        //2、级别更新创建新的记录
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
        String token = WeixinUtil.getUserToken();
        try{

            //计算提成给上一级用户ruleType对应店的钱
            PayRecordDTO payRecordDTO = new PayRecordDTO();
            payRecordDTO.setTransactionId(instanceReturnMoneySignalDTO.getTransactionId());
            payRecordDTO.setOutTradeNo(instanceReturnMoneySignalDTO.getOutTradeNo());
            payRecordDTO.setSysUserId(instanceReturnMoneySignalDTO.getSysUserId());
            payRecordDTO.setStatus("1");
            List<PayRecordDTO> payRecordDTOList = payRecordService.getUserPayRecordList(payRecordDTO);

            float expenseAmount = 0;
            for(PayRecordDTO payRecord : payRecordDTOList)
            {
                expenseAmount = expenseAmount + payRecord.getAmount();
            }

            float returnMoney = 0;
            //逻辑先写死
            if(userRuleType.equals(ConfigConstant.businessC1))
            {
                if(parentRuleType.equals(ConfigConstant.businessA1)){

                    if(expenseAmount>=ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE)
                    {
                        returnMoney = ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE*5/100 + (expenseAmount-ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE)*2/100;

                        //记录月度交易流水
                        float amount = expenseAmount-ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE;
                        recordMonthTransaction(parentUserId,instanceReturnMoneySignalDTO,amount);
                    }
                    else if(expenseAmount>=ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE&&expenseAmount<=ConfigConstant.PROMOTE_B1_LEVEL_MAX_EXPENSE)
                    {
                        returnMoney = ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE*10/100 + (expenseAmount-ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE)*2/100;

                        //记录月度交易流水
                        float amount = expenseAmount-ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE;
                        recordMonthTransaction(parentUserId,instanceReturnMoneySignalDTO,amount);
                    }
                    else
                    {
                        returnMoney = expenseAmount * 2/100;

                        //记录月度交易流水
                        float amount = expenseAmount;
                        recordMonthTransaction(parentUserId,instanceReturnMoneySignalDTO,amount);
                    }

                }
                else if(parentRuleType.equals(ConfigConstant.businessB1))
                {
                    if(expenseAmount>=ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE&&expenseAmount<=ConfigConstant.PROMOTE_B1_LEVEL_MAX_EXPENSE)
                    {
                        returnMoney = ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE*10/100 + (expenseAmount-ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE)*10/100;

                        //记录月度交易流水
                        float amount = expenseAmount-ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE;
                        recordMonthTransaction(parentUserId,instanceReturnMoneySignalDTO,amount);
                    }
                    else if(expenseAmount>=ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE)
                    {
                        returnMoney = ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE*5/100 + (expenseAmount-ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE)*10/100;

                        //记录月度交易流水
                        float amount = expenseAmount-ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE;
                        recordMonthTransaction(parentUserId,instanceReturnMoneySignalDTO,amount);
                    }
                    else
                    {
                        returnMoney = expenseAmount * 10/100;

                        //记录月度交易流水
                        float amount = expenseAmount;
                        recordMonthTransaction(parentUserId,instanceReturnMoneySignalDTO,amount);
                    }
                }
                else if(parentRuleType.equals("A1B1"))
                {
                    if(expenseAmount>=ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE&&expenseAmount<=ConfigConstant.PROMOTE_B1_LEVEL_MAX_EXPENSE)
                    {
                        returnMoney = (expenseAmount-ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE)*2/100;

                        //记录月度交易流水
                        float amount = expenseAmount-ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE;
                        recordMonthTransaction(parentUserId,instanceReturnMoneySignalDTO,amount);
                    }
                    else if(expenseAmount>=ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE)
                    {
                        returnMoney = (expenseAmount-ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE)*2/100;

                        //记录月度交易流水
                        float amount = expenseAmount-ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE;
                        recordMonthTransaction(parentUserId,instanceReturnMoneySignalDTO,amount);
                    }
                    else
                    {
                        returnMoney = expenseAmount * 2/100;

                        //记录月度交易流水
                        float amount = expenseAmount;
                        recordMonthTransaction(parentUserId,instanceReturnMoneySignalDTO,amount);
                    }
                }
            }
            else if(userRuleType.equals(ConfigConstant.businessB1))
            {
                if(parentRuleType.equals(ConfigConstant.businessA1)){
                    if(expenseAmount>=ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE)
                    {
                        returnMoney = ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE*5/100 + (expenseAmount-ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE)*2/100;

                        //记录月度交易流水
                        float amount = expenseAmount-ConfigConstant.PROMOTE_A_LEVEL_MIN_EXPENSE;
                        recordMonthTransaction(parentUserId,instanceReturnMoneySignalDTO,amount);
                    }
                    else
                    {
                        returnMoney = expenseAmount * 2/100;

                        //记录月度交易流水
                        float amount = expenseAmount;
                        recordMonthTransaction(parentUserId,instanceReturnMoneySignalDTO,amount);
                    }
                }
            }


            if(returnMoney>0)
            {
                //将returnMoney去更新要返现的用户ID的account和income两个表的数据
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setSysUserId(parentUserId);
                List<AccountDTO> accountDTOS = accountService.getUserAccountInfo(accountDTO);
                if(accountDTOS.size()==0)
                {
                    //为用户创建一个账户
                    accountDTO = new AccountDTO();
                    accountDTO.setId(UUID.randomUUID().toString());
                    accountDTO.setSysUserId(parentUserId);

                    UserInfoDTO userInfoDTO = new UserInfoDTO();
                    userInfoDTO.setId(parentUserId);
                    List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);
                    accountDTO.setUserOpenId(userInfoDTOList.get(0).getUserOpenid());

                    accountDTO.setBalance((float)0.00);
                    accountDTO.setScore((float)0.00);
                    accountDTO.setStatus("normal");
                    accountDTO.setUpdateDate(new Date());
                    accountDTO.setBalanceDeny((float)0.00);
                    accountService.createUserAccount(accountDTO);
                }
                else
                {
                    accountDTO = accountDTOS.get(0);
                    float balance = accountDTO.getBalance() + returnMoney;
                    float balanceDeny = accountDTO.getBalanceDeny() + returnMoney;
                    accountDTO.setBalance(balance);
                    accountDTO.setBalanceDeny(balanceDeny);
                    accountDTO.setUpdateDate(new Date());
                    accountService.updateUserAccountInfo(accountDTO);
                }

                IncomeRecordDTO incomeRecordDTO = new IncomeRecordDTO();
                incomeRecordDTO.setId(UUID.randomUUID().toString());
                incomeRecordDTO.setSysUserId(parentUserId);
                incomeRecordDTO.setAmount(returnMoney);
                incomeRecordDTO.setCreateDate(new Date());
                incomeRecordDTO.setIncomeType("instance");
                incomeRecordDTO.setUpdateDate(new Date());
                incomeRecordDTO.setTransactionId(instanceReturnMoneySignalDTO.getTransactionId());
                incomeRecordDTO.setStatus("0");
                incomeService.insertUserIncomeInfo(incomeRecordDTO);
                WeixinTemplateMessageUtil.sendLowLevelBusinessExpenseTemplateWXMessage(accountDTO.getNickName(),expenseAmount+"", DateUtils.DateToStr(new Date()),token,"",accountDTO.getUserOpenId());
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void recordMonthTransaction(String userId, InstanceReturnMoneySignalDTO instanceReturnMoneySignalDTO, float amount) {
        MonthTransactionRecordDTO monthTransactionRecordDTO = new MonthTransactionRecordDTO();
        monthTransactionRecordDTO.setId(UUID.randomUUID().toString());
        monthTransactionRecordDTO.setTransactionId(instanceReturnMoneySignalDTO.getTransactionId());
        monthTransactionRecordDTO.setAmount(amount);
        monthTransactionRecordDTO.setUserId(userId);
        monthTransactionRecordDTO.setCreateDate(new Date());
        monthTransactionRecordDTO.setUpdateDate(new Date());
        transactionMapper.recordMonthTransaction(monthTransactionRecordDTO);
    }

}

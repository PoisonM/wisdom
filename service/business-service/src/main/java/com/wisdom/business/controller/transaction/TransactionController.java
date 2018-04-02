package com.wisdom.business.controller.transaction;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.service.account.AccountService;
import com.wisdom.business.service.account.IncomeService;
import com.wisdom.business.service.account.WithDrawService;
import com.wisdom.business.service.transaction.TransactionService;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.WithDrawRecordDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.system.UserInfoDTO;
import com.wisdom.common.dto.transaction.*;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.WeixinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 微信页面参数获取相关的控制类
 * Created by baoweiw on 2015/7/27.
 */

@Controller
@RequestMapping(value = "transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WithDrawService withDrawService;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserServiceClient userServiceClient;

    @LoginRequired
    @RequestMapping(value ="putNeedPayOrderListToRedis",method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseDTO putNeedPayOrderListToRedis(@RequestBody NeedPayOrderListDTO needPayOrderList) {
        ResponseDTO responseDTO = new ResponseDTO();
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        String needPayValue = (new Gson()).toJson(needPayOrderList);
        JedisUtils.del(userInfoDTO.getId()+"needPay");
        JedisUtils.set(userInfoDTO.getId()+"needPay",needPayValue,60*5);

        //将商品放入未支付订单列表
        for(NeedPayOrderDTO needPayOrderDTO:needPayOrderList.getNeedPayOrderList())
        {
            BusinessOrderDTO businessOrderDTO = new BusinessOrderDTO();
            businessOrderDTO.setBusinessProductId(needPayOrderDTO.getProductId());
            businessOrderDTO.setProductSpec(needPayOrderDTO.getProductSpec());
            businessOrderDTO = transactionService.getBusinessOrderByOrderId(needPayOrderDTO.getOrderId());
            businessOrderDTO.setStatus("0");
            try {
                transactionService.updateBusinessOrderStatus(businessOrderDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

    @LoginRequired
    @RequestMapping(value ="getNeedPayOrderListToRedis",method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseDTO<NeedPayOrderListDTO> getNeedPayOrderListToRedis() {
        ResponseDTO<NeedPayOrderListDTO> responseDTO = new ResponseDTO();
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        String value = JedisUtils.get(userInfoDTO.getId()+"needPay");
        NeedPayOrderListDTO needPayOrderListDTO = (new Gson()).fromJson(value, NeedPayOrderListDTO.class);
        if(needPayOrderListDTO==null)
        {
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        else
        {
            responseDTO.setResponseData(needPayOrderListDTO);
            responseDTO.setResult(StatusConstant.SUCCESS);
        }
        return responseDTO;
    }

    @LoginRequired
    @RequestMapping(value ="getTransactionList",method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseDTO<List<TransactionDTO>> getTransactionList(@RequestBody PageParamDTO pageParamDTO) {
        ResponseDTO<List<TransactionDTO>> responseDTO = new ResponseDTO();
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        List<TransactionDTO> transactionDTOList =  new ArrayList<>();
        List<IncomeRecordDTO> incomeRecordDTOS = incomeService.getUserIncomeRecordInfoByPage(userInfoDTO.getId(),pageParamDTO);
        List<WithDrawRecordDTO> withDrawRecordDTOS = withDrawService.getWithdrawInfoByPage(userInfoDTO.getId(),pageParamDTO);

        for(IncomeRecordDTO incomeRecordDTO : incomeRecordDTOS)
        {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setTransactionType(incomeRecordDTO.getIncomeType());
            transactionDTO.setAmount(incomeRecordDTO.getAmount());
            transactionDTO.setTransactionDate(incomeRecordDTO.getUpdateDate());
            transactionDTO.setTransactionId(incomeRecordDTO.getTransactionId());
            transactionDTO.setTransactionStatus(incomeRecordDTO.getStatus());
            transactionDTOList.add(transactionDTO);
        }
        for(WithDrawRecordDTO withDrawRecordDTO:withDrawRecordDTOS)
        {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setTransactionType("withdraw");
            transactionDTO.setAmount(withDrawRecordDTO.getMoneyAmount());
            transactionDTO.setTransactionDate(withDrawRecordDTO.getUpdateDate());
            transactionDTO.setTransactionId(withDrawRecordDTO.getWithdrawId());
            transactionDTO.setTransactionStatus(withDrawRecordDTO.getStatus());
            transactionDTOList.add(transactionDTO);
        }

        //快速排序
        Collections.sort(transactionDTOList, new Comparator<TransactionDTO>() {
            @Override
            public int compare(TransactionDTO p1, TransactionDTO p2) {
                return (p1.getTransactionDate().getTime() - p2.getTransactionDate().getTime())>0?1:0;
            }
        });

        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(transactionDTOList);
        return responseDTO;
    }

    @LoginRequired
    @RequestMapping(value ="getUserTransactionDetail",method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseDTO<TransactionDTO> getUserTransactionDetail(@RequestParam String transactionId, @RequestParam String transactionType) {
        ResponseDTO<TransactionDTO> responseDTO = new ResponseDTO();

        if(transactionType.equals("withdraw"))
        {
            WithDrawRecordDTO withDrawRecordDTO = withDrawService.getWithdrawDetail(transactionId);
            if(withDrawRecordDTO==null)
            {
                responseDTO.setResult(StatusConstant.FAILURE);
            }else
            {
                TransactionDTO transactionDTO = new TransactionDTO();
                transactionDTO.setTransactionType("withdraw");
                transactionDTO.setAmount(withDrawRecordDTO.getMoneyAmount());
                transactionDTO.setTransactionDate(withDrawRecordDTO.getUpdateDate());
                transactionDTO.setTransactionId(withDrawRecordDTO.getWithdrawId());
                transactionDTO.setTransactionStatus(withDrawRecordDTO.getStatus());
                responseDTO.setResponseData(transactionDTO);
                responseDTO.setResult(StatusConstant.SUCCESS);
            }
        }
        else
        {
            IncomeRecordDTO incomeRecordDTO = incomeService.getIncomeRecordDetail(transactionId);
            if(incomeRecordDTO==null)
            {
                responseDTO.setResult(StatusConstant.FAILURE);
            }
            else
            {
                TransactionDTO transactionDTO = new TransactionDTO();
                transactionDTO.setTransactionType(incomeRecordDTO.getIncomeType());
                transactionDTO.setAmount(incomeRecordDTO.getAmount());
                transactionDTO.setTransactionDate(incomeRecordDTO.getUpdateDate());
                transactionDTO.setTransactionId(incomeRecordDTO.getTransactionId());
                transactionDTO.setTransactionStatus(incomeRecordDTO.getStatus());
                responseDTO.setResponseData(transactionDTO);
                responseDTO.setResult(StatusConstant.SUCCESS);
            }
        }

        return responseDTO;
    }

    @RequestMapping(value ="getBusinessOrderByProductId",method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseDTO<String> getBusinessOrderByProductId(@RequestParam String productId, HttpSession session, HttpServletRequest request) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        String openId = WeixinUtil.getCustomerOpenId(session,request);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserOpenid(openId);
        List<UserInfoDTO> userInfoDTOS = userServiceClient.getUserInfo(userInfoDTO);
        if(userInfoDTOS.size()>0)
        {
            userInfoDTO = userInfoDTOS.get(0);
            List<BusinessOrderDTO> businessOrderDTOS = transactionService.getBusinessOrderByUserIdAndProductId(userInfoDTO.getId(),productId);
            if(businessOrderDTOS.size()>0)
            {
                responseDTO.setResult(StatusConstant.SUCCESS);
            }
            else
            {
                responseDTO.setResult(StatusConstant.FAILURE);
            }
        }
        else
        {
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        return  responseDTO;
    }

    //获取每日红包
    @RequestMapping(value ="getTripleMonthBonus",method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseDTO<Float> getTripleMonthBonus(HttpSession session, HttpServletRequest request) {
        ResponseDTO<Float> responseDTO = new ResponseDTO<>();
        String openId = WeixinUtil.getCustomerOpenId(session,request);

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserOpenid(openId);

        List<UserInfoDTO> userInfoDTOS = userServiceClient.getUserInfo(userInfoDTO);
        if(userInfoDTOS.size()>0)
        {
            //判断用户是否购买了指定活动的商品
            String productId = ConfigConstant.promote_businessB1_ProductId_No1;
            Query query = new Query(Criteria.where("userId").is(userInfoDTOS.get(0).getId()))
                    .addCriteria(Criteria.where("productId").is(productId));
            BonusFlagDTO bonusFlagDTO = mongoTemplate.findOne(query,BonusFlagDTO.class,"bonusFlag");

            if(bonusFlagDTO!=null&&bonusFlagDTO.getBonusFlag().equals("true"))
            {
                int leftDay = ConfigConstant.livingPeriodTripleMonth-differentDaysByMillisecond(new Date(),bonusFlagDTO.getBonusEndDate());
                if(leftDay<ConfigConstant.livingPeriodTripleMonth)
                {
                    float min = 1.5f;
                    float max = 3.0f;
                    DecimalFormat decimalFormat = new DecimalFormat(".00");
                    float floatBounded =  Float.parseFloat(decimalFormat.format(min + new Random().nextFloat() * (max - min)));

                    query = new Query(Criteria.where("userId").is(userInfoDTOS.get(0).getId()))
                            .addCriteria(Criteria.where("productId").is(productId));
                    TripleMonthBonusDTO tripleMonthBonusDTO = mongoTemplate.findOne(query,TripleMonthBonusDTO.class,"tripleMonthBonus");

                    if(tripleMonthBonusDTO==null)
                    {
                        List<BonusRecordDTO> bonusRecordDTOS = new ArrayList<>();
                        tripleMonthBonusDTO = new TripleMonthBonusDTO();
                        tripleMonthBonusDTO.setUserId(userInfoDTOS.get(0).getId());
                        tripleMonthBonusDTO.setLeftDay(String.valueOf(ConfigConstant.livingPeriodTripleMonth-differentDaysByMillisecond(new Date(),bonusFlagDTO.getBonusEndDate())));
                        tripleMonthBonusDTO.setProductId(productId);

                        BonusRecordDTO bonusRecordDTO = new BonusRecordDTO();
                        bonusRecordDTO.setDate(DateUtils.getDate());
                        bonusRecordDTO.setNum(floatBounded);
                        bonusRecordDTOS.add(bonusRecordDTO);

                        tripleMonthBonusDTO.setBonusRecords(bonusRecordDTOS);
                        mongoTemplate.insert(tripleMonthBonusDTO, "tripleMonthBonus");
                    }
                    else
                    {
                        List<BonusRecordDTO> bonusRecordDTOS = tripleMonthBonusDTO.getBonusRecords();
                        if(bonusRecordDTOS==null)
                        {
                            bonusRecordDTOS = new ArrayList<>();
                        }
                        BonusRecordDTO bonusRecordDTO = new BonusRecordDTO();
                        bonusRecordDTO.setDate(DateUtils.getDate());
                        bonusRecordDTO.setNum(floatBounded);
                        bonusRecordDTOS.add(bonusRecordDTO);
                        Update update = new Update();
                        update.set("bonusRecords",bonusRecordDTOS);
                        update.set("leftDay",String.valueOf(ConfigConstant.livingPeriodTripleMonth-differentDaysByMillisecond(new Date(),bonusFlagDTO.getBonusEndDate())));
                        mongoTemplate.updateFirst(query,update,"tripleMonthBonus");
                    }

                    AccountDTO accountDTO = new AccountDTO();
                    accountDTO.setSysUserId(userInfoDTOS.get(0).getId());
                    List<AccountDTO> accountDTOS = accountService.getUserAccountInfo(accountDTO);

                    if(accountDTOS.size()>0)
                    {
                        accountDTO = accountDTOS.get(0);
                        float balance = accountDTO.getBalance() + floatBounded;
                        accountDTO.setBalance(balance);
                        accountService.updateUserAccountInfo(accountDTO);
                    }
                    else
                    {
                        accountDTO.setId(UUID.randomUUID().toString());
                        accountDTO.setSysUserId(userInfoDTOS.get(0).getId());
                        accountDTO.setUserOpenId(userInfoDTOS.get(0).getUserOpenid());
                        accountDTO.setBalance(floatBounded);
                        accountDTO.setScore((float)0.00);
                        accountDTO.setStatus("normal");
                        accountDTO.setUpdateDate(new Date());
                        accountDTO.setBalanceDeny((float)0.00);
                        accountService.createUserAccount(accountDTO);
                    }
                    responseDTO.setResult(StatusConstant.SUCCESS);
                    responseDTO.setResponseData(floatBounded);
                }
            }
            else
            {
                responseDTO.setResult(StatusConstant.FAILURE);
            }
        }
        else
        {
            responseDTO.setResult(StatusConstant.FAILURE);
        }

        return  responseDTO;
    }

    @RequestMapping(value = "checkTripleMonthBonus", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<Integer> checkTripleMonthBonus(HttpSession session, HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO<>();

        Query query = new Query();
        ActivitySwitchDTO activitySwitchDTO = mongoTemplate.findOne(query,ActivitySwitchDTO.class,"activitySwitch");
        if(activitySwitchDTO.getActivityStatus().equals("off"))
        {
            responseDTO.setResult(StatusConstant.FAILURE);
            return responseDTO;
        }

        String openId = WeixinUtil.getCustomerOpenId(session,request);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserOpenid(openId);
        List<UserInfoDTO> userInfoDTOS = userServiceClient.getUserInfo(userInfoDTO);
        if(userInfoDTOS.size()>0)
        {
            String productId = ConfigConstant.promote_businessB1_ProductId_No1;
            query = new Query(Criteria.where("userId").is(userInfoDTOS.get(0).getId()))
                    .addCriteria(Criteria.where("productId").is(productId));
            BonusFlagDTO bonusFlagDTO = mongoTemplate.findOne(query,BonusFlagDTO.class,"bonusFlag");

            if(bonusFlagDTO==null||bonusFlagDTO.getMessageFlag().equals("true"))
            {
                //对于此种情况，判断用户是否今天已经抽过奖了
                query = new Query(Criteria.where("userId").is(userInfoDTOS.get(0).getId()))
                        .addCriteria(Criteria.where("productId").is(productId));
                TripleMonthBonusDTO tripleMonthBonusDTO = mongoTemplate.findOne(query,TripleMonthBonusDTO.class,"tripleMonthBonus");

                Boolean alreadyGetBonus = false;
                Boolean outOfDate = false;
                int dayValue = 0;
                if(tripleMonthBonusDTO!=null)
                {
                    int day = differentDaysByMillisecond(new Date(),bonusFlagDTO.getBonusEndDate());
                    if(day>0)
                    {
                        //没有过期
                        dayValue = ConfigConstant.livingPeriodTripleMonth - day;
                    }
                    else if(day<=0)
                    {
                        //已经过期
                        outOfDate = true;
                    }

                    if(tripleMonthBonusDTO.getBonusRecords()!=null)
                    {
                        for(BonusRecordDTO bonusRecordDTO:tripleMonthBonusDTO.getBonusRecords())
                        {
                            if(DateUtils.getDate().equals(bonusRecordDTO.getDate()))
                            {
                                alreadyGetBonus = true;
                                break;
                            }
                        }
                    }
                }

                if(alreadyGetBonus||outOfDate)
                {
                    responseDTO.setResult(StatusConstant.FAILURE);
                }
                else if(!alreadyGetBonus&&!outOfDate)
                {
                    responseDTO.setResponseData(dayValue);
                    responseDTO.setResult(StatusConstant.SUCCESS);
                }
            }
            else if(bonusFlagDTO.getMessageFlag().equals("false"))
            {
                responseDTO.setResult(StatusConstant.FAILURE);
            }
        }
        else
        {
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        return responseDTO;
    }

    private int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

}

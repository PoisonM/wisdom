package com.wisdom.business.controller.transaction;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.aliyun.oss.ServiceException;
import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.service.account.AccountService;
import com.wisdom.business.service.account.IncomeService;
import com.wisdom.business.service.account.WithDrawService;
import com.wisdom.business.service.product.ProductService;
import com.wisdom.business.service.transaction.TransactionService;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.WithDrawRecordDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.ResponseDTO;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;

import static com.wisdom.common.constant.ConfigConstant.RECOMMEND_PROMOTE_A1_REWARD;

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
    private ProductService productService;

    @Autowired
    private UserServiceClient userServiceClient;

    Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @LoginRequired
    @RequestMapping(value ="putNeedPayOrderListToRedis",method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseDTO putNeedPayOrderListToRedis(@RequestBody NeedPayOrderListDTO needPayOrderList) {
        long startTime = System.currentTimeMillis();
        logger.info("putNeedPayOrderListToRedis需要支付订单放入redis==={}开始",startTime);
        ResponseDTO responseDTO = new ResponseDTO();
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        for(NeedPayOrderDTO needPayOrder: needPayOrderList.getNeedPayOrderList()){
            if(needPayOrder.getProductPrefecture()!=null){
                if((!userInfoDTO.getUserType().equals(ConfigConstant.businessC1))&&needPayOrder.getProductPrefecture().equals("1")){
                    responseDTO.setResult(StatusConstant.FAILURE);
                    responseDTO.setErrorInfo("failure");
                    logger.info("_______________________________________________________________________"+userInfoDTO.getUserType());
                    return responseDTO;
                }
                logger.info("_______________________________________________________________________"+userInfoDTO.getUserType());
            }
        }
        for(NeedPayOrderDTO needPayOrderDTO : needPayOrderList.getNeedPayOrderList()){
            if("0".equals(needPayOrderDTO.getProductStatus())){
                responseDTO.setResult(StatusConstant.FAILURE);
                responseDTO.setErrorInfo("此商品已下架-"+needPayOrderDTO.getProductName());
                return responseDTO;
            }
        }
        RedisLock redisLock = new RedisLock("putNeedPayProductAmount");
        String needPayValue = (new Gson()).toJson(needPayOrderList);
        JedisUtils.del(userInfoDTO.getId()+"needPay");
        JedisUtils.set(userInfoDTO.getId()+"needPay",needPayValue,60*5);

        try {
            //todo log
            //logger.info("锁前订单号=={}",needPayOrderList.getNeedPayOrderList().get(0).getOrderId());
            redisLock.lock();
            //todo log
            //logger.info("锁下订单号=={}",needPayOrderList.getNeedPayOrderList().get(0).getOrderId());
            //将商品放入未支付订单列表
            for (NeedPayOrderDTO needPayOrderDTO : needPayOrderList.getNeedPayOrderList()) {
                BusinessOrderDTO businessOrderDTO = new BusinessOrderDTO();
                businessOrderDTO.setBusinessProductId(needPayOrderDTO.getProductId());
                businessOrderDTO.setProductSpec(needPayOrderDTO.getProductSpec());
                //todo log
                logger.info("查询订单前订单号=={}",needPayOrderDTO.getOrderId());
                businessOrderDTO = transactionService.getBusinessOrderByOrderId(needPayOrderDTO.getOrderId());
                if(null != businessOrderDTO){
                    if("3".equals(businessOrderDTO.getStatus())){
                        //todo log
                        logger.info("状态3进入订单号=={}",needPayOrderDTO.getOrderId());
                        logger.info("状态3进入订单状态=={}",businessOrderDTO.getStatus());
                        logger.info("状态3进入商品数量=={}",needPayOrderDTO.getProductNum());
                        ProductDTO productDTO = productService.getBusinessProductInfo(needPayOrderDTO.getProductId());
                        logger.info("状态3进入查出库中商品库存=={}",productDTO.getProductAmount());
                        if (Integer.parseInt(needPayOrderDTO.getProductNum()) > Integer.parseInt(productDTO.getProductAmount())) {
                            //todo log
                            logger.info("商品数量大于商品库存订单号=={}",needPayOrderDTO.getOrderId());
                            responseDTO.setErrorInfo("库存不足");
                            responseDTO.setResult(StatusConstant.FAILURE);
                            return responseDTO;
                        }
                    }
                    //todo log
                    logger.info("状态3判断通过订单号=={}",needPayOrderDTO.getOrderId());
                    businessOrderDTO.setStatus("0");
                    businessOrderDTO.setUpdateDate(new Date());
                    transactionService.updateBusinessOrder(businessOrderDTO);
                }
            }
        }catch (Throwable e)
        {
            logger.error("需要支付订单放入redis异常,异常信息为{}"+e.getMessage(),e);
            e.printStackTrace();
            throw new ServiceException("");
        }
        finally
        {
            redisLock.unlock();
        }
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("putNeedPayOrderListToRedis需要支付订单放入redis,耗时{}毫秒",(System.currentTimeMillis() - startTime));


        return responseDTO;
    }

    @LoginRequired
    @RequestMapping(value ="getNeedPayOrderListToRedis",method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseDTO<NeedPayOrderListDTO> getNeedPayOrderListToRedis() {
        long startTime = System.currentTimeMillis();
        logger.info("getNeedPayOrderListToRedi从redis获取需要支付订单==={}开始",startTime);
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
        logger.info("getNeedPayOrderListToRedi从redis获取需要支付订单,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    @LoginRequired
    @RequestMapping(value ="getTransactionList",method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseDTO<List<TransactionDTO>> getTransactionList(@RequestBody PageParamDTO pageParamDTO) {
        long startTime = System.currentTimeMillis();
        logger.info("getTransactionList==={}开始",startTime);
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
        if(transactionDTOList!=null&&transactionDTOList.size()>0){
            responseDTO.setResponseData(transactionDTOList);
        }else{
            responseDTO.setResponseData(null);
        }
        logger.info("getTransactionList,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    @LoginRequired
    @RequestMapping(value ="getUserTransactionDetail",method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseDTO<TransactionDTO> getUserTransactionDetail(@RequestParam String transactionId, @RequestParam String transactionType) {
        long startTime = System.currentTimeMillis();
        logger.info("getUserTransactionDetail==={}开始",startTime);
        ResponseDTO<TransactionDTO> responseDTO = new ResponseDTO();
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        logger.info("getUserTransactionDetail参数transactionId={},transactionType={}开始",transactionId,transactionType);
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
            IncomeRecordDTO incomeRecordDTOValue  = new IncomeRecordDTO();
            incomeRecordDTOValue.setSysUserId(userInfoDTO.getId());
            incomeRecordDTOValue.setTransactionId(transactionId);
            incomeRecordDTOValue.setIncomeType(transactionType);
            List<IncomeRecordDTO> incomeRecordDTOS = incomeService.getUserIncomeRecordInfo(incomeRecordDTOValue);
            if(incomeRecordDTOS!=null&&incomeRecordDTOS.size()>0){
                IncomeRecordDTO incomeRecordDTO = incomeRecordDTOS.get(0);
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

        }
        logger.info("getUserTransactionDetail,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    @RequestMapping(value ="getBusinessOrderByProductId",method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseDTO<String> getBusinessOrderByProductId(@RequestParam String productId, HttpSession session, HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        logger.info("根据商品id={}获取订单信息==={}开始",productId,startTime);
        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        String openId = WeixinUtil.getUserOpenId(session,request);
        logger.info("根据商品id获取订单信息用户openid={}",openId);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserOpenid(openId);
        List<UserInfoDTO> userInfoDTOS = userServiceClient.getUserInfo(userInfoDTO);
        if(userInfoDTOS.size()>0)
        {
            userInfoDTO = userInfoDTOS.get(0);
            List<BusinessOrderDTO> businessOrderDTOS = transactionService.getBusinessOrderByUserIdAndProductId(userInfoDTO.getId(),productId);
            logger.info("根据商品id获取订单List",businessOrderDTOS.size());
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
        logger.info("getUserTransactionDetail,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return  responseDTO;
    }

    //获取每日红包
    @RequestMapping(value ="getTripleMonthBonus",method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseDTO<Float> getTripleMonthBonus(HttpSession session, HttpServletRequest request) throws UnsupportedEncodingException {
        long startTime = System.currentTimeMillis();
        logger.info("获取每日红包==={}开始",startTime);
        ResponseDTO<Float> responseDTO = new ResponseDTO<>();
        String openId = WeixinUtil.getUserOpenId(session,request);
        logger.info("获取每日红包openid==={}",openId);

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserOpenid(openId);

        List<UserInfoDTO> userInfoDTOS = userServiceClient.getUserInfo(userInfoDTO);
        if(userInfoDTOS.size()>0)
        {
            //判断用户是否购买了指定活动的商品
            String productId = ConfigConstant.promote_businessB1_ProductId_No1;
            logger.info("指定活动的商品id==={}",productId);
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
                        float balanceDeny = accountDTO.getBalanceDeny() + floatBounded;
                        accountDTO.setBalance(balance);
                        accountDTO.setBalanceDeny(balanceDeny);
                        accountService.updateUserAccountInfo(accountDTO);

                        IncomeRecordDTO incomeRecordDTO = new IncomeRecordDTO();
                        incomeRecordDTO.setId(UUID.randomUUID().toString());
                        incomeRecordDTO.setSysUserId(userInfoDTOS.get(0).getId());
                        incomeRecordDTO.setUserType(userInfoDTOS.get(0).getUserType());
                        incomeRecordDTO.setNextUserId("");
                        incomeRecordDTO.setNextUserType("");
                        incomeRecordDTO.setAmount(balance);
                        incomeRecordDTO.setTransactionAmount(0);
                        incomeRecordDTO.setTransactionId(CodeGenUtil.getTransactionCodeNumber());
                        incomeRecordDTO.setUpdateDate(new Date());
                        incomeRecordDTO.setCreateDate(new Date());
                        incomeRecordDTO.setStatus("0");
                        incomeRecordDTO.setIdentifyNumber(userInfoDTOS.get(0).getIdentifyNumber());
                        incomeRecordDTO.setNextUserIdentifyNumber("");
                        incomeRecordDTO.setNickName(URLEncoder.encode(userInfoDTOS.get(0).getNickname(), "utf-8"));
                        incomeRecordDTO.setNextUserNickName("");
                        incomeRecordDTO.setIncomeType("activityBonus");
                        incomeRecordDTO.setMobile(userInfoDTOS.get(0).getMobile());
                        incomeRecordDTO.setNextUserMobile("");
                        incomeRecordDTO.setParentRelation("");
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
        logger.info("获取每日红包,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return  responseDTO;
    }

    @RequestMapping(value = "checkTripleMonthBonus", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<Integer> checkTripleMonthBonus(HttpSession session, HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        logger.info("审核三个月奖励==={}开始",startTime);
        ResponseDTO responseDTO = new ResponseDTO<>();

        Query query = new Query();
        ActivitySwitchDTO activitySwitchDTO = mongoTemplate.findOne(query,ActivitySwitchDTO.class,"activitySwitch");
        if(activitySwitchDTO.getActivityStatus().equals("off"))
        {
            responseDTO.setResult(StatusConstant.FAILURE);
            return responseDTO;
        }

        String openId = WeixinUtil.getUserOpenId(session,request);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserOpenid(openId);
        logger.info("审核三个月奖励用户openid==={}",openId);
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
        logger.info("审核三个月奖励,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    private int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

}

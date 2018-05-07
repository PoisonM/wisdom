package com.wisdom.business.service.account;

import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.mapper.account.AccountMapper;
import com.wisdom.business.mapper.account.WithDrawMapper;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.entity.Article;
import com.wisdom.common.util.WeixinTemplateMessageUtil;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.account.WithDrawRecordDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.UserBankCardInfoDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.persistence.Page;
import com.wisdom.common.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.HashMap;

@Service
@Transactional(readOnly = false)
public class WithDrawService {

    Logger logger = LoggerFactory.getLogger(WithDrawService.class);

    @Autowired
    private WithDrawMapper withDrawMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private UserServiceClient userServiceClient;


    
    public Map<String,String> updateWithdrawById(WithDrawRecordDTO withDrawRecordDTO,HttpServletRequest request) {

        String openid = JedisUtils.get("openid");
        RedisLock redisLock = new RedisLock("deFrozenWithDrawRecord"+openid);
        redisLock.lock();
        String withdrawId = withDrawRecordDTO.getWithdrawId();
        String status = withDrawRecordDTO.getStatus();
        String token = WeixinUtil.getUserToken();
        Float moneyAmount = withDrawRecordDTO.getMoneyAmount();
        String time = DateUtils.getDateTime();
        WithDrawRecordDTO withDrawRecordDTO1 = new WithDrawRecordDTO();
        withDrawRecordDTO1.setId(withdrawId);
        List<WithDrawRecordDTO> withDrawRecordDTOS = withDrawMapper.getWithdrawRecordInfo(withDrawRecordDTO1);

        if (withDrawRecordDTOS != null && withDrawRecordDTOS.size() > 0) {

            //判断当前状态是否未审核
            if (withDrawRecordDTOS.get(0).getStatus().equals("0")) {

                withDrawMapper.updateWithdrawById(withdrawId, status);
                if ("2".equals(status)) {//拒绝提现,提现金额返还余额
                    AccountDTO accountDTO = accountMapper.getUserAccountInfoByUserId(withDrawRecordDTO.getSysUserId());
                    Float money = accountDTO.getBalance();//用户余额
                    Float balance = withDrawRecordDTO.getMoneyAmount();//提现金额
                    Float f = money + balance;
                    accountDTO.setBalance(f);
                    accountMapper.updateUserAccountInfo(accountDTO);
                    //发送提现失败消息模板
                    WeixinTemplateMessageUtil.sendWithDrawTemplateFailureMessage(String.valueOf(moneyAmount), time, token, "", openid);
                    HashMap<String, String> result = new HashMap<String, String>();
                    result.put("result","success");
                    result.put("message","执行成功");
                    redisLock.unlock();
                    return result;

                } else {

                    try {
                        //将钱打入用户零钱账户
                        returnMoneyToUser(moneyAmount, request, openid, token);
                        HashMap<String, String> result = new HashMap<String, String>();
                        result.put("result","success");
                        result.put("message","执行成功");
                        redisLock.unlock();
                        return result;
                    } catch (Exception e) {
                        logger.info(e.getMessage());
                        withDrawMapper.updateWithdrawById(withdrawId, "0");
                        HashMap<String, String> result = new HashMap<String, String>();
                        result.put("result","failure");
                        result.put("message","公司公众号帐户余额不足，请联系财务人员");
                        redisLock.unlock();
                        return result;
                    }
                }
            }else{
                HashMap<String, String> result = new HashMap<String, String>();
                result.put("result","failure");
                result.put("message","账户已审核，请勿重复操作");
                redisLock.unlock();
                return result;
            }

        }else{
            HashMap<String, String> result = new HashMap<String, String>();
            result.put("result","failure");
            result.put("message","此用户信息存在问题，请联系开发人员");
            redisLock.unlock();
            return result;
        }

    }


    
    public PageParamDTO<List<WithDrawRecordDTO>> queryWithdrawsByParameters(PageParamVoDTO<ProductDTO> pageParamVoDTO) {
        PageParamDTO<List<WithDrawRecordDTO>> page = new  PageParamDTO<>();
        int count = withDrawMapper.queryWithdrawsCountByParameters(pageParamVoDTO);
        page.setTotalCount(count);
        List<WithDrawRecordDTO> withDrawRecordDTOList = withDrawMapper.queryWithdrawsByParameters(pageParamVoDTO);

        for(WithDrawRecordDTO withDrawRecordDTO : withDrawRecordDTOList){
            try {
                Query query = new Query().addCriteria(Criteria.where("withDrawId").is(withDrawRecordDTO.getWithdrawId()));
                UserBankCardInfoDTO userBankCardInfoDTO = mongoTemplate.findOne(query, UserBankCardInfoDTO.class,"userBankCardInfo");
                if(userBankCardInfoDTO != null){
                    withDrawRecordDTO.setUserName(userBankCardInfoDTO.getUserName());
                    withDrawRecordDTO.setBankCardNumber(userBankCardInfoDTO.getBankCardNumber());
                    withDrawRecordDTO.setBankCardAddress(userBankCardInfoDTO.getBankCardAddress());
                }

                withDrawRecordDTO.setNickName(URLDecoder.decode(URLDecoder.decode(withDrawRecordDTO.getNickName(),"utf-8"),"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        page.setResponseData(withDrawRecordDTOList);

        return page;

    }
    
    public PageParamDTO<List<WithDrawRecordDTO>> queryAllWithdraw(PageParamDTO pageParamDTO) {
        PageParamDTO<List<WithDrawRecordDTO>> pageResult = new  PageParamDTO<>();
        String currentPage = String.valueOf(pageParamDTO.getPageNo());
        String pageSize = String.valueOf(pageParamDTO.getPageSize());
        Page<WithDrawRecordDTO> page = FrontUtils.generatorPage(currentPage, pageSize);
        Page<WithDrawRecordDTO> resultPage = withDrawMapper.queryAllWithdraw(page);
        for(WithDrawRecordDTO withDrawRecordDTO : resultPage.getList()){
            try {
                withDrawRecordDTO.setNickName(URLDecoder.decode(withDrawRecordDTO.getNickName(),"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        pageResult.setTotalCount((int)resultPage.getCount());
        pageResult.setResponseData(resultPage.getList());
        return pageResult;
    }
    
    public List<WithDrawRecordDTO> getWithdrawInfoByPage(String userId, PageParamDTO pageParamDTO) {
        return  withDrawMapper.getWithdrawInfoByPage(userId,pageParamDTO.getPageNo(),pageParamDTO.getPageSize());
    }
    
    public WithDrawRecordDTO getWithdrawDetail(String transactionId) {
        return withDrawMapper.getWithdrawDetail(transactionId);
    }

    public List<WithDrawRecordDTO> getWithdrawRecordInfo(WithDrawRecordDTO withDrawRecordDTO)
    {
        return  withDrawMapper.getWithdrawRecordInfo(withDrawRecordDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    public void withDrawMoneyFromAccount(float moneyAmount, HttpServletRequest request, String openid,boolean checkFlag)  throws Exception{

        String token = WeixinUtil.getUserToken();

        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        AccountDTO accountDTO = accountMapper.getUserAccountInfoByUserId(userInfoDTO.getId());

        //扣除账户余额中的款项
        if((accountDTO.getBalance()-moneyAmount)<0)
        {
            List<Article> articleList = new ArrayList<>();
            Article article = new Article();
            article.setTitle("对不起，您的提现金额大于您的账户余额，提现失败。");
            articleList.add(article);
            WeixinUtil.senImgMsgToWeixin(token,openid,articleList);
            throw new Exception();
        }
        else
        {
            accountDTO.setBalance(accountDTO.getBalance()-moneyAmount);
            accountDTO.setUpdateDate(new Date());
            accountMapper.updateUserAccountInfo(accountDTO);
        }

        WithDrawRecordDTO withDrawRecordDTO = new WithDrawRecordDTO();
        withDrawRecordDTO.setId(UUID.randomUUID().toString());
        withDrawRecordDTO.setWithdrawId(CodeGenUtil.getTransactionCodeNumber());
        withDrawRecordDTO.setMoneyAmount(moneyAmount);
        withDrawRecordDTO.setSysUserId(userInfoDTO.getId());
        withDrawRecordDTO.setCreateDate(new Date());
        withDrawRecordDTO.setUpdateDate(new Date());
        withDrawRecordDTO.setUserOpenId(openid);
        withDrawRecordDTO.setUserAccountId(accountDTO.getId());
        if(checkFlag)
        {
            withDrawRecordDTO.setStatus("0");
            withDrawMapper.addWithDrawRecord(withDrawRecordDTO);
        }
        else
        {
            withDrawRecordDTO.setStatus("1");
            withDrawMapper.addWithDrawRecord(withDrawRecordDTO);

            //将钱打入用户零钱账户
            returnMoneyToUser(moneyAmount,request,openid,token);
        }
    }

    public void deFrozenWithDrawRecord(WithDrawRecordDTO withDrawRecordDTO,HttpServletRequest request) throws Exception {
        withDrawMapper.updateWithdrawById(withDrawRecordDTO.getWithdrawId(),"1");
        String token = WeixinUtil.getUserToken();
        returnMoneyToUser(withDrawRecordDTO.getMoneyAmount(),request,withDrawRecordDTO.getUserOpenId(),token);
    }

    private static void returnMoneyToUser(float moneyAmount, HttpServletRequest request, String openid, String token) throws Exception {
        //公众号中的提现操作，moneyAmount为提现金额
        int returnMoney = (int) moneyAmount*100;

        String money = String.valueOf(returnMoney);
        //调用企业统一支付接口对用户进行退款
        SortedMap<Object,Object> parameters = new TreeMap<>();
        parameters.put("mch_appid", ConfigConstant.APP_ID);//APPid
        parameters.put("mchid", ConfigConstant.MCH_ID);
        parameters.put("nonce_str", IdGen.uuid());
        parameters.put("partner_trade_no",IdGen.uuid());
        parameters.put("check_name","NO_CHECK");
        parameters.put("amount", money);//金额
        parameters.put("desc", "提现零钱");
        parameters.put("spbill_create_ip",request.getRemoteAddr());
        parameters.put("openid", openid);
        String sign = JsApiTicketUtil.createSign("UTF-8", parameters);
        parameters.put("sign", sign);
        String requestXML = JsApiTicketUtil.getRequestXml(parameters);
        try{
            String result = HttpRequestUtil.clientCustomSSLS(ConfigConstant.transfer, requestXML);
            Map<String, String> returnMap = XMLUtil.doXMLParse(result);
            //解析微信返回的信息，以Map形式存储便于取值
            if(!"SUCCESS".equals(returnMap.get("result_code"))){
                throw new Exception();
            }
            else
            {
                WeixinTemplateMessageUtil.withdrawalsSuccess2Weixin(openid,token,"",String.valueOf(moneyAmount),DateUtils.DateToStr(new Date(), "date"));
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

}

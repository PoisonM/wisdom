package com.wisdom.business.service.account;

import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.mapper.account.AccountMapper;
import com.wisdom.business.mapper.account.WithDrawMapper;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.dto.wexin.WeixinTokenDTO;
import com.wisdom.common.util.WeixinTemplateMessageUtil;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.account.WithDrawRecordDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.UserBankCardInfoDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.persistence.Page;
import com.wisdom.common.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.*;

@Service
@Transactional(readOnly = false)
public class WithDrawService {

    @Autowired
    private WithDrawMapper withDrawMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private UserServiceClient userServiceClient;
    
    public void updateWithdrawById(WithDrawRecordDTO withDrawRecordDTO) {

        String withdrawId = withDrawRecordDTO.getWithdrawId();
        String status = withDrawRecordDTO.getStatus();
        String openid = "";
        String token  = WeixinUtil.getUserToken();
        String moneyAmount = String.valueOf(withDrawRecordDTO.getMoneyAmount());
        String time = DateUtils.getDateTime();

        withDrawMapper.updateWithdrawById(withdrawId,status);
        if("2".equals(status)){//拒绝提现,提现金额返还余额
            AccountDTO accountDTO = accountMapper.getUserAccountInfoByUserId(withDrawRecordDTO.getSysUserId());
            Float money = accountDTO.getBalance();//用户余额
            Float balance = withDrawRecordDTO.getMoneyAmount();//提现金额
            Float f = money + balance;
            accountDTO.setBalance(f);
            accountMapper.updateUserAccountInfo(accountDTO);
            //发送提现失败消息模板
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId(withDrawRecordDTO.getSysUserId());
            List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);
            for (UserInfoDTO userInfoDTO1 : userInfoDTOList) {
                openid = userInfoDTO1.getUserOpenid();
            }
            WeixinTemplateMessageUtil.sendWithDrawTemplateFailureMessage(moneyAmount,time,token,"",openid);
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
                withDrawRecordDTO.setNickName(URLDecoder.decode(withDrawRecordDTO.getNickName(),"utf-8"));
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
    
    @Transactional(rollbackFor = Exception.class)
    public void withDrawMoneyFromAccount(float moneyAmount, HttpServletRequest request, String openid,
                                         UserBankCardInfoDTO userBankCardInfoDTO)  throws Exception{

        //生成提现记录
        RedisLock redisLock = new RedisLock("withDrawRecordLock" + openid);

        try{
            redisLock.lock();

            UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
            AccountDTO accountDTO = accountMapper.getUserAccountInfoByUserId(userInfoDTO.getId());

            //扣除账户余额中的款项
            if((accountDTO.getBalance()-moneyAmount)<0)
            {
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
            withDrawRecordDTO.setStatus("0");
            withDrawRecordDTO.setSysUserId(userInfoDTO.getId());
            withDrawRecordDTO.setCreateDate(new Date());
            withDrawRecordDTO.setUpdateDate(new Date());
            withDrawRecordDTO.setUserOpenId(openid);
            withDrawRecordDTO.setUserAccountId(accountDTO.getId());
            withDrawMapper.addWithDrawRecord(withDrawRecordDTO);

            userBankCardInfoDTO.setWithDrawId(withDrawRecordDTO.getWithdrawId());
            mongoTemplate.insert(userBankCardInfoDTO,"userBankCardInfo");

        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            redisLock.unlock();
        }

        //公众号中的提现操作，moneyAmount为提现金额
        Float returnMoney = moneyAmount*100;

        DecimalFormat decimalFormat=new DecimalFormat("");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String money = decimalFormat.format(returnMoney);//format 返回的是字符串

        //调用企业统一支付接口对用户进行退款
        SortedMap<Object,Object> parameters = new TreeMap<>();
        parameters.put("mch_appid", ConfigConstant.APP_ID);//APPid
        parameters.put("mchid", ConfigConstant.MCH_ID);
        parameters.put("nonce_str", IdGen.uuid());
        parameters.put("partner_trade_no",IdGen.uuid());
        parameters.put("check_name","NO_CHECK");
//        parameters.put("check_name","FORCE_CHECK");
//        parameters.put("re_user_name","陈佳科");
        parameters.put("amount", money);//金额
        parameters.put("desc", "提现零钱");
        parameters.put("spbill_create_ip",request.getRemoteAddr());
        parameters.put("openid", openid);
        String sign = JsApiTicketUtil.createSign("UTF-8", parameters);
        parameters.put("sign", sign);
        String requestXML = JsApiTicketUtil.getRequestXml(parameters);
        try{
            String result = HttpRequestUtil.clientCustomSSLS(ConfigConstant.transfer, requestXML);
            Map<String, String> returnMap = XMLUtil.doXMLParse(result);//解析微信返回的信息，以Map形式存储便于取值
            if(!"SUCCESS".equals(returnMap.get("result_code"))){
                throw new Exception();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }

        Query query = new Query(Criteria.where("weixinFlag").is(ConfigConstant.weixinUserFlag));
        WeixinTokenDTO weixinTokenDTO = this.mongoTemplate.findOne(query,WeixinTokenDTO.class,"weixinParameter");
        String token = weixinTokenDTO.getToken();
        WeixinTemplateMessageUtil.withdrawalsSuccess2Weixin(openid,token,"",String.valueOf(moneyAmount),DateUtils.DateToStr(new Date(), "date"));
    }

}

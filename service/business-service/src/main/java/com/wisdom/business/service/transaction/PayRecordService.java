package com.wisdom.business.service.transaction;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.business.mapper.transaction.PayRecordMapper;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.account.PrePayInfoDTO;
import com.wisdom.common.dto.product.InvoiceDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.dto.transaction.NeedPayOrderDTO;
import com.wisdom.common.dto.transaction.NeedPayOrderListDTO;
import com.wisdom.common.util.*;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Created by sunxiao on 2017/6/26.
 */

@Service
public class PayRecordService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PayRecordMapper payRecordMapper;

    @Transactional(rollbackFor = Exception.class)
    public PrePayInfoDTO getPrepayInfo(HttpServletRequest request, HttpSession session, String productType) {

        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();

        RedisLock redisLock = new RedisLock("payRecord" + userInfoDTO.getId());
        PrePayInfoDTO prePayInfoDTO = new PrePayInfoDTO();
        try{
            redisLock.lock();

            SortedMap<Object, Object> prePayInfoMap = new TreeMap<>();

            prePayInfoMap.put("appid", ConfigConstant.APP_ID);
            prePayInfoMap.put("mch_id",ConfigConstant.MCH_ID);
            prePayInfoMap.put("device_info",ConfigConstant.DEVICE_INFO);
            prePayInfoMap.put("nonce_str",IdGen.uuid());
            prePayInfoMap.put("body",productType);
            prePayInfoMap.put("attach","detail");
            //生成的商户订单号
            String outTradeNo = IdGen.uuid();
            prePayInfoMap.put("out_trade_no",outTradeNo);
            prePayInfoMap.put("fee_type",ConfigConstant.FEE_TYPE);


            String value = JedisUtils.get(userInfoDTO.getId()+"needPay");
            NeedPayOrderListDTO needPayOrderListDTO = (new Gson()).fromJson(value, NeedPayOrderListDTO.class);
            Float payPrice = 0F;
            for(NeedPayOrderDTO needPayOrderDTO:needPayOrderListDTO.getNeedPayOrderList())
            {
                payPrice = payPrice + Float.valueOf(needPayOrderDTO.getProductPrice())*Float.valueOf(needPayOrderDTO.getProductNum());
            }
            //prePayInfoMap.put("total_fee",(int)(payPrice*100)+"");//todo 后续将total_fee塞入payPrice,测试后用得是1分钱
            prePayInfoMap.put("total_fee",1+"");//todo 后续将total_fee塞入payPrice,测试后用得是1分钱
            prePayInfoMap.put("spbill_create_ip",request.getRemoteAddr());

            if(productType.equals("offline"))
            {
                prePayInfoMap.put("notify_url",ConfigConstant.OFFLINE_PRODUCT_BUY_NOTIFY_URL);
            }
            else if(productType.equals("special"))
            {
                prePayInfoMap.put("notify_url",ConfigConstant.SPECIAL_PRODUCT_BUY_NOTIFY_URL);
            }
            else if(productType.equals("trainingProduct"))
            {
                prePayInfoMap.put("notify_url",ConfigConstant.TRAINING_PRODUCT_BUY_NOTIFY_URL);
            }

            prePayInfoMap.put("trade_type","JSAPI");

            String openId = WeixinUtil.getUserOpenId(session, request);
            prePayInfoMap.put("openid",openId);

            //将上述参数进行签名
            String sign = JsApiTicketUtil.createSign("UTF-8", prePayInfoMap);
            prePayInfoMap.put("sign",sign);

            String requestXML = JsApiTicketUtil.getRequestXml(prePayInfoMap);
            String payResult = HttpRequestUtil.httpsRequest(ConfigConstant.GATE_URL, "POST", requestXML).replaceAll("200>>>>",""); //gate url是腾讯的支付URL


            Map<String, Object> payResultMap = XMLUtil.doXMLParse(payResult);
            if (!"FAIL".equals(payResultMap.get("return_code"))) {
                prePayInfoMap = new TreeMap<>();

                prePayInfoDTO.setAppId(ConfigConstant.APP_ID);
                prePayInfoMap.put("appId",ConfigConstant.APP_ID);

                String timeStamp = Sha1Util.getTimeStamp();
                prePayInfoDTO.setTimeStamp(timeStamp);//生成1970年到现在的秒数
                prePayInfoMap.put("timeStamp",timeStamp);

                String nonceStr = IdGen.uuid();
                prePayInfoDTO.setNonceStr(nonceStr);//生成随机字符串
                prePayInfoMap.put("nonceStr",nonceStr);

                prePayInfoDTO.setPackageData("prepay_id=" + payResultMap.get("prepay_id"));
                prePayInfoMap.put("package","prepay_id=" + payResultMap.get("prepay_id"));

                prePayInfoDTO.setSignType(ConfigConstant.SIGN_METHOD);
                prePayInfoMap.put("signType",ConfigConstant.SIGN_METHOD);

                String paySign = JsApiTicketUtil.createSign("UTF-8", prePayInfoMap);
                prePayInfoDTO.setPaySign(paySign);//paySign的生成规则和Sign的生成规则一致
                prePayInfoMap.put("paySign", paySign); //paySign的生成规则和Sign的生成规则一致
                String userAgent = request.getHeader("user-agent");
                char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger") + 15);
                prePayInfoDTO.setAgent((int)agent);//微信版本号，用于前面提到的判断用户手机微信的版本是否是5.0以上版本。
                prePayInfoMap.put("agent", new String(new char[]{agent}));

                PayRecordDTO payRecordDTO = new PayRecordDTO();
                if (userInfoDTO.getId() != null) {
                    payRecordDTO.setSysUserId(userInfoDTO.getId());
                } else {
                    payRecordDTO.setId("noUser");
                }

                String transactionId = CodeGenUtil.getTransactionCodeNumber();

                String needInvoice = "0";
                for(NeedPayOrderDTO needPayOrderDTO:needPayOrderListDTO.getNeedPayOrderList())
                {
                    Query query = new Query(Criteria.where("orderId").is(needPayOrderDTO.getOrderId()));
                    List<InvoiceDTO> invoiceDTOS = mongoTemplate.find(query,InvoiceDTO.class,"invoice");
                    if(invoiceDTOS.size()>0)
                    {
                        needInvoice = "1";
                    }
                }

                for(NeedPayOrderDTO needPayOrderDTO:needPayOrderListDTO.getNeedPayOrderList())
                {
                    payRecordDTO.setId(UUID.randomUUID().toString());
                    payRecordDTO.setAmount(Float.valueOf(needPayOrderDTO.getProductPrice())*Float.valueOf(needPayOrderDTO.getProductNum()));
                    payRecordDTO.setInvoice(needInvoice);//todo 需要填入用户是否需要发票，需要调用junjie的接口
                    payRecordDTO.setOrderId(needPayOrderDTO.getOrderId());
                    payRecordDTO.setOpenid(openId);
                    payRecordDTO.setPayDate(new Date());
                    payRecordDTO.setStatus("0");//等待支付
                    payRecordDTO.setTransactionId(transactionId);
                    payRecordDTO.setOutTradeNo(outTradeNo);
                    payRecordDTO.setPayType("wx");
                    payRecordMapper.insertPayRecord(payRecordDTO);
                }

                prePayInfoDTO.setResult(StatusConstant.SUCCESS);
            }
            else
            {
                prePayInfoDTO.setResult(StatusConstant.FAILURE);
            }
        }
        catch (JDOMException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            redisLock.unlock();
        }

        return prePayInfoDTO;
    }

    public List<PayRecordDTO> getUserPayRecordList(PayRecordDTO payRecordDTO) {
        return payRecordMapper.getUserPayRecordList(payRecordDTO);
    }

    public void updatePayRecordStatus(PayRecordDTO payRecord){
        payRecordMapper.updatePayRecordStatus(payRecord);
    }

    public List<PayRecordDTO> getUserPayRecordListByDate(String userId, String startDate, String endDate) {
        return payRecordMapper.getUserPayRecordListByDate(userId,startDate,endDate);
    }
    public String getSellNumByProductId(String productId){
        return payRecordMapper.getSellNumByProductId(productId);
    }

    public List<BusinessOrderDTO> queryOrderInfoByTransactionId(String transactionId) {
        return payRecordMapper.queryOrderInfoByTransactionId(transactionId);
    }

    public List<PayRecordDTO> queryUserInfoByTransactionId(String transactionId) {
        return payRecordMapper.queryUserInfoByTransactionId(transactionId);
    }
}

package com.wisdom.business.controller.transaction;

import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.service.transaction.PayCoreService;
import com.wisdom.business.service.transaction.PayRecordService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.account.PrePayInfoDTO;
import com.wisdom.common.dto.specialShop.SpecialShopBusinessOrderDTO;
import com.wisdom.common.dto.specialShop.SpecialShopInfoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.RedisLock;
import com.wisdom.common.util.XMLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "transaction")
public class PayController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PayRecordService payRecordService;

    @Autowired
    private PayCoreService payCoreService;

    /**
     * js支付
     */
    @RequestMapping(value = "pay/{productType}", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<PrePayInfoDTO> userPay(HttpServletRequest request, HttpSession session, @PathVariable String productType) throws Exception {
        long startTime = System.currentTimeMillis();
        logger.info("js支付==={}开始",startTime);
        ResponseDTO<PrePayInfoDTO> responseDTO = new ResponseDTO<>();

        //获取统一支付接口参数
        PrePayInfoDTO prePayInfoDTO = payRecordService.getPrepayInfo(request, session, productType);

        if(prePayInfoDTO.getResult().equals(StatusConstant.FAILURE))
        {
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setErrorInfo("支付失败");
        }
        else if(prePayInfoDTO.getResult().equals(StatusConstant.SUCCESS))
        {
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setResponseData(prePayInfoDTO);
        }
        logger.info("js支付,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 接收支付成后微信notify_url参数中传来的参数
     * 支付完成 后服务器故障 事物无法回滚
     * */
    @RequestMapping(value = "getOfflineProductPayNotifyInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    String getOfflineProductPayNotifyInfo(HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        logger.info("接收OfflineProductPay支付成后微信notify_url参数中传来的参数==={}开始",startTime);
        InputStream inStream = null;
        try {
            inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String result  = new String(outSteam.toByteArray(),"utf-8");
            Map<String, Object> map = XMLUtil.doXMLParse(result);
            //放入service层进行事物控制
            if("SUCCESS".equals(map.get("return_code"))){
                //String accountNeedPay = (String) map.get("attach");//未来此处，作为账户余额支付的入口
                PayRecordDTO payRecordDTO = new PayRecordDTO();
                payRecordDTO.setOutTradeNo((String) map.get("out_trade_no"));
                payRecordDTO.setStatus("0");

                logger.info("放入service层进行事物控制");
                payCoreService.handleProductPayNotifyInfo(payRecordDTO,"offline");
            }
            return  XMLUtil.setXML("SUCCESS", "");
        } catch (Exception e) {
            logger.error("接收OfflineProductPay支付成后微信notify_url参数中传来的参数,异常信息为=={}"+e.getMessage(),e);
            e.printStackTrace();
        }
        logger.info("接收OfflineProductPay支付成后微信notify_url参数中传来的参数,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return "";
    }

    /**
     * 接收支付成后微信notify_url参数中传来的参数
     * 支付完成 后服务器故障 事物无法回滚
     * */
    @RequestMapping(value = "getTrainingProductPayNotifyInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    String getTrainingProductPayNotifyInfo(HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        logger.info("接收TrainingProductPay支付成后微信notify_url参数中传来的参数==={}开始",startTime);

        RedisLock redisLock = new RedisLock("trainingProductPayNotifyInfo");
        InputStream inStream = null;
        try {
            redisLock.lock();

            inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String result  = new String(outSteam.toByteArray(),"utf-8");
            Map<String, Object> map = XMLUtil.doXMLParse(result);

            //放入service层进行事物控制
            if("SUCCESS".equals(map.get("return_code"))){
                PayRecordDTO payRecordDTO = new PayRecordDTO();
                payRecordDTO.setOutTradeNo((String) map.get("out_trade_no"));
                payRecordDTO.setStatus("0");
                payCoreService.handleProductPayNotifyInfo(payRecordDTO,"training");
            }
            return  XMLUtil.setXML("SUCCESS", "");
        } catch (Exception e) {
            logger.error("接收TrainingProductPay支付成后微信notify_url参数中传来的参数,异常信息为=={}"+e.getMessage(),e);
            e.printStackTrace();
        }finally {
            redisLock.unlock();
        }
        logger.info("接收TrainingProductPay支付成后微信notify_url参数中传来的参数,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return "";
    }

    /**
     * 接收支付成后微信notify_url参数中传来的参数
     * 支付完成 后服务器故障 事物无法回滚
     * */
    @RequestMapping(value = "getSpecialProductPayNotifyInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    String getSpecialProductPayNotifyInfo(HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        logger.info("接收SpecialProductPay支付成后微信notify_url参数中传来的参数==={}开始",startTime);
        RedisLock redisLock = new RedisLock("speicalProductPayNotifyInfo");
        InputStream inStream = null;
        try {
            redisLock.lock();

            inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String result  = new String(outSteam.toByteArray(),"utf-8");
            Map<String, Object> map = XMLUtil.doXMLParse(result);

            //放入service层进行事物控制
            if("SUCCESS".equals(map.get("return_code"))){
                PayRecordDTO payRecordDTO = new PayRecordDTO();
                payRecordDTO.setOutTradeNo((String) map.get("out_trade_no"));
                payRecordDTO.setStatus("0");
                payCoreService.handleProductPayNotifyInfo(payRecordDTO,"special");
            }
            return  XMLUtil.setXML("SUCCESS", "");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            redisLock.unlock();
        }
        logger.info("接收SpecialProductPay支付成后微信notify_url参数中传来的参数,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return "";
    }
    

}

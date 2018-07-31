package com.wisdom.business.controller.transaction;

import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.service.product.SeckillProductService;
import com.wisdom.business.service.transaction.BuyCartService;
import com.wisdom.business.service.transaction.TransactionService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.RedisLock;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 秒杀活动创建订单接口
 * Created by wangbaowei on 2018/7/25.
 */

@Controller
@RequestMapping(value = "seckillOrder")
public class SeckillOrderController {

    /**
     * 预约详情缓存时常，20分钟
     */
    private int productInfoCacheSeconds = 1200;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BuyCartService buyCartService;
    @Autowired
    private SeckillProductService seckillProductService;
    @Autowired
    private TransactionService transactionService;


    /**
     * 秒杀活动购买某个商品，创建订单
     * @param businessOrderDTO
     * output ResponseDTO<String>
     */
    @RequestMapping(value = "createSeckillOrder", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<String> createSeckillOrder(@RequestBody BusinessOrderDTO businessOrderDTO) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        long startTime = System.currentTimeMillis();
        logger.info("秒杀某个商品，创建订单==={}开始", startTime);
        RedisLock productAmountLock = new RedisLock("seckillProduct" + businessOrderDTO.getBusinessProductId());
        logger.info("秒杀商品生成订单加锁");
        try {
            productAmountLock.lock();
            if (seckillProductService.getProductAmout(businessOrderDTO.getId()) > 0) {
                String businessOrderId = transactionService.createBusinessOrder(businessOrderDTO);
                if (businessOrderId.equals(StatusConstant.FAILURE)) {
                    logger.info("总库存不足");
                    responseDTO.setResult(StatusConstant.FAILURE);
                    responseDTO.setErrorInfo("总库存不足");
                } else {
                JedisUtils.set("seckillproductOrder:"+businessOrderDTO.getId(),businessOrderId,productInfoCacheSeconds);
                responseDTO.setResponseData(businessOrderId);
                responseDTO.setResult(StatusConstant.SUCCESS);
                }
            } else {
                logger.info("库存不足");
                responseDTO.setResult(StatusConstant.FAILURE);
                responseDTO.setErrorInfo("库存不足");
            }
        } catch (Exception e) {
            logger.info("订单创建异常,异常信息为{}" + e.getMessage(), e);
            e.printStackTrace();
            throw e;
        } finally {
            productAmountLock.unlock();
        }
        logger.info("购买某个商品，创建订单,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 检测订单是否过期
     */
    @RequestMapping(value = "cheackSeckillOrder", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<String> cheackSeckillOrder(@RequestParam String orderID) {
        long startTime = System.currentTimeMillis();
        logger.info("根据订单id查询该订单是否有效", orderID);
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        String order = JedisUtils.get("seckillproductOrder:"+orderID);
        if(StringUtils.isNotNull(order)){
            responseDTO.setResult(StatusConstant.SUCCESS);
        }else{
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        logger.info("根据订单状态获取订单列表,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }
}

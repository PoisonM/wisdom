package com.wisdom.business.controller.transaction;

import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.service.product.SeckillProductService;
import com.wisdom.business.service.transaction.BuyCartService;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 秒杀活动创建订单接口
 * Created by wangbaowei on 2018/7/25.
 */

@Controller
@RequestMapping(value = "seckillOrder")
public class SeckillOrderController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BuyCartService buyCartService;
    @Autowired
    private SeckillProductService seckillProductService;


    /**
     * 秒杀活动购买某个商品，创建订单
     * @param fieldId
     * @param productSpec
     * @param productNum
     * output ResponseDTO<String>
     */
    @RequestMapping(value = "createSeckillOrder", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<String> createSeckillOrder(@RequestParam String fieldId,@RequestParam String productId, @RequestParam String productSpec,@RequestParam int productNum) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        long startTime = System.currentTimeMillis();
        logger.info("秒杀某个商品，创建订单==={}开始", startTime);
        RedisLock productAmountLock = new RedisLock("seckillProduct" + productId);
        logger.info("秒杀商品生成订单加锁");
        try {
            productAmountLock.lock();
            if (seckillProductService.getProductAmout(fieldId)>0) {
                buyCartService.seckillProductBuyNow(productId,productNum,productSpec);
                responseDTO.setResult(StatusConstant.SUCCESS);
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
     * 根据订单状态获取订单列表
     * 0表示未支付，1表示待支付，
     * 2表示已支付且未收货，3表示已支付且已收货，
     * 4表示订单失效
     * <p>
     * input PageParamDto
     * <p>
     * output ResponseDTO<List<ProductDTO>>
     */
    @RequestMapping(value = "businessOrderList", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<List<BusinessOrderDTO>> businessOrderList(@RequestParam String status) {
        long startTime = System.currentTimeMillis();
        logger.info("根据订单状态{}获取订单列表", status);
        ResponseDTO<List<BusinessOrderDTO>> responseDTO = new ResponseDTO<>();
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        //若businessOrderId为""，则用户订单列表是获取所有根据status状态订单，如果不为空，则为指定ID的订单
//        List<BusinessOrderDTO> businessOrderDTOList = transactionService.getBusinessOrderListByUserIdAndStatus(userInfoDTO.getId(), status);
//        responseDTO.setResponseData(businessOrderDTOList);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("根据订单状态获取订单列表,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }
}

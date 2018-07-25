package com.wisdom.business.controller.crossBorder;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.service.product.ProductService;
import com.wisdom.business.service.transaction.BuyCartService;
import com.wisdom.business.service.transaction.PayRecordService;
import com.wisdom.business.service.transaction.TransactionService;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.NeedPayOrderDTO;
import com.wisdom.common.dto.transaction.NeedPayOrderListDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.JedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * 跨境电商 订单相关的接口
 * 包含放入购物车、立即购买 功能
 * Created by wangbaowei on 2018/7/23.
 */


@RequestMapping("crossBorder/order")
public class OrderController {
    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private BuyCartService buyCartService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ProductService productService;
    @Autowired
    private PayRecordService payRecordService;

    /**
     * 加入购物车
     * @param productId
     * @param productNum
     * @return ResponseDto
     */
    @LoginRequired
    @RequestMapping(value = "addProduct2ShoppingCart", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO addProduct2ShoppingCart(@RequestParam String productId, @RequestParam int productNum, HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO<>();
        long startTime = System.currentTimeMillis();
        logger.info("将货品加入用户的购物车==={}开始",startTime);
        String result = buyCartService.addCrossBorderProduct(productId,productNum);
        responseDTO.setResult(result);
        logger.info("将货品加入用户的购物车,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 立即购买、结算
     * @param needPayOrderList
     * @return ResponseDto
     */
    @LoginRequired
    @RequestMapping(value ="settlementOrder",method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO settlementOrder(@RequestBody NeedPayOrderListDTO needPayOrderList) {
        ResponseDTO responseDTO = new ResponseDTO();
        for (NeedPayOrderDTO needPayOrderDTO : needPayOrderList.getNeedPayOrderList()) {
            BusinessOrderDTO businessOrderDTO = new BusinessOrderDTO();
            businessOrderDTO.setBusinessProductId(needPayOrderDTO.getProductId());
            businessOrderDTO.setProductSpec(needPayOrderDTO.getProductSpec());
            logger.info("查询订单前订单号=={}",needPayOrderDTO.getOrderId());
            businessOrderDTO = transactionService.getBusinessOrderByOrderId(needPayOrderDTO.getOrderId());

            UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
            String needPayValue = (new Gson()).toJson(needPayOrderList);
            JedisUtils.del(userInfoDTO.getId()+"needPay");
            JedisUtils.set(userInfoDTO.getId()+"needPay",needPayValue,60*5);
            if("3".equals(businessOrderDTO.getStatus())){
                ProductDTO productDTO = productService.getBusinessProductInfo(needPayOrderDTO.getProductId());
                logger.info("状态3进入查出库中商品库存=={}",productDTO.getProductAmount());
                if (Integer.parseInt(needPayOrderDTO.getProductNum()) > Integer.parseInt(productDTO.getProductAmount())) {
                    responseDTO.setErrorInfo("库存不足");
                    responseDTO.setResult(StatusConstant.FAILURE);
                    return responseDTO;
                }
            }
            businessOrderDTO.setStatus("0");
            businessOrderDTO.setUpdateDate(new Date());
            transactionService.updateBusinessOrder(businessOrderDTO);
        }
        return responseDTO;
    }

    /**
     * 支付--生成支付二维码地址
     * @param request
     * @return ResponseDto
     */
    @LoginRequired
    @RequestMapping(value ="payOrder",method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO payOrder(@RequestBody HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO();
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        String codeUrl = payRecordService.corssBorderPay(request,userInfoDTO.getMobile());
        responseDTO.setResult(codeUrl);
        return responseDTO;
    }
}

package com.wisdom.business.controller.transaction;

import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.service.transaction.BuyCartService;
import com.wisdom.business.service.transaction.TransactionService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
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
 * 微信页面参数获取相关的控制类
 * Created by baoweiw on 2015/7/27.
 */

@Controller
@RequestMapping(value = "transaction")
public class BuyCartController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BuyCartService buyCartService;

    /**
     * 将货品加入用户的购物车
     *
     * input PageParamDto
     *
     * output ResponseDTO<List<ProductDTO>>
     *
     */
    @RequestMapping(value = "addProduct2BuyCart", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO addProduct2BuyCart(@RequestParam String productId, @RequestParam String productSpec,@RequestParam int productNum) {
        long startTime = System.currentTimeMillis();
        logger.info("将货品加入用户的购物车==={}开始",startTime);
        ResponseDTO responseDTO = new ResponseDTO<>();
        String result = buyCartService.addOfflineProduct2BuyCart(productId,productSpec,productNum);
        responseDTO.setResult(result);
        logger.info("将货品加入用户的购物车,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    @RequestMapping(value = "minusProduct2BuyCart", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO minusProduct2BuyCart(@RequestParam String productId, @RequestParam String productSpec) {
        long startTime = System.currentTimeMillis();
        logger.info("购物车减少商品数量==={}开始",startTime);
        logger.info("减少购物车中商品数=={},商品规格=={}",productId,productSpec);
        ResponseDTO responseDTO = new ResponseDTO<>();
        try
        {
            buyCartService.minusProduct2BuyCart(productId,productSpec);
            responseDTO.setResult(StatusConstant.SUCCESS);
        }
        catch (Exception e)
        {
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        logger.info("购物车减少商品数量,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 获取用户购物车中的商品总数
     *
     * input PageParamDto
     *
     * output ResponseDTO<List<ProductDTO>>
     *
     */
    @RequestMapping(value = "getProductNumFromBuyCart", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<String> getProductNumFromBuyCart() {
        long startTime = System.currentTimeMillis();
        logger.info("获取用户购物车中的商品总数==={}开始",startTime);
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try
        {
            responseDTO.setResponseData(buyCartService.getProductNumFromBuyCart());
            responseDTO.setResult(StatusConstant.SUCCESS);
        }
        catch (Exception e)
        {
            logger.error("获取用户购物车中的商品总数异常,异常信息为{}"+e.getMessage(),e);
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        logger.info("获取用户购物车中的商品总数,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }


    /**
     * 获取用户购物车中的信息
     *
     * input PageParamDto
     *
     * output ResponseDTO<List<ProductDTO>>
     *
     */
    @RequestMapping(value = "buyCart", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<List<BusinessOrderDTO>> buyCart() {
        long startTime = System.currentTimeMillis();
        logger.info("获取用户购物车中的信息==={}开始",startTime);
        ResponseDTO<List<BusinessOrderDTO>> responseDTO = new ResponseDTO<>();
        List<BusinessOrderDTO> businessOrderDTOList = buyCartService.getUserUnPayOrderInBuyCart();
        logger.info("获取用户购物车中的信息Size==={}",businessOrderDTOList.size());
        responseDTO.setResponseData(businessOrderDTOList);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("获取用户购物车中的信息,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }

}

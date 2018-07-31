package com.wisdom.business.controller.crossBorder;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.service.product.ProductService;
import com.wisdom.business.service.transaction.BuyCartService;
import com.wisdom.business.service.transaction.PayRecordService;
import com.wisdom.business.service.transaction.TransactionService;
import com.wisdom.business.service.transaction.UserOrderAddressService;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.NeedPayOrderDTO;
import com.wisdom.common.dto.transaction.NeedPayOrderListDTO;
import com.wisdom.common.dto.transaction.OrderAddressRelationDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 跨境电商 订单相关的接口
 * 包含放入购物车、立即购买 功能
 * Created by wangbaowei on 2018/7/23.
 */

@Controller
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
    @Autowired
    private UserOrderAddressService userOrderAddressService;

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
    ResponseDTO addProduct2ShoppingCart(@RequestParam String productId, @RequestParam String productSpec,@RequestParam int productNum) {
        ResponseDTO responseDTO = new ResponseDTO<>();
        long startTime = System.currentTimeMillis();
        logger.info("将货品加入用户的购物车==={}开始",startTime);
        String result = buyCartService.addCrossBorderProduct(productId,productNum,productSpec);
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
    ResponseDTO payOrder(HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO();
        Map<String,String> resultMap = payRecordService.corssBorderPay(request);
        responseDTO.setResponseData(resultMap);
        return responseDTO;
    }
    /**
     * 根据用户手机号和订单状态查询订单
     * @param orderStatus all 代表所有订单,
     * @return
     */
    @LoginRequired
    @RequestMapping(value = "getBorderSpecialOrderListByStauts", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<List<BusinessOrderDTO>> getBorderSpecialOrderListByStauts(@RequestParam String orderStatus) {
        long startTime = System.currentTimeMillis();
        logger.info("根据手机号获取跨境订单列表==={}开始" , startTime);
        ResponseDTO<List<BusinessOrderDTO>> responseDTO = new ResponseDTO<>();
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        List<BusinessOrderDTO> businessOrderDTOS = transactionService.getBusinessOrderListByUserIdAndStatus(userInfoDTO.getId(),orderStatus,"special");
        logger.info("根据手机号获取到的跨境订单个数===" + businessOrderDTOS.size());
        responseDTO.setResponseData(businessOrderDTOS);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("根据手机号获取跨境订单列表结束,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 创建跨境订单关联收货地址
     * @param orderAddressRelationDTO
     * @return
     */
    @LoginRequired
    @RequestMapping(value = "createSpecialOrderAddressRelation", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<List<BusinessOrderDTO>> createSpecialOrderAddressRelation(@RequestBody OrderAddressRelationDTO orderAddressRelationDTO) {

        long startTime = System.currentTimeMillis();
        logger.info("创建跨境订单关联收货地址==={}开始" , startTime);
        ResponseDTO<List<BusinessOrderDTO>> responseDTO = new ResponseDTO<>();
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        String value = JedisUtils.get(userInfoDTO.getId()+"needPay");
        NeedPayOrderListDTO needPayOrderListDTO = (new Gson()).fromJson(value, NeedPayOrderListDTO.class);
        for (NeedPayOrderDTO needPayOrderDTO : needPayOrderListDTO.getNeedPayOrderList()) {
            //查询此订单是否已有地址,如果有则不进行新增
            List<OrderAddressRelationDTO> orderAddressRelationDTOs = userOrderAddressService.getOrderAddressRelationByOrderId(needPayOrderDTO.getOrderId());
            if(0 == orderAddressRelationDTOs.size()){
                //缺少一个身份证字段,DTO,XML,
                logger.info("增加跨境订单{}关联地址{}",needPayOrderDTO.getOrderId());
                orderAddressRelationDTO.setId(UUIDUtil.getUUID());
                orderAddressRelationDTO.setBusinessOrderId(needPayOrderDTO.getOrderId());
                orderAddressRelationDTO.setAddressCreateDate(new Date());
                orderAddressRelationDTO.setAddressUpdateDate(new Date());
                userOrderAddressService.addOrderAddressRelation(orderAddressRelationDTO);
            }else {
                logger.info("修改跨境订单{}关联地址{}", needPayOrderDTO.getOrderId());
                orderAddressRelationDTO.setBusinessOrderId(needPayOrderDTO.getOrderId());
                orderAddressRelationDTO.setAddressUpdateDate(new Date());
                userOrderAddressService.updateOrderAddressRelationByOrderId(orderAddressRelationDTO);
            }
        }
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("创建跨境订单关联收货地址,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }
    /**
     * 跨境订单详情
     * @param
     * @return
     */
    @RequestMapping(value = "queryOrderDetailsById", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<BusinessOrderDTO> queryOrderDetailsById(@RequestParam String orderId) {
        long startTime = System.currentTimeMillis();
        logger.info("跨境订单=={}详情==={}开始" ,orderId,startTime);
        ResponseDTO<BusinessOrderDTO> responseDTO = new ResponseDTO<>();
        BusinessOrderDTO businessOrderDTO = transactionService.queryOrderDetailsById(orderId);
        responseDTO.setResponseData(businessOrderDTO);
        responseDTO.setErrorInfo(StatusConstant.SUCCESS);
        logger.info("跨境订单详情,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 订单列表
     * @return list
     */
    @RequestMapping(value = "crossBordOrderList", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<List<BusinessOrderDTO>> crossBordOrderList(@RequestParam(required=false) String status) {
        long startTime = System.currentTimeMillis();
        logger.info("根据状态获取跨境电商订单列表",status);
        ResponseDTO<List<BusinessOrderDTO>> responseDTO = new ResponseDTO<>();
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        List<BusinessOrderDTO> businessOrderDTOList =  transactionService.getBusinessOrderListByUserIdAndStatus(userInfoDTO.getId(),status,"special");
        responseDTO.setResponseData(businessOrderDTOList);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("根据订单状态获取订单列表,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }


    /**
     * 根据订单号查询订单是否支付完成
     * @return list
     */
    @RequestMapping(value = "cheackOrderStatus" ,method= {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<String> cheackOrderStatus(@RequestParam String transactionId) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        List<PayRecordDTO> list = payRecordService.queryUserInfoByTransactionId(transactionId);
        responseDTO.setResponseData("success");
        for(PayRecordDTO recordDTO : list){
            if(recordDTO.getStatus() == "0"){
                responseDTO.setResponseData("false");
                break ;
            }
        }
        return responseDTO;
    }
}

package com.wisdom.business.controller.transaction;

import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.service.product.ProductService;
import com.wisdom.business.service.transaction.BuyCartService;
import com.wisdom.business.service.transaction.PayRecordService;
import com.wisdom.business.service.transaction.TransactionService;
import com.wisdom.business.service.transaction.UserOrderAddressService;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.dto.system.ExportOrderExcelDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.system.UserOrderAddressDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.OrderAddressRelationDTO;
import com.wisdom.common.dto.transaction.OrderCopRelationDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.*;
import com.wisdom.common.util.excel.ExportExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 微信页面参数获取相关的控制类
 * Created by baoweiw on 2015/7/27.
 */

@Controller
@RequestMapping(value = "transaction")
public class BusinessOrderController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BuyCartService buyCartService;

    @Autowired
    private UserOrderAddressService userOrderAddressService;

    @Autowired
    private PayRecordService payRecordService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserServiceClient userServiceClient;

    /**
     * 购买某个商品，创建订单
     *
     * input PageParamDto
     *
     * output ResponseDTO<List<ProductDTO>>
     *
     */
    @RequestMapping(value = "createBusinessOrder", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<String> createBusinessOrder(@RequestBody BusinessOrderDTO businessOrderDTO) {
        long startTime = System.currentTimeMillis();
        logger.info("购买某个商品，创建订单==={}开始" , startTime);
        RedisLock productAmountLock = new RedisLock("putNeedPayProductAmount");
        //todo log
        logger.info("锁前商品id=={}", businessOrderDTO.getBusinessProductId());
        productAmountLock.lock();
        logger.info("锁后商品id=={}", businessOrderDTO.getBusinessProductId());
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try {
            ProductDTO productDTO = productService.getBusinessProductInfo(businessOrderDTO.getBusinessProductId());
            if (businessOrderDTO.getBusinessProductNum() > Integer.parseInt(productDTO.getProductAmount())) {
                logger.info("库存不足订单创建失败");
                responseDTO.setResult(StatusConstant.FAILURE);
                responseDTO.setErrorInfo("订单创建失败");
            }
            String businessOrderId = transactionService.createBusinessOrder(businessOrderDTO);
            if(businessOrderId.equals(StatusConstant.FAILURE))
            {
                logger.info("订单创建失败");
                responseDTO.setResult(StatusConstant.FAILURE);
                responseDTO.setErrorInfo("订单创建失败");
            }
            else if(businessOrderId.equals(StatusConstant.NO_USER_ADDRESS))
            {
                responseDTO.setResult(StatusConstant.NO_USER_ADDRESS);
            }
            else
            {
                responseDTO.setResponseData(businessOrderId);
                responseDTO.setResult(StatusConstant.SUCCESS);
                responseDTO.setErrorInfo("订单创建成功");
            }
        }catch (Exception e)
        {
            logger.info("订单创建异常,异常信息为{}"+e.getMessage(),e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            productAmountLock.unlock();
        }
        logger.info("购买某个商品，创建订单,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 获取某个订单的情况
     *
     * input PageParamDto
     *
     * output ResponseDTO<List<ProductDTO>>
     *
     */
    @RequestMapping(value = "getTrainingBusinessOrder", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<BusinessOrderDTO> getTrainingBusinessOrder(@RequestParam String productId, HttpSession session, HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        logger.info("获取某个订单的情况==={}开始" , startTime);
        logger.info("获取某个订单{}的情况" , productId);
        ResponseDTO<BusinessOrderDTO> responseDTO = new ResponseDTO<>();

        //判断此课程是否是免费课程
        ProductDTO productDTO = productService.getBusinessProductInfo(productId);
        if(Float.parseFloat(productDTO.getPrice())==0.00)
        {
            logger.info("此课程为免费课程" );
            responseDTO.setResult(StatusConstant.SUCCESS);
        }
        else
        {
            //先获取用户的openid
            String openId = WeixinUtil.getUserOpenId(session,request);
            logger.info("先获取用户的openid" , openId);
            BusinessOrderDTO businessOrderDTO = new BusinessOrderDTO();
            if(openId==null)
            {
                responseDTO.setResult(StatusConstant.FAILURE);
            }
            else
            {
                UserInfoDTO userInfoDTO = new UserInfoDTO();
                userInfoDTO.setUserOpenid(openId);
                List<UserInfoDTO> userInfoDTOS = userServiceClient.getUserInfo(userInfoDTO);
                businessOrderDTO.setBusinessProductId(productId);
                businessOrderDTO.setSysUserId(userInfoDTOS.get(0).getId());
                businessOrderDTO = transactionService.getTrainingBusinessOrder(businessOrderDTO);
                if(businessOrderDTO==null)
                {
                    responseDTO.setResult(StatusConstant.FAILURE);
                }
                else
                {
                    if(businessOrderDTO.getStatus().equals("0"))
                    {
                        responseDTO.setResult(StatusConstant.FAILURE);
                    }
                    else if(businessOrderDTO.getStatus().equals("1")||businessOrderDTO.getStatus().equals("2"))
                    {
                        responseDTO.setResult(StatusConstant.SUCCESS);
                    }
                }
            }
        }
        logger.info("获取某个订单的情况,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }


    /**
     * 更改某个订单的状态，可以修改为未支付、待支付等状态
     * 0表示未支付，1表示待支付，2表示已支付，3表示订单失效
     *
     * input PageParamDto
     *
     * output ResponseDTO<List<ProductDTO>>
     *
     */
    @RequestMapping(value = "updateBusinessOrderStatus", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<String> updateBusinessOrderStatus(@RequestBody BusinessOrderDTO businessOrderDTO) {
        logger.info("更改某个订单的状态orderId={}"+businessOrderDTO.getBusinessOrderId(), "orderStatus = [" + businessOrderDTO.getStatus() + "]");
        logger.info("更改某个订单的状态{}", businessOrderDTO.getStatus());
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if(null == businessOrderDTO.getStatus() || "".equals(businessOrderDTO.getStatus())){
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setErrorInfo("订单状态参数为空");
            return responseDTO;
        }
        try
        {
            //查出库中订单数据
            BusinessOrderDTO newBusinessOrderDTO = transactionService.getBusinessOrderByOrderId(businessOrderDTO.getBusinessOrderId());
            newBusinessOrderDTO.setUpdateDate(new Date());
            newBusinessOrderDTO.setStatus(businessOrderDTO.getStatus());
            transactionService.updateBusinessOrder(newBusinessOrderDTO);
            responseDTO.setResult(StatusConstant.SUCCESS);
        }
        catch (Exception e)
        {
            logger.info("更改某个订单的状态异常,异常信息为{}" +e.getMessage(),e);
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        return responseDTO;
    }

    @RequestMapping(value = "updateBusinessOrderAddress", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<String> updateBusinessOrderAddress(@RequestParam("orderIds[]") List<String> orderIds, @RequestParam String orderAddressId) {
        logger.info("修改订单地址方法传入参数={}", "订单数量orderIdsSize = [" + orderIds.size() + "]", "订单地址id = [" + orderAddressId + "]");

        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try
        {
            for(String orderId:orderIds)
            {
                logger.info("修改订单{}地址",orderId);
                BusinessOrderDTO businessOrderDTO = transactionService.getBusinessOrderByOrderId(orderId);
                businessOrderDTO.setUserOrderAddressId(orderAddressId);
                transactionService.updateBusinessOrder(businessOrderDTO);
                //根据地址id查询地址
                UserOrderAddressDTO userOrderAddressDTO =  userOrderAddressService.findUserAddressById(orderAddressId);
                //查询此订单是否已有地址,如果有则不进行新增
                List<OrderAddressRelationDTO> orderAddressRelationDTOs = userOrderAddressService.getOrderAddressRelationByOrderId(orderId);
                if(0 == orderAddressRelationDTOs.size()){
                    logger.info("增加订单{}关联地址{}",orderId,orderAddressId);
                    OrderAddressRelationDTO orderAddressRelationDTO1 = new OrderAddressRelationDTO();
                    orderAddressRelationDTO1.setId(UUIDUtil.getUUID());
                    orderAddressRelationDTO1.setBusinessOrderId(orderId);
                    orderAddressRelationDTO1.setUserOrderAddressId(orderAddressId);
                    orderAddressRelationDTO1.setUserNameAddress(userOrderAddressDTO.getUserName());
                    orderAddressRelationDTO1.setUserPhoneAddress(userOrderAddressDTO.getUserPhone());
                    orderAddressRelationDTO1.setUserProvinceAddress(userOrderAddressDTO.getProvince());
                    orderAddressRelationDTO1.setUserCityAddress(userOrderAddressDTO.getCity());
                    orderAddressRelationDTO1.setUserDetailAddress(userOrderAddressDTO.getDetailAddress());
                    orderAddressRelationDTO1.setAddressCreateDate(new Date());
                    orderAddressRelationDTO1.setAddressUpdateDate(new Date());
                    logger.info("订单没有地址插入订单地址"+orderAddressRelationDTO1.toString());
                    userOrderAddressService.addOrderAddressRelation(orderAddressRelationDTO1);
                }else {
                    logger.info("修改订单{}关联地址{}",orderId,orderAddressId);
                    OrderAddressRelationDTO orderAddressRelationDTO1 = new OrderAddressRelationDTO();
                    orderAddressRelationDTO1.setBusinessOrderId(orderId);
                    orderAddressRelationDTO1.setUserOrderAddressId(orderAddressId);
                    orderAddressRelationDTO1.setUserNameAddress(userOrderAddressDTO.getUserName());
                    orderAddressRelationDTO1.setUserPhoneAddress(userOrderAddressDTO.getUserPhone());
                    orderAddressRelationDTO1.setUserProvinceAddress(userOrderAddressDTO.getProvince());
                    orderAddressRelationDTO1.setUserCityAddress(userOrderAddressDTO.getCity());
                    orderAddressRelationDTO1.setUserDetailAddress(userOrderAddressDTO.getDetailAddress());
                    orderAddressRelationDTO1.setAddressUpdateDate(new Date());
                    logger.info("订单已有地址修改订单地址"+orderAddressRelationDTO1.toString());
                    userOrderAddressService.updateOrderAddressRelationByOrderId(orderAddressRelationDTO1);
                }
            }
            responseDTO.setResult(StatusConstant.SUCCESS);
        }
        catch (Exception e)
        {
            logger.info("修改订单地址方法异常,异常信息为{}"+e.getMessage(),e);
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        return responseDTO;
    }

    /**
     * 根据订单状态获取订单列表
     * 0表示未支付，1表示待支付，
     * 2表示已支付且未收货，3表示已支付且已收货，
     * 4表示订单失效
     *
     * input PageParamDto
     *
     * output ResponseDTO<List<ProductDTO>>
     *
     */
    @RequestMapping(value = "businessOrderList", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<List<BusinessOrderDTO>> businessOrderList(@RequestParam String status) {
        long startTime = System.currentTimeMillis();
        logger.info("根据订单状态{}获取订单列表",status);
        ResponseDTO<List<BusinessOrderDTO>> responseDTO = new ResponseDTO<>();
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        //若businessOrderId为""，则用户订单列表是获取所有根据status状态订单，如果不为空，则为指定ID的订单
        List<BusinessOrderDTO> businessOrderDTOList =  transactionService.getBusinessOrderListByUserIdAndStatus(userInfoDTO.getId(),status);
        responseDTO.setResponseData(businessOrderDTOList);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("根据订单状态获取订单列表,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 删除用户订单
     *
     * input PageParamDto
     *
     * output ResponseDTO
     *
     */
    @RequestMapping(value = "deleteOrderFromBuyCart", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO deleteOrderFromBuyCart(@RequestParam String orderId) {
        long startTime = System.currentTimeMillis();
        logger.info("删除用户订单{}",orderId);
        ResponseDTO responseDTO = new ResponseDTO<>();
        try{
            buyCartService.deleteOrderFromBuyCart(orderId);
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setErrorInfo("删除订单成功");
        }catch (Exception e){
            logger.info("删除用户订单异常,异常信息为{}"+e.getMessage(),e);
            responseDTO.setErrorInfo("删除订单失败");
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        logger.info("删除用户订单,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 待发货导出excel到OSS并修改状态为已发货
     * @param
     * @return
     */
    @RequestMapping(value = "exportExcelToOSS", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO exportExcelToSSO() {
        long startTime = System.currentTimeMillis();
        logger.info("待发货导出excel到OSS并修改状态为已发货");
        ResponseDTO responseDTO = new ResponseDTO<>();
        ExportExcel<ExportOrderExcelDTO> ex = new ExportExcel<>();
        List<ExportOrderExcelDTO> list = transactionService.selectExcelContent();
        logger.info("待发货导出excel到OSS并修改状态为已发货Size==={}",list.size());
        try{
            String[] headers =
                    {"订单编号","用户id","用户名","用户手机号", "付款时间", "商品品牌","商品编号", "商品名称", "商品规格", "商品数量"
                            ,"订单状态", "收货人姓名", "收货人电话",
                            "收货人详细地址", "订单金额", "是否要发票", "发票抬头", "纳税人识别号"};
            //ByteArrayInputStream in = ex.getWorkbookIn("代发货订单EXCEL文档",headers, list,"yyy-MM-dd");
            ByteArrayInputStream in = ex.getWorkbookIn("订单EXCEL文档",headers,list);//JXL
            String url = CommonUtils.orderExcelToOSS(in);
            logger.info("待发货导出Url=={}到OSS并修改状态为已发货",url);
            responseDTO.setResult(url);
            responseDTO.setErrorInfo(StatusConstant.SUCCESS);
            System.out.println("excel导出成功！");
            in.close();
        }catch (Exception e){
            logger.info("待发货导出excel到OSS并修改状态为已发货异常,异常信息为{}"+e.getMessage(),e);
            e.printStackTrace();
            responseDTO.setErrorInfo(StatusConstant.FAILURE);
            System.out.println("excel导出失败！");
        }
        logger.info("删除用户订单,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 查询所有订单
     * @return
     */
    /*@RequestMapping(value = "queryAllBusinessOrders", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<PageParamDTO<List<BusinessOrderDTO>>>  queryAllBusinessOrders(@RequestBody PageParamVoDTO<BusinessOrderDTO> pageParamVoDTO) {
        long startTime = System.currentTimeMillis();
        logger.info("查询所有订单==={}开始" , startTime);
        ResponseDTO<PageParamDTO<List<BusinessOrderDTO>>> responseDTO = new ResponseDTO<>();
        PageParamDTO<List<BusinessOrderDTO>> page = transactionService.queryAllBusinessOrders(pageParamVoDTO);
        responseDTO.setResponseData(page);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("查询所有订单,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }*/

    /**
     * 条件查询查询订单
     * @return
     */
    @RequestMapping(value = "queryBusinessOrderByParameters", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<PageParamVoDTO<List<BusinessOrderDTO>>>  queryBusinessOrderByParameters(@RequestBody PageParamVoDTO<BusinessOrderDTO> pageParamVoDTO) {
        long startTime = System.currentTimeMillis();
        logger.info("条件查询查询订单==={}开始" , startTime);
        logger.info("条件查询查询订单是否导表==={}" , pageParamVoDTO.getIsExportExcel());
        ResponseDTO<PageParamVoDTO<List<BusinessOrderDTO>>> responseDTO = new ResponseDTO<>();
        //设定起始时间 0 1 2 为时间类型
        String startDate = "1990-01-01";
        if (!"0".equals(pageParamVoDTO.getTimeType())){
            pageParamVoDTO.setStartTime("".equals(pageParamVoDTO.getStartTime()) ? startDate : pageParamVoDTO.getStartTime());
            pageParamVoDTO.setEndTime(CommonUtils.getEndDate(pageParamVoDTO.getEndTime()));
        }
        PageParamVoDTO<List<BusinessOrderDTO>> page = transactionService.queryBusinessOrderByParameters(pageParamVoDTO);
        if("Y".equals(pageParamVoDTO.getIsExportExcel())){
            try{
                String[] orderHeaders =
                        {"订单编号","用户id","用户名","用户手机号", "付款时间", "商品品牌","商品编号", "商品名称", "商品规格", "商品数量"
                                ,"订单状态", "收货人姓名", "收货人电话",
                                "收货人详细地址", "订单金额", "是否要发票", "发票抬头", "纳税人识别号"};
                ExportExcel<ExportOrderExcelDTO> ex = new ExportExcel<>();
                List<ExportOrderExcelDTO> excelList= new ArrayList<>();
                for (BusinessOrderDTO businessOrderDTO : page.getResponseData()) {
                    BusinessOrderDTO businessOrderinfo = transactionService.queryOrderDetailsById(businessOrderDTO.getBusinessOrderId());
                    ExportOrderExcelDTO exportOrderExcelDTO = new ExportOrderExcelDTO();
                    exportOrderExcelDTO.setId(businessOrderDTO.getId());
                    exportOrderExcelDTO.setNickName(businessOrderDTO.getNickName());
                    exportOrderExcelDTO.setAmount(businessOrderinfo.getAmount());
                    exportOrderExcelDTO.setMobile(businessOrderDTO.getMobile());
                    exportOrderExcelDTO.setOrderId(businessOrderDTO.getBusinessOrderId());
                    exportOrderExcelDTO.setOrderStatus(businessOrderDTO.getStatus());
                    if(null!=businessOrderinfo.getPayDate()) {
                        exportOrderExcelDTO.setPayDate(DateUtils.formatDate(businessOrderinfo.getPayDate(), "yyyy-MM-dd HH:mm:ss"));
                    }else{
                        exportOrderExcelDTO.setPayDate(null);
                    }
                    exportOrderExcelDTO.setProductBrand(businessOrderinfo.getProductBrand());
                    exportOrderExcelDTO.setProductId(businessOrderinfo.getBusinessProductId());
                    exportOrderExcelDTO.setProductName(businessOrderinfo.getBusinessProductName());
                    exportOrderExcelDTO.setProductNum(businessOrderinfo.getBusinessProductNum()+"");
                    exportOrderExcelDTO.setProductSpec(businessOrderinfo.getProductSpec());
                    if(businessOrderinfo.getUserAddress()!=null){
                        exportOrderExcelDTO.setUserAddress(businessOrderinfo.getUserProvinceAddress()+businessOrderinfo.getUserDetailAddress());
                    }else{
                        exportOrderExcelDTO.setUserAddress(businessOrderinfo.getUserProvinceAddress()+businessOrderinfo.getUserDetailAddress());
                    }
                    exportOrderExcelDTO.setUserNameAddress(businessOrderinfo.getUserNameAddress());
                    exportOrderExcelDTO.setUserPhoneAddress(businessOrderinfo.getUserPhoneAddress());
                    excelList.add(exportOrderExcelDTO);
                }
                ByteArrayInputStream in = ex.getWorkbookIn("新订单EXCEL文档",orderHeaders, excelList);
                String url = CommonUtils.orderExcelToOSS(in);
                logger.info("条件查询查询订单导出Url==={}" , url);
                responseDTO.setResult(url);
                responseDTO.setErrorInfo(StatusConstant.SUCCESS);
            }catch (Exception e){
                logger.info("条件查询查询订单导出异常,异常信息为==={}" +e.getMessage(),e);
                e.printStackTrace();
                responseDTO.setErrorInfo(StatusConstant.FAILURE);
            }
            return responseDTO;
        }
        responseDTO.setResponseData(page);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("条件查询查询订单,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 给订单绑定相应的COP号
     * @param
     * @return
     */
    @RequestMapping(value = "insertOrderCopRelation", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO insertOrderCopRelation(@RequestBody OrderCopRelationDTO orderCopRelationDTO) {
        long startTime = System.currentTimeMillis();
        logger.info("给订单绑定相应的COP号==={}开始" , startTime);
        ResponseDTO responseDTO = new ResponseDTO<>();
        OrderCopRelationDTO orderCopRelation = new OrderCopRelationDTO();
        orderCopRelation.setOrderId(orderCopRelationDTO.getOrderId());
        List<OrderCopRelationDTO> orderCopRelationList = transactionService.queryOrderCopRelationById(orderCopRelation);
        if (orderCopRelationList.size() != 0) {
            try {
                logger.info("给订单=={}修改绑定相应的COP号==={}" , orderCopRelationDTO.getOrderId(),orderCopRelationDTO.getWaybillNumber());
                transactionService.updateOrderCopRelation(orderCopRelationDTO);
                responseDTO.setResult(StatusConstant.SUCCESS);
                responseDTO.setErrorInfo("修改成功");
            } catch (Exception e) {
                logger.info("给订单修改绑定相应的COP号异常,异常信息为==={}"+e.getMessage(),e);
                responseDTO.setResult(StatusConstant.FAILURE);
                responseDTO.setErrorInfo("修改失败");
                e.printStackTrace();
            }
        } else {
            try {
                String uuid = UUIDUtil.getUUID();
                orderCopRelationDTO.setId(uuid);
                transactionService.insertOrderCopRelation(orderCopRelationDTO);
                logger.info("给订单=={}绑定相应的COP号==={}" , orderCopRelationDTO.getOrderId(),orderCopRelationDTO.getWaybillNumber());
                responseDTO.setResult(StatusConstant.SUCCESS);
                responseDTO.setErrorInfo("绑定成功");
            } catch (Exception e) {
                logger.info("给订单绑定相应的COP号异常,异常信息为==={}"+e.getMessage(),e);
                responseDTO.setResult(StatusConstant.FAILURE);
                responseDTO.setErrorInfo("绑定失败");
                e.printStackTrace();
            }
        }
        logger.info("给订单绑定相应的COP号,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 查询订单绑定相应的COP号
     * @param
     * @return
     */
    @RequestMapping(value = "queryOrderCopRelationById", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<List<OrderCopRelationDTO>> queryOrderCopRelationById(@RequestBody OrderCopRelationDTO orderCopRelationDTO) {
        long startTime = System.currentTimeMillis();
        logger.info("查询订单绑定相应的COP号==={}开始" ,startTime);
        ResponseDTO<List<OrderCopRelationDTO>> responseDTO = new ResponseDTO<>();
        List<OrderCopRelationDTO> orderCopRelationList = transactionService.queryOrderCopRelationById(orderCopRelationDTO);
        logger.info("查询订单绑定相应的COP号Size==={}" ,orderCopRelationList.size());
        responseDTO.setResponseData(orderCopRelationList);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("查询订单绑定相应的COP号,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 订单详情
     * @param
     * @return
     */
    @RequestMapping(value = "queryOrderDetailsById", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<BusinessOrderDTO> queryOrderDetailsById(@RequestParam String orderId) {
        long startTime = System.currentTimeMillis();
        logger.info("订单=={}详情==={}开始" ,orderId,startTime);
        ResponseDTO<BusinessOrderDTO> responseDTO = new ResponseDTO<>();
        BusinessOrderDTO businessOrderDTO = transactionService.queryOrderDetailsById(orderId);
        responseDTO.setResponseData(businessOrderDTO);
        responseDTO.setErrorInfo(StatusConstant.SUCCESS);
        logger.info("订单详情,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 修改订单收货地址
     *
     * input PageParamDto
     *
     * output ResponseDTO
     *
     */
    @RequestMapping(value = "updateOrderAddress", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO updateOrderAddress(@RequestBody UserOrderAddressDTO userOrderAddressDTO) {
        long startTime = System.currentTimeMillis();
        logger.info("修改订单收货地址==={}开始" ,startTime);
        ResponseDTO responseDTO = new ResponseDTO<>();
        try{
            transactionService.updateOrderAddress(userOrderAddressDTO);
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setErrorInfo("更新用户收货地址成功");
        }catch (Exception e){
            responseDTO.setErrorInfo("更新用户收货地址失败");
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        logger.info("修改订单收货地址,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    @RequestMapping(value = "orderAddressDetail", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<UserOrderAddressDTO> orderAddressDetail(@RequestParam String orderId) {
        long startTime = System.currentTimeMillis();
        logger.info("订单=={}收货地址详情==={}开始" ,orderId,startTime);
        ResponseDTO responseDTO = new ResponseDTO<>();
        //根据订单ID，查询此订单的收货地址
        UserOrderAddressDTO userOrderAddressDTO1 =  userOrderAddressService.getUserOrderAddressByOrderId(orderId);
        List<OrderAddressRelationDTO> orderAddressRelationDTOS =  userOrderAddressService.getOrderAddressRelationByOrderId(orderId);
        UserOrderAddressDTO userOrderAddressDTO =new UserOrderAddressDTO();
        if(orderAddressRelationDTOS.size() != 0){
            userOrderAddressDTO.setUserName(orderAddressRelationDTOS.get(0).getUserNameAddress());
            userOrderAddressDTO.setUserPhone(orderAddressRelationDTOS.get(0).getUserPhoneAddress());
            userOrderAddressDTO.setCity(orderAddressRelationDTOS.get(0).getUserCityAddress());
            userOrderAddressDTO.setDetailAddress(orderAddressRelationDTOS.get(0).getUserDetailAddress());
            userOrderAddressDTO.setId(orderAddressRelationDTOS.get(0).getId());
            userOrderAddressDTO.setProvince(orderAddressRelationDTOS.get(0).getUserProvinceAddress());
        }
        if(userOrderAddressDTO!=null)
        {
            responseDTO.setResponseData(userOrderAddressDTO);
            responseDTO.setResult(StatusConstant.SUCCESS);
        }
        else
        {
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        logger.info("订单收货地址详情,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    @RequestMapping(value = "orderDetailInfo", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<BusinessOrderDTO> orderDetailInfo(@RequestParam String orderId) {
        long startTime = System.currentTimeMillis();
        logger.info("订单=={}详情orderDetailInfo==={}开始" ,orderId,startTime);
        ResponseDTO<BusinessOrderDTO> responseDTO = new ResponseDTO<>();

        //根据订单ID获取订单的详细信息
        BusinessOrderDTO businessOrderDTO = transactionService.getBusinessOrderDetailInfoByOrderId(orderId);
        if(businessOrderDTO!=null)
        {
            responseDTO.setResponseData(businessOrderDTO);

            //根据orderId获取这个orderId的支付时间
            PayRecordDTO payRecordDTO = new PayRecordDTO();
            payRecordDTO.setOrderId(orderId);
            List<PayRecordDTO> payRecordDTOS = payRecordService.getUserPayRecordList(payRecordDTO);
            if(payRecordDTOS.size()>0)
            {
                businessOrderDTO.setTransactionId(payRecordDTOS.get(0).getTransactionId());
                businessOrderDTO.setPayDate(payRecordDTOS.get(0).getPayDate());
            }

            //todo 根据orderId获取发货时间
            Date sendDate = transactionService.getBusinessOrderSendDate(orderId);
            businessOrderDTO.setSendDate(sendDate);

            responseDTO.setResponseData(businessOrderDTO);
            responseDTO.setResult(StatusConstant.SUCCESS);
        }
        else
        {
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        logger.info("订单详情orderDetailInfo,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }

}

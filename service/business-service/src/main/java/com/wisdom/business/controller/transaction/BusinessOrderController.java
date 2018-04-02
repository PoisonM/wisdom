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
import com.wisdom.common.dto.system.*;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.OrderCopRelationDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.UUIDUtil;
import com.wisdom.common.util.WeixinUtil;
import com.wisdom.common.util.excel.ExportExcel;
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

        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        String businessOrderId = transactionService.createBusinessOrder(businessOrderDTO);

        if(businessOrderId.equals(StatusConstant.FAILURE))
        {
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

        ResponseDTO<BusinessOrderDTO> responseDTO = new ResponseDTO<>();

        //判断此课程是否是免费课程
        ProductDTO productDTO = productService.getBusinessProductInfo(productId);
        if(Float.parseFloat(productDTO.getPrice())==0.00)
        {
            responseDTO.setResult(StatusConstant.SUCCESS);
        }
        else
        {
            //先获取用户的openid
            String openId = WeixinUtil.getCustomerOpenId(session,request);
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
                    else if(businessOrderDTO.getStatus().equals("1"))
                    {
                        responseDTO.setResult(StatusConstant.SUCCESS);
                    }
                }
            }
        }

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

        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try
        {
            transactionService.updateBusinessOrderStatus(businessOrderDTO);
            responseDTO.setResult(StatusConstant.SUCCESS);
        }
        catch (Exception e)
        {
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        return responseDTO;
    }

    @RequestMapping(value = "updateBusinessOrderAddress", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<String> updateBusinessOrderAddress(@RequestParam("orderIds[]") List<String> orderIds, @RequestParam String orderAddressId) {

        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        try
        {
            for(String orderId:orderIds)
            {
                BusinessOrderDTO businessOrderDTO = transactionService.getBusinessOrderByOrderId(orderId);
                businessOrderDTO.setUserOrderAddressId(orderAddressId);
                transactionService.updateBusinessOrder(businessOrderDTO);
            }
            responseDTO.setResult(StatusConstant.SUCCESS);
        }
        catch (Exception e)
        {
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

        ResponseDTO<List<BusinessOrderDTO>> responseDTO = new ResponseDTO<>();
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        //若businessOrderId为""，则用户订单列表是获取所有根据status状态订单，如果不为空，则为指定ID的订单
        List<BusinessOrderDTO> businessOrderDTOList =  transactionService.getBusinessOrderListByUserIdAndStatus(userInfoDTO.getId(),status);
        responseDTO.setResponseData(businessOrderDTOList);
        responseDTO.setResult(StatusConstant.SUCCESS);
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

        ResponseDTO responseDTO = new ResponseDTO<>();
        try{
            buyCartService.deleteOrderFromBuyCart(orderId);
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setErrorInfo("删除订单成功");
        }catch (Exception e){
            responseDTO.setErrorInfo("删除订单失败");
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        return responseDTO;
    }

    /**
     * 导出excel到OSS
     * @param
     * @return
     */
    @RequestMapping(value = "exportExcelToOSS", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO exportExcelToSSO() {
        ResponseDTO responseDTO = new ResponseDTO<>();
        ExportExcel<ExportOrderExcelDTO> ex = new ExportExcel<>();
        List<ExportOrderExcelDTO> list = transactionService.selectExcelContent();
        try{
            String[] headers =
                    /*{"订单编号","用户手机号", "付款时间", "商品品牌", "商品名称", "商品规格", "商品数量"
                            ,"订单状态", "收货人姓名", "收货人电话",
                            "收货人详细地址", "订单金额", "是否要发票", "发票抬头", "纳税人识别号"};*/
                    {"订单编号","用户id","用户名","用户手机号", "付款时间", "商品品牌","商品编号", "商品名称", "商品规格", "商品数量"
                            ,"订单状态", "收货人姓名", "收货人电话",
                            "收货人详细地址", "订单金额", "是否要发票", "发票抬头", "纳税人识别号"};
            //ByteArrayInputStream in = ex.getWorkbookIn("代发货订单EXCEL文档",headers, list,"yyy-MM-dd");
            ByteArrayInputStream in = ex.getWorkbookIn("订单EXCEL文档",headers,list);//JXL
            String url = CommonUtils.orderExcelToOSS(in);
            responseDTO.setResult(url);
            responseDTO.setErrorInfo(StatusConstant.SUCCESS);
            System.out.println("excel导出成功！");
            in.close();
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorInfo(StatusConstant.FAILURE);
            System.out.println("excel导出失败！");
        }

        return responseDTO;
    }

    /**
     * 查询所有订单
     * @return
     */
    @RequestMapping(value = "queryAllBusinessOrders", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<PageParamDTO<List<BusinessOrderDTO>>>  queryAllBusinessOrders(@RequestBody PageParamVoDTO<BusinessOrderDTO> pageParamVoDTO) {

        ResponseDTO<PageParamDTO<List<BusinessOrderDTO>>> responseDTO = new ResponseDTO<>();
        PageParamDTO<List<BusinessOrderDTO>> page = transactionService.queryAllBusinessOrders(pageParamVoDTO);
        responseDTO.setResponseData(page);
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

    /**
     * 条件查询查询订单
     * @return
     */
    @RequestMapping(value = "queryBusinessOrderByParameters", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<PageParamVoDTO<List<BusinessOrderDTO>>>  queryBusinessOrderByParameters(@RequestBody PageParamVoDTO<BusinessOrderDTO> pageParamVoDTO) {
        ResponseDTO<PageParamVoDTO<List<BusinessOrderDTO>>> responseDTO = new ResponseDTO<>();
        String startDate = "1990-01-01";//设定起始时间 0 1 2 为时间类型
        if (!"0".equals(pageParamVoDTO.getTimeType())){
            pageParamVoDTO.setStartTime("".equals(pageParamVoDTO.getStartTime()) ? startDate : pageParamVoDTO.getStartTime());
            pageParamVoDTO.setEndTime(CommonUtils.getEndDate(pageParamVoDTO.getEndTime()));
        }
        PageParamVoDTO<List<BusinessOrderDTO>> page = transactionService.queryBusinessOrderByParameters(pageParamVoDTO);
        if("Y".equals(pageParamVoDTO.getIsExportExcel())){
            try{
                String[] orderHeaders =
                        /*{"id","用户id","订单编号ID","订单地址id","订单商品id","订单类型","订单状态","创建时间","变更时间",
                                "商品数量", "商品名字","商品图片url", "商品价格","此订单价格","收货人姓名",  "收货人联系方式", "收货地址",
                                "商品型号","用户名","用户手机号","身份证号","付款时间", "senderAddress"};*/
                        {"订单编号","用户id","用户名","用户手机号", "付款时间", "商品品牌","商品编号", "商品名称", "商品规格", "商品数量"
                                ,"订单状态", "收货人姓名", "收货人电话",
                                "收货人详细地址", "订单金额", "是否要发票", "发票抬头", "纳税人识别号"};
                ExportExcel<ExportOrderExcelDTO> ex = new ExportExcel<>();
                List<ExportOrderExcelDTO> excelList= new ArrayList<>();
                for (BusinessOrderDTO businessOrderDTO : page.getResponseData()) {
                    ExportOrderExcelDTO exportOrderExcelDTO = new ExportOrderExcelDTO();
                    exportOrderExcelDTO.setId(businessOrderDTO.getId());
                    exportOrderExcelDTO.setNickName(businessOrderDTO.getNickName());
                    exportOrderExcelDTO.setAmount(businessOrderDTO.getAmount());
                    //exportOrderExcelDTO.setCompanyName();
                    //exportOrderExcelDTO.setInvoice(businessOrderDTO.g);
                    exportOrderExcelDTO.setMobile(businessOrderDTO.getMobile());
                    exportOrderExcelDTO.setOrderId(businessOrderDTO.getBusinessOrderId());
                    exportOrderExcelDTO.setOrderStatus(businessOrderDTO.getStatus());
                    //exportOrderExcelDTO.setPayDate(businessOrderDTO.g);
                    //exportOrderExcelDTO.setProductBrand(businessOrderDTO.g);
                    exportOrderExcelDTO.setProductId(businessOrderDTO.getBusinessProductId());
                    exportOrderExcelDTO.setProductName(businessOrderDTO.getBusinessProductName());
                    //exportOrderExcelDTO.setProductNum(businessOrderDTO.g);
                    exportOrderExcelDTO.setProductSpec(businessOrderDTO.getProductSpec());
                    //exportOrderExcelDTO.setTaxpayerNumber(businessOrderDTO.getIdentifyNumber());
                    exportOrderExcelDTO.setUserAddress(businessOrderDTO.getUserAddress());
                    exportOrderExcelDTO.setUserName(businessOrderDTO.getUserNameAddress());
                    exportOrderExcelDTO.setUserPhone(businessOrderDTO.getUserPhoneAddress());
                    excelList.add(exportOrderExcelDTO);
                }
                //ByteArrayInputStream in = ex.getWorkbookIn("订单EXCEL文档",orderHeaders, page.getResponseData(),"yyy-MM-dd HH:mm:ss");
                ByteArrayInputStream in = ex.getWorkbookIn("新订单EXCEL文档",orderHeaders, excelList);
                String url = CommonUtils.orderExcelToOSS(in);
                responseDTO.setResult(url);
                responseDTO.setErrorInfo(StatusConstant.SUCCESS);
            }catch (Exception e){
                e.printStackTrace();
                responseDTO.setErrorInfo(StatusConstant.FAILURE);
            }
            return responseDTO;
        }

        responseDTO.setResponseData(page);
        responseDTO.setResult(StatusConstant.SUCCESS);
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
        ResponseDTO responseDTO = new ResponseDTO<>();
        OrderCopRelationDTO orderCopRelation = new OrderCopRelationDTO();
        orderCopRelation.setOrderId(orderCopRelationDTO.getOrderId());
        List<OrderCopRelationDTO> orderCopRelationList = transactionService.queryOrderCopRelationById(orderCopRelation);
        if (orderCopRelationList.size() != 0) {
            try {
                transactionService.updateOrderCopRelation(orderCopRelationDTO);
                responseDTO.setResult(StatusConstant.SUCCESS);
                responseDTO.setErrorInfo("修改成功");
            } catch (Exception e) {
                responseDTO.setResult(StatusConstant.FAILURE);
                responseDTO.setErrorInfo("修改失败");
                e.printStackTrace();
            }
        } else {
            try {
                String uuid = UUIDUtil.getUUID();
                orderCopRelationDTO.setId(uuid);
                transactionService.insertOrderCopRelation(orderCopRelationDTO);
                responseDTO.setResult(StatusConstant.SUCCESS);
                responseDTO.setErrorInfo("绑定成功");
            } catch (Exception e) {
                responseDTO.setResult(StatusConstant.FAILURE);
                responseDTO.setErrorInfo("绑定失败");
                e.printStackTrace();
            }
        }
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
        ResponseDTO<List<OrderCopRelationDTO>> responseDTO = new ResponseDTO<>();
        List<OrderCopRelationDTO> orderCopRelationList = transactionService.queryOrderCopRelationById(orderCopRelationDTO);
        responseDTO.setResponseData(orderCopRelationList);
        responseDTO.setResult(StatusConstant.SUCCESS);
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
        ResponseDTO<BusinessOrderDTO> responseDTO = new ResponseDTO<>();
        BusinessOrderDTO businessOrderDTO = transactionService.queryOrderDetailsById(orderId);
        responseDTO.setResponseData(businessOrderDTO);
        responseDTO.setErrorInfo(StatusConstant.SUCCESS);
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
        ResponseDTO responseDTO = new ResponseDTO<>();
        try{
            transactionService.updateOrderAddress(userOrderAddressDTO);
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setErrorInfo("更新用户收货地址成功");
        }catch (Exception e){
            responseDTO.setErrorInfo("更新用户收货地址失败");
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        return responseDTO;
    }

    @RequestMapping(value = "orderAddressDetail", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<UserOrderAddressDTO> orderAddressDetail(@RequestParam String orderId) {
        ResponseDTO responseDTO = new ResponseDTO<>();

        //根据订单ID，查询此订单的收货地址
        UserOrderAddressDTO userOrderAddressDTO =  userOrderAddressService.getUserOrderAddressByOrderId(orderId);

        if(userOrderAddressDTO!=null)
        {
            responseDTO.setResponseData(userOrderAddressDTO);
            responseDTO.setResult(StatusConstant.SUCCESS);
        }
        else
        {
            responseDTO.setResult(StatusConstant.FAILURE);
        }

        return responseDTO;
    }

    @RequestMapping(value = "orderDetailInfo", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<BusinessOrderDTO> orderDetailInfo(@RequestParam String orderId) {
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

            //根据orderId获取发货时间
            Date sendDate = transactionService.getBusinessOrderSendDate(orderId);
            businessOrderDTO.setSendDate(sendDate);

            responseDTO.setResponseData(businessOrderDTO);
            responseDTO.setResult(StatusConstant.SUCCESS);
        }
        else
        {
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        return responseDTO;
    }

}

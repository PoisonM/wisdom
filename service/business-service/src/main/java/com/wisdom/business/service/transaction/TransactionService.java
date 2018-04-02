package com.wisdom.business.service.transaction;

import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.mapper.transaction.PayRecordMapper;
import com.wisdom.business.mapper.transaction.TransactionMapper;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.product.InvoiceDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.dto.system.*;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.OrderCopRelationDTO;
import com.wisdom.common.dto.transaction.OrderProductRelationDTO;
import com.wisdom.common.util.CodeGenUtil;
import com.wisdom.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by zbm84 on 2017/5/10.
 */

@Service
public class TransactionService {

    @Autowired
    private UserOrderAddressService userOrderAddressService;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private PayRecordMapper payRecordMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserServiceClient userServiceClient;

    public void updateBusinessOrderStatus(BusinessOrderDTO businessOrderDTO){
        BusinessOrderDTO businessOrderDTONew = transactionMapper.getBusinessOrderByOrderId(businessOrderDTO.getBusinessOrderId());
        businessOrderDTONew.setStatus(businessOrderDTO.getStatus());
        businessOrderDTONew.setUpdateDate(new Date());
        transactionMapper.updateBusinessOrder(businessOrderDTONew);
    }

    public void updateBusinessOrder(BusinessOrderDTO businessOrderDTO){
        transactionMapper.updateBusinessOrder(businessOrderDTO);
    }

    public List<BusinessOrderDTO> getBusinessOrderListByUserIdAndStatus(String userId, String status) {
        if(status.equals("all"))
        {
            status="";
        }
        List<BusinessOrderDTO> businessOrderDTOList=transactionMapper.getBusinessOrderListByUserIdAndStatus(userId,status);
        return businessOrderDTOList;
    }

    //返回订单的ID号
    @Transactional(rollbackFor = Exception.class)
    public String createBusinessOrder(BusinessOrderDTO businessOrderDTO){
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();

        //查找用户是否下过单，并处于待支付状态，若处于待支付状态，直接返回当前订单号，若没有下过单，则进入后续流程则创建新的订单并返回
        if(businessOrderDTO.getProductSpec().equals("training"))
        {
            businessOrderDTO.setType("training");
            businessOrderDTO.setSysUserId(userInfoDTO.getId());
            List<BusinessOrderDTO> businessOrderDTOList = transactionMapper.getTrainingBusinessOrder(businessOrderDTO);
            if(businessOrderDTOList.size()>0)
            {
                return businessOrderDTOList.get(0).getBusinessOrderId();
            }
        }

        businessOrderDTO.setId(UUID.randomUUID().toString());
        businessOrderDTO.setSysUserId(userInfoDTO.getId());
        businessOrderDTO.setBusinessOrderId(CodeGenUtil.getOrderCodeNumber());
        businessOrderDTO.setStatus("0");
        businessOrderDTO.setCreateDate(new Date());
        businessOrderDTO.setUpdateDate(new Date());

        //查询用户默认收货地址的ID
        List<UserOrderAddressDTO> userOrderAddressDTOList = userOrderAddressService.getUserOrderAddress(userInfoDTO.getId(), "1");

        if(userOrderAddressDTOList.size()!=0)
        {
            businessOrderDTO.setUserOrderAddressId(userOrderAddressDTOList.get(0).getId());
        }
        transactionMapper.createBusinessOrder(businessOrderDTO);

        OrderProductRelationDTO orderProductRelationDTO = new OrderProductRelationDTO();
        orderProductRelationDTO.setId(UUID.randomUUID().toString());
        orderProductRelationDTO.setBusinessOrderId(businessOrderDTO.getBusinessOrderId());
        orderProductRelationDTO.setBusinessProductId(businessOrderDTO.getBusinessProductId());
        orderProductRelationDTO.setProductNum(businessOrderDTO.getBusinessProductNum());
        orderProductRelationDTO.setProductSpec(businessOrderDTO.getProductSpec());
        transactionMapper.createOrderProductRelation(orderProductRelationDTO);

        return businessOrderDTO.getBusinessOrderId();
    }

    public BusinessOrderDTO getBusinessOrderByOrderId(String orderId) {
        return transactionMapper.getBusinessOrderByOrderId(orderId);
    }

    /**
     * 根据条件查询提成信息
     * @return
     */
    public PageParamDTO<List<PayRecordDTO>> queryPayRecordsByParameters(PageParamVoDTO<ProductDTO> pageParamVoDTO) {
        PageParamDTO<List<PayRecordDTO>> page = new  PageParamDTO<>();
        int count = payRecordMapper.queryPayRecordCountByParameters(pageParamVoDTO);
        page.setTotalCount(count);
        List<PayRecordDTO> PayRecordDTOList = payRecordMapper.queryPayRecordsByParameters(pageParamVoDTO);

        for(PayRecordDTO payRecordDTO : PayRecordDTOList){
            try {
                if(payRecordDTO.getNickName() != null && payRecordDTO.getNickName() != ""){
                    payRecordDTO.setNickName(URLDecoder.decode(payRecordDTO.getNickName(),"utf-8"));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        page.setResponseData(PayRecordDTOList);

        return page;

    }

    public BusinessOrderDTO getTrainingBusinessOrder(BusinessOrderDTO businessOrderDTO) {
        businessOrderDTO.setType("training");
        List<BusinessOrderDTO> businessOrderDTOList = transactionMapper.getTrainingBusinessOrder(businessOrderDTO);
        if(businessOrderDTOList.size()==0)
        {
            return  null;
        }
        else
        {
            return businessOrderDTOList.get(0);
        }
    }

    /**
     * 条件查询订单
     * @return
     */
    public PageParamVoDTO<List<BusinessOrderDTO>> queryBusinessOrderByParameters(PageParamVoDTO pageParamVoDTO) {
        PageParamVoDTO<List<BusinessOrderDTO>> page = new  PageParamVoDTO<>();
        int count = transactionMapper.queryBusinessOrderCountByParameters(pageParamVoDTO);
        List<BusinessOrderDTO> businessOrderDTODTOList = transactionMapper.queryBusinessOrderByParameters(pageParamVoDTO);
        for(BusinessOrderDTO businessOrderDTO : businessOrderDTODTOList){//用户名解码
            businessOrderDTO.setNickName(CommonUtils.nameDecoder(businessOrderDTO.getNickName()));
        }
        page.setTotalCount(count);
        page.setResponseData(businessOrderDTODTOList);
        return page;
    }

    //查询所有订单
    public PageParamDTO<List<BusinessOrderDTO>> queryAllBusinessOrders(PageParamVoDTO<BusinessOrderDTO> pageParamVoDTO) {
        PageParamDTO<List<BusinessOrderDTO>> page = new  PageParamDTO<>();
        page.setPageNo(pageParamVoDTO.getPageNo());
        page.setPageSize(pageParamVoDTO.getPageSize());
        int count = transactionMapper.queryAllBusinessOrderCount(pageParamVoDTO);
        List<BusinessOrderDTO> businessOrderDTOList = transactionMapper.queryAllBusinessOrders(pageParamVoDTO.getPageStartNo(),page.getPageSize(),pageParamVoDTO.getIsExportExcel());
        for(BusinessOrderDTO businessOrderDTO : businessOrderDTOList){
            if(businessOrderDTO.getNickName() != null && businessOrderDTO.getNickName() != ""){
                try {
                    businessOrderDTO.setNickName(URLDecoder.decode(businessOrderDTO.getNickName(),"utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        page.setTotalCount(count);
        page.setResponseData(businessOrderDTOList);
        return page;
    }

    public BusinessOrderDTO getBusinessOrderDetailInfoByOrderId(String orderId) {
        return transactionMapper.getBusinessOrderDetailInfoByOrderId(orderId);
    }

    public BusinessOrderDTO queryOrderDetailsById(String orderId) {
        return transactionMapper.queryOrderDetailsById(orderId);
    }

    public void updateOrderAddress(UserOrderAddressDTO userOrderAddressDTO) {
        transactionMapper.updateOrderAddress(userOrderAddressDTO);
    }

    public List<BusinessOrderDTO> getBusinessOrderByUserIdAndProductId(String userId, String productId) {
        return transactionMapper.getBusinessOrderByUserIdAndProductId(userId, productId);
    }

    public void createOrderProductRelation(OrderProductRelationDTO orderProductRelationDTO) {
        transactionMapper.createOrderProductRelation(orderProductRelationDTO);
    }

    /**
     * 查询导出订单Excel信息
     * @return
     */
    public List<ExportOrderExcelDTO> selectExcelContent() {
        BusinessOrderDTO businessOrderDTO = new BusinessOrderDTO();
        List<ExportOrderExcelDTO> productDTOList = transactionMapper.selectExcelContent();
        for (ExportOrderExcelDTO exportOrderExcelDTO : productDTOList) {
            //修改订单为已发货
            businessOrderDTO.setBusinessOrderId(exportOrderExcelDTO.getOrderId());
            businessOrderDTO.setStatus("4");
            businessOrderDTO.setUpdateDate(new Date());
            //transactionMapper.updateBusinessOrder(businessOrderDTO);
            transactionMapper.updateOrderByOrderId(businessOrderDTO);
            if("是".equals(exportOrderExcelDTO.getInvoice())){
                Query query = new Query().addCriteria(Criteria.where("orderId").is(exportOrderExcelDTO.getOrderId()));
                InvoiceDTO invoiceDTO = mongoTemplate.findOne(query, InvoiceDTO.class,"invoice");
                exportOrderExcelDTO.setCompanyName(invoiceDTO.getCompanyName());
                exportOrderExcelDTO.setTaxpayerNumber(invoiceDTO.getTaxpayerNumber());
            }
            //用户名解码
            exportOrderExcelDTO.setNickName(CommonUtils.nameDecoder(exportOrderExcelDTO.getNickName()));
        }
        return productDTOList;
    }

    public Date getBusinessOrderSendDate(String orderId) {
        return null;
    }

    //给订单绑定COP号
    public void insertOrderCopRelation(OrderCopRelationDTO orderCopRelationDTO) {
        transactionMapper.insertOrderCopRelation(orderCopRelationDTO);
    }
    //查询订单绑定相应的COP号
    public List<OrderCopRelationDTO> queryOrderCopRelationById(OrderCopRelationDTO orderCopRelationDTO) {
        return transactionMapper.queryOrderCopRelationById(orderCopRelationDTO);
    }
    //编辑订单绑定相应的COP号
    public void updateOrderCopRelation(OrderCopRelationDTO orderCopRelationDTO) {
        transactionMapper.updateOrderCopRelation(orderCopRelationDTO);
    }
    //修改商品库存
    public void updateOfflineProductAmount(ProductDTO productDTO) {
        Query query = new Query().addCriteria(Criteria.where("productId").is(productDTO.getProductId()));
        ProductDTO productDTO1 = mongoTemplate.findOne(query, ProductDTO.class,"offlineProduct");
        if(productDTO1 != null){
            if(productDTO.getProductAmount() != 0) {
                if (productDTO1.getProductAmount() >= productDTO.getProductAmount()) {
                    Update update = new Update();
                    update.set("playNum", productDTO1.getProductAmount() - productDTO.getProductAmount());
                    mongoTemplate.updateFirst(query, update, "offlineProduct");
                }
            }
        }
    }
}

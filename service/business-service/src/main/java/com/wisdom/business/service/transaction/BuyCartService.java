package com.wisdom.business.service.transaction;

import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.mapper.transaction.TransactionMapper;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.product.OfflineProductDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.dto.system.UserOrderAddressDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.OrderProductRelationDTO;
import com.wisdom.common.util.CodeGenUtil;
import com.wisdom.common.util.ObjectUtils;
import com.wisdom.common.util.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by zbm84 on 2017/5/10.
 */

@Service
public class BuyCartService {

    @Autowired
    private UserOrderAddressService userOrderAddressService;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserServiceClient userServiceClient;

    public List<BusinessOrderDTO> getUserUnPayOrderInBuyCart() {
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        List<BusinessOrderDTO> businessOrderDTOS = transactionMapper.getUserUnPayOrderInBuyCart(userInfoDTO.getId());
        for(BusinessOrderDTO businessOrderDTO:businessOrderDTOS)
        {
            Query query = new Query().addCriteria(Criteria.where("productId").is(businessOrderDTO.getBusinessProductId()));
            OfflineProductDTO offlineProductDTO = mongoTemplate.findOne(query, OfflineProductDTO.class,"offlineProduct");
            businessOrderDTO.setSenderAddress(offlineProductDTO.getSenderAddress());
        }
        return businessOrderDTOS;
    }

    @Transactional(rollbackFor = Exception.class)
    public String addOfflineProduct2BuyCart(String productId, String productSpec,int num) {
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();

        //判断用户是否已经将此商品加入过购物车，如果已经加入过，则直接增加订单中，产品的数量
        OrderProductRelationDTO orderProductRelationUnPaid = transactionMapper.getOrderProductUnPaidInBuyCart(productId,productSpec,userInfoDTO.getId());

        if(ObjectUtils.isNullOrEmpty(orderProductRelationUnPaid))
        {
            try {
                //如果没有加入过，则直接增加订单中，并设置产品的数量为1
                String businessOrderId = CodeGenUtil.getOrderCodeNumber();
                BusinessOrderDTO businessOrderDTO = new BusinessOrderDTO();
                businessOrderDTO.setId(UUID.randomUUID().toString());
                businessOrderDTO.setSysUserId(userInfoDTO.getId());
                businessOrderDTO.setBusinessOrderId(businessOrderId);
                businessOrderDTO.setType("offline");
                businessOrderDTO.setStatus("3");
                businessOrderDTO.setCreateDate(new Date());
                businessOrderDTO.setUpdateDate(new Date());
                //查询用户默认收货地址的ID
                List<UserOrderAddressDTO> userOrderAddressDTOList = userOrderAddressService.getUserOrderAddress(userInfoDTO.getId(), "1");
                if (userOrderAddressDTOList.size() != 0) {
                    businessOrderDTO.setUserOrderAddressId(userOrderAddressDTOList.get(0).getId());
                }
                transactionMapper.createBusinessOrder(businessOrderDTO);

                OrderProductRelationDTO orderProductRelationDTO = new OrderProductRelationDTO();
                orderProductRelationDTO.setId(UUID.randomUUID().toString());
                orderProductRelationDTO.setBusinessOrderId(businessOrderId);
                orderProductRelationDTO.setBusinessProductId(productId);
                orderProductRelationDTO.setProductNum(num);
                orderProductRelationDTO.setProductSpec(productSpec);
                transactionMapper.createOrderProductRelation(orderProductRelationDTO);

                return StatusConstant.SUCCESS;
            }
            catch (Exception e)
            {
                return  StatusConstant.FAILURE;
            }
        }
        else
        {
            RedisLock redisLock = new RedisLock("OrderProductRelation"+productId);
            try
            {
                redisLock.lock();
                Integer productNum = orderProductRelationUnPaid.getProductNum() + num;
                orderProductRelationUnPaid.setProductNum(productNum);
                transactionMapper.updateOrderProductRelation(orderProductRelationUnPaid);
                return StatusConstant.SUCCESS;
            }
            catch (Exception e) {
                e.printStackTrace();
                return StatusConstant.FAILURE;
            } finally {
                redisLock.unlock();
            }
        }
    }

    public String getProductNumFromBuyCart() {
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        if(userInfoDTO==null){
            return "0";
        }
        else
        {
            Integer num = 0;
            List<OrderProductRelationDTO> orderProductRelationDTOS = transactionMapper.getUserAllOrderProductFromBuyCart(userInfoDTO.getId());
            for(OrderProductRelationDTO orderProductRelationDTO:orderProductRelationDTOS)
            {
                num = num + orderProductRelationDTO.getProductNum();
            }
            return num.toString();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void minusProduct2BuyCart(String productId, String productSpec) {

        RedisLock redisLock = new RedisLock("OrderProductRelation");
        try
        {
            redisLock.lock();

            UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
            OrderProductRelationDTO orderProductRelationUnPaid = transactionMapper.getOrderProductUnPaidInBuyCart(productId,productSpec,userInfoDTO.getId());

            Integer productNum = orderProductRelationUnPaid.getProductNum()-1;
            orderProductRelationUnPaid.setProductNum(productNum);
            transactionMapper.updateOrderProductRelation(orderProductRelationUnPaid);
        }
        catch (Exception e) {
            throw e;
        } finally {
            redisLock.unlock();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteOrderFromBuyCart(String orderId) {

        RedisLock redisLock = new RedisLock("businessOrder"+orderId);
        try
        {
            redisLock.lock();

            BusinessOrderDTO businessOrderDTO = transactionMapper.getBusinessOrderByOrderId(orderId);
            businessOrderDTO.setUpdateDate(new Date());
            businessOrderDTO.setStatus("del");
            transactionMapper.updateBusinessOrder(businessOrderDTO);
        }
        catch (Exception e) {
            throw e;
        } finally {
            redisLock.unlock();
        }

    }

}

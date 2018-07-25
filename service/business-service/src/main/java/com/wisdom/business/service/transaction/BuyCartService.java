package com.wisdom.business.service.transaction;

import com.wisdom.business.mapper.transaction.TransactionMapper;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.product.OfflineProductDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.dto.system.UserOrderAddressDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.OrderProductRelationDTO;
import com.wisdom.common.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    /**
     * 预约详情缓存时常，20分钟
     */
    private int productInfoCacheSeconds = 1200;

    @Autowired
    private UserOrderAddressService userOrderAddressService;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    Logger logger = LoggerFactory.getLogger(BuyCartService.class);

    public List<BusinessOrderDTO> getUserUnPayOrderInBuyCart() {
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        logger.info("获取用户=={}购物车中的信息",userInfoDTO.getId());
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
        logger.info("用户=={}将货品id=={}规格=={}数量=={}加入用户的购物车==={}",userInfoDTO.getMobile(),productId,productSpec,num);

        //判断用户是否已经将此商品加入过购物车，如果已经加入过，则直接增加订单中，产品的数量
        OrderProductRelationDTO orderProductRelationUnPaid = transactionMapper.getOrderProductUnPaidInBuyCart(productId,productSpec,userInfoDTO.getId());

        if(ObjectUtils.isNullOrEmpty(orderProductRelationUnPaid))
        {
            try {
                //如果没有加入过，则直接增加订单中，并设置产品的数量为1
                String businessOrderId = CodeGenUtil.getOrderCodeNumber();
                logger.info("如果没有加入过，则直接增加订单中=={}，并设置产品的数量为1",businessOrderId);
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
                logger.error("则直接增加订单异常,异常信息为{}"+e.getMessage(),e);
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
                logger.info("购物车已经加入过，则直接增加订单中，产品的数量=={}",productNum);
                orderProductRelationUnPaid.setProductNum(productNum);
                transactionMapper.updateOrderProductRelation(orderProductRelationUnPaid);
                return StatusConstant.SUCCESS;
            }
            catch (Exception e) {
                logger.error("购物车已经加入过，则直接增加订单中，产品的数量异常,异常信息为{}"+e.getMessage(),e);
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
            logger.info("获取用户=={}购物车中的商品总数==={}",userInfoDTO.getId(),num);
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
            logger.info("用户=={}减少购物车中商品数=={},商品规格=={}",userInfoDTO.getId(),productId,productSpec);
            OrderProductRelationDTO orderProductRelationUnPaid = transactionMapper.getOrderProductUnPaidInBuyCart(productId,productSpec,userInfoDTO.getId());

            Integer productNum = orderProductRelationUnPaid.getProductNum()-1;
            orderProductRelationUnPaid.setProductNum(productNum);
            transactionMapper.updateOrderProductRelation(orderProductRelationUnPaid);
        }
        catch (Exception e) {
            logger.error("减少购物车中商品数异常,异常信息为{}"+e.getMessage(),e);
            throw e;
        } finally {
            redisLock.unlock();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteOrderFromBuyCart(String orderId) {

        logger.info("service == 减少购物车中的订单"+orderId);

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
            logger.info("减少购物车中的订单异常,异常信息为{}"+e.getMessage(),e);
            throw e;
        } finally {
            redisLock.unlock();
        }

    }


    /**
     *跨境电商 加入购物车
     *
     * */
    @Transactional(rollbackFor = Exception.class)
    public String addCrossBorderProduct(String productId,int num) {
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        logger.info("用户=={}将货品id=={}数量=={}",userInfoDTO.getId(),productId,num);
        OrderProductRelationDTO orderProductRelationUnPaid = transactionMapper.getOrderProductUnPaidInBuyCart(productId,"special",userInfoDTO.getId());
        if(ObjectUtils.isNullOrEmpty(orderProductRelationUnPaid)){
            try {
                String businessOrderId = CodeGenUtil.getOrderCodeNumber();
                logger.info("如果没有加入过，则直接增加订单中=={}，并设置产品的数量为1",businessOrderId);
                BusinessOrderDTO businessOrderDTO = new BusinessOrderDTO();
                businessOrderDTO.setId(UUID.randomUUID().toString());
                businessOrderDTO.setSysUserId(userInfoDTO.getId());
                businessOrderDTO.setBusinessOrderId(businessOrderId);
                businessOrderDTO.setType("special");
                businessOrderDTO.setStatus("3");
                businessOrderDTO.setCreateDate(new Date());
                businessOrderDTO.setUpdateDate(new Date());
                transactionMapper.createBusinessOrder(businessOrderDTO);
                OrderProductRelationDTO orderProductRelationDTO = new OrderProductRelationDTO();
                orderProductRelationDTO.setId(UUID.randomUUID().toString());
                orderProductRelationDTO.setBusinessOrderId(businessOrderId);
                orderProductRelationDTO.setBusinessProductId(productId);
                orderProductRelationDTO.setProductNum(num);
                orderProductRelationDTO.setProductSpec("special");
                transactionMapper.createOrderProductRelation(orderProductRelationDTO);
                return StatusConstant.SUCCESS;
            }
            catch (Exception e){
                logger.error("则直接增加订单异常,异常信息为{}"+e.getMessage(),e);
                return  StatusConstant.FAILURE;
            }
        }else{
            try{
                Integer productNum = orderProductRelationUnPaid.getProductNum() + num;
                logger.info("购物车已经加入过，则直接增加订单中，产品的数量=={}",productNum);
                orderProductRelationUnPaid.setProductNum(productNum);
                transactionMapper.updateOrderProductRelation(orderProductRelationUnPaid);
                return StatusConstant.SUCCESS;
            }catch (Exception e) {
                logger.error("购物车已经加入过，则直接增加订单中，产品的数量异常,异常信息为{}"+e.getMessage(),e);
                e.printStackTrace();
                return StatusConstant.FAILURE;
            }
        }
    }

    /**
     *秒杀活动 抢购
     *
     * */
    @Transactional(rollbackFor = Exception.class)
    public String seckillProductBuyNow(String fieldId,int num,String productSpec) {
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        logger.info("用户=={}将货品id=={}数量=={}",userInfoDTO.getId(),fieldId,num);
            try {
                String businessOrderId = CodeGenUtil.getOrderCodeNumber();
                BusinessOrderDTO businessOrderDTO = new BusinessOrderDTO();
                businessOrderDTO.setId(UUID.randomUUID().toString());
                businessOrderDTO.setSysUserId(userInfoDTO.getId());
                businessOrderDTO.setBusinessOrderId(businessOrderId);
                businessOrderDTO.setType(productSpec);
                businessOrderDTO.setStatus("0");
                businessOrderDTO.setCreateDate(new Date());
                businessOrderDTO.setUpdateDate(new Date());
                JedisUtils.setObject("seckillproductOrder:"+fieldId+":"+businessOrderDTO.getId(),businessOrderDTO,productInfoCacheSeconds);
                return StatusConstant.SUCCESS;
            }
            catch (Exception e){
                logger.error("则直接增加订单异常,异常信息为{}"+e.getMessage(),e);
                return  StatusConstant.FAILURE;
            }
    }
}

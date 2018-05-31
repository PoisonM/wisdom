package com.wisdom.beauty.controller.order;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectGroupRelRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.enums.OrderStatusEnum;
import com.wisdom.beauty.api.extDto.ShopUserOrderDTO;
import com.wisdom.beauty.core.service.ShopCardService;
import com.wisdom.beauty.core.service.ShopOrderService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * FileName: OrderController
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
@Controller
@LoginAnnotations
@RequestMapping(value = "orderInfo")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private ShopCardService shopCardService;

    @Resource
    private ShopOrderService shopOrderService;

    /**
     * 查询用户最近一次订单信息
     *
     * @param sysUserId 用户id
     * @return
     */
    @RequestMapping(value = "getShopUserRecentlyOrderInfo", method = {RequestMethod.POST, RequestMethod.GET})

    public
    @ResponseBody
    ResponseDTO<ShopUserOrderDTO> getShopUserRecentlyOrderInfo(@RequestParam String sysUserId, @RequestParam(required = false) String orderId) {

        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        ResponseDTO<ShopUserOrderDTO> responseDTO = new ResponseDTO<>();

        ShopUserOrderDTO shopUserOrderDTO = null;
        if (StringUtils.isNotBlank(orderId)) {
            Query query = new Query(Criteria.where("orderId").is(orderId));
            shopUserOrderDTO = mongoTemplate.findOne(query, ShopUserOrderDTO.class, "shopUserOrderDTO");
        } else {
            Query query = new Query(Criteria.where("shopId").is(clerkInfo.getSysShopId())).addCriteria(Criteria.where("userId").is(sysUserId));
            query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createDate")));
            shopUserOrderDTO = mongoTemplate.findOne(query, ShopUserOrderDTO.class, "shopUserOrderDTO");
        }
        if (null != shopUserOrderDTO) {
            //查询用户账户总余额
            ShopUserRechargeCardDTO shopUserRechargeCardDTO = new ShopUserRechargeCardDTO();
            shopUserRechargeCardDTO.setSysUserId(shopUserOrderDTO.getUserId());
            shopUserRechargeCardDTO.setSysShopId(shopUserOrderDTO.getShopId());
            List<ShopUserRechargeCardDTO> userRechargeCardList = shopCardService.getUserRechargeCardList(shopUserRechargeCardDTO);
            if (CommonUtils.objectIsEmpty(userRechargeCardList)) {
                logger.error("用户特殊账号为空={}", "sysUserId = [" + sysUserId + "], orderId = [" + orderId + "]");
                throw new ServiceException("用户特殊账号为空");
            }
            shopUserOrderDTO.setAvailableBalance(userRechargeCardList.get(0).getSurplusAmount());
        }

        responseDTO.setResponseData(shopUserOrderDTO);
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

    /**
     * 保存用户的订单信息
     * @param shopUserOrderDTO 订单对象
     * @return
     */
    @RequestMapping(value = "saveShopUserOrderInfo", method = {RequestMethod.POST, RequestMethod.GET})

    public
    @ResponseBody
    ResponseDTO<String> saveShopUserOrderInfo(@RequestBody ShopUserOrderDTO shopUserOrderDTO) {

        if(null == shopUserOrderDTO || StringUtils.isBlank(shopUserOrderDTO.getUserId())){
            logger.error("保存用户的订单信息传入参数为空");
            return null;
        }
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        //先查询最后一次订单信息
        Query query = new Query(Criteria.where("shopId").is(clerkInfo.getSysShopId())).addCriteria(Criteria.where("userId").is(shopUserOrderDTO.getUserId()));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createDate")));
        ShopUserOrderDTO searchOrderInfo = mongoTemplate.findOne(query, ShopUserOrderDTO.class, "shopUserOrderDTO");
        if (null != searchOrderInfo) {
            responseDTO.setResponseData(searchOrderInfo.getOrderId());
            responseDTO.setResult(StatusConstant.SUCCESS);
            return responseDTO;
        }
        //如果最后一次订单为空则需初始化插入
        searchOrderInfo = new ShopUserOrderDTO();
        searchOrderInfo.setShopId(clerkInfo.getSysShopId());
        searchOrderInfo.setOrderId(DateUtils.DateToStr(new Date(), "dateMillisecond"));
        searchOrderInfo.setStatus(OrderStatusEnum.NOT_PAY.getCode());
        searchOrderInfo.setCreateDate(new Date());
        searchOrderInfo.setUserId(shopUserOrderDTO.getUserId());
        mongoTemplate.save(searchOrderInfo, "shopUserOrderDTO");

        responseDTO.setResponseData(searchOrderInfo.getOrderId());
        responseDTO.setResult(StatusConstant.SUCCESS);

        return responseDTO;
    }

    /**
     * 更新用户的订单信息
     *
     * @param shopUserOrderDTO 订单对象
     * @return
     */
    @RequestMapping(value = "updateShopUserOrderInfo", method = {RequestMethod.POST, RequestMethod.GET})

    public
    @ResponseBody
    ResponseDTO<String> updateShopUserOrderInfo(@RequestBody ShopUserOrderDTO shopUserOrderDTO) {

        ResponseDTO responseDTO = new ResponseDTO<String>();

        //mongodb中更新订单的状态
        Query query = new Query().addCriteria(Criteria.where("orderId").is(shopUserOrderDTO.getOrderId()));
        Update update = new Update();
        update.set("status", shopUserOrderDTO.getStatus());
        update.set("signUrl", shopUserOrderDTO.getSignUrl());
        update.set("orderPrice", shopUserOrderDTO.getOrderPrice());
        update.set("projectGroupRelRelationDTOS", shopUserOrderDTO.getProjectGroupRelRelationDTOS());
        update.set("shopUserProductRelationDTOS", shopUserOrderDTO.getShopUserProductRelationDTOS());
        update.set("shopUserProjectRelationDTOS", shopUserOrderDTO.getShopUserProjectRelationDTOS());
        mongoTemplate.upsert(query, update, "shopUserOrderDTO");
        responseDTO.setResponseData(StatusConstant.SUCCESS);
        responseDTO.setResult(StatusConstant.SUCCESS);

        return responseDTO;
    }

    /**
     * 更新订单虚拟商品的信息
     *
     * @param shopUserOrderDTO 订单对象
     * @return
     */
    @RequestMapping(value = "updateVirtualGoodsOrderInfo", method = {RequestMethod.POST, RequestMethod.GET})

    public
    @ResponseBody
    ResponseDTO<String> updateVirtualGoodsOrderInfo(@RequestBody ShopUserOrderDTO shopUserOrderDTO) {
        ResponseDTO responseDTO = shopOrderService.updateShopUserOrderInfo(shopUserOrderDTO);
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData("更新成功");
        return responseDTO;
    }

    /**
     * 收银板块界面回显接口，查询所有订单里的项目id、产品id
     *
     * @param orderId 订单对象
     * @return
     */
    @RequestMapping(value = "getConsumeDisplayIds", method = {RequestMethod.POST, RequestMethod.GET})

    public
    @ResponseBody
    ResponseDTO<String> getConsumeDisplayIds(@RequestParam String orderId) {

        ResponseDTO responseDTO = new ResponseDTO<String>();

        Query query = new Query(Criteria.where("orderId").is(orderId));
        ShopUserOrderDTO userOrderDTO = mongoTemplate.findOne(query, ShopUserOrderDTO.class, "shopUserOrderDTO");

        HashMap<Object, Object> returnMap = new HashMap<>(4);
        //获取项目回显数据
        List<ShopUserProjectRelationDTO> shopUserProjectRelationDTOS = userOrderDTO.getShopUserProjectRelationDTOS();
        if (CommonUtils.objectIsNotEmpty(shopUserProjectRelationDTOS)) {
            HashMap<Object, Object> timeCardMap = new HashMap<>(5);
            ArrayList<Object> timeCardList = new ArrayList<>();
            HashMap<Object, Object> periodMap = new HashMap<>(5);
            ArrayList<Object> periodCardList = new ArrayList<>();
            int timeCardSize = 0;
            int periodCardSize = 0;

            for (ShopUserProjectRelationDTO userProjectRelationDTO : shopUserProjectRelationDTOS) {
                //如果是次卡的话
                if (GoodsTypeEnum.TIME_CARD.getCode().equals(userProjectRelationDTO.getUseStyle())) {
                    timeCardSize++;
                    timeCardList.add(userProjectRelationDTO.getSysShopProjectId());
                } else {
                    periodCardSize++;
                    periodCardList.add(userProjectRelationDTO.getSysShopProjectId());
                }
            }
            timeCardMap.put("timeCardSize", timeCardSize);
            timeCardMap.put("timeCardIds", timeCardList);
            periodMap.put("periodCardSize", periodCardSize);
            periodMap.put("periodCardIds", periodCardList);
            returnMap.put("timeCard", timeCardMap);
            returnMap.put("periodCard", periodMap);
        }

        //获取套卡回显数据
        List<ShopUserProjectGroupRelRelationDTO> projectGroupRelRelationDTOS = userOrderDTO.getProjectGroupRelRelationDTOS();
        if (CommonUtils.objectIsNotEmpty(projectGroupRelRelationDTOS)) {
            HashMap<Object, Object> hashMap = new HashMap<>(5);
            hashMap.put("groupSize", projectGroupRelRelationDTOS.size());
            ArrayList<String> ids = new ArrayList<>();
            for (ShopUserProjectGroupRelRelationDTO userProjectGroupRelRelationDTO : projectGroupRelRelationDTOS) {
                //套卡id作为回显数据
                ids.add(userProjectGroupRelRelationDTO.getShopProjectGroupId());
            }
            hashMap.put("groupIds", ids);
            returnMap.put("groupCard", hashMap);
        }

        //获取产品回显数据
        List<ShopUserProductRelationDTO> shopUserProductRelationDTOS = userOrderDTO.getShopUserProductRelationDTOS();
        if (CommonUtils.objectIsNotEmpty(shopUserProductRelationDTOS)) {
            HashMap<Object, Object> hashMap = new HashMap<>(5);
            hashMap.put("productSize", shopUserProductRelationDTOS.size());
            //项目主键作为回显数据
            ArrayList<String> ids = new ArrayList<>();
            for (ShopUserProductRelationDTO userProductRelationDTO : shopUserProductRelationDTOS) {
                ids.add(userProductRelationDTO.getShopProductId());
            }
            hashMap.put("productIds", ids);
            returnMap.put("product", hashMap);
        }
        responseDTO.setResponseData(returnMap);
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }
}

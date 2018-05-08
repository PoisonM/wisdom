package com.wisdom.beauty.controller.order;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectGroupRelRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectRelationDTO;
import com.wisdom.beauty.api.enums.CommonCodeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.enums.OrderStatusEnum;
import com.wisdom.beauty.api.extDto.ShopUserOrderDTO;
import com.wisdom.beauty.core.service.ShopUerConsumeRecordService;
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
import java.util.*;


/**
 * FileName: OrderController
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
@Controller
@RequestMapping(value = "orderInfo")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private ShopUerConsumeRecordService shopUerConsumeRecordService;

    /**
     * 查询用户最近一次订单信息
     *
     * @param sysUserId 用户id
     * @return
     */
    @RequestMapping(value = "getShopUserRecentlyOrderInfo", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
    public
    @ResponseBody
    ResponseDTO<ShopUserOrderDTO> getShopUserRecentlyOrderInfo(@RequestParam String sysUserId) {

        long currentTimeMillis = System.currentTimeMillis();
        logger.info("查询用户最近一次订单信息传入参数={}", "shopUserArchivesId = [" + sysUserId + "]");
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        ResponseDTO<ShopUserOrderDTO> responseDTO = new ResponseDTO<>();

        Query query = new Query(Criteria.where("shopId").is(clerkInfo.getSysShopId())).addCriteria(Criteria.where("userId").is(sysUserId));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createDate")));
        ShopUserOrderDTO shopUserOrderDTO = mongoTemplate.findOne(query, ShopUserOrderDTO.class, "shopUserOrderDTO");

        responseDTO.setResponseData(shopUserOrderDTO);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * 保存用户的订单信息
     * @param shopUserOrderDTO 订单对象
     * @return
     */
    @RequestMapping(value = "saveShopUserOrderInfo", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
    public
    @ResponseBody
    ResponseDTO<String> saveShopUserOrderInfo(@RequestBody ShopUserOrderDTO shopUserOrderDTO) {

        long currentTimeMillis = System.currentTimeMillis();
        logger.info("保存用户的订单信息传入参数={}", "shopUserOrderDTO = [" + shopUserOrderDTO + "]");
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        //先查询最后一次订单信息
        Query query = new Query(Criteria.where("shopId").is(clerkInfo.getSysShopId())).addCriteria(Criteria.where("userId").is(shopUserOrderDTO.getUserId()));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createDate")));
        shopUserOrderDTO = mongoTemplate.findOne(query, ShopUserOrderDTO.class, "shopUserOrderDTO");
        if (null != shopUserOrderDTO) {
            responseDTO.setResponseData(shopUserOrderDTO.getOrderId());
            responseDTO.setResult(StatusConstant.SUCCESS);
            return responseDTO;
        }
        //如果最后一次订单为空则需初始化插入
        shopUserOrderDTO.setShopId(clerkInfo.getSysShopId());
        shopUserOrderDTO.setOrderId(DateUtils.DateToStr(new Date(), "dateMillisecond"));
        shopUserOrderDTO.setStatus(OrderStatusEnum.NOT_PAY.getCode());
        shopUserOrderDTO.setCreateDate(new Date());

        mongoTemplate.save(shopUserOrderDTO, "shopUserOrderDTO");

        responseDTO.setResponseData(shopUserOrderDTO.getOrderId());
        responseDTO.setResult(StatusConstant.SUCCESS);

        logger.info("保存用户的订单信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * 更新用户的订单信息
     *
     * @param shopUserOrderDTO 订单对象
     * @return
     */
    @RequestMapping(value = "updateShopUserOrderInfo", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
    public
    @ResponseBody
    ResponseDTO<String> updateShopUserOrderInfo(@RequestBody ShopUserOrderDTO shopUserOrderDTO) {

        long currentTimeMillis = System.currentTimeMillis();
        logger.info("更新用户的订单信息传入参数={}", "shopUserOrderDTO = [" + shopUserOrderDTO + "]");
        ResponseDTO responseDTO = new ResponseDTO<String>();

        //mongodb中更新订单的状态
        Query query = new Query().addCriteria(Criteria.where("orderId").is(shopUserOrderDTO.getOrderId()));
        Update update = new Update();
        update.set("status", shopUserOrderDTO.getStatus());
        update.set("signUrl", shopUserOrderDTO.getSignUrl());
        update.set("projectGroupRelRelationDTOS", shopUserOrderDTO.getProjectGroupRelRelationDTOS());
        update.set("shopUserProductRelationDTOS", shopUserOrderDTO.getShopUserProductRelationDTOS());
        update.set("shopUserProjectRelationDTOS", shopUserOrderDTO.getShopUserProjectRelationDTOS());
        mongoTemplate.upsert(query, update, "shopUserOrderDTO");
        responseDTO.setResponseData(StatusConstant.SUCCESS);
        responseDTO.setResult(StatusConstant.SUCCESS);

        logger.info("保存用户的订单信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * 更新订单虚拟商品的信息
     *
     * @param shopUserOrderDTO 订单对象
     * @return
     */
    @RequestMapping(value = "updateVirtualGoodsOrderInfo", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
    public
    @ResponseBody
    ResponseDTO<String> updateVirtualGoodsOrderInfo(@RequestBody ShopUserOrderDTO shopUserOrderDTO) {

        long currentTimeMillis = System.currentTimeMillis();
        logger.info("更新订单虚拟商品的信息传入参数={}", "shopUserOrderDTO = [" + shopUserOrderDTO + "]");
        ResponseDTO responseDTO = new ResponseDTO<String>();
        String orderId = shopUserOrderDTO.getOrderId();
        //0添加  1删除
        String operation = shopUserOrderDTO.getOperation();

        if (StringUtils.isBlank(orderId)) {
            logger.info("更新订单虚拟商品的信息传入订单号为空");
            throw new ServiceException("更新订单虚拟商品的信息传入订单号为空");
        }
        //根据订单编号查询订单信息
        Query query = new Query(Criteria.where("orderId").is(shopUserOrderDTO.getOrderId()));
        ShopUserOrderDTO alreadyOrderDTO = mongoTemplate.findOne(query, ShopUserOrderDTO.class, "shopUserOrderDTO");
        logger.info("根据订单号，{}，查询订单信息为，{}", orderId, alreadyOrderDTO);
        if (CommonUtils.objectIsEmpty(alreadyOrderDTO)) {
            logger.info("根据订单号，{},mongo查询订单信息为空", orderId);
            throw new ServiceException("mongo查询订单信息为空");
        }

        String goodsType = shopUserOrderDTO.getGoodsType();

        //次卡或者是疗程卡
        if (GoodsTypeEnum.TIME_CARD.getCode().equals(goodsType) || GoodsTypeEnum.TREATMENT_CARD.getCode().equals(goodsType)) {

            //获取已经保存的项目信息
            List<ShopUserProjectRelationDTO> alreadyExistProjectRelationDTOS = alreadyOrderDTO.getShopUserProjectRelationDTOS();
            logger.info("查询项目信息为，{}", "shopUserProjectRelationDTOS = [" + alreadyExistProjectRelationDTOS + "]");
            if (null == alreadyExistProjectRelationDTOS) {
                alreadyExistProjectRelationDTOS = new ArrayList<>();
            }
            int updateFlag = alreadyExistProjectRelationDTOS.size();
            //获取需要添加的项目信息
            List<ShopUserProjectRelationDTO> newProjectRelationDTOS = shopUserOrderDTO.getShopUserProjectRelationDTOS();
            if (CommonUtils.objectIsEmpty(newProjectRelationDTOS)) {
                responseDTO.setResult(StatusConstant.FAILURE);
                responseDTO.setResponseData("传入的项目信息为空");
                return responseDTO;
            }

            List<ShopUserProjectRelationDTO> helperList = new ArrayList<>();
            Iterator<ShopUserProjectRelationDTO> alreadyIterator = alreadyExistProjectRelationDTOS.iterator();
            if (!alreadyIterator.hasNext()) {
                alreadyExistProjectRelationDTOS.addAll(newProjectRelationDTOS);
            } else {
                while (alreadyIterator.hasNext()) {
                    Iterator<ShopUserProjectRelationDTO> newIterator = newProjectRelationDTOS.iterator();
                    ShopUserProjectRelationDTO alreadyRelation = alreadyIterator.next();
                    while (newIterator.hasNext()) {
                        ShopUserProjectRelationDTO newRelation = newIterator.next();
                        if (!newRelation.getId().equals(alreadyRelation.getId())) {
                            if (CommonCodeEnum.ADD.getCode().equals(operation)) {
                                helperList.add(newRelation);
                            } else if (CommonCodeEnum.DELETE.getCode().equals(operation)) {
                                alreadyIterator.remove();
                            }
                        }
                    }
                }
            }

            if (CommonCodeEnum.ADD.getCode().equals(operation)) {
                alreadyExistProjectRelationDTOS.addAll(helperList);
            }

            //更新操作
            if (alreadyExistProjectRelationDTOS.size() != updateFlag) {
                Query updateQuery = new Query().addCriteria(Criteria.where("orderId").is(shopUserOrderDTO.getOrderId()));
                Update update = new Update();
                update.set("shopUserProjectRelationDTOS", alreadyExistProjectRelationDTOS);
                mongoTemplate.upsert(updateQuery, update, "shopUserOrderDTO");
                responseDTO.setResponseData(StatusConstant.SUCCESS);
                responseDTO.setResult(StatusConstant.SUCCESS);
            }

        }
        //产品
        else if (GoodsTypeEnum.PRODUCT.getCode().equals(goodsType)) {
            logger.info("获取已存在的产品信息");
            List<ShopUserProductRelationDTO> alreadyProductRelationDTOS = alreadyOrderDTO.getShopUserProductRelationDTOS();
            if (CommonUtils.objectIsEmpty(alreadyProductRelationDTOS)) {
                alreadyProductRelationDTOS = new ArrayList<>();
            }
            int updateFlag = alreadyProductRelationDTOS.size();
            List<ShopUserProductRelationDTO> newProductRelationDTOS = shopUserOrderDTO.getShopUserProductRelationDTOS();
            if (CommonUtils.objectIsEmpty(newProductRelationDTOS)) {
                responseDTO.setResult(StatusConstant.FAILURE);
                responseDTO.setResponseData("传入的产品信息为空");
                return responseDTO;
            }
            List<ShopUserProductRelationDTO> helperList = new ArrayList<>();
            Iterator<ShopUserProductRelationDTO> alreadyIterator = alreadyProductRelationDTOS.iterator();
            if (!alreadyIterator.hasNext()) {
                alreadyProductRelationDTOS.addAll(newProductRelationDTOS);
            } else {
                while (alreadyIterator.hasNext()) {
                    Iterator<ShopUserProductRelationDTO> newIterator = newProductRelationDTOS.iterator();
                    ShopUserProductRelationDTO alreadyRelation = alreadyIterator.next();
                    while (newIterator.hasNext()) {
                        ShopUserProductRelationDTO newRelation = newIterator.next();
                        if (!newRelation.getId().equals(alreadyRelation.getId())) {
                            if (CommonCodeEnum.ADD.getCode().equals(operation)) {
                                helperList.add(newRelation);
                            } else if (CommonCodeEnum.DELETE.getCode().equals(operation)) {
                                alreadyIterator.remove();
                            }
                        }
                    }
                }
            }

            if (CommonCodeEnum.ADD.getCode().equals(operation)) {
                alreadyProductRelationDTOS.addAll(helperList);
            }
            //更新操作
            if (alreadyProductRelationDTOS.size() != updateFlag) {
                Query updateQuery = new Query().addCriteria(Criteria.where("orderId").is(shopUserOrderDTO.getOrderId()));
                Update update = new Update();
                update.set("shopUserProductRelationDTOS", alreadyProductRelationDTOS);
                mongoTemplate.upsert(updateQuery, update, "shopUserOrderDTO");
                responseDTO.setResponseData(StatusConstant.SUCCESS);
                responseDTO.setResult(StatusConstant.SUCCESS);
            }
        }

        //套卡
        else if (GoodsTypeEnum.COLLECTION_CARD.getCode().equals(goodsType)) {
            logger.info("获取已存在的套卡信息");
            List<ShopUserProjectGroupRelRelationDTO> alreadyProjectGroupRelRelationDTOS = alreadyOrderDTO.getProjectGroupRelRelationDTOS();
            if (CommonUtils.objectIsEmpty(alreadyProjectGroupRelRelationDTOS)) {
                alreadyProjectGroupRelRelationDTOS = new ArrayList<>();
            }
            int updateFlag = alreadyProjectGroupRelRelationDTOS.size();
            List<ShopUserProjectGroupRelRelationDTO> newProjectGroupRelationDTOS = shopUserOrderDTO.getProjectGroupRelRelationDTOS();
            if (CommonUtils.objectIsEmpty(newProjectGroupRelationDTOS)) {
                responseDTO.setResult(StatusConstant.FAILURE);
                responseDTO.setResponseData("传入的产品信息为空");
                return responseDTO;
            }
            List<ShopUserProjectGroupRelRelationDTO> helperList = new ArrayList<>();
            Iterator<ShopUserProjectGroupRelRelationDTO> alreadyIterator = alreadyProjectGroupRelRelationDTOS.iterator();
            if (!alreadyIterator.hasNext()) {
                alreadyProjectGroupRelRelationDTOS.addAll(newProjectGroupRelationDTOS);
            } else {
                while (alreadyIterator.hasNext()) {
                    Iterator<ShopUserProjectGroupRelRelationDTO> newIterator = newProjectGroupRelationDTOS.iterator();
                    ShopUserProjectGroupRelRelationDTO alreadyRelation = alreadyIterator.next();
                    while (newIterator.hasNext()) {
                        ShopUserProjectGroupRelRelationDTO newRelation = newIterator.next();
                        if (!newRelation.getId().equals(alreadyRelation.getId())) {
                            if (CommonCodeEnum.ADD.getCode().equals(operation)) {
                                helperList.add(newRelation);
                            } else if (CommonCodeEnum.DELETE.getCode().equals(operation)) {
                                alreadyIterator.remove();
                            }
                        }
                    }
                }
            }

            if (CommonCodeEnum.ADD.getCode().equals(operation)) {
                alreadyProjectGroupRelRelationDTOS.addAll(helperList);
            }
            //更新操作
            if (alreadyProjectGroupRelRelationDTOS.size() != updateFlag) {
                Query updateQuery = new Query().addCriteria(Criteria.where("orderId").is(shopUserOrderDTO.getOrderId()));
                Update update = new Update();
                update.set("projectGroupRelRelationDTOS", alreadyProjectGroupRelRelationDTOS);
                mongoTemplate.upsert(updateQuery, update, "shopUserOrderDTO");
                responseDTO.setResponseData(StatusConstant.SUCCESS);
                responseDTO.setResult(StatusConstant.SUCCESS);
            }
        }

        responseDTO.setResponseData("更新成功");
        responseDTO.setResult(StatusConstant.SUCCESS);

        logger.info("更新订单虚拟商品的信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * 收银板块界面回显接口，查询所有订单里的项目id、产品id
     *
     * @param orderId 订单对象
     * @return
     */
    @RequestMapping(value = "getConsumeDisplayIds", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
    public
    @ResponseBody
    ResponseDTO<String> getConsumeDisplayIds(@RequestParam String orderId) {

        long currentTimeMillis = System.currentTimeMillis();
        logger.info("更新用户的订单信息传入参数={}", "orderId = [" + orderId + "]");
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
            hashMap.put("productSize", projectGroupRelRelationDTOS.size());
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
        logger.info("保存用户的订单信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }
}

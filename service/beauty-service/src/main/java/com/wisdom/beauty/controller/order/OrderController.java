package com.wisdom.beauty.controller.order;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.ExtCardTypeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.enums.OrderStatusEnum;
import com.wisdom.beauty.api.extDto.*;
import com.wisdom.beauty.api.responseDto.ShopProductInfoResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopProjectInfoResponseDTO;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.*;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
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
import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.ROUND_HALF_DOWN;


/**
 * FileName: OrderController
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 订单相关
 */
@Controller
@LoginAnnotations
@RequestMapping(value = "orderInfo")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ShopCardService shopCardService;

    @Resource
    private ShopOrderService shopOrderService;

    @Resource
    private ShopProjectGroupService shopProjectGroupService;

    @Resource
    private ShopProjectService shopProjectService;

    @Resource
    private ShopCustomerArchivesService shopCustomerArchivesService;

    @Resource
    private ShopProductInfoService shopProductInfoService;

    private final long orderOutTime = 10L;

    /**
     * 查询用户最近一次订单信息
     *
     * @param sysUserId 用户id
     * @return
     */
    @RequestMapping(value = "getShopUserRecentlyOrderInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<ShopUserOrderDTO> getShopUserRecentlyOrderInfo(@RequestParam(required = false) String sysUserId, @RequestParam(required = false) String orderId) {

        String sysShopId = redisUtils.getShopId();
        ResponseDTO<ShopUserOrderDTO> responseDTO = new ResponseDTO<>();

        ShopUserOrderDTO shopUserOrderDTO = null;
        if (StringUtils.isNotBlank(orderId)) {
            logger.info("根据订单编号查询订单数据={}",orderId);
            Query query = new Query(Criteria.where("orderId").is(orderId));
            shopUserOrderDTO = mongoTemplate.findOne(query, ShopUserOrderDTO.class, "shopUserOrderDTO");
        } else if(StringUtils.isNotBlank(sysShopId) && StringUtils.isNotBlank(sysUserId)){
            logger.info("根据用户id和shopId查询用户最近一次订单记录={}");
            shopUserOrderDTO = getUserRecentlyOrder(shopUserOrderDTO);
        }
        ShopUserRechargeCardDTO shopUserRechargeCardDTO = null;
        if (null != shopUserOrderDTO) {
            //查询用户账户总余额
            shopUserRechargeCardDTO = new ShopUserRechargeCardDTO();
            shopUserRechargeCardDTO.setSysUserId(shopUserOrderDTO.getUserId());
            shopUserRechargeCardDTO.setSysShopId(shopUserOrderDTO.getShopId());
            List<ShopUserRechargeCardDTO> userRechargeCardList = shopCardService.getUserRechargeCardList(shopUserRechargeCardDTO);
            if (CommonUtils.objectIsEmpty(userRechargeCardList)) {
                logger.error("用户特殊账号为空={}", "sysUserId = [" + sysUserId + "], orderId = [" + orderId + "]");
                throw new ServiceException("用户特殊账号为空");
            }
            shopUserRechargeCardDTO = userRechargeCardList.get(0);
            shopUserOrderDTO.setAvailableBalance(shopUserRechargeCardDTO.getSurplusAmount());

            //计算订单价格
            BigDecimal orderPrice = new BigDecimal(0);
            //项目价格
            List<ExtShopUserProjectRelationDTO> projectRelationDTOS = shopUserOrderDTO.getShopUserProjectRelationDTOS();
            if(CommonUtils.objectIsNotEmpty(projectRelationDTOS)){
                for(ShopUserProjectRelationDTO dto:projectRelationDTOS){
                    orderPrice = orderPrice.add(dto.getDiscountPrice().multiply(new BigDecimal(dto.getSysShopProjectInitTimes())));
                }
            }
            //产品价格
            List<ExtShopUserProductRelationDTO> productRelationDTOS = shopUserOrderDTO.getShopUserProductRelationDTOS();
            if(CommonUtils.objectIsNotEmpty(productRelationDTOS)){
                for(ShopUserProductRelationDTO dto:productRelationDTOS){
                    orderPrice = orderPrice.add(dto.getDiscountPrice().multiply(new BigDecimal(dto.getInitTimes())));
                }
            }
            //套卡价格
            List<ExtShopUserProjectGroupRelRelationDTO> group = shopUserOrderDTO.getProjectGroupRelRelationDTOS();
            if(CommonUtils.objectIsNotEmpty(group)){
                for(ExtShopUserProjectGroupRelRelationDTO dto:group){
                    orderPrice = orderPrice.add(dto.getDiscountPrice().multiply(new BigDecimal(dto.getProjectInitTimes())));
                    //查询套卡下的项目
                    ShopProjectInfoGroupRelationDTO relationDTO = new ShopProjectInfoGroupRelationDTO();
                    relationDTO.setShopProjectGroupId(dto.getShopProjectGroupId());
                    relationDTO.setSysShopId(sysShopId);
                    List<ShopProjectInfoGroupRelationDTO> projectList = shopProjectService.getShopProjectInfoGroupRelations(relationDTO);
                    dto.setProjectList(projectList);
                }
            }
            shopUserOrderDTO.setOrderPrice(orderPrice.toString());

        }
        //保持订单10分钟之内有效
        Query query = new Query().addCriteria(Criteria.where("orderId").is(shopUserOrderDTO.getOrderId()));
        Update update = new Update();
        update.set("updateDate",new Date());
        mongoTemplate.upsert(query, update, "shopUserOrderDTO");
        //默认添加余额充值
        if(null == shopUserOrderDTO.getShopUserRechargeCardDTO()){
            shopUserOrderDTO.setShopUserRechargeCardDTO(shopUserRechargeCardDTO);
            shopOrderService.updateShopUserOrderInfo(shopUserOrderDTO);
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

        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if(null == shopUserOrderDTO || StringUtils.isBlank(shopUserOrderDTO.getUserId())){
            logger.error("保存用户的订单信息传入参数为空");
            responseDTO.setErrorInfo("数据异常！请核对输入数据^_^");
            responseDTO.setResult(StatusConstant.SUCCESS);
            return responseDTO;
        }
        shopUserOrderDTO = getUserRecentlyOrder(shopUserOrderDTO);
        responseDTO.setResponseData(shopUserOrderDTO.getOrderId());
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

    /**
     * 获取用户最近一笔订单信息
     * @param shopUserOrderDTO
     * @return
     */
    public ShopUserOrderDTO getUserRecentlyOrder(ShopUserOrderDTO shopUserOrderDTO){
        //先查询最后一次未支付的订单信息
        Criteria notPay = new Criteria().and("status").is(OrderStatusEnum.NOT_PAY.getCode());
        Criteria waitPay = new Criteria().and("status").is(OrderStatusEnum.WAIT_PAY.getCode());
        Criteria waitSign = new Criteria().and("status").is(OrderStatusEnum.WAIT_SIGN.getCode());
        String shopId = redisUtils.getShopId();
        Query query = new Query(Criteria.where("shopId").is(shopId)).addCriteria(Criteria.where("userId").
                is(shopUserOrderDTO.getUserId())).addCriteria(new Criteria().orOperator(notPay,waitPay,waitSign));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createDate")));
        ShopUserOrderDTO searchOrderInfo = mongoTemplate.findOne(query, ShopUserOrderDTO.class, "shopUserOrderDTO");
        //最近一笔未支付的订单不为空，并且在10分钟以内，则认为有效订单
        if (null != searchOrderInfo && null!= searchOrderInfo.getUpdateDate() && DateUtils.pastMinutes(searchOrderInfo.getUpdateDate())<orderOutTime) {
            return searchOrderInfo;
        }
        //如果最后一次订单为空则需初始化插入
        searchOrderInfo = new ShopUserOrderDTO();
        searchOrderInfo.setShopId(shopId);
        searchOrderInfo.setOrderId(DateUtils.DateToStr(new Date(), "dateMillisecond"));
        searchOrderInfo.setStatus(OrderStatusEnum.NOT_PAY.getCode());
        searchOrderInfo.setCreateDate(new Date());
        searchOrderInfo.setUpdateDate(new Date());
        searchOrderInfo.setStatusDesc(OrderStatusEnum.NOT_PAY.getDesc());
        searchOrderInfo.setUserId(shopUserOrderDTO.getUserId());
        mongoTemplate.save(searchOrderInfo, "shopUserOrderDTO");
        return searchOrderInfo;
    }
    /**
     * 用户下单接口，消费界面选完待消费物品点击确认按钮
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
        update.set("status", OrderStatusEnum.WAIT_PAY.getCode());
        update.set("statusDesc", OrderStatusEnum.WAIT_PAY.getDesc());
        update.set("signUrl", shopUserOrderDTO.getSignUrl());
        update.set("orderPrice", shopUserOrderDTO.getOrderPrice());
        update.set("updateDate",new Date());
        update.set("sysClerkId",shopUserOrderDTO.getSysClerkId());
        update.set("sysClerkName",shopUserOrderDTO.getSysClerkName());
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
     * 获取订单消费明细
     *
     * @param orderId 订单id
     * @return
     */
    @RequestMapping(value = "/getOrderConsumeDetailInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<Object> getOrderConsumeDetailInfo(@RequestParam String orderId) {
        ResponseDTO<Object> responseDTO = new ResponseDTO();
        Query query = new Query(Criteria.where("orderId").is(orderId));
        String shopId = redisUtils.getShopId();
        ShopUserOrderDTO userOrderDTO = mongoTemplate.findOne(query, ShopUserOrderDTO.class, "shopUserOrderDTO");
        if(null != userOrderDTO){
            HashMap<Object, Object> responseMap = new HashMap<>(4);
            //解析项目
            List<ExtShopUserProjectRelationDTO> projectInfo = userOrderDTO.getShopUserProjectRelationDTOS();
            if(CommonUtils.objectIsNotEmpty(projectInfo)){
                //存储单次列表
                List<Object> timeProjectList = new ArrayList<>();
                //存储疗程卡列表
                List<Object> periodProjectList = new ArrayList<>();
                for(ShopUserProjectRelationDTO dto:projectInfo){

                    ShopProjectInfoResponseDTO projectDetail = shopProjectService.getProjectDetail(dto.getSysShopProjectId());
                    dto.setSysShopProjectName(projectDetail.getProjectName());
                    //如果是疗程卡
                    if(ExtCardTypeEnum.TREATMENT_CARD.getCode().equals(dto.getUseStyle())){
                        periodProjectList.add(dto);
                    }
                    //单次卡
                    else{
                        timeProjectList.add(dto);
                    }
                }
                responseMap.put("timeProjectList",timeProjectList);
                responseMap.put("periodProjectList",periodProjectList);
            }
            //解析套卡
            List<ExtShopUserProjectGroupRelRelationDTO> projectGroupInfo = userOrderDTO.getProjectGroupRelRelationDTOS();
            if (CommonUtils.objectIsNotEmpty(projectGroupInfo)) {
                List<Object> groupList = new ArrayList<>();
                //遍历每种套卡信息
                for (ShopUserProjectGroupRelRelationDTO groupDto : projectGroupInfo) {
                    Map<Object, Object> eachMap = new HashMap<>(16);
                    eachMap.put("shopProjectGroupName",groupDto.getShopProjectGroupName());
                    int serviceTime = 0;
                    //默认一种套卡只购买一个
                    if (null == groupDto.getProjectInitTimes()) {
                        groupDto.setProjectInitTimes(1);
                    }
                    //查询套卡信息
                    ShopProjectGroupDTO shopProjectGroupDTO = shopProjectGroupService.getShopProjectGroupDTO(groupDto.getShopProjectGroupId());
                    //购买一个套卡的金额
                    BigDecimal discount = groupDto.getShopGroupPuchasePrice().divide(shopProjectGroupDTO.getMarketPrice(), 2, ROUND_HALF_DOWN);
                    groupDto.setDiscount(discount.floatValue());
                    eachMap.put("discount",discount);
                    //单个购买价格
                    eachMap.put("discountPrice",groupDto.getDiscountPrice());
                    eachMap.put("number",groupDto.getProjectInitTimes());
                    //总金额
                    eachMap.put("projectInitAmount",groupDto.getDiscountPrice().multiply(new BigDecimal(groupDto.getProjectInitTimes())));
                    //同一种套卡购买多个，groupDto.getProjectInitTimes()存储的是购买了几个同一种套卡
                    for (int i = 0; i < groupDto.getProjectInitTimes(); i++) {

                        //根据套卡id查询项目列表
                        ShopProjectInfoGroupRelationDTO shopProjectInfoGroupRelationDTO = new ShopProjectInfoGroupRelationDTO();
                        shopProjectInfoGroupRelationDTO.setShopProjectGroupId(groupDto.getShopProjectGroupId());
                        shopProjectInfoGroupRelationDTO.setSysShopId(shopId);
                        List<ShopProjectInfoGroupRelationDTO> groupRelations = shopProjectService.getShopProjectInfoGroupRelations(shopProjectInfoGroupRelationDTO);
                        if(CommonUtils.objectIsNotEmpty(groupRelations)){
                            for(ShopProjectInfoGroupRelationDTO dto:groupRelations){
                                serviceTime = serviceTime+dto.getShopProjectServiceTimes();
                            }
                        }
                        eachMap.put("containProject",groupRelations);
                    }
                    eachMap.put("serviceTime",serviceTime);
                    groupList.add(eachMap);
                }
                responseMap.put("groupList",groupList);
            }
            //解析产品
            List<ExtShopUserProductRelationDTO> productList = userOrderDTO.getShopUserProductRelationDTOS();
            if(CommonUtils.objectIsNotEmpty(productList)){
                for(ShopUserProductRelationDTO dto : productList){
                    ShopProductInfoResponseDTO productDetail = shopProductInfoService.getProductDetail(dto.getShopProductId());
                    dto.setShopProductName(productDetail.getProductName());
                }
                responseMap.put("productList",productList);
            }
            //支付金额相关
            responseDTO.setResponseData(responseMap);
            ShopUserPayDTO shopUserPayDTO = userOrderDTO.getShopUserPayDTO();
            shopUserPayDTO.setRechargeCardPay(userOrderDTO.getRechargeCardPay());
            responseMap.put("shopUserPayDTO",shopUserPayDTO);
            //查询用户信息
            ShopUserArchivesDTO shopUserArchivesDTO = new ShopUserArchivesDTO();
            shopUserArchivesDTO.setSysUserId(userOrderDTO.getUserId());
            shopUserArchivesDTO.setSysShopId(shopId);
            List<ShopUserArchivesDTO> shopUserArchivesInfo = shopCustomerArchivesService.getShopUserArchivesInfo(shopUserArchivesDTO);
            if(CommonUtils.objectIsNotEmpty(shopUserArchivesInfo)){
                responseMap.put("userInfo",shopUserArchivesInfo.get(0));
            }

        }
        responseDTO.setResult(StatusConstant.SUCCESS);
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
        List<ExtShopUserProjectRelationDTO> shopUserProjectRelationDTOS = userOrderDTO.getShopUserProjectRelationDTOS();
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
        List<ExtShopUserProjectGroupRelRelationDTO> projectGroupRelRelationDTOS = userOrderDTO.getProjectGroupRelRelationDTOS();
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
        List<ExtShopUserProductRelationDTO> shopUserProductRelationDTOS = userOrderDTO.getShopUserProductRelationDTOS();
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

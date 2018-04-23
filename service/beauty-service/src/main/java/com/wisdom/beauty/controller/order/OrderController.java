package com.wisdom.beauty.controller.order;

import com.wisdom.beauty.api.enums.OrderStatusEnum;
import com.wisdom.beauty.api.extDto.ShopUserOrderDTO;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

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

    /**
     * 查询用户最近一次订单信息
     *
     * @param shopUserArchivesId 档案表主键
     * @return
     */
    @RequestMapping(value = "getShopUserRecentlyOrderInfo", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
    public
    @ResponseBody
    ResponseDTO<ShopUserOrderDTO> getShopUserRecentlyOrderInfo(@RequestParam String shopUserArchivesId) {

        long startTime = System.currentTimeMillis();
        logger.info("查询用户最近一次订单信息传入参数={}", "shopUserArchivesId = [" + shopUserArchivesId + "]");
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        ResponseDTO<ShopUserOrderDTO> responseDTO = new ResponseDTO<>();

        Query query = new Query(Criteria.where("shopId").is(clerkInfo.getSysShopId())).addCriteria(Criteria.where("shopUserArchivesId").is(shopUserArchivesId));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createDate")));
        ShopUserOrderDTO shopUserOrderDTO = mongoTemplate.findOne(query, ShopUserOrderDTO.class, "shopUserOrderDTO");

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
//	@LoginRequired
    public
    @ResponseBody
    ResponseDTO<String> saveShopUserOrderInfo(@RequestBody ShopUserOrderDTO shopUserOrderDTO) {

        long currentTimeMillis = System.currentTimeMillis();
        logger.info("保存用户的订单信息传入参数={}", "shopUserOrderDTO = [" + shopUserOrderDTO + "]");
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        shopUserOrderDTO.setShopId(clerkInfo.getSysShopId());
        shopUserOrderDTO.setOrderId(DateUtils.DateToStr(new Date(), "dateMillisecond"));
        shopUserOrderDTO.setStatus(OrderStatusEnum.NOT_PAY.getCode());
        shopUserOrderDTO.setCreateDate(new Date());

        mongoTemplate.insert(shopUserOrderDTO, "shopUserOrderDTO");

        responseDTO.setResponseData(StatusConstant.SUCCESS);
        responseDTO.setResult(StatusConstant.SUCCESS);

        logger.info("保存用户的订单信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

}

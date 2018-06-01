package com.wisdom.beauty.controller.pay;

import com.wisdom.beauty.api.enums.OrderStatusEnum;
import com.wisdom.beauty.api.extDto.ShopUserOrderDTO;
import com.wisdom.beauty.api.extDto.ShopUserPayDTO;
import com.wisdom.beauty.core.service.ShopUserConsumeService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * FileName: OrderController
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
@Controller
@LoginAnnotations
@RequestMapping(value = "userPay")
public class PayController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private ShopUserConsumeService shopUserConsumeService;

    /**
     * 用户支付接口
     *
     * @param shopUserPayDTO
     * @return
     */
    @RequestMapping(value = "userPayOpe", method = {RequestMethod.POST})

    public
    @ResponseBody
    ResponseDTO<ShopUserOrderDTO> userPayOpe(@RequestBody ShopUserPayDTO shopUserPayDTO) {

        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        ResponseDTO<ShopUserOrderDTO> responseDTO = new ResponseDTO<>();

        Query query = new Query(Criteria.where("orderId").is(shopUserPayDTO.getOrderId()));
        ShopUserOrderDTO shopUserOrderDTO = mongoTemplate.findOne(query, ShopUserOrderDTO.class, "shopUserOrderDTO");

        int operation = shopUserConsumeService.userRechargeOperation(shopUserOrderDTO, shopUserPayDTO, clerkInfo);

        responseDTO.setResult(operation > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);

        return responseDTO;
    }

    /**
     * 用户支付确认接口
     *
     * @param shopUserPayDTO 付款对象
     * @return
     */
    @RequestMapping(value = "paySignConfirm", method = {RequestMethod.POST, RequestMethod.GET})

    public
    @ResponseBody
    ResponseDTO<String> paySignConfirm(@RequestBody ShopUserPayDTO shopUserPayDTO) {

        ResponseDTO responseDTO = new ResponseDTO<String>();

        //mongodb中更新订单的状态
        Query query = new Query().addCriteria(Criteria.where("orderId").is(shopUserPayDTO.getOrderId()));
        Update update = new Update();
        update.set("status", OrderStatusEnum.CONFIRM_PAY.getCode());
        update.set("signUrl", shopUserPayDTO.getSignUrl());
        mongoTemplate.upsert(query, update, "shopUserOrderDTO");
        responseDTO.setResponseData(StatusConstant.SUCCESS);
        responseDTO.setResult(StatusConstant.SUCCESS);

        return responseDTO;
    }

    /**
     * 更新订单的充值卡信息，pad端支付界面选择充值卡抵扣
     *
     * @param shopUserOrderDTO 订单对象
     * @return
     */
    @RequestMapping(value = "updateShopUserOrderPayInfo", method = {RequestMethod.POST, RequestMethod.GET})

    public
    @ResponseBody
    ResponseDTO<String> updateShopUserOrderPayInfo(@RequestBody ShopUserOrderDTO shopUserOrderDTO) {

        ResponseDTO responseDTO = new ResponseDTO<String>();

        //mongodb中更新订单的状态
        Query query = new Query().addCriteria(Criteria.where("orderId").is(shopUserOrderDTO.getOrderId()));
        Update update = new Update();
        update.set("userPayRechargeCardList", shopUserOrderDTO.getUserPayRechargeCardList());
        mongoTemplate.upsert(query, update, "shopUserOrderDTO");
        responseDTO.setResponseData(StatusConstant.SUCCESS);
        responseDTO.setResult(StatusConstant.SUCCESS);

        return responseDTO;
    }

}

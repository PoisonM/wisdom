package com.wisdom.beauty.controller.pay;

import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.api.enums.OrderStatusEnum;
import com.wisdom.beauty.api.extDto.ExtShopUserRechargeCardDTO;
import com.wisdom.beauty.api.extDto.ShopUserOrderDTO;
import com.wisdom.beauty.api.extDto.ShopUserPayDTO;
import com.wisdom.beauty.core.service.ShopRechargeCardService;
import com.wisdom.beauty.core.service.ShopUerConsumeRecordService;
import com.wisdom.beauty.core.service.ShopUserConsumeService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.RedisLock;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import java.util.ArrayList;
import java.util.Date;
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
@RequestMapping(value = "userPay")
public class PayController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private ShopRechargeCardService shopRechargeCardService;

    @Resource
    private ShopUerConsumeRecordService shopUerConsumeRecordService;

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
        ResponseDTO<ShopUserOrderDTO> responseDTO = new ResponseDTO<>();
        if(null == shopUserPayDTO || StringUtils.isBlank(shopUserPayDTO.getOrderId())){
            responseDTO.setErrorInfo("数据异常！请联系客服，谢谢");
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        RedisLock redisLock = null;
        try {
            redisLock = new RedisLock("userPayOpe:"+shopUserPayDTO.getOrderId());
            redisLock.lock();
            Query query = new Query(Criteria.where("orderId").is(shopUserPayDTO.getOrderId()));
            ShopUserOrderDTO shopUserOrderDTO = mongoTemplate.findOne(query, ShopUserOrderDTO.class, "shopUserOrderDTO");
            //用户为待支付的状态才能执行签字确认操作
            if(OrderStatusEnum.WAIT_PAY.getCode().equals(shopUserOrderDTO.getStatus()) ||
                    OrderStatusEnum.WAIT_SIGN.getCode().equals(shopUserOrderDTO.getStatus())){
                Update update = new Update();
                update.set("status", OrderStatusEnum.WAIT_SIGN.getCode());
                update.set("statusDesc",OrderStatusEnum.WAIT_SIGN.getDesc());
                update.set("signUrl", shopUserPayDTO.getSignUrl());
                update.set("shopUserPayDTO",shopUserPayDTO);
                update.set("updateDate",new Date());
                mongoTemplate.upsert(query, update, "shopUserOrderDTO");
            }else{
                responseDTO.setResult(StatusConstant.FAILURE);
                responseDTO.setErrorInfo("订单已失效，请勿重复操作");
            }
        } catch (Exception e) {
            logger.error("订单支付失败，失败信息为={}"+e.getMessage(),e);
            responseDTO.setErrorInfo("处理异常，请联系客服，谢谢");
            responseDTO.setResult(StatusConstant.FAILURE);
        }finally {
            redisLock.unlock();
        }
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
        Query query = new Query(Criteria.where("orderId").is(shopUserPayDTO.getOrderId()));
        ShopUserOrderDTO shopUserOrderDTO = mongoTemplate.findOne(query, ShopUserOrderDTO.class, "shopUserOrderDTO");
        if(null == shopUserOrderDTO){
            responseDTO.setErrorInfo("未查询到此订单信息");
            responseDTO.setResult(StatusConstant.FAILURE);
            return responseDTO;
        }
        RedisLock redisLock = null;
        try {
            redisLock = new RedisLock("paySign:"+shopUserPayDTO.getOrderId());
            redisLock.lock();
            //进入待签字确认状态之后才能签字确认
            if(OrderStatusEnum.WAIT_SIGN.getCode().equals(shopUserOrderDTO.getStatus())){
                //mongodb中更新订单的状态
                shopUserOrderDTO.getShopUserPayDTO().setSignUrl(shopUserPayDTO.getSignUrl());
                responseDTO = shopUserConsumeService.userRechargeOperation(shopUserOrderDTO);
            }else{
                responseDTO.setErrorInfo("订单已失效，请勿重复提交");
                responseDTO.setResult(StatusConstant.FAILURE);
            }
        } catch (Exception e) {
            logger.error("签字确认失败，失败信息为={}"+e.getMessage(),e);
        }finally {
            redisLock.unlock();
        }

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
        if(StringUtils.isBlank(shopUserOrderDTO.getOrderId())){
            responseDTO.setErrorInfo("订单号为空");
            responseDTO.setResult(StatusConstant.FAILURE);
            return responseDTO;
        }
        List<ExtShopUserRechargeCardDTO> userPayRechargeCardList = shopUserOrderDTO.getUserPayRechargeCardList();
        List<ExtShopUserRechargeCardDTO> filterList = new ArrayList<>();
        if(CommonUtils.objectIsNotEmpty(userPayRechargeCardList)){
            userPayRechargeCardList.forEach(e->{
                ShopUserRechargeCardDTO shopUserRechargeInfo = shopRechargeCardService.getShopUserRechargeInfo(e);
                ExtShopUserRechargeCardDTO extShopUserRechargeCardDTO = new ExtShopUserRechargeCardDTO();
                BeanUtils.copyProperties(shopUserRechargeInfo,extShopUserRechargeCardDTO);
                extShopUserRechargeCardDTO.setConsumePrice(e.getConsumePrice());
                filterList.add(extShopUserRechargeCardDTO);
            });
        }
        //mongodb中更新订单的状态
        Query query = new Query().addCriteria(Criteria.where("orderId").is(shopUserOrderDTO.getOrderId()));
        Update update = new Update();
        update.set("userPayRechargeCardList", filterList);
        update.set("updateDate",new Date());
        mongoTemplate.upsert(query, update, "shopUserOrderDTO");
        responseDTO.setResponseData(StatusConstant.SUCCESS);
        responseDTO.setResult(StatusConstant.SUCCESS);

        return responseDTO;
    }

}

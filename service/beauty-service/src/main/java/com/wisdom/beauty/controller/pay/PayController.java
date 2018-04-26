package com.wisdom.beauty.controller.pay;

import com.wisdom.beauty.api.extDto.ShopUserOrderDTO;
import com.wisdom.beauty.api.extDto.ShopUserPayDTO;
import com.wisdom.beauty.core.service.ShopUserConsumeService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
//	@LoginRequired
    public
    @ResponseBody
    ResponseDTO<ShopUserOrderDTO> userPayOpe(@RequestBody ShopUserPayDTO shopUserPayDTO) {

        long currentTimeMillis = System.currentTimeMillis();

        logger.info("用户支付接口传入参数={}", "shopUserPayDTO = [" + shopUserPayDTO.toString() + "]");
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        ResponseDTO<ShopUserOrderDTO> responseDTO = new ResponseDTO<>();

        Query query = new Query(Criteria.where("orderId").is(shopUserPayDTO.getOrderId()));
        ShopUserOrderDTO shopUserOrderDTO = mongoTemplate.findOne(query, ShopUserOrderDTO.class, "shopUserOrderDTO");

        int operation = shopUserConsumeService.userRechargeOperation(shopUserOrderDTO, shopUserPayDTO, clerkInfo);

        responseDTO.setResult(operation > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);

        logger.info("用户支付接口耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

}

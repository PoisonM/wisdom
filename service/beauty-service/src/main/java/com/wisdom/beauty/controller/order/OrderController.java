package com.wisdom.beauty.controller.order;

import com.wisdom.beauty.api.extDto.ShopUserOrderDTO;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /**
     * 保存用户的订单信息
     *
     * @param shopUserOrderDTO
     * @return
     */
    @RequestMapping(value = "saveShopUserOrderInfo", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
    public
    @ResponseBody
    ResponseDTO<String> saveShopUserOrderInfo(@RequestBody ShopUserOrderDTO shopUserOrderDTO) {

        long startTime = System.currentTimeMillis();
//		logger.info("获取某次预约详情传入参数={}", "shopAppointServiceId = [" + shopAppointServiceId + "]");

        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        responseDTO.setResult(StatusConstant.SUCCESS);
//		responseDTO.setResponseData();
        logger.info("获取某次预约详情传入参数耗时{}毫秒", (System.currentTimeMillis() - startTime));

        return responseDTO;
    }

}

/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 工具 Controller
 *
 * @author ThinkGem
 * @version 2013-10-17
 */
@Controller
public class OrderPayController {

    /**
     *
     * 处理来自微信服务器的请求
     *
     */
    @RequestMapping(value = "customer/orderPay.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String wxPay(@RequestParam String productType, @RequestParam(required=false) String productId, Model model) {

        if(productType.equals("offline")){
            model.addAttribute("productType", productType);
            return "native/orderPay";
        }
        if(productType.equals("special")){
            model.addAttribute("productType", productType);
            return "native/orderPay";
        }
        else if(productType.equals("trainingProduct"))
        {
            model.addAttribute("productType", productType);
            model.addAttribute("productId", productId);
            return "native/orderPay";
        }
        else
        {
            return null;
        }
    }

}


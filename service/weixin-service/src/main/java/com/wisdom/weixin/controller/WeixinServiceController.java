package com.wisdom.weixin.controller;

import com.wisdom.weixin.service.user.WeixinUserCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenjiake on 17/11/4.
 * 负责响应微商平台用户端微信公众平台的请求
 *
 */

@RestController
public class WeixinServiceController {

    @Autowired
    private WeixinUserCoreService weixinCustomerCoreService;

    @RequestMapping(value = "/updateUserWeixinToken",method=RequestMethod.POST)
    @ResponseBody
    void updateUserWeixinToken()
    {
        weixinCustomerCoreService.updateUserWeixinToken();
    }

    @RequestMapping(value = "/updateBossWeixinToken",method=RequestMethod.POST)
    @ResponseBody
    void updateBossWeixinToken()
    {
        weixinCustomerCoreService.updateUserWeixinToken();
    }

}

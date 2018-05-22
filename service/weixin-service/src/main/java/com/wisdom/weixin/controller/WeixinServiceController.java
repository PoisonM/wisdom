package com.wisdom.weixin.controller;

import com.wisdom.weixin.service.beauty.WeixinBeautyCoreService;
import com.wisdom.weixin.service.user.WeixinUserCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by chenjiake on 17/11/4.
 * 负责响应微商平台用户端微信公众平台的请求
 *
 */

@RestController
public class WeixinServiceController {

    @Autowired
    private WeixinUserCoreService weixinCustomerCoreService;

    @Autowired
    private WeixinBeautyCoreService weixinBeautyCoreService;

    @RequestMapping(value = "/updateUserWeixinToken",method=RequestMethod.POST)
    @ResponseBody
    void updateUserWeixinToken()
    {
        weixinCustomerCoreService.updateUserWeixinToken();
    }

    @RequestMapping(value = "/updateBeautyWeixinToken",method=RequestMethod.POST)
    @ResponseBody
    void updateBeautyWeixinToken()
    {
        weixinBeautyCoreService.updateBeautyWeixinToken();
    }

    @RequestMapping(value = "/updateBossWeixinToken",method=RequestMethod.POST)
    @ResponseBody
    void updateBossWeixinToken()
    {
        weixinCustomerCoreService.updateUserWeixinToken();
    }

    @RequestMapping(value = "/getTemporaryQrCode", method = RequestMethod.GET)
    @ResponseBody
    String getTemporaryQrCode(@RequestParam(value = "info") String info) {
        return weixinCustomerCoreService.getUserQRCode(info);
    }

}

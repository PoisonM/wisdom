package com.wisdom.timer.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("weixin-service")
public interface WeixinServiceClient {

    @RequestMapping(value = "/updateUserWeixinToken",method=RequestMethod.POST)
    void updateUserWeixinToken();

    @RequestMapping(value = "/updateBeautyWeixinToken",method=RequestMethod.POST)
    void updateBeautyWeixinToken();
}

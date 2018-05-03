package com.wisdom.beauty.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("weixin-service")
public interface WeixinServiceClient {

    @RequestMapping(value = "/getTemporaryQrCode", method = RequestMethod.GET)
    String getTemporaryQrCode(@RequestParam(value = "info") String info);
}

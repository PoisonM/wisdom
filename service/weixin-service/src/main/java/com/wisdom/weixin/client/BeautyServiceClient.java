package com.wisdom.weixin.client;

import com.wisdom.common.dto.system.ResponseDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("beauty-service")
public interface BeautyServiceClient {

    /**
     * 获取用户绑定关系
     *
     * @param openId
     * @param shopId
     * @return
     */
    @RequestMapping(value = "/getUserBindingInfo", method = RequestMethod.GET)
    ResponseDTO<String> getUserBindingInfo(@RequestParam(value = "openId") String openId, @RequestParam(value = "shopId") String shopId);
}

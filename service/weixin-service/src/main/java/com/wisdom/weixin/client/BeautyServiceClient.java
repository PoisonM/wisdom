package com.wisdom.weixin.client;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("beauty-service")
public interface BeautyServiceClient {

}

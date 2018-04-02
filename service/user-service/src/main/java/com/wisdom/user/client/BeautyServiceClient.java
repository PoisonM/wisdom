package com.wisdom.user.client;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("beauty-service")
public interface BeautyServiceClient {

}

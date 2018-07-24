package com.wisdom.search.client;

import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("beauty-service")
public interface BeautyServiceClient {
	@RequestMapping(value = "/clerkSchedule/saveOneClerkSchedule", method = RequestMethod.POST)
	ResponseDTO<Object> saveOneClerkSchedule(@RequestBody SysClerkDTO sysClerkDTO);

}

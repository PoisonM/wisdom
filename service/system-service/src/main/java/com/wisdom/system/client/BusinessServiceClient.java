package com.wisdom.system.client;

import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("business-service")
public interface BusinessServiceClient {

    @RequestMapping(value = "/getUserBusinessType",method= RequestMethod.POST)
    List<UserBusinessTypeDTO> getUserBusinessType(@RequestBody UserBusinessTypeDTO userBusinessTypeDTO);

}

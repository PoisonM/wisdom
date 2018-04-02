package com.wisdom.business.client;

import com.wisdom.common.dto.system.UserInfoDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("user-service")
public interface UserServiceClient {

    //project1
    @RequestMapping(value = "/getUserInfo",method=RequestMethod.POST)
    List<UserInfoDTO> getUserInfo(@RequestBody UserInfoDTO userInfoDTO);

    @RequestMapping(value = "/getUserInfoFromUserId",method=RequestMethod.GET)
    UserInfoDTO getUserInfoFromUserId(@RequestParam(value="userId") String userId);

    @RequestMapping(value = "/updateUserInfo",method=RequestMethod.POST)
    void updateUserInfo(@RequestBody UserInfoDTO userInfoDTO);
}

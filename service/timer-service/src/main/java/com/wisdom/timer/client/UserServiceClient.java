package com.wisdom.timer.client;

import com.wisdom.common.dto.user.UserInfoDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("user-service")
public interface UserServiceClient {

    @RequestMapping(value = "/getUserInfo",method=RequestMethod.POST)
    List<UserInfoDTO> getUserInfo(@RequestBody UserInfoDTO userInfoDTO);

    @RequestMapping(value = "/updateUserInfo",method=RequestMethod.POST)
    void updateUserInfo(@RequestBody UserInfoDTO userInfo);

}

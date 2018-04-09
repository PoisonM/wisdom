package com.wisdom.beauty.client;

import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("user-service")
public interface UserServiceClient {


    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    List<UserInfoDTO> getUserInfo(@RequestBody UserInfoDTO userInfoDTO);

    /**
     *
     * 获取店员、美容师相关信息
     * @param shopId
     * @return
     */
    @RequestMapping(value = "/getClerkInfo", method = RequestMethod.POST)
    List<SysClerkDTO> getClerkInfo(@RequestParam(value = "shopId") String shopId);

    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getUserInfoFromUserId", method = RequestMethod.GET)
    UserInfoDTO getUserInfoFromUserId(@RequestParam(value = "userId") String userId);
}

package com.wisdom.business.client;

import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.user.RealNameInfoDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("user-service")
public interface UserServiceClient {

    //project2
    @RequestMapping(value = "/getUserInfo",method=RequestMethod.POST)
    List<UserInfoDTO> getUserInfo(@RequestBody UserInfoDTO userInfoDTO);

    @RequestMapping(value = "/getUserInfoFromUserId",method=RequestMethod.GET)
    UserInfoDTO getUserInfoFromUserId(@RequestParam(value="userId") String userId);

    @RequestMapping(value = "/updateUserInfo",method=RequestMethod.POST)
    void updateUserInfo(@RequestBody UserInfoDTO userInfoDTO);

    @RequestMapping(value = "/verifyUserIdentify",method=RequestMethod.GET)
    RealNameInfoDTO verifyUserIdentify(@RequestParam(value="idCard") String idCard, @RequestParam(value="name") String name);

    @RequestMapping(value = "/queryUserInfoDTOByParameters",method=RequestMethod.POST)
    PageParamDTO<List<UserInfoDTO>> queryUserInfoDTOByParameters(@RequestBody PageParamVoDTO<UserInfoDTO> pageParamVoDTO);

}

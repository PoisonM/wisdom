package com.wisdom.business.hystric;

import com.wisdom.business.client.UserServiceClient;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.user.RealNameInfoDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
public class UserServiceHystric implements UserServiceClient {


    @Override
    public List<UserInfoDTO> getUserInfo(UserInfoDTO userInfoDTO) {
        return null;
    }

    @Override
    public UserInfoDTO getUserInfoFromUserId(String userId) {
        return null;
    }

    @Override
    public void updateUserInfo(UserInfoDTO userInfoDTO) {

    }

    @Override
    public RealNameInfoDTO verifyUserIdentify(String idCard, String name) {
        return null;
    }

    @Override
    public PageParamDTO<List<UserInfoDTO>> queryUserInfoDTOByParameters(PageParamVoDTO<UserInfoDTO> pageParamVoDTO) {
        return null;
    }
}

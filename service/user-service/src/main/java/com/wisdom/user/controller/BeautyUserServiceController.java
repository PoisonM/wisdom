package com.wisdom.user.controller;

import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.user.service.BeautyUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class BeautyUserServiceController {

    @Autowired
    private BeautyUserInfoService beautyUserInfoService;


    @RequestMapping(value = "/getBeautyUserInfo", method = RequestMethod.POST)
    @ResponseBody
    List<UserInfoDTO> getBeautyUserInfo(@RequestBody UserInfoDTO userInfoDTO) {
        return beautyUserInfoService.getBeautyUserInfo(userInfoDTO);
    }

    @RequestMapping(value = "/getBeautyUserInfoFromUserId", method = RequestMethod.GET)
    @ResponseBody
    UserInfoDTO getBeautyUserInfoFromUserId(@RequestParam(value = "userId") String userId) {
        return beautyUserInfoService.getBeautyUserInfoFromUserId(userId);
    }

    @RequestMapping(value = "/updateBeautyUserInfo", method = RequestMethod.POST)
    @ResponseBody
    void updateBeautyUserInfo(@RequestBody UserInfoDTO userInfoDTO) {
        beautyUserInfoService.updateBeautyUserInfo(userInfoDTO);
    }

    @RequestMapping(value = "/insertBeautyUserInfo", method = RequestMethod.POST)
    @ResponseBody
    void insertBeautyUserInfo(@RequestBody UserInfoDTO userInfoDTO) {
        beautyUserInfoService.insertBeautyUserInfo(userInfoDTO);
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description:
     * @Date:2018/5/24 16:43
     */
    @RequestMapping(value = "/beauty/getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    List<UserInfoDTO> getUserInfo(@RequestBody UserInfoDTO userInfoDTO) {
        return beautyUserInfoService.getBeautyUserInfo(userInfoDTO);
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description:
     * @Date:2018/5/24 16:58
     */
    @RequestMapping(value = "/beauty/getUserInfoFromUserId", method = RequestMethod.GET)
    @ResponseBody
    UserInfoDTO getUserInfoFromUserId(@RequestParam(value = "userId") String userId) {
        return beautyUserInfoService.getBeautyUserInfoFromUserId(userId);
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description:
     * @Date:2018/5/24 17:06
     */
    @RequestMapping(value = "/beauty/insertUserInfo", method = RequestMethod.POST)
    @ResponseBody
    void insertUserInfo(@RequestBody UserInfoDTO userInfoDTO) {
        beautyUserInfoService.insertBeautyUserInfo(userInfoDTO);
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description:
     * @Date:2018/5/24 17:09
     */
    @RequestMapping(value = "/beauty/getUserInfoListFromUserId", method = RequestMethod.GET)
    @ResponseBody
    List<UserInfoDTO> getUserInfoFromUserId(@RequestParam String[] userIds,
                                            @RequestParam(required = false) String searchFile) {
        List<String> list = Arrays.asList(userIds);
        return beautyUserInfoService.getUserInfoFromUserId(list, searchFile);
    }

    @RequestMapping(value = "/beauty/updateUserInfo",method=RequestMethod.POST)
    @ResponseBody
    void updateUserInfo(@RequestBody UserInfoDTO userInfoDTO)
    {
        beautyUserInfoService.updateUserInfo(userInfoDTO);
    }
}

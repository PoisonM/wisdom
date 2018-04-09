package com.wisdom.business.controller.transaction;

import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 微信页面参数获取相关的控制类
 * Created by baoweiw on 2015/7/27.
 */

@Controller
@RequestMapping(value = "transaction")
public class AttentionTeacherController {

    /**
     * 根据用户的所有收货地址
     *
     * input PageParamDto
     *
     * output ResponseDTO<List<ProductDTO>>
     *
     */
    @RequestMapping(value = "getAttentionTeacherStatus", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<String> getAttentionTeacherStatus() {
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        ResponseDTO responseDTO = new ResponseDTO();
        if(!ObjectUtils.isNullOrEmpty(userInfoDTO))
        {
            responseDTO.setResponseData(JedisUtils.get(userInfoDTO.getMobile()+"attentionTeacher"));
        }
        return responseDTO;
    }

    /**
     * 根据用户id查询收货地址
     *
     * input PageParamDto
     *
     * output ResponseDTO<List<ProductDTO>>
     *
     */
    @RequestMapping(value = "attentionTeacher", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO attentionTeacher() {
        ResponseDTO responseDTO = new ResponseDTO();
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        if(!ObjectUtils.isNullOrEmpty(userInfoDTO))
        {
            JedisUtils.set(userInfoDTO.getMobile()+"attentionTeacher","true", ConfigConstant.logintokenPeriod);
        }
        return responseDTO;
    }
}

package com.wisdom.business.controller.transaction;

import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 查询用户的所有收货地址
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
        long startTime = System.currentTimeMillis();
        logger.info("查询用户的所有收货地址==={}开始" , startTime);
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        ResponseDTO responseDTO = new ResponseDTO();
        if(!ObjectUtils.isNullOrEmpty(userInfoDTO))
        {
            logger.info("查询用户{}的所有收货地址" , userInfoDTO.getMobile());
            responseDTO.setResponseData(JedisUtils.get(userInfoDTO.getMobile()+"attentionTeacher"));
        }
        logger.info("查询用户的所有收货地址,耗时{}毫秒", (System.currentTimeMillis() - startTime));
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
        long startTime = System.currentTimeMillis();
        logger.info("查询用户的所有收货地址==={}开始" , startTime);
        ResponseDTO responseDTO = new ResponseDTO();
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        if(!ObjectUtils.isNullOrEmpty(userInfoDTO))
        {
            JedisUtils.set(userInfoDTO.getMobile()+"attentionTeacher","true", ConfigConstant.logintokenPeriod);
        }
        logger.info("查询用户的所有收货地址,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }

 /*   *//**
     * 查询用户等级
     *
     *
     * *//*
    @RequestMapping(value = "getUserInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<UserInfoDTO> getUserInfo() {
        long startTime = System.currentTimeMillis();
        ResponseDTO<UserInfoDTO> responseDTO = new ResponseDTO();
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        responseDTO.setResponseData(userInfoDTO);
        return responseDTO;
    }*/
}

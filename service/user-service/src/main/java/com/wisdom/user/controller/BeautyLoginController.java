/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.user.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.BeautyLoginResultDTO;
import com.wisdom.common.dto.system.LoginDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.WeixinUtil;
import com.wisdom.user.interceptor.LoginRequired;
import com.wisdom.user.service.BeautyLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping(value = "")
public class BeautyLoginController {

    @Autowired
    private BeautyLoginService beautyLoginService;

    @RequestMapping(value = "beautyLogin", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<BeautyLoginResultDTO> beautyLogin(@RequestBody LoginDTO loginDTO,
                                                      HttpServletRequest request,
                                                      HttpSession session) throws Exception {

        ResponseDTO<BeautyLoginResultDTO> result = new ResponseDTO<>();

        //获取用户的基本信息 todo 需要完成注释部分的代码
        String openid = WeixinUtil.getBeautyOpenId(session,request);

        BeautyLoginResultDTO loginResult = beautyLoginService.beautyLogin(loginDTO, request.getRemoteAddr().toString(),openid);

        if (loginResult.getResult().equals(StatusConstant.VALIDATECODE_ERROR))
        {
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("验证码输入不正确");
            return result;
        }
        else if(loginResult.getResult().equals("phoneNotUse"))
        {
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("手机号不可用");
            return result;
        }
        else if(loginResult.getResult().equals("phoneIsError"))
        {
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("手机号不可用手机号错误");
            return result;
        }
        else
        {
            result.setResult(StatusConstant.SUCCESS);
            result.setErrorInfo("调用成功");
            result.setResponseData(loginResult);
            return result;
        }
    }

    @RequestMapping(value = "beautyLoginOut", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<String> userLoginOut(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        BeautyLoginResultDTO beautyLoginResultDTO = new BeautyLoginResultDTO();

        String beautyUserLoginToken = request.getHeader("beautyUserLoginToken");
        beautyLoginResultDTO.setBeautyUserLoginToken(beautyUserLoginToken);

        String beautyBossLoginToken = request.getHeader("beautyBossLoginToken");
        beautyLoginResultDTO.setBeautyBossLoginToken(beautyBossLoginToken);

        String beautyClerkLoginToken = request.getHeader("beautyClerkLoginToken");
        beautyLoginResultDTO.setBeautyClerkLoginToken(beautyClerkLoginToken);

        String status = beautyLoginService.beautyLoginOut(beautyLoginResultDTO,request,response,session);
        ResponseDTO<String> result = new ResponseDTO<>();
        result.setResult(StatusConstant.SUCCESS);
        result.setErrorInfo(status.equals(StatusConstant.LOGIN_OUT) ? "退出登录" : "保持在线");
        return result;
    }
}

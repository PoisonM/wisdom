/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.user.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.LoginDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.SMSUtil;
import com.wisdom.common.util.StringUtils;
import com.wisdom.common.util.WeixinUtil;
import com.wisdom.user.interceptor.LoginRequired;
import com.wisdom.user.service.BeautyLoginService;
import com.wisdom.user.service.BeautyUserInfoService;
import com.wisdom.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping(value = "")
public class BeautyLoginController {

    @Autowired
    private BeautyLoginService beautyLoginService;

    @RequestMapping(value = "beautyUserLogin", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<String> beautyUserLogin(@RequestBody LoginDTO loginDTO,
                                  HttpServletRequest request,
                                  HttpSession session) throws Exception {
        ResponseDTO<String> result = new ResponseDTO<>();

        //获取用户的基本信息 todo 需要完成注释部分的代码
        String openid = WeixinUtil.getBeautyOpenId(session,request);
        if(openid==null||openid.equals(""))
        {
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("没有openid，请在微信公众号中注册登录");
            return result;
        }

        String loginResult = beautyLoginService.beautyUserLogin(loginDTO, request.getRemoteAddr().toString(),openid);

        if (loginResult.equals(StatusConstant.VALIDATECODE_ERROR))
        {
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("验证码输入不正确");
            return result;
        }
        else if (loginResult.equals(StatusConstant.WEIXIN_ATTENTION_ERROR))
        {
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("请在关注公众号后，再绑定登录");
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

    @RequestMapping(value = "bossLogin", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<String> bossLogin(@RequestBody LoginDTO loginDTO,
                                  HttpServletRequest request,
                                  HttpSession session) throws Exception {
        ResponseDTO<String> result = new ResponseDTO<>();
        String loginResult = "";

        String openid = WeixinUtil.getBeautyOpenId(session,request);
        if((openid==null||openid.equals(""))&&loginDTO.getSource().equals("mobile"))
        {
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("没有openid，请在微信公众号中注册登录");
            return result;
        }
        else if(!(openid==null||openid.equals(""))&&loginDTO.getSource().equals("mobile"))
        {
            loginResult = beautyLoginService.bossMobileLogin(loginDTO, request.getRemoteAddr().toString(),openid);

        }
        else if((openid==null||openid.equals(""))&&!loginDTO.getSource().equals("mobile"))
        {
            loginResult = beautyLoginService.bossWebLogin(loginDTO, request.getRemoteAddr().toString());
        }

        if (loginResult.equals(StatusConstant.VALIDATECODE_ERROR))
        {
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("验证码输入不正确");
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

    @RequestMapping(value = "clerkLogin", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<String> clerkLogin(@RequestBody LoginDTO loginDTO,
                                  HttpServletRequest request,
                                  HttpSession session) throws Exception {
        ResponseDTO<String> result = new ResponseDTO<>();
        String loginResult = "";

        String openid = WeixinUtil.getBeautyOpenId(session,request);
        if((openid==null||openid.equals(""))&&loginDTO.getSource().equals("mobile"))
        {
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("没有openid，请在微信公众号中注册登录");
            return result;
        }
        else if(!(openid==null||openid.equals(""))&&loginDTO.getSource().equals("mobile"))
        {
            loginResult = beautyLoginService.ClerkMobileLogin(loginDTO, request.getRemoteAddr().toString(),openid);

        }
        else if((openid==null||openid.equals(""))&&!loginDTO.getSource().equals("mobile"))
        {
            loginResult = beautyLoginService.ClerkWebLogin(loginDTO, request.getRemoteAddr().toString());
        }

        if (loginResult.equals(StatusConstant.VALIDATECODE_ERROR))
        {
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("验证码输入不正确");
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

}

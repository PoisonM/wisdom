/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.user.controller;

import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.*;
import com.wisdom.common.util.SMSUtil;
import com.wisdom.common.util.WeixinUtil;
import com.wisdom.user.interceptor.LoginRequired;
import com.wisdom.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping(value = "")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "userLogin", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<String> userLogin(@RequestBody LoginDTO loginDTO,
                                  HttpServletRequest request,
                                  HttpSession session) throws Exception {
        ResponseDTO<String> result = new ResponseDTO<>();

        //获取用户的基本信息 todo 需要完成注释部分的代码
        String openid = WeixinUtil.getUserOpenId(session,request);
        if(openid==null||openid.equals(""))
        {
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("没有openid，请在微信公众号中注册登录");
            return result;
        }

        String loginResult = loginService.userLogin(loginDTO.getUserPhone(), loginDTO.getCode(), request.getRemoteAddr().toString(),openid);

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

    @RequestMapping(value = "userLoginOut", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<String> userLoginOut(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String logintoken = request.getHeader("logintoken");
        if(logintoken==null||logintoken.equals("")){
            logintoken = request.getSession().getAttribute("token").toString();
        }
        String status = loginService.userLoginOut(logintoken,request,response,session);
        ResponseDTO<String> result = new ResponseDTO<>();
        result.setResult(StatusConstant.SUCCESS);
        result.setErrorInfo(status.equals(StatusConstant.LOGIN_OUT) ? "退出登录" : "保持在线");
        return result;
    }

    @RequestMapping(value = "bossLogin", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<String> bossLogin(@RequestBody LoginDTO loginDTO,
                                  HttpServletRequest request,
                                  HttpSession session) throws Exception {
        ResponseDTO<String> result = new ResponseDTO<>();
        String loginResult = "";

        String openid = WeixinUtil.getBossOpenId(session,request);
        if((openid==null||openid.equals(""))&&loginDTO.getSource().equals("mobile"))
        {
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("没有openid，请在微信公众号中注册登录");
            return result;
        }
        else if(!(openid==null||openid.equals(""))&&loginDTO.getSource().equals("mobile"))
        {
            loginResult = loginService.bossMobileLogin(loginDTO, request.getRemoteAddr().toString(),openid);

        }
        else if((openid==null||openid.equals(""))&&!loginDTO.getSource().equals("mobile"))
        {
            loginResult = loginService.bossWebLogin(loginDTO, request.getRemoteAddr().toString());
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

        String openid = WeixinUtil.getBossOpenId(session,request);
        if((openid==null||openid.equals(""))&&loginDTO.getSource().equals("mobile"))
        {
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("没有openid，请在微信公众号中注册登录");
            return result;
        }

        String loginResult = loginService.clerkLogin(loginDTO, request.getRemoteAddr().toString(),openid);

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



    @RequestMapping(value = "managerLogin", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<String> managerLogin(@RequestBody LoginDTO loginDTO,
                                     HttpServletRequest request,
                                     HttpSession session) throws Exception {
        ResponseDTO<String> result = new ResponseDTO<>();

        String loginResult = loginService.managerLogin(loginDTO.getUserPhone(),loginDTO.getCode());

        if (loginResult.equals(StatusConstant.FAILURE))
        {
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("用户登录失败");
            return result;
        }
        else
        {
            result.setResult(StatusConstant.SUCCESS);
            result.setErrorInfo("用户登录成功");
            result.setResponseData(loginResult);
            return result;
        }
    }

    /**
     * 发送验证码
     */
    @RequestMapping(value = "getUserValidateCode", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO getUserValidateCode(@RequestBody UserInfoDTO userInfoDto) {
        ResponseDTO result = new ResponseDTO<>();
        try
        {
            SMSUtil.sendUserValidateCode(userInfoDto.getMobile());
            result.setResult(StatusConstant.SUCCESS);
        }
        catch (Exception e)
        {
            result.setResult(StatusConstant.FAILURE);
        }
        return result;
    }

}

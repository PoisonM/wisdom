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
import com.wisdom.user.service.BusinessLoginService;
import com.wisdom.user.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping(value = "")
public class BusinessLoginController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BusinessLoginService businessLoginService;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "userLogin", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<String> userLogin(@RequestBody LoginDTO loginDTO,
                                  HttpServletRequest request,
                                  HttpSession session) throws Exception {
        long startTime = System.currentTimeMillis();
        logger.info("userLogin,方法开始==={}", startTime);
        ResponseDTO<String> result = new ResponseDTO<>();

        //获取用户的基本信息 todo 需要完成注释部分的代码
        String openid = WeixinUtil.getUserOpenId(session, request);
        logger.info("userLogin,openId==={}", openid);
        if (!StringUtils.isNotNull(openid)) {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setMobile(loginDTO.getUserPhone());
            List<UserInfoDTO> userInfoList = userInfoService.getUserInfo(userInfoDTO);
            if (userInfoList != null && userInfoList.size() > 0) {
                if (userInfoList.size() == 1) {
                    openid = userInfoList.get(0).getUserOpenid();
                } else {
                    logger.info("userLogin,方法返回结果该手机号绑定多个微信号，请联系客服人员，解绑多余微信号");
                    result.setResult(StatusConstant.FAILURE);
                    result.setErrorInfo("该手机号绑定多个微信号，请联系客服人员，解绑多余微信号。");
                    return result;
                }
            }
        }
        if (openid == null || openid.equals("")) {
            logger.info("userLogin,方法返回结果,没有openid，请在微信公众号中注册登录");
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("没有openid，请在微信公众号中注册登录");
            return result;
        }
        String loginResult = "";
        try {
            loginResult = businessLoginService.businessUserLogin(loginDTO, request.getRemoteAddr().toString(), openid);
            logger.info("userLogin,loginResult={}", loginResult);
        } catch (Exception e) {
            logger.info("userLogin,异常,异常信息为{}" + e.getMessage(), e);
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("您输入的手机号与该微信登录平台手机号不符！");
            return result;
        }
        if (loginResult.equals("phoneNotUse")) {
            logger.info("userLogin,方法返回结果,您输入的手机号以被其他用户的微信绑定过美享平台！");
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("您输入的手机号以被其他用户的微信绑定过美享平台！");
            return result;
        }
        if (loginResult.equals("phoneIsError")) {
            logger.info("userLogin,方法返回结果,您输入的手机号与该微信登录平台手机号不符！");
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("您输入的手机号与该微信登录平台手机号不符！");
            return result;
        }

        if (loginResult.equals(StatusConstant.VALIDATECODE_ERROR)) {
            logger.info("userLogin,方法返回结果,验证码输入不正确！");
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("验证码输入不正确");
            return result;
        } else if (loginResult.equals(StatusConstant.WEIXIN_ATTENTION_ERROR)) {
            logger.info("userLogin,方法返回结果,请在关注公众号后，再绑定登录！");
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("请在关注公众号后，再绑定登录");
            return result;
        } else {
            logger.info("userLogin,方法返回结果,调用成功");
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
        long startTime = System.currentTimeMillis();
        logger.info("userLoginOut,方法开始==={}", startTime);
        String logintoken = request.getHeader("logintoken");
        if (logintoken == null || logintoken.equals("")) {
            logintoken = request.getSession().getAttribute("token").toString();
        }
        String status = businessLoginService.businessUserLoginOut(logintoken, request, response, session);
        ResponseDTO<String> result = new ResponseDTO<>();
        result.setResult(StatusConstant.SUCCESS);
        result.setErrorInfo(status.equals(StatusConstant.LOGIN_OUT) ? "退出登录" : "保持在线");
        logger.info("userLoginOut方法" + "耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return result;
    }

    @RequestMapping(value = "managerLogin", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<String> managerLogin(@RequestBody LoginDTO loginDTO,
                                     HttpServletRequest request,
                                     HttpSession session) throws Exception {
        long startTime = System.currentTimeMillis();
        logger.info("managerLogin,方法开始==={}", startTime);
        ResponseDTO<String> result = new ResponseDTO<>();

        String loginResult = businessLoginService.managerLogin(loginDTO.getUserPhone(), loginDTO.getCode());

        if (loginResult.equals(StatusConstant.FAILURE)) {
            logger.info("managerLogin方法返回结果,用户登录失败,耗时{}毫秒", (System.currentTimeMillis() - startTime));
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("用户登录失败");
            return result;
        } else {
            logger.info("managerLogin方法返回结果,用户登录成功,耗时{}毫秒", (System.currentTimeMillis() - startTime));
            result.setResult(StatusConstant.SUCCESS);
            result.setErrorInfo("用户登录成功");
            result.setResponseData(loginResult);
            return result;
        }
    }

    /**
     * 跨境平台用户登录
     */
    @RequestMapping(value = "crossBorderLogin", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<String> crossBorderLogin(@RequestBody LoginDTO loginDTO, HttpServletRequest request,
                                         HttpServletResponse response, HttpSession session) throws Exception {
        long startTime = System.currentTimeMillis();
        logger.info("crossBorderLogin,方法开始==={}", startTime);
        ResponseDTO<String> result = new ResponseDTO<>();
        String loginResult = businessLoginService.crossBorderLogin(loginDTO, request.getRemoteAddr().toString());
        if (loginResult.equals(StatusConstant.VALIDATECODE_ERROR)) {
            logger.info("crossBorderLogin方法返回结果,用户登录失败,耗时{}毫秒", (System.currentTimeMillis() - startTime));
            result.setResult(StatusConstant.FAILURE);
            result.setErrorInfo("用户登录失败");
            return result;
        } else {
            logger.info("crossBorderLogin方法返回结果,用户登录成功,耗时{}毫秒", (System.currentTimeMillis() - startTime));
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
    ResponseDTO getUserValidateCode(@RequestParam String mobile) {
        long startTime = System.currentTimeMillis();
        logger.info("发送验证码参数mobile={},方法开始==={}", mobile, startTime);
        ResponseDTO result = new ResponseDTO<>();
        try {
            SMSUtil.sendUserValidateCode(mobile);
            result.setResult(StatusConstant.SUCCESS);
        } catch (Exception e) {
            logger.info("发送验证码参数异常,异常信息为==={}" + e.getMessage(), e);
            result.setResult(StatusConstant.FAILURE);
        }
        logger.info("发送验证码方法,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return result;
    }

}

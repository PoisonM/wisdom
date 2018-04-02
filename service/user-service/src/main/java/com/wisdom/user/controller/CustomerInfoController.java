/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.user.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import com.wisdom.common.dto.system.UserInfoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.WeixinUtil;
import com.wisdom.user.interceptor.LoginRequired;
import com.wisdom.user.service.CustomerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "customer")
public class CustomerInfoController {

    @Autowired
    private CustomerInfoService customerInfoService;

    /**
     * 获取用户头像和手机号
     */
    @RequestMapping(value = "getUserInfo", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<UserInfoDTO> getUserInfo() {
        ResponseDTO<UserInfoDTO> responseDTO = new ResponseDTO<>();
        UserInfoDTO userInfoDTO = customerInfoService.getUserInfoFromRedis();
        responseDTO.setResponseData(userInfoDTO);
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

    /**
     * 根据用户的OpenId获取用户的信息
     */
    @RequestMapping(value = "getUserInfoByOpenId", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<UserInfoDTO> getUserInfoByOpenId(HttpSession session, HttpServletRequest request) {
        ResponseDTO<UserInfoDTO> responseDTO = new ResponseDTO<>();
        String openId = WeixinUtil.getCustomerOpenId(session,request);
        List<UserInfoDTO> userInfoDTOList = new ArrayList<>();
        if(openId!=null)
        {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setUserOpenid(openId);
            userInfoDTOList = customerInfoService.getUserInfo(userInfoDTO);
        }

        if(userInfoDTOList.size()>0)
        {
            responseDTO.setResponseData(userInfoDTOList.get(0));
            responseDTO.setResult(StatusConstant.SUCCESS);
        }
        else
        {
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        return responseDTO;
    }

    /**
     * 根据用户的OpenId获取用户的信息
     */
    @RequestMapping(value = "getUserOpenIdFromSession", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<String> getUserOpenIdFromSession(HttpSession session, HttpServletRequest request) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        String openId = WeixinUtil.getCustomerOpenId(session,request);
        if(openId!=null)
        {
            responseDTO.setResponseData(openId);
            responseDTO.setResult(StatusConstant.SUCCESS);
        }
        else
        {
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        return responseDTO;
    }

    /**
     * 根据id查询等级详情
     * @return
     */
    @RequestMapping(value = "queryUserBusinessById", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<PageParamDTO<List<UserBusinessTypeDTO>>>  queryUserBusinessById(@RequestParam String sysUserId) {
        ResponseDTO<PageParamDTO<List<UserBusinessTypeDTO>>> responseDTO = new ResponseDTO<>();
        PageParamDTO<List<UserBusinessTypeDTO>> userBusinessTypeDTOs = customerInfoService.queryUserBusinessById(sysUserId);
        responseDTO.setResponseData(userBusinessTypeDTOs);
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

    /**
     * 根据id查询下级代理
     * @return
     */
    @RequestMapping(value = "queryNextUserById", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<PageParamDTO<List<UserInfoDTO>>> queryNextUserById(@RequestParam String sysUserId) {
        ResponseDTO<PageParamDTO<List<UserInfoDTO>>> responseDTO = new ResponseDTO<>();
        PageParamDTO<List<UserInfoDTO>> page = new  PageParamDTO<>();
        List<UserInfoDTO> userInfoDTOs = customerInfoService.queryNextUserById(sysUserId);
        page.setResponseData(userInfoDTOs);
        responseDTO.setResponseData(page);
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

    /**
     * 根据id查询上级代理
     * @return
     */
    @RequestMapping(value = "queryParentUserById", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<List<UserInfoDTO>> queryParentUserById(@RequestParam String parentUserId) {
        ResponseDTO<List<UserInfoDTO>> responseDTO = new ResponseDTO<>();
        if("" != parentUserId && parentUserId != null ){
            List<UserInfoDTO> userInfoDTOs = customerInfoService.queryParentUserById(parentUserId);
            responseDTO.setResponseData(userInfoDTOs);
            responseDTO.setResult(StatusConstant.SUCCESS);
        }else {
            responseDTO.setResult("没有上级");
        }
        return responseDTO;
    }

    /**
     * 根据条件查询人员List
     * @return
     */
    @RequestMapping(value = "queryUserInfoDTOByParameters", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<PageParamDTO<List<UserInfoDTO>>>  queryUserInfoDTOByParameters(@RequestBody PageParamVoDTO<UserInfoDTO> pageParamVoDTO) {
        ResponseDTO<PageParamDTO<List<UserInfoDTO>>> responseDTO = new ResponseDTO<>();
        String startDate = "1990-01-01";//设定起始时间
        pageParamVoDTO.setStartTime("".equals(pageParamVoDTO.getStartTime()) ? startDate : pageParamVoDTO.getStartTime());
        pageParamVoDTO.setEndTime(CommonUtils.getEndDate(pageParamVoDTO.getEndTime()));
        PageParamDTO<List<UserInfoDTO>> page = customerInfoService.queryUserInfoDTOByParameters(pageParamVoDTO);
        responseDTO.setResponseData(page);
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }
}

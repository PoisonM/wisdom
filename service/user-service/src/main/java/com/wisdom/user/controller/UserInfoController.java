/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.user.controller;

import com.wisdom.common.constant.RealNameResultEnum;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.specialShop.SpecialShopBusinessOrderDTO;
import com.wisdom.common.dto.specialShop.SpecialShopInfoDTO;
import com.wisdom.common.dto.system.ExportUserInfoExcelDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import com.wisdom.common.dto.user.RealNameInfoDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.StringUtils;
import com.wisdom.common.util.WeixinUtil;
import com.wisdom.common.util.excel.ExportExcel;
import com.wisdom.user.client.BusinessServiceClient;
import com.wisdom.user.interceptor.LoginRequired;
import com.wisdom.user.service.RealNameAuthService;
import com.wisdom.user.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(value = "customer")
public class UserInfoController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RealNameAuthService realNameAuthService;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 获取用户头像和手机号
     */
    @RequestMapping(value = "getUserInfo", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<UserInfoDTO> getUserInfo() {
        long startTime = System.currentTimeMillis();
        logger.info("获取用户头像和手机号,方法开始==={}" ,startTime);
        ResponseDTO<UserInfoDTO> responseDTO = new ResponseDTO<>();
        UserInfoDTO userInfoDTO = userInfoService.getUserInfoFromRedis();
        responseDTO.setResponseData(userInfoDTO);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info( "获取用户头像和手机号,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    @RequestMapping(value = "getSpecialBossCondition", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<SpecialShopInfoDTO> getSpecialBossCondition(HttpSession session, HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        logger.info("getSpecialBossCondition,方法开始==={}" ,startTime);
        ResponseDTO<SpecialShopInfoDTO> responseDTO = new ResponseDTO<>();
        SpecialShopInfoDTO specialShopInfoDTO = new SpecialShopInfoDTO();
        String openId = WeixinUtil.getUserOpenId(session,request);
        logger.info("getSpecialBossCondition,方法openId={}" ,openId);
        if(StringUtils.isNotNull(openId))
        {
            responseDTO.setResponseData(specialShopInfoDTO);

            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setUserOpenid(openId);
            List<UserInfoDTO> userInfoDTOList = userInfoService.getUserInfo(userInfoDTO);
            if(userInfoDTOList.size()>0&& StringUtils.isNotNull(userInfoDTOList.get(0).getMobile()))
            {
                Query query = new Query(Criteria.where("shopBossMobile").is(userInfoDTOList.get(0).getMobile()));
                specialShopInfoDTO = mongoTemplate.findOne(query,SpecialShopInfoDTO.class,"specialShopInfo");
                responseDTO.setResponseData(specialShopInfoDTO);
                responseDTO.setResult(StatusConstant.SUCCESS);
            }
            else
            {
                responseDTO.setResult(StatusConstant.FAILURE);
            }
        }
        else
        {
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        logger.info( "getSpecialBossCondition,耗时{}毫秒", (System.currentTimeMillis() - startTime));
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
        String openId = WeixinUtil.getUserOpenId(session,request);
        List<UserInfoDTO> userInfoDTOList = new ArrayList<>();
        if(openId!=null)
        {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setUserOpenid(openId);
            userInfoDTOList = userInfoService.getUserInfo(userInfoDTO);
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
        String openId = WeixinUtil.getUserOpenId(session,request);
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
        PageParamDTO<List<UserBusinessTypeDTO>> userBusinessTypeDTOs = userInfoService.queryUserBusinessById(sysUserId);
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
        List<UserInfoDTO> userInfoDTOs = userInfoService.queryNextUserById(sysUserId);
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
            List<UserInfoDTO> userInfoDTOs = userInfoService.queryParentUserById(parentUserId);
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
        PageParamDTO<List<UserInfoDTO>> page = userInfoService.queryUserInfoDTOByParameters(pageParamVoDTO);

        if ("Y".equals(pageParamVoDTO.getIsExportExcel())) {
            List<ExportUserInfoExcelDTO> exportUserInfoExcelDTOS = new ArrayList<>();
            for (UserInfoDTO userInfoDTO : page.getResponseData()) {
                ExportUserInfoExcelDTO exportUserInfoExcelDTO = new ExportUserInfoExcelDTO();
                exportUserInfoExcelDTO.setNickName(userInfoDTO.getNickname());
                exportUserInfoExcelDTO.setMobile(userInfoDTO.getMobile());
                exportUserInfoExcelDTO.setUserType(userInfoDTO.getUserType());
                exportUserInfoExcelDTO.setCreateDate(DateUtils.formatDate(userInfoDTO.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
                exportUserInfoExcelDTO.setLivingPeriod(userInfoDTO.getLivingPeriod()+"");
                exportUserInfoExcelDTOS.add(exportUserInfoExcelDTO);
            }
            try {
                String[] orderHeaders = {"用户昵称","用户账号", "等级","时间", "时效"};
                ExportExcel<ExportUserInfoExcelDTO> ex = new ExportExcel<>();
                ByteArrayInputStream in = ex.getWorkbookIn("佣金奖励EXCEL文档", orderHeaders, exportUserInfoExcelDTOS);
                String url = CommonUtils.orderExcelToOSS(in);
                if ("".equals(url) && url == null) {
                    logger.info("佣金奖励Excel 获取OSSUrl为空");
                }
                logger.info("佣金奖励导出Url=={}", url);
                responseDTO.setResult(url);
                responseDTO.setErrorInfo(StatusConstant.SUCCESS);
            } catch (Exception e) {
                logger.error("佣金即时导表New异常，异常信息为，{}" + e.getMessage(), e);
                e.printStackTrace();
                responseDTO.setErrorInfo(StatusConstant.FAILURE);
            }
        }
        responseDTO.setResponseData(page);
        responseDTO.setErrorInfo(StatusConstant.SUCCESS);
        return responseDTO;
    }

    /**
     * 用户实名认证接口
     *
     * @param cardNo
     * @param name
     * @return
     */
    @RequestMapping(value = "/queryRealNameAuthentication", method = RequestMethod.GET)
    @LoginRequired
    @ResponseBody
    RealNameInfoDTO queryRealNameAuthentication(@RequestParam(value = "cardNo") String cardNo,
                                                @RequestParam(value = "name") String name,
                                                @RequestParam(value = "specialShopId") String specialShopId,
                                                @RequestParam("orderIds[]") List<String> orderIds) {

        RealNameInfoDTO realNameInfoDTO = realNameAuthService.getRealNameInfoDTO(cardNo, name);

        if (RealNameResultEnum.MATCHING.getCode().equals(realNameInfoDTO.getCode()))
        {
            String orderId = orderIds.get(0);
            UserInfoDTO userInfoDTO = userInfoService.getUserInfoFromRedis();

            SpecialShopBusinessOrderDTO specialShopBusinessOrderDTO = new SpecialShopBusinessOrderDTO();
            specialShopBusinessOrderDTO.setOrderId(orderId);
            specialShopBusinessOrderDTO.setUserId(userInfoDTO.getId());
            specialShopBusinessOrderDTO.setUserIdentifyNumber(cardNo);
            specialShopBusinessOrderDTO.setUserName(name);
            specialShopBusinessOrderDTO.setCreateDate(new Date());
            specialShopBusinessOrderDTO.setShopId(specialShopId);
            specialShopBusinessOrderDTO.setUserPhone(userInfoDTO.getMobile());
            mongoTemplate.insert(specialShopBusinessOrderDTO, "specialShopBusinessOrder");
        }

        return realNameAuthService.getRealNameInfoDTO(cardNo, name);
    }
}
